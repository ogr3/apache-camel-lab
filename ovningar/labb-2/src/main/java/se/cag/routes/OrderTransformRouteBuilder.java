package se.cag.routes;

import org.apache.camel.builder.RouteBuilder;

public class OrderTransformRouteBuilder extends RouteBuilder {


  @Override
  public void configure() throws Exception {


    from("direct:transformOrder")
//        gör ett rest-anrop mot "http4://api.open-notify.org/iss-now.json"
        .to("http4://api.open-notify.org/iss-now.json")
        .streamCaching()
        .log("rest  body: ${body}, headers: ${headers}")
//
        .process(new OrderTransformProcessor())
        .log("Efter konvertering: ${body}");
  }

}
