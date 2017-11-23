package se.cag.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class ProcessorRouteBuilderTest extends CamelTestSupport {

  protected RouteBuilder createRouteBuilder() {
    return new ProcessorRouteBuilder();
  }

  public void setUp() throws Exception {
    deleteDirectory("target/inbox");
    deleteDirectory("target/outbox");
    super.setUp();
  }

  @Test
  public void testMoveFile() throws Exception {
    // ProducerTemplate can send messages to routes
    template.sendBodyAndHeader(
        "file://target/inbox", "Hello World", Exchange.FILE_NAME, "hello.txt");
  }
}