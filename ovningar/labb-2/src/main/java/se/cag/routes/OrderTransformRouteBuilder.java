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
                .log("rest  body: ${body}, headers: ${headers}")
//  Använd processorn för att transformera inkommande JSON till ett objekt (OpenNotifyIssPositionBean)
                .process(new OrderTransformProcessor())
                .log("Efter konvertering: ${body}");

//    Gör om transformeringen genom att ersätta processorn med en böna

        from("direct:transformOrderWithBean")
                .routeId("BeanTransformer")
//        gör ett rest-anrop mot "http4://api.open-notify.org/iss-now.json"
                .to("http4://api.open-notify.org/iss-now.json")
                .log("rest  body: ${body}, headers: ${headers}")
                .streamCaching()
//  Använd bönan för att transformera inkommande JSON till ett objekt (OpenNotifyIssPositionBean)
                .bean(OrderTransformBean.class, "balle")
                .log("Efter konvertering: ${body}");

    }

}
