package se.cag.camel;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;

import javax.jms.ConnectionFactory;

public class RestServiceRouteBuilder extends RouteBuilder {

    private WaterContainerBean waterContainerBean = new WaterContainerBean();

    @Override
    public void configure() throws Exception {
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("vm://localhost");
        CamelContext context = getContext();
        context.addComponent("jms",
                JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        context.start();
// Här har vi ett objekt, WaterContainerBean, som håller reda på en vatten-nivå.
// Vi ska skapa ett rest-api me en route för varje operation som motsvarar de operationer som den har.
// Observera att förändringen av nivån sker inkrementellt, dvs, ett rest-anrop ökar/minskar nivån med 1.
// Rest-anropen innehåller alltså inga parametrar gällande nivån.
// En liten twist är att vi går via jms-köer för de operationer som uppdaterar vatten-nivån

        from("restlet:http://0.0.0.0:18080/add-water?restletMethods=GET")
                .routeId("add-water")
                .transform().simple("1")
// Skapa ett värde (= 1) som ska läggas på kön jms:queue:addWater
                .log("add-water called")
// Lägg anropet till kön här
                .to("jms:queue:addWater");

// Upprepa mönstret ovan, men för remove-water-anropet
        from("restlet:http://0.0.0.0:18080/remove-water?restletMethods=GET")
                .routeId("remove-water")
                .transform().simple("1")
// Skapa ett värde (= 1) som ska läggas på kön jms:queue:addWater
                .log("remove-water called")
// Lägg anropet till kön här
                .to("jms:queue:removeWater");
// Sedan en rest-tjänst, water-level för att få aktuell vatten-nivå.
// Här behövs ingen kö, ni kan hämta nivån direkt från  waterContainerBean
        from("restlet:http://0.0.0.0:18080/water-level?restletMethods=GET")
                .bean(waterContainerBean, "getWaterLevel")
                .log("water-level called");
// bean-endpointen som anropar getWaterLevel läggs här

//  Kompletera kön med anropet till waterContainerBean
        from("jms:queue:addWater")
                .routeId("jmsAddWater")
                .log("From JMS:${body}")
                .bean(waterContainerBean, "addWater").id("bean");

// upprepa för removeWater-kön
        from("jms:queue:removeWater")
                .routeId("jmsRemoveWater")
                .log("From JMS:${body}")
                .bean(waterContainerBean, "removeWater").id("beanRemove");
    }
}
