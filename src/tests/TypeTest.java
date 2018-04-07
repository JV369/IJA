//package tests;

import org.junit.Assert;
import org.junit.Test;
import components.Type;
import org.junit.Before;


public class TypeTest {

    private Type typeHuman;
    private Type typeFood;
    private Type typeTime;
    private Type typeSomething;

    @Before
    public void setUp() {
        typeHuman = new Type("Human");
        typeFood = new Type("Food");
        typeTime = new Type("Time");
        typeSomething = new Type("Something");
    }

    @Test
    public void testTypeName() {
        Assert.assertEquals("Test nazev typu Human","Human",typeHuman.getName());
        Assert.assertEquals("Test nazev typu Food","Food",typeFood.getName());
        Assert.assertEquals("Test nazev typu Time","Time",typeTime.getName());
        Assert.assertEquals("Test nazev typu Something","Human",typeSomething.getName());

    }
/*
    @Test
    public void testTypeImplicValues() {
        Assert.assertEquals("Test implicitni hodnoty Human",80.0,typeHuman.getValues().get("weight"),0.001);
        Assert.assertEquals("Test implicitni hodnoty Human",100.0,typeHuman.getValues().get("stamina"),0.001);

        Assert.assertEquals("Test implicitni hodnoty Food",1000.0,typeFood.getValues().get("calories"),0.001);

        Assert.assertEquals("Test implicitni hodnoty Time",0.0,typeTime.getValues().get("hours"),0.001);
        Assert.assertEquals("Test implicitni hodnoty Time",30.0,typeTime.getValues().get("minutes"),0.001);

        Assert.assertEquals("Test implicitni hodnoty Something",80.0,typeSomething.getValues().get("weight"),0.001);
        Assert.assertEquals("Test implicitni hodnoty Something",100.0,typeSomething.getValues().get("stamina"),0.001);
    }

    @Test
    public void testUpdate() {
        typeHuman.updateHuman(50.0,25.5);
        Assert.assertEquals("Test aktualizované hodnoty Human",50.0,typeHuman.getValues().get("weight"),0.001);
        Assert.assertEquals("Test aktualizované hodnoty Human",25.5,typeHuman.getValues().get("stamina"),0.001);

    }
*/
}
