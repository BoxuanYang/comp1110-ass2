package comp1110.ass2;

import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static comp1110.ass2.FocusGame.coveredLocations;
import static org.junit.Assert.*;
import static comp1110.ass2.Solution.SOLUTIONS;
import static comp1110.ass2.FocusGame.notCoveredLocations;

public class locationsTest {

    @Test
    public void testCoveredLocations() {
        //If the placement is "", then coveredLocations should return null
        assertTrue(coveredLocations("") == null);

        //If the placement is complete, the coveredLocations should return every Point in the Board
        ArrayList<Point> allPoints;


        for(Solution solution : SOLUTIONS){
            allPoints = buildFullPoints();
            String placement = solution.placement;
            ArrayList<Point> pointsCovered = coveredLocations(placement);
            for(Point point : pointsCovered){
                assertTrue(allPoints.contains(point));
                pointsCovered.remove(point);
                allPoints.remove(point);
            }

            assertTrue(pointsCovered.size() == 0);
            assertTrue(allPoints.size() == 0);
        }

    }

    public ArrayList<Point> buildFullPoints(){
        ArrayList<Point> allPoints = new ArrayList<>();
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
        return allPoints;
    }
    @Test
    public void testNotCoveredLocations() {
        //If the placement is already complete, then notCoveredLocations should return bunch of nulls
        for(Solution solution : SOLUTIONS){
            ArrayList<Point> points = notCoveredLocations(solution.placement);
            assertTrue(points == null);
        }


    }
}