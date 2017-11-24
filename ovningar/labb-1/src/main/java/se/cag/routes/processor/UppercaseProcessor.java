package se.cag.routes.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class UppercaseProcessor implements Processor{

    public void process(Exchange exchange) throws Exception {
        String body = exchange.getIn().getBody(String.class);

//        Hämta ut filens innehåll body-delen av exchange

//        Gör om innehållet till stora bokstäver
        String upperCase = body.toUpperCase();
//        Ersätt innehållet i body med det nya
        exchange.getOut().setBody(upperCase);
        exchange.getOut().setHeaders(exchange.getIn().getHeaders());
    }
}
