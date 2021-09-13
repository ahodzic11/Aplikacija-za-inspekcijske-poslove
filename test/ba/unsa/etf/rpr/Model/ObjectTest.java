package ba.unsa.etf.rpr.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectTest {

    @Test
    void constructorTest(){
        Object o = new Object(15, 92, "Del", "Alije Izetbegovića 13", "Pub");
        assertEquals(15, o.getId());
        assertEquals(92, o.getOwnerId());
        assertEquals("Del", o.getName());
        assertEquals("Alije Izetbegovića 13", o.getAddress());
        assertEquals("Pub", o.getType());
    }

    @Test
    void testToString() {
        Object o = new Object(15, 92, "Del", "Alije Izetbegovića 13", "Pub");
        assertEquals("Del, Alije Izetbegovića 13", o.toString());
    }
}