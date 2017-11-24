package se.cag.mydemo;

import org.apache.camel.impl.DefaultCamelContext;

public class FirstDemo {
    public static void main(String[] args) throws Exception {
        DefaultCamelContext context = new DefaultCamelContext();
        context.addRoutes(new CopyFileRoute());

        context.start();

        Thread.sleep(10000L);

        context.stop();
    }

}
