package se.cag.camel;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.camel.Message;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class MessageFilterRouteBuilderTest extends CamelTestSupport {

  protected RouteBuilder createRouteBuilder() {
    return new MessageFilterRouteBuilder();
  }

   @Override
   public boolean isUseAdviceWith() {
     return true;
   }

  @Test
  public void testMessageFilter() throws Exception {
    MockEndpoint orderMock = getMockEndpoint("mock:jms:order");
    MockEndpoint testMock = getMockEndpoint("mock:jms:test");
    orderMock.expectedMessageCount(1);
    testMock.expectedMessageCount(0);
    NotifyBuilder notifyBuilder = new NotifyBuilder(context).whenDone(1).create();

    RouteDefinition routeDefinition = context.getRouteDefinition("inbox");

    MockBean mockBean = new MockBean();
    routeDefinition.adviceWith(context, new AdviceWithRouteBuilder() {
      @Override
      public void configure() throws Exception {
        replaceFromWith("direct:hitme");
        weaveById("bean").replace().bean(mockBean);
      }
  });

    template.sendBodyAndHeader("direct:hitme", "fluff", "test", "false");
    assertTrue(notifyBuilder.matches());
    assertTrue("fluff".equals(mockBean.body));
//    assertMockEndpointsSatisfied();
  }

  public class MockBean {

    String body;

    @Handler
    public void process(@Body Message message) throws Exception {
      body = message.getBody(String.class);
    }
  }

}
