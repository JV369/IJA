//package tests;

import org.junit.Assert;
import org.junit.Test;
import components.Type;
import org.junit.Before;
import static java.lang.Double.NaN;

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
  
    @Test
    public void testTypeImplicValues() {
        Assert.assertEquals("Test implicitni hodnoty Human",80.0,typeHuman.getValue("weight"),0.001);
        Assert.assertEquals("Test implicitni hodnoty Human",100.0,typeHuman.getValue("stamina"),0.001);

        Assert.assertEquals("Test implicitni hodnoty Food",1000.0,typeFood.getValue("calories"),0.001);

        Assert.assertEquals("Test implicitni hodnoty Time",0.0,typeTime.getValue("hours"),0.001);
        Assert.assertEquals("Test implicitni hodnoty Time",30.0,typeTime.getValue("minutes"),0.001);

        Assert.assertEquals("Test implicitni hodnoty Something",80.0,typeSomething.getValue("weight"),0.001);
        Assert.assertEquals("Test implicitni hodnoty Something",100.0,typeSomething.getValue("stamina"),0.001);
    }

    @Test
    public void testUpdate() {
        Assert.assertTrue(typeHuman.update("weight",50.0));
        Assert.assertTrue(typeHuman.update("stamina",25.5));
        Assert.assertEquals("Test aktualizované hodnoty Human",50.0,typeHuman.getValue("weight"),0.001);
        Assert.assertEquals("Test aktualizované hodnoty Human",25.5,typeHuman.getValue("stamina"),0.001);

        Assert.assertTrue(typeFood.update("calories",500.0));
        Assert.assertEquals("Test aktualizované hodnoty Food",500.0,typeFood.getValue("calories"),0.001);

        Assert.assertTrue(typeTime.update("hours",2.0));
        Assert.assertTrue(typeTime.update("minutes",15.0));
        Assert.assertEquals("Test aktualizované hodnoty Time",2.0,typeTime.getValue("hours"),0.001);
        Assert.assertEquals("Test aktualizované hodnoty Time",15.0,typeTime.getValue("minutes"),0.001);

        Assert.assertFalse(typeSomething.update("Something",666));
        Assert.assertEquals("Test na neznámou hodnotu",NaN,typeSomething.getValue("Something"),0.00001);
    }
}
