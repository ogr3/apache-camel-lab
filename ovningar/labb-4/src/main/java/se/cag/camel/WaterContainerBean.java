package se.cag.camel;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.camel.Message;


public class WaterContainerBean {
    private Integer waterLevel = 0;

    @Handler
    public void addWater(@Body Message message){
       waterLevel += message.getBody(Integer.class);
        System.out.println("waterLevel = " + waterLevel);
    }

    @Handler
    public void removeWater(@Body Message message){
       waterLevel -= message.getBody(Integer.class);
        System.out.println("waterLevel = " + waterLevel);
    }
    @Handler
    public void getWaterLevel(@Body Message message){
       message.setBody(waterLevel);
        System.out.println("waterLevel = " + waterLevel);
    }

    public int getWater() {
        return waterLevel;
    }
}
