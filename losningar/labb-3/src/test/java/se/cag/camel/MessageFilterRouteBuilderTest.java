package se.cag.camel;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.AssertionClause;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

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
//    MockEndpoint mock = getMockEndpoint("mock:jms:order");
    MockEndpoint mock2 = getMockEndpoint("mock:jms:test");
//    mock.expectedBodiesReceived("fluff");
    mock2.expectedBodiesReceived(new Object());
//    NotifyBuilder notifyBuilder = new NotifyBuilder(context).whenDone(1).create();

    RouteDefinition routeDefinition = context.getRouteDefinition("inbox");

    routeDefinition.adviceWith(context, new AdviceWithRouteBuilder() {
      @Override
      public void configure() throws Exception {
        replaceFromWith("direct:hitme");
      }
  });

    template.sendBodyAndHeader("direct:hitme", "fluff", "test", "false");
//    assertTrue(notifyBuilder.matches());
    assertMockEndpointsSatisfied();
  }

}
