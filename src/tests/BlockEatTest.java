//package tests;

import components.*;
import org.junit.Before;
import org.junit.Test;
import components.BlockEat;
import interfaces.Block;

import static org.junit.Assert.*;

public class BlockEatTest {
    private BlockEat block;

    @Before
    public void setUp() {
        block = new BlockEat();
    }

    @Test
    public void testInstance() {
        assertTrue(block instanceof AbstractBlock);
        assertTrue(block instanceof Block);
    }

    @Test
    public void testInitValues(){
        BlockEat b2 = new BlockEat();
        assertNotEquals("Test ID equality", block.getId(), b2.getId());
        assertArrayEquals("Test coordinates", new double[]{0.0, 0.0}, block.getCoordinates(), 0.001);
    }

    @Test
    public void testSetCoordinates(){
        block.setCoordinates(42.0, 66.6);
        assertArrayEquals("Test new coordinates", new double[]{42.0, 66.6}, block.getCoordinates(), 0.001);
    }

    @Test
    public void testExecute(){
        block.getInPort(0).getType().update("stamina", 50.0);
        block.execute();
        assertEquals("Test stamina after eat", 63.819789939, block.getOutPort(0).getType().getValue("stamina"), 0.001);
        assertEquals("Test weight after eat", 80.333, block.getOutPort(0).getType().getValue("weight"), 0.001);
    }

}