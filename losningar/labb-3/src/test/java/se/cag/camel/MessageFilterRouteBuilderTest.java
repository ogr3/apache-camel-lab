package se.cag.camel;

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
    MockEndpoint mock = getMockEndpoint("mock:jms:order");
    mock.expectedBodiesReceived("body");
    NotifyBuilder notifyBuilder = new NotifyBuilder(context).whenDone(1).create();
    RouteDefinition routeDefinition = context.getRouteDefinition("inbox");

    routeDefinition.adviceWith(context, new AdviceWithRouteBuilder() {
      @Override
      public void configure() throws Exception {
        replaceFromWith("direct:hitme");
      }
  });

    template.sendBodyAndHeader("direct:hitme", "body", "test", "false");
    assertTrue(notifyBuilder.matches());

//    mock.assertIsSatisfied();

  }

}
