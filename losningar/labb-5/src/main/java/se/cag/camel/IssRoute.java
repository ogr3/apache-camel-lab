package se.cag.camel;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;
import se.cag.camel.processors.IssPositionProcessor;
import se.cag.camel.processors.PositionToPlaceProcessor;
import se.cag.camel.processors.WeatherProcessor;

import javax.inject.Inject;

/**
 * Camel as client that calls the hello service using a timer every 2nd seconds
 * and logs the response
 */
@Component
public class IssRoute extends RouteBuilder {

    @Inject
    private IssPositionProcessor issPositionProcessor;

    @Inject
    private PositionToPlaceProcessor positionToPlaceProcessor;

    @Inject
    private WeatherProcessor weatherProcessor ;

    @Override
    public void configure() throws Exception {
        JsonDataFormat jsonDataFormat = new JsonDataFormat();
//        jsonDataFormat.setUseList(true);
        jsonDataFormat.setUnmarshalType(OpenNotifyIssPositionBean.class);
        jsonDataFormat.setLibrary(JsonLibrary.Jackson);

        onException(com.fasterxml.jackson.core.JsonParseException.class).logExhaustedMessageBody(true).log(LoggingLevel.ERROR, "JsonParseException: Inforeuro site is probably down or the Json format has changed. Check the Uri response. Error message: ${exception.message}").handled(true);
        onException(com.fasterxml.jackson.databind.JsonMappingException.class).logExhaustedMessageBody(true).log(LoggingLevel.ERROR, "JsonMappingException: Inforeuro site is probably down or the Json format has changed. Check the Uri response. Error message: ${exception.message}").handled(true);


        from("timer:foo?period=15000")
//                .to("http4://api.open-notify.org/iss-now.json").streamCaching()
                .recipientList(simple("http4://api.open-notify.org/iss-now.json"), "false")
            .log("rest headers: ${headers}")
//                .unmarshal(jsonDataFormat).log("${body}")
//                .process(new IssPositionProcessor())
                .process(issPositionProcessor)
                .log(LoggingLevel.INFO,"${header.latitude}")
                .log(LoggingLevel.INFO,"${header.longitude}")
                .recipientList(simple("https4://maps.googleapis.com/maps/api/geocode/json?latlng=${header.latitude},${header.longitude}&sensor=false"), "false")
//                .process(new PositionToPlaceProcessor())
                .process(positionToPlaceProcessor)
                .log(LoggingLevel.INFO, "${body}")
                .log(LoggingLevel.INFO, "${headers}")
                .recipientList(simple("https4://api.openweathermap.org/data/2.5/weather?lat=${header.latitude}&lon=${header.longitude}&appid=93e711f5c2bb6f3e6dfaffc3f431858c&units=metric"), "false")
                .process(weatherProcessor)
                .log(LoggingLevel.INFO, "${headers}");

    }

}



