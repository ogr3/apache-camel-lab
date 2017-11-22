package se.cag.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.model.dataformat.JsonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

public class OrderTransformProcessor
    implements Processor {
  public void process(Exchange exchange)
      throws Exception {
    ObjectMapper mapper = new ObjectMapper();
//    Konvertera inkommande JSON-sträng från exchange till ett OpenNotifyIssPositionBean-objekt mha ObjectMapern
    OpenNotifyIssPositionBean openNotifyIssPositionBean = mapper.readValue(exchange.getIn().getBody(String.class), OpenNotifyIssPositionBean.class);
//    Sätt det nya objektet som body i exchange
    exchange.getIn().setBody(openNotifyIssPositionBean);
  }
}
