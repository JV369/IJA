//package tests;

import org.junit.Assert;
import org.junit.Test;
import components.Port;
import org.junit.Before;
import static java.lang.Double.NaN;

public class PortTest {

    private Port port1;
    private Port port2;
    private Port port3;

    @Before
    public void setUp() {
        port1 = new Port("Human");
        port2 = new Port("Food");
        port3 = new Port("Time");
    }

    @Test
    public void testPortID() {
        Assert.assertEquals("Test inkrementace ID",0,port1.getId());
        Assert.assertEquals("Test inkrementace ID",1,port2.getId());
        Assert.assertEquals("Test inkrementace ID",2,port3.getId());
    }

    @Test
    public void testPortInitValues() {
        Assert.assertEquals("Test typu portu","Human",port1.getType().getName());
        Assert.assertEquals("Test typu portu","Food",port2.getType().getName());
        Assert.assertEquals("Test typu portu","Time",port3.getType().getName());

        Assert.assertEquals("Test hodnot portu",80.0,port1.getType().getValue("weight"),0.001);
        Assert.assertEquals("Test hodnot portu",100.0,port1.getType().getValue("stamina"),0.001);

        Assert.assertEquals("Test hodnot portu",1000.0,port2.getType().getValue("calories"),0.001);

        Assert.assertEquals("Test hodnot portu",0.0,port3.getType().getValue("hours"),0.001);
        Assert.assertEquals("Test hodnot portu",30.0,port3.getType().getValue("minutes"),0.001);
    }

    @Test
    public void testPortUpdate() {
        Assert.assertTrue(port1.getType().update("weight",150.0));
        Assert.assertEquals("Test hodnot portu",150.0,port1.getType().getValue("weight"),0.001);
        Assert.assertEquals("Test hodnot portu",100.0,port1.getType().getValue("stamina"),0.001);

        Assert.assertTrue(port3.getType().update("hours",3.0));
        Assert.assertEquals("Test hodnot portu",3.0,port3.getType().getValue("hours"),0.001);
        Assert.assertEquals("Test hodnot portu",30.0,port3.getType().getValue("minutes"),0.001);

        Assert.assertFalse(port2.getType().update("minutes",250.0));
        Assert.assertEquals("Test na špatnou hodnotu",NaN,port3.getType().getValue("calories"));
    }
}
