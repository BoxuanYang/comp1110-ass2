package comp1110.ass2;

import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import static comp1110.ass2.Solver.*;
import static comp1110.ass2.FocusGame.*;
import static org.junit.Assert.*;
import static comp1110.ass2.Solution.SOLUTIONS;

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
        ArrayList<Point> points = coveredLocations(s1);
        System.out.println(points);
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
        ArrayList<Point> points = notCoveredLocations(s1);
        assertTrue(points.contains(new Point(0, 0)));
        assertFalse(points.contains(new Point(1, 0)));
        assertTrue(points.contains(new Point(1,3)));


        String complete = "a630b103c130d200e412f801g430h501i210j003";
        points = notCoveredLocations(complete);
        System.out.println(points);
        assertTrue(points == null);
    }

    @Test
    public void testGetAdjacentSquares(){
        //*If the placement is complete, it should return null
        for(Solution solution : SOLUTIONS){
            String placement = solution.placement;
            Point point = getAdjacentEmptySquare(placement);
            assertTrue(point == null);
        }

        //*If the placement is "", return (4,2)
        Point point = getAdjacentEmptySquare("");
        assertTrue(point.equals(new Point(4, 2)));

        //*Try other things
        point = getAdjacentEmptySquare("c410d303e111f330g030h000i733j332");//a701b400c410
        //System.out.println(getViablePiecePlacements("d303e111f330g030h000i733j332", "RWWRRRWWW", point.x, point.y));
        //System.out.println(point);

        ArrayList<Point> adjacentSquares = getAdjacentEmptySquares("f330");
        int count = 0;
        for(Point da : adjacentSquares){
            System.out.println(da);
            count ++;
        }
        System.out.println(count);
        adjacentSquares = getAdjacentEmptySquares("a000b013c113d302e323f400g420h522i613j701");
        assertTrue(adjacentSquares == null);
    }

    @Test
    public void testGetAdjacentSquare(){
        for(Solution solution : SOLUTIONS){
            String placement = solution.placement;
            Point point = getAdjacentEmptySquare(placement);
            assertTrue(point == null);
        }

        Point point = getAdjacentEmptySquare("");
        assertTrue(point.x == 4 && point.y == 2);

        point = getAdjacentEmptySquare("a000");
        System.out.println(point);
    }
}