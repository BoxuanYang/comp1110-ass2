package comp1110.ass2;

import static comp1110.ass2.Shape.getShape;

public class Rotation {

    public static State[][] toRotate(char shape, int rotation) {
        State[][] s = getShape(shape);
        int rowLength = s.length;
        int colLength = s[0].length;

        State[][] rotated = new State[rowlength][colLength];

        if (rotation == 1) {
            for (int c = 0; c < colLength; c++) {
                for (int r = 0; r < rowLength; r++) {
                    rotated[r][c] = s[c][rowLength - r - 1];
                }

            }

        }
        if (rotation == 2) {
            for (int c = 0; c < colLength; c++) {
                for (int r = 0; r < rowLength; r++) {
                    rotated[r][c] = s[rowLength - r -1][c];
                }

            }
        }

        if (rotation == 3) {
            for (int c = 0; c < colLength; c++) {
                for (int r = 0; r < rowLength; r++) {
                    rotated[r][c] = s[colLength - c -q][r];
                }

            }

        }
    return rotated;
    }

}
