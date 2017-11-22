package se.cag.camel;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
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

    from("jms:inbox").id("inbox")
        .choice()
        .when(simple("${header.test} contains 'true'"))
        .to("jms:test")
        .otherwise()
        .to("jms:order").id("order");
//    from("jms:inbox").id("inbox")
//        .filter(header("test").isNotEqualTo("true"))
//        .to("jms:order");
  }
}
