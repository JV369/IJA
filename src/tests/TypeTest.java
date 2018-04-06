//package tests;

import org.junit.Test;
import components.Type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TypeTest {

    @Test
    public void setValue() {
        Type type = new Type("Real");
        assertTrue("Error seting value of Type", type.setTypeValue(420.00));
        //assertEquals(420.00, type.getTypeValue("Real"));

    }
}
