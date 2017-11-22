package se.cag.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.camel.Message;

import java.io.IOException;

public class OrderTransformBean {
  @Handler
  public void transform(@Body Message message) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
//    Konvertera inkommande JSON-sträng från exchange till ett OpenNotifyIssPositionBean-objekt mha ObjectMapern
    OpenNotifyIssPositionBean openNotifyIssPositionBean = mapper.readValue(message.getBody(String.class), OpenNotifyIssPositionBean.class);
//    Sätt det nya objektet som body i exchange
    message.setBody(openNotifyIssPositionBean);
  }
}
