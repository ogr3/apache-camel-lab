package se.cag.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.io.File;

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
        NotifyBuilder notifyBuilder = new NotifyBuilder(context).whenDone(1).create();

        // ProducerTemplate can send messages to routes
        template.sendBodyAndHeader(
                "file://target/inbox", "Hello World", Exchange.FILE_NAME, "hello.txt");
        assertTrue(notifyBuilder.matchesMockWaitTime());
        File file = new File("target/outbox/hello.txt");
        assertTrue(file.exists());
        String s = context.getTypeConverter().convertTo(String.class, file);
        assertTrue("Hello World".equals(s));
    }
}