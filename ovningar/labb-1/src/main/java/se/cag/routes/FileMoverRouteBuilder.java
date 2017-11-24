package se.cag.routes;

import org.apache.camel.builder.RouteBuilder;
import se.cag.routes.processor.UppercaseProcessor;

public class FileMoverRouteBuilder extends RouteBuilder {
    //
    @Override
    public void configure() throws Exception {
        from("file://target/inbox")
                .process(new UppercaseProcessor())
                .log("Camel body: ${body}")
                .to("file://target/outbox");
    }
}
