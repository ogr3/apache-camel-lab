package se.cag.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class OrderTransformProcessor
    implements Processor {
  public void process(Exchange exchange)
      throws Exception {
    System.out.println("exchange.getBody() = " + exchange.getIn().getBody());
  }
}
