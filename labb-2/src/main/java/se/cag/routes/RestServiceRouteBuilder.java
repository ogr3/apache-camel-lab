package se.cag.routes;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

import javax.jms.ConnectionFactory;

public class RestServiceRouteBuilder extends RouteBuilder {

    private WaterContainerBean waterContainerBean = new WaterContainerBean();
    @Override
    public void configure() throws Exception {
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("vm://localhost");
//        CamelContext context = new DefaultCamelContext();
        CamelContext context = getContext();
        context.addComponent("jms",
                JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        context.start();

        from("restlet:http://0.0.0.0:18080/add-water?restletMethods=GET")
                .routeId("add-water")
                .transform().simple("1")
                .log("add-water called")
                .to("jms:queue:addWater");

        from("restlet:http://0.0.0.0:18080/remove-water?restletMethods=GET")
                .transform().simple("1")
                .log("remove-water called")
                .to("jms:queue:removeWater");

        from("restlet:http://0.0.0.0:18080/water-level?restletMethods=GET")
                .log("water-level called")
                .bean(waterContainerBean,"getWaterLevel");

        from("jms:queue:addWater")
                .log("From JMS:${body}")
                .bean(waterContainerBean,"addWater");

        from("jms:queue:removeWater")
                .log("From JMS:${body}")
                .bean(waterContainerBean,"removeWater");

    }
}
