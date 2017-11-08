package se.cag.routes;

import org.apache.camel.main.Main;

/**
 * Project:apache-camel-lab
 * User: fredrik
 * Date: 2017-11-08
 * Time: 18:54
 */
public class MainApp {

    public static void main(String... args) throws Exception {
        Main main = new Main();
        main.addRouteBuilder(new RestServiceRouteBuilder());
        main.run(args);
    }

}


