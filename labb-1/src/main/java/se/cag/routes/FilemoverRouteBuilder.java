package se.cag.routes;

import org.apache.camel.builder.RouteBuilder;
import se.cag.routes.processor.UppercaseProcessor;

public class FilemoverRouteBuilder extends RouteBuilder {
//
    @Override
    public void configure() throws Exception {
        from("file://target/inbox")
//            lägg in processorn UppercaseProcessor
//            .process(new UppercaseProcessor())
            .to("file://target/outbox");
    }
}
