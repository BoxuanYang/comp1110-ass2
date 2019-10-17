package comp1110.ass2;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import static comp1110.ass2.FocusGame.contains;
import static comp1110.ass2.FocusGame.withinCentralBoard;
import static org.junit.Assert.*;

public class MyTest {
    @Test
    public void testContains(){
        assertEquals(true, contains("a001b003", 'a'));
        assertEquals(true, contains("a001f401b003", 'f'));
        assertEquals(true, contains("a001j331f401b003", 'b'));
        assertEquals(false, contains("a001j331f401b003", 'g'));
    }

    @Test
    public void testWithinBoard(){
        assertEquals(true, withinCentralBoard(3,1));
        assertEquals(true, withinCentralBoard(3,2));
        assertEquals(true, withinCentralBoard(3,3));
        assertEquals(true, withinCentralBoard(4,1));
        assertEquals(true, withinCentralBoard(4,2));
        assertEquals(true, withinCentralBoard(4,3));
        assertEquals(true, withinCentralBoard(5,1));
        assertEquals(true, withinCentralBoard(5,2));
        assertEquals(true, withinCentralBoard(5,3));
        assertEquals(false, withinCentralBoard(6,3));
        assertEquals(false, withinCentralBoard(7,3));
        assertEquals(false, withinCentralBoard(1,3));
        Set set = new HashSet();
        set.add("a001");
        set.add("b330");
        System.out.println(set);
    }



}