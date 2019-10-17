package comp1110.ass2;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;
import static comp1110.ass2.Solution.SOLUTIONS;
import static comp1110.ass2.FocusGame.*;


public class sdg {
    public static void main(String[] args) {
        for(Solution solution : SOLUTIONS){
            ArrayList<Point> points = notCoveredLocations(solution.placement);
            System.out.println(points);
        }
    }
}
