package comp1110.ass2;

import java.awt.*;
import java.util.ArrayList;

public class vb {
    public static void main(String[] args) {
        Point p = new Point(1,2);
        ArrayList<Point> points = new ArrayList<>(10);
        points.add(new Point(1,2));
        System.out.println(points.contains(p));
    }
}
