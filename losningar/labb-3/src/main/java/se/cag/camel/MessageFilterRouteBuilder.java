package se.cag.camel;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.Body;
import org.apache.camel.CamelContext;
import org.apache.camel.Handler;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;

import javax.jms.ConnectionFactory;

public class MessageFilterRouteBuilder extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    ConnectionFactory connectionFactory =
        new ActiveMQConnectionFactory("vm://localhost");
    CamelContext context = getContext();
    context.addComponent("jms",
        JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

    context.start();

    from("jms:inbox").routeId("inbox").log("Xbody: ${body}, Xheaders: ${headers}")
        .choice()
        .when(header("test").isEqualTo("true")).log("Till test")
        .to("jms:test")
        .otherwise().log("Till order: ${body}")
        .to("jms:order").bean(new PrintBean(), "printResult").id("bean");
  }

  public static class PrintBean {
    @Handler
    public void printResult(@Body Message message) {
      System.out.println("Xmessage.getBody() = " + message.getBody());
    }
  }
}
