package comp1110.ass2;

import static comp1110.ass2.Shape.getShape;

public class Rotation {

    public State[][] toRotate(char shape, int rotation) {
        State[][] s = getShape(shape);
        int rowLength = s.length;
        int colLength = s[0].length;

        State[][] rotated = new State[colLength][rowLength];

        if (rotation == 1) {
            for (int c = 0; c < colLength; c++) {
                for (int r = 0; r < rowLength; r++) {
                    rotated[c][r] = s[rowLength - r - 1][c];
                }

            }

        }
        if (rotation == 2) {
            for (int c = 0; c < colLength; c++) {
                for (int r = 0; r < rowLength; r++) {
                    rotated[c][r] = s[c][rowLength - r - 1];
                }

            }
        }

        if (rotation == 3) {
            for (int c = 0; c < colLength; c++) {
                for (int r = 0; r < rowLength; r++) {
                    rotated[c][r] = s[r][colLength - c - 1];
                }

            }

        }
    return rotated;
    }

}
