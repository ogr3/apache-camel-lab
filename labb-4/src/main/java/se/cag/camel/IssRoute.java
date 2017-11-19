package se.cag.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import se.cag.camel.processors.IssPositionProcessor;
import se.cag.camel.processors.PositionToPlaceProcessor;

import javax.inject.Inject;

/**
 * Camel as client that calls the hello service using a timer every 2nd seconds
 * and logs the response
 */
@Component
public class IssRoute extends RouteBuilder {

    @Inject
    private IssPositionProcessor issPositionProcessor;

    @Inject PositionToPlaceProcessor positionToPlaceProcessor;

    @Override
    public void configure() throws Exception {
        from("timer:foo?period=15000")
                .to("http4://api.open-notify.org/iss-now.json")//.log("${body}")
//                .unmarshal().json(JsonLibrary.Jackson, se.cag.camel.beans.OpenNotifyIssPositionBean.class)//.log("${body}")
//                .process(new IssPositionProcessor())
                .process(issPositionProcessor)
                .log("${header.latitude}")
                .log("${header.longitude}")
                .recipientList(simple("https4://maps.googleapis.com/maps/api/geocode/json?latlng=${header.latitude},${header.longitude}&sensor=false"), "false")
//                .process(new PositionToPlaceProcessor())
                .process(positionToPlaceProcessor)
                .log("${body}")
                .log("${headers}");
//                .recipientList(simple("https4://api.darksky.net:443/forecast/6ed6d5d0ad208e35f2a5c0aa913fdb00/${header.latitude},${header.longitude}/"), "false")
//                .log("${body}");
    }
}



