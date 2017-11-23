package se.cag.routes;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.camel.Message;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class OrderTransformRouteBuilderTest extends CamelTestSupport {

  protected RouteBuilder createRouteBuilder() {
    return new OrderTransformRouteBuilder();
  }

  @Test
  public void testSendMessage() throws Exception {
    RouteDefinition processorTransformer = context.getRouteDefinition("ProcessorTransformer");
    RouteDefinition beanTransformer = context.getRouteDefinition("BeanTransformer");
    final BodyChecker prosessorChecker = new BodyChecker();

    processorTransformer.adviceWith(context, new AdviceWithRouteBuilder() {
      @Override
      public void configure() throws Exception {
        weaveAddLast().bean(prosessorChecker, "checkBody");
      }
    });

    template.sendBody("direct:transformOrder", null);
    assertTrue(prosessorChecker.bean != null);

    final BodyChecker beanChecker = new BodyChecker();
    beanTransformer.adviceWith(context, new AdviceWithRouteBuilder() {
      @Override
      public void configure() throws Exception {
        weaveAddLast().bean(beanChecker, "checkBody");
      }
    });
    template.sendBody("direct:transformOrderWithBean",null);
    assertTrue(beanChecker.bean != null);

  }

  public class BodyChecker {
    private OpenNotifyIssPositionBean bean;
    @Handler
    public void checkBody(@Body Message message) {
      bean = message.getBody(OpenNotifyIssPositionBean.class);
    }

    public OpenNotifyIssPositionBean getBean() {
      return bean;
    }
  }

}