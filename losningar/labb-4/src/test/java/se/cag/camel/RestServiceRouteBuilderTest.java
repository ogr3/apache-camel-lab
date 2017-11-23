package se.cag.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import static org.junit.Assert.*;

public class RestServiceRouteBuilderTest extends CamelTestSupport {

  protected RouteBuilder createRouteBuilder() {
    return new RestServiceRouteBuilder();
  }

  @Override
  public boolean isUseAdviceWith() {
    return true;
  }

  @Test
  public void testAddWater() {

  }


}