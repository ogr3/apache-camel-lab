package se.cag.camel;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.camel.Message;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
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
    RouteDefinition routeDefinition = context.getRouteDefinition("inbox");
    MockBean mockBean = new MockBean();
    routeDefinition.adviceWith(context, new AdviceWithRouteBuilder() {
      @Override
      public void configure() throws Exception {
        weaveAddLast().bean(mockBean, "process");
      }
    });
    context.start();
//        assertTrue(notifyBuilder.matchesMockWaitTime());
    Thread.sleep(2000);
    template.sendBodyAndHeader("direct:message", "fluff", "test", "false");

//    Fixa till testen så den inte failar
    assertTrue("fluff".equals(mockBean.body));
//    assertMockEndpointsSatisfied();
    template.sendBodyAndHeader("direct:message", "floff", "test", "true");
    assertFalse("floff".equals(mockBean.body));
  }

  public class MockBean {

    String body;

    @Handler
    public void process(@Body Message message) throws Exception {
      body = message.getBody(String.class);
    }
  }

}
