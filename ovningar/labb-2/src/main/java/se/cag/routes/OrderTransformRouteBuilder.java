package se.cag.routes;

import org.apache.camel.builder.RouteBuilder;

public class OrderTransformRouteBuilder extends RouteBuilder {


  @Override
  public void configure() throws Exception {


    from("direct:transformOrder")
//        gör ett rest-anrop mot "http4://api.open-notify.org/iss-now.json"
        .to("http4://api.open-notify.org/iss-now.json")
        .log("rest  body: ${body}, headers: ${headers}")
//  Använd processorn för att transformera inkommande JSON till ett objekt (OpenNotifyIssPositionBean)

        .log("Efter konvertering: ${body}");

//    Gör om transformeringen genom att ersätta processorn med en böna

    from("direct:transformOrderWithBean")
//        gör ett rest-anrop mot "http4://api.open-notify.org/iss-now.json"
        .to("http4://api.open-notify.org/iss-now.json")
        .log("rest  body: ${body}, headers: ${headers}")
//  Använd bönan för att transformera inkommande JSON till ett objekt (OpenNotifyIssPositionBean)

        .log("Efter konvertering: ${body}");

  }

}
