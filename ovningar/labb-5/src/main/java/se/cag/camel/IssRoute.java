package se.cag.camel;

import org.apache.camel.builder.RouteBuilder;
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

// Fyll på med routes :-)
        from("timer:foo?period=15000");

    }

}



