package se.cag;

import org.apache.camel.Exchange;
import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import se.cag.routes.FileMoverRouteBuilder;

import java.io.File;

public class FileMoverRouteBuilderTest extends CamelTestSupport {

    // Specify the route builder that has the routes we want to test
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new FileMoverRouteBuilder();
    }

    public void setUp() throws Exception {
        deleteDirectory("target/inbox");
        deleteDirectory("target/outbox");
        super.setUp();
    }

    /**
     * Testar att filen target/inbox/hello.txt flyttas till target/outbox/hello.txt
     */
    @Test
    public void testMoveFile() throws Exception {
// Skapar en notifybuilder som lyssnar på meddelanden och triggar när ett meddelande gått igenom routen.
        NotifyBuilder notifyBuilder = new NotifyBuilder(context).whenDone(1).create();
        // ProducerTemplate can send messages to routes
        template.sendBodyAndHeader(
                "file://target/inbox", "Hello World", Exchange.FILE_NAME, "hello.txt");
// Här väntar vi på att notifyBuildern signalerar att ett meddelande gåttigenom. Då kan vi kontrollera resultatet.
        assertTrue(notifyBuilder.matchesMockWaitTime());

        File target = new File("target/outbox/hello.txt");
        assertTrue("File not moved", target.exists());

    }


    /**
     * Testar att innehållet i filen är uppercase
     */
    @Test
    public void testFileContentIsUppercase() throws Exception {

        NotifyBuilder notifyBuilder = new NotifyBuilder(context).whenDone(1).create();
        // ProducerTemplate can send messages to routes
        template.sendBodyAndHeader(
                "file://target/inbox", "Hello World", Exchange.FILE_NAME, "hello.txt");

        assertTrue(notifyBuilder.matchesMockWaitTime());
        File target = new File("target/outbox/hello.txt");
        assertTrue("File not moved", target.exists());

        // CamelContext has nifty utilities
        String content = context.getTypeConverter()
                .convertTo(String.class, target);

        assertEquals("HELLO WORLD", content);
    }

}
