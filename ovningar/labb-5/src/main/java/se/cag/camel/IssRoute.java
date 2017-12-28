package se.cag.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import se.cag.camel.processors.IssPositionProcessor;
import se.cag.camel.processors.PositionToPlaceProcessor;
import se.cag.camel.processors.WeatherProcessor;

import javax.inject.Inject;

/**
 * Camel as client that calls the hello service using a timer every 2nd seconds and logs the
 * response
 */
@Component
public class IssRoute extends RouteBuilder {

  @Inject private IssPositionProcessor issPositionProcessor;

  @Inject private PositionToPlaceProcessor positionToPlaceProcessor;

  @Inject private WeatherProcessor weatherProcessor;

  @Override
  public void configure() throws Exception {

    // Fyll på med routes :-)
    // from("timer:foo?period=15000");
    from("timer:foo?period=15000")
        .to("http4://api.open-notify.org/iss-now.json")
        .streamCaching()
        .process(issPositionProcessor)
        .log("${header.latitude}")
        .recipientList(
            simple(
                "http4://maps.googleapis.com/maps/api/geocode/json?latlng=${header.latitude},${header.longitude}"),
            "false")
        .process(positionToPlaceProcessor)
        .recipientList(
            simple(
                "https4://api.openweathermap.org/data/2.5/weather?lat=${header.latitude}&lon=${header.longitude}&appid=93e711f5c2bb6f3e6dfaffc3f431858c&units=metric"),
            "false")
        .process(weatherProcessor)
        .log("headers: ${headers}");
  }
}
