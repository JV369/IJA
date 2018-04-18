//package tests;//package tests;

import components.AbstractBlock;
import components.BlockCook;
import components.BlockEat;
import interfaces.Block;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlockCookTest {
    private BlockCook block;

    @Before
    public void setUp() {
        block = new BlockCook();
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
        block.execute();
        assertEquals("Test food calories", 2000, block.getOutPort(0).getType().getValue("calories"), 0.001);
    }
}