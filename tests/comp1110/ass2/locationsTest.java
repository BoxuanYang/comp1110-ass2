package comp1110.ass2;

import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static comp1110.ass2.Solver.*;
import static org.junit.Assert.*;
import static comp1110.ass2.Solution.SOLUTIONS;
import static comp1110.ass2.FocusGame.*;

public class locationsTest {

    @Test
    public void testCoveredLocations() {
        //*If the placement is "", then coveredLocations should return null
        assertTrue(coveredLocations("") == null);

        //*If the placement is complete, the coveredLocations should return every Point in the Board
        ArrayList<Point> allPoints = new ArrayList<>();
        //build an ArrayList of all points in board
        for(int x = 0; x < 9; x++){
            for(int y = 0; y < 5; y++){
                //continue the loop if it is (0,4) or (8,4)
                if((x == 0 || x == 8) && y == 4){
                    continue;
                }

                Point point = new Point(x, y);
                allPoints.add(point);
            }
        }


        for(Solution solution : SOLUTIONS){
            String placement = solution.placement;
            ArrayList<Point> pointsCovered = coveredLocations(placement);
            for(Point point : pointsCovered){
                assertTrue(allPoints.contains(point));
            }

            for(Point allPoint : allPoints){
                assertTrue(pointsCovered.contains(allPoint));
            }
        }


    }




    @Test
    public void testNotCoveredLocations() {
        //If the placement is already complete, then notCoveredLocations should return bunch of nulls
        for(Solution solution : SOLUTIONS){
            ArrayList<Point> points = notCoveredLocations(solution.placement);
            assertTrue(points == null);
        }

        //If the placement is "", then notCoveredLocations should return whole points
        ArrayList<Point> allPoints = new ArrayList<>();
        //build an ArrayList of ArrayList<Point> allPoints = new ArrayList<>();
        //build an ArrayList of all points in board
        for(int x = 0; x < 9; x++){
            for(int y = 0; y < 5; y++){
                //continue the loop if it is (0,4) or (8,4)
                if((x == 0 || x == 8) && y == 4){
                    continue;
                }

                Point point = new Point(x, y);
                allPoints.add(point);
            }
        }
        for(int x = 0; x < 9; x++){
            for(int y = 0; y < 5; y++){
                //continue the loop if it is (0,4) or (8,4)
                if((x == 0 || x == 8) && y == 4){
                    continue;
                }

                Point point = new Point(x, y);
                allPoints.add(point);
            }
        }
        ArrayList<Point> notCoveredPoints = notCoveredLocations("");
        for(Point point : notCoveredPoints){
            assertTrue(allPoints.contains(point));
        }

        for(Point allPoint : allPoints){
            assertTrue(notCoveredPoints.contains(allPoint));
        }

        //a100b210
        notCoveredPoints = notCoveredLocations("c220d400e003f801g030h502i733j332");
        for(Point point : notCoveredPoints){
            System.out.println(point);
        }

    }
}