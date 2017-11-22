package se.cag.routes;

import org.apache.camel.builder.RouteBuilder;

public class FileMoverRouteBuilder extends RouteBuilder {
// EIP: Transform
    @Override
    public void configure() throws Exception {
        from("file://target/inbox")
//            lägg in processorn UppercaseProcessor
            .to("file://target/outbox");
    }
}
