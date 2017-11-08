package se.cag.routes;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.camel.Message;

/**
 * Project:apache-camel-lab
 * User: fredrik
 * Date: 2017-11-08
 * Time: 19:23
 */
public class WaterContainerBean {
    private Integer waterLevel = 0;

    @Handler
    public void addWater(@Body Message message){
       waterLevel += message.getBody(Integer.class);
        System.out.println("waterLevel = " + waterLevel);
    }
}