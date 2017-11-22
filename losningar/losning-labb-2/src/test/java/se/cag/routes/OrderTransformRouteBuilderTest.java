package se.cag.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrderTransformRouteBuilderTest extends CamelTestSupport {

  protected RouteBuilder createRouteBuilder() {
    return new OrderTransformRouteBuilder();
  }

  @Test
  public void testSendMessage() throws Exception {
    template.setDefaultEndpointUri("direct:transformOrder");
    template.sendBody(null);

  }


}