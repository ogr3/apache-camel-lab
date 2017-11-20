package se.cag.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class OrderTransformRouteBuilder extends RouteBuilder {


  @Override
  public void configure() throws Exception {

    from("direct:transformOrder").process(new OrderTransformProcessor());
  }

  public class OrderTransformProcessor
      implements Processor {
    public void process(Exchange exchange)
        throws Exception {
      // do message translation here
    }
  }

}
