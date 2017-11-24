package se.cag.mydemo;

import org.apache.camel.builder.RouteBuilder;

public class CopyFileRoute extends RouteBuilder {
    public void configure() throws Exception {
        from("file://data/inbox/")
                .log("hej ${body}")
                .process(new UpperCaseProcessor())
                .log("hopp ${body}");

    }
}
