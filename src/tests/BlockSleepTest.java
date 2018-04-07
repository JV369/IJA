//package tests;

import components.AbstractBlock;
import components.BlockSleep;
import org.junit.Test;
import interfaces.Block;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BlockSleepTest {
    private BlockSleep block = new BlockSleep();

    @Test
    public void TestInstance() {
        assertTrue(block instanceof AbstractBlock);
        assertTrue(block instanceof Block);
    }

    @Test
    public void TestExecute(){
        block.getInPort(0).getType().update("stamina", 50.0);
        block.getInPort(1).getType().update("hours", 2.0);
        block.execute();
        assertEquals(87.5, block.getOutPort(0).getType().getValue("stamina"), 0.001);
    }
}