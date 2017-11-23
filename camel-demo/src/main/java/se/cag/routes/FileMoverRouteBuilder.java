package se.cag.routes;

import org.apache.camel.builder.RouteBuilder;

public class FileMoverRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file://target/inbox")
            .to("file://target/outbox");
    }
}
