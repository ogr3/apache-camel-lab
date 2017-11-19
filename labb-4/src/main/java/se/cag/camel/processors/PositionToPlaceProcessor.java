package se.cag.camel.processors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Iterator;
import java.util.Map;

public class PositionToPlaceProcessor implements Processor{

    public void process(Exchange exchange) throws Exception {
// Get input from exchange
        String msg = exchange.getIn().getBody(String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(msg);

        Map<String, Object> headers = exchange.getIn().getHeaders();

        if(actualObj.get("status").textValue().equals("OK")) {
            Iterator<JsonNode> message = actualObj.get("results").elements();
            JsonNode issPosition = message.next().get("formatted_address");
            System.out.println("message.textValue() = " + issPosition.textValue());
//            exchange.getOut().setHeader("position", issPosition.textValue());
            headers.put("position", issPosition.textValue());
            exchange.getOut().setBody(issPosition.textValue());
        } else {
//            exchange.getOut().setHeader("position", "iternationellt vatten");
            headers.put("position", "iternationellt vatten");
            exchange.getOut().setBody("iternationellt vatten");

        }
        exchange.getOut().setHeaders(headers);
    }
}
