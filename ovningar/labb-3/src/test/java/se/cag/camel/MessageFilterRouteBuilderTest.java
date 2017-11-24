package se.cag.camel;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Message;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.spi.BrowsableEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import javax.jms.ConnectionFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.Is.is;

public class MessageFilterRouteBuilderTest extends CamelTestSupport {

    protected RouteBuilder createRouteBuilder() {
        return new MessageFilterRouteBuilder();
    }

    @Before
    public void setup() throws IOException {
        File file = new File("activemq-data");
        if (file.exists()) FileUtils.deleteDirectory(file);
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("vm://localhost");
        context.addComponent("jms",
                JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
    }

    @AfterClass
    public static void teardown() throws IOException {
        File file = new File("activemq-data");
        if (file.exists()) {
            FileUtils.deleteDirectory(file);
        }
    }

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Test
    public void testMessageFilter() throws Exception {

        RouteDefinition routeDefinition = context.getRouteDefinition("inbox");
        MockBean mockBean = new MockBean();
        routeDefinition.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                weaveAddLast().bean(mockBean, "process");
            }
        });

        NotifyBuilder notifyBuilder = new NotifyBuilder(context).whenDone(1);
        context.start();
//        assertTrue(notifyBuilder.matchesMockWaitTime());
        Thread.sleep(2000);
        List<Exchange> exchanges = context.getEndpoint("jms:test", BrowsableEndpoint.class).getExchanges();

        template.sendBodyAndHeader("direct:message", "fluff", "test", "false");


        List<Exchange> orderExchanges = context.getEndpoint("jms:test", BrowsableEndpoint.class).getExchanges();
        //assertThat(notifyBuilder.whenDone(1).matchesMockWaitTime(), is(true));
//    Fixa till testen så den inte failar
        assertEquals(1, orderExchanges.size());
        assertEquals(0, exchanges.size());
        //assertTrue("fluff".equals(mockBean.body));

        template.sendBodyAndHeader("direct:message", "floff", "test", "true");
//    assertMockEndpointsSatisfied();
        // assertFalse("floff".equals(mockBean.body));
    }

    public class MockBean {

        String body;

        @Handler
        public void process(@Body Message message) throws Exception {
            body = message.getBody(String.class);
        }
    }

}
