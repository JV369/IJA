//package tests;

import components.AbstractBlock;
import components.BlockSleep;

import components.Port;
import org.junit.Assert;
import org.junit.Before;

import org.junit.Test;
import interfaces.Block;

import static org.junit.Assert.*;

public class BlockSleepTest {
    private BlockSleep block;

    @Before
    public void setUp() {
        block = new BlockSleep();
    }

    @Test
    public void testInstance() {
        assertTrue(block instanceof AbstractBlock);
        assertTrue(block instanceof Block);
    }

    @Test
    public void testInitValues(){
        BlockSleep b2 = new BlockSleep();
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
        assertEquals("Test stamina after sleep",57.5, block.getOutPort(0).getType().getValue("stamina"), 0.001);
        block.getInPort(1).getType().update("hours", 2.0);
        block.execute();
        assertEquals("Test stamina after sleep 2", 87.5, block.getOutPort(0).getType().getValue("stamina"), 0.001);
    }
}