package se.cag.routes.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class UppercaseProcessor implements Processor{

    public void process(Exchange exchange) throws Exception {
//        Hämta ut filens innehåll body-delen av exchange
        String originalFileContent = (String) exchange.getIn().getBody(String.class);
//        Gör om innehållet till stora bokstäver
        String upperCaseFileContent = originalFileContent.toUpperCase();
//        Ersätt innehållet i body med det nya
        exchange.getIn().setBody(upperCaseFileContent);
    }
}
