package se.cag.mydemo;

import org.apache.camel.Exchange;

public class UpperCaseProcessor implements org.apache.camel.Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getIn().getBody(String.class);
        exchange.getOut().setBody(body.toUpperCase());
    }
}
