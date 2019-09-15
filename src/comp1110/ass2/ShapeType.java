package comp1110.ass2;
import static comp1110.ass2.Shape.getShape;
/*
public class ShapeType {
    public State[][] toRotate1(char shape){
        State[][] s = getShape(shape);
        int rowLength = s[0].length;
        int colLength = s.length;

        State[][] rotate1 = new State[colLength][rowLength];

        for (int c = 0; c < colLength; c++){
            for (int r = 0; r < rowLength; r++){
                rotate1[ r ][ colLength - 1 - c ] = s[c][r];
            }
        }
        return rotate1;
    }

    public State[][] toRotate2 (char shape){
        State[][]rotate1 = toRotate1 (shape);
        int row1Length = rotate1[0].length;
        int col1Length = rotate1.length;

        State[][] rotate2 = new State[col1Length][row1Length];

        for (int c = 0; c < col1Length; c++){
            for (int r = 0; r < row1Length; r++){
                rotate2[ r ][ col1Length - 1 - c ] = rotate1[c][r];
            }
        }
        return rotate2;

    }

    public State[][] toRotate3 (char shape){
        State[][]rotate2 = toRotate2 (shape);
        int row2Length = rotate2[0].length;
        int col2Length = rotate2.length;

        State[][] rotate3 = new State[col2Length][row2Length];

        for (int c = 0; c < col2Length; c++){
            for (int r = 0; r < row2Length; r++){
                rotate3[ r ][ col2Length - 1 - c ] = rotate2[c][r];
            }
        }
        return rotate3;

    }


}
*/
