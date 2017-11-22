package se.cag.routes;

import org.apache.camel.builder.RouteBuilder;

public class ProcessorRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("file://target/inbox")
                .process(exchange -> {
                    System.out.println("exchange.getIn().hasHeaders() = " + exchange.getIn().hasHeaders());
                    System.out.println("exchange.getIn().getBody() = " + exchange.getIn().getBody());
                    })
                .to("file://target/outbox");
    }
}
