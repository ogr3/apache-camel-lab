package se.cag.routes;

import org.apache.camel.builder.RouteBuilder;

public class OrderTransformRouteBuilder extends RouteBuilder {


  @Override
  public void configure() throws Exception {

    from("direct:transformOrder")
        .to("http4://api.open-notify.org/iss-now.json").log("rest headers: ${headers}, body: ${body}").process(new OrderTransformProcessor());
  }

}
