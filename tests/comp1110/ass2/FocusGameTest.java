package comp1110.ass2;

import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import static comp1110.ass2.FocusGame.orderSolution;
import static comp1110.ass2.FocusGame.*;
import static org.junit.Assert.*;

public class FocusGameTest {
    @Test
    public void testOrderSolution(){
        String s1 = "j412a022c202b132e630f410d003g110h601i431";
        assertTrue(orderSolution(s1).equals("a022b132c202d003e630f410g110h601i431j412"));
        String s2 = "b103j003c130d200a630f801g432e412h501i210";
        assertTrue(orderSolution(s2).equals("a630b103c130d200e412f801g430h501i210j003"));
    }

    @Test
    public void testAvailableLocations(){
        String s1 = "a001";
        ArrayList<Point> points = availableLocations(s1);
        int width = getWidth(s1);
        int height = getHeight(s1);
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                State s = getState(s1, x, y);
                if(s != null){
                    assertTrue(points.contains(new Point(x, y)));
                }

                else
                    assertFalse(points.contains(new Point(x, y)));
            }
        }
    }

    @Test
    public void testComplementLocations(){
        String s1 = "a001";
        ArrayList<Point> points = complementLocations(availableLocations(s1));
        assertTrue(points.contains(new Point(0, 0)));
        assertFalse(points.contains(new Point(1, 0)));
        assertTrue(points.contains(new Point(1,3)));
    }
}