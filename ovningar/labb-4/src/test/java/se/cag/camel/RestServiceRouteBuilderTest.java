package se.cag.camel;

import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class RestServiceRouteBuilderTest extends CamelTestSupport {

    protected RouteBuilder createRouteBuilder() {
        return new RestServiceRouteBuilder();
    }

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Test
    public void testAddWater() throws Exception {
        NotifyBuilder notifyBuilder = new NotifyBuilder(context).whenDone(1).create();

        RouteDefinition restDefinition = context.getRouteDefinition("add-water");
        RouteDefinition jmsDefinition = context.getRouteDefinition("jmsAddWater");
        restDefinition.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:hitme");
            }
        });
        WaterContainerBean waterContainerBean = new WaterContainerBean();

        jmsDefinition.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                weaveById("bean").replace().bean(waterContainerBean, "addWater");
            }
        });
        template.sendBody("direct:hitme", "nothing");
        // TODO: Fixa wait
        Thread.sleep(2000);
//        Av okänd anledning fungerar inte camels wait här...
//        assertTrue(notifyBuilder.matchesMockWaitTime());

        assertThat(waterContainerBean.getWater(), is(1));

        restDefinition = context.getRouteDefinition("remove-water");
        jmsDefinition = context.getRouteDefinition("jmsRemoveWater");
        restDefinition.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:hitme2");
            }
        });

        jmsDefinition.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                weaveById("beanRemove").replace().bean(waterContainerBean, "removeWater");
            }
        });
        template.sendBody("direct:hitme2", "nothing");

        assertThat(waterContainerBean.getWater(), is(0));
    }


}