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
//        Meddelanden från inbox-kön ska sorteras baserat på nyckeln "test" i headers.
//        Om test är true, ska meddelandet läggas i kön jms:test, annars läggs den i kön jms:order.
        from("direct:message").routeId("inbox").log("Xbody: ${body}, Xheaders: ${headers}")
        .to("jms:inbox").log("Xbody: ${body}, Xheaders: ${headers}")
//        In med en kontroll av headern här. Det finns flera möjliga alternativ
               .filter(header("test").isEqualTo("true"))
                .log("Till test")
                .to("jms:test")
//        Här ska en else-gren in
                .filter(header("test").isEqualTo(false))
                .to("jms:order")
                .process(exchange -> {
                    System.out.println(exchange.getIn().getBody());
                });
    }

}
