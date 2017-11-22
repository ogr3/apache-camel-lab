package se.cag.routes;

import org.apache.camel.builder.RouteBuilder;

public class OrderTransformRouteBuilder extends RouteBuilder {


  @Override
  public void configure() throws Exception {


    from("direct:transformOrder")
        .routeId("ProcessorTransformer")
//        gör ett rest-anrop mot "http4://api.open-notify.org/iss-now.json"
        .to("http4://api.open-notify.org/iss-now.json")
        .streamCaching()
        .log("Processor: rest  body: ${body}, headers: ${headers}")
//  Använd processorn för att transformera inkommande JSON till ett objekt (OpenNotifyIssPositionBean)
        .process(new OrderTransformProcessor())
        .log("Efter konvertering med Processor: ${body}");

//    Gör om transformeringen genom att ersätta processorn med en böna

    from("direct:transformOrderWithBean")
        .routeId("BeanTransformer")
//        gör ett rest-anrop mot "http4://api.open-notify.org/iss-now.json"
        .to("http4://api.open-notify.org/iss-now.json")
        .streamCaching()
        .log("Bean: rest  body: ${body}, headers: ${headers}")
//  Använd processorn för att transformera inkommande JSON till ett objekt (OpenNotifyIssPositionBean)
        .bean(new OrderTransformBean(), "transform")
        .log("Efter konvertering med Bean: ${body}");

  }

}
