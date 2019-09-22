package comp1110.ass2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static comp1110.ass2.Shape.getShape;
import static comp1110.ass2.State.*;

/**
 * This class provides the text interface for the IQ Focus Game
 * <p>
 * The game is based directly on Smart Games' IQ-Focus game
 * (https://www.smartgames.eu/uk/one-player-games/iq-focus)
 */


public class FocusGame {

    public static State[][] boardStates = {
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {null,  EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, null },

    };

    //The width and height for all pieces in orientation 0
    static int[][] width_and_height = {
            {3, 2},//a
            {4, 2},//b
            {4, 2},//c
            {3, 2},//d
            {3, 2},//e
            {3, 1},//f
            {3, 2},//g
            {3, 3},//h
            {2, 2},//i
            {4, 2}//j
    };

    //All states of all pieces in orientation 0
    static State[][] pieceMap = {
            {GREEN, WHITE, RED,//a
             null,  RED,   null},

            {null,  BLUE, GREEN, GREEN,//b
             WHITE, WHITE, null, null},


            {null, null, GREEN,  null,//c
             RED,   RED,  WHITE, BLUE},

            {RED,   RED,    RED,//d
             null,  null,   BLUE},

            {BLUE,  BLUE,  BLUE,//e
             RED,   RED,   null},

            {WHITE, WHITE, WHITE},//f

            {WHITE, BLUE,  null,
             null,  BLUE,  WHITE},//g

            {RED, GREEN, GREEN,//h
             WHITE, null,null,
             WHITE, null, null},

            {BLUE,  BLUE,//i
             null,  WHITE},

            {GREEN, GREEN, WHITE, RED,//j
             GREEN, null,  null,  null}
    };


    /**
     * Determine whether a piece placement is well-formed according to the
     * following criteria:
     * - it consists of exactly four characters
     * - the first character is in the range a .. j (shape)
     * - the second character is in the range 0 .. 8 (column)
     * - the third character is in the range 0 .. 4 (row)
     * - the fourth character is in the range 0 .. 3 (orientation)
     *
     * @param piecePlacement A string describing a piece placement
     * @return True if the piece placement is well-formed
     */
    static boolean isPiecePlacementWellFormed(String piecePlacement) {

        // piece placement consists of exactly four characters
        if (piecePlacement.length() != 4) {
            return false;
        }

        // the first character is in the range a .. j (shape)
        if (!(piecePlacement.charAt(0) <= 'j' && piecePlacement.charAt(0) >= 'a')) {
            return false;
        }

        // the second character is in the range 0 .. 8 (column)
        if (!(piecePlacement.charAt(1) <= '8' && piecePlacement.charAt(1) >= '0')) {
            return false;
        }

        // the third character is in the range 0 .. 4 (row)
        if (!(piecePlacement.charAt(2) <= '4' && piecePlacement.charAt(2) >= '0')) {
            return false;
        }

        // the fourth character is in the range 0 .. 3 (orientation)
        return piecePlacement.charAt(3) <= '3' && piecePlacement.charAt(3) >= '0';
    }

    /**
     * Given a piece placement, return the width specified by its type and orientation
     * @param placement a well-formed piece placement
     * @return an int value
     */
    public static int getWidth(String placement){
        int type = placement.charAt(0) - 97;
        int ori = placement.charAt(3) - '0';

        int width = width_and_height[type][0];
        int height = width_and_height[type][1];

        //If orientation is 1 or 3, then switch width and height
        if(ori % 2 == 1){
            int t = width;
            width = height;
            height = t;
        }

        return width;
    }

    /**
     * Given a piece placement, return the width specified by its type and orientation
     * @param placement a well-formed piece placement
     * @return an int value
     */
    public static int getHeight(String placement){
        int type = placement.charAt(0) - 97;
        int ori = placement.charAt(3) - '0';

        int width = width_and_height[type][0];
        int height = width_and_height[type][1];
        //If orientation is 1 or 3, then switch width and height
        if(ori % 2 == 1){
            int t = width;
            width = height;
            height = t;
        }

        return height;
    }

    /**
     * Given the orientation, xoff distance off left-top point and yoff distance off left-top point
     * ,return the state at that position
     * @param placement a  well-formed piece placement string
     * @param xoff x-axis distance from top-left point
     * @param yoff y-axis distance from top-left point
     * @return an object of type State
     *                                               19   18   17   16
     *      0  1  2     3       16  12  8  4  0      15   14   13   12     3  7  11  15  19
     *      4  5  6     7       17  13  9  5  1      11   10   9    8      2  6  10  14  18
     *      8  9  10    11      18  14  10 6  2      7    6    5    4      1  5  9   13  17
     *      12 13 14    15      19  15  11 7  3      3    2    1    0      0  4  8   12  16
     *      16 17 18    19
     *
     *
     *
     *
     *      0 1 2 3    4 0    7 6 5 4    3 7
     *      4 5 6 7    5 1    3 2 1 0    2 6
     *                 6 2               1 5
     *                 7 3               0 4
     */
    public static State getState(String placement, int xoff, int yoff){
        int type = placement.charAt(0) - 97;//
        int width = width_and_height[type][0];
        int height = width_and_height[type][1];
        int ori = placement.charAt(3) - '0';

        if(ori == 0){
            //                    xoff + yoff * width
            return pieceMap[type][xoff + yoff * width];
        }

        if(ori == 1){
            //                    yoff +  width  - xoff      * width
            /*System.out.println("The width is: " +width);
            System.out.println("The height is: " + height);
            System.out.println("The index is: " + yoff + (width -1 - xoff) * width);
            System.out.println(yoff);*/
            return pieceMap[type][yoff + (height -1 - xoff) * width];
        }

        if(ori == 2){
            //                  width - xoff    height - yoff    * width
            return pieceMap[type][(width- 1 - xoff) + (height - 1 - yoff) * width];
        }

        //                    width - yoff + xoff * width
        return pieceMap[type][(width - 1 - yoff) + xoff * width];
    }

    /**The placement string can be assumed to be well-formed
     * Return true if it is within the board, false otherwise
     * @param placement a piece placement string of length 4
     * @return a boolean value
     */
    public static boolean isPieceOnBoard(String placement){
        int x = placement.charAt(1) - '0';
        int y = placement.charAt(2) - '0';
        int width = getWidth(placement);
        int height = getHeight(placement);

        for(int xoff = 0; xoff < width; xoff++){
            for(int yoff = 0; yoff < height; yoff++){
                State state = getState(placement, xoff, yoff);
                //Ignore the case the sqaures to which to piece extends is null
                if(state == null){
                    continue;
                }

                //If a sqaure reaches out of the board
                if(x + xoff > 8 || y + yoff > 4){
                    return false;
                }
                //If a square lies in the null positions in boardState
                if(boardStates[y + yoff][x + xoff] == null){
                    return false;
                }
        }
    }
        return true;
    }

    /**
     * The two placmeent string can be assumed to be well-formed and within the board
     * @param str1 First piece placement String
     * @param str2 Second piece placement String
     * @return return true if two strings overlap, false other wise
     */
    public static boolean doesPlacementOverlap(String str1, String str2){
        int x1 = str1.charAt(1) - '0';
        int y1 = str1.charAt(2) - '0';
        int width1 = getWidth(str1);
        int height1 = getHeight(str1);

        int x2 = str2.charAt(1) - '0';
        int y2 = str2.charAt(2) - '0';
        int width2 = getWidth(str2);
        int height2 = getHeight(str2);

         State[][] board = {
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {null,  EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, null},

        };

         //Update String str1
         for(int xoff = 0; xoff < width1; xoff++){
             for(int yoff = 0; yoff < height1; yoff++){
                 State s = getState(str1, xoff, yoff);
                 //Ignore null squares
                 if(s == null){
                     continue;
                 }

                 board[y1 + yoff][x1 + xoff] = s;
             }
         }

         //Check in all squares of str2, does it overlap with str1
        for(int xoff = 0; xoff < width2; xoff++){
            for(int yoff = 0; yoff < height2; yoff++){
                State s = getState(str2, xoff, yoff);
                //Ignore null squares
                if(s == null){
                    continue;
                }

                if(board[y2 + yoff][x2 + xoff] != EMPTY){
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Determine whether a placement string is well-formed:
     * - it consists of exactly N four-character piece placements (where N = 1 .. 10);
     * - each piece placement is well-formed
     * - no shape appears more than once in the placement
     *
     * @param placement A string describing a placement of one or more pieces
     * @return True if the placement is well-formed
     */
    public static boolean isPlacementStringWellFormed(String placement) {

        int length = placement.length();
        int N = length / 4;

        // placement string has exactly N four-character piece placements
        if (length % 4 != 0 || N < 1) {
            return false;
        }

        // each  piece placement is well-formed
        for (int i = 0; i < N; i++) {
            int a = 4 * i;
            String piece = placement.substring(a, a + 4);
            if (!isPiecePlacementWellFormed(piece)) {
                return false;
            }
        }

        // create a sorted char array of all shape characters
        char[] shapes = new char[N];
        for (int i = 0; i < N; i++) {
            shapes[i] = placement.charAt(i * 4);
        }
        Arrays.sort(shapes);

        // check if no shape appears more than once in the placement
        for (int i = 0; i < N - 1; i++) {
            if (shapes[i] == shapes[i + 1]) {
                return false;
            }
        }
        return true;
    }


    /**
     * Determine whether a placement string is valid.
     * <p>
     * To be valid, the placement string must be:
     * - well-formed, and
     * - each piece placement must be a valid placement according to the
     * rules of the game:
     * - pieces must be entirely on the board
     * - pieces must not overlap each other
     *
     * @param placement A placement string
     * @return True if the placement sequence is valid
     */
    public static boolean isPlacementStringValid(String placement) {
        // FIXME Task 5: determine whether a placement string is valid
        // is placement string well formed
        if (!isPlacementStringWellFormed(placement)) {
            return false;
        }
        // need some code that rotates the pieces -- make a method in the orientation

        /*int length = placement.length();

        for (int i = 0; i + 4 < length; i = i + 4) {
            char s = placement.charAt(i);

            // should get rotated version but this is currently un-rotated
            State[][] piece = getShape(s);


            int y = Character.getNumericValue(placement.charAt(i + 1));
            int x = Character.getNumericValue(placement.charAt(i + 2));


            int rowNo = piece.length;
            int colNo = piece[0].length;


            for (int y1 = 0; y1 < colNo; y1++) {
                for (int x1 = 0; x1 < rowNo; x1++) {
                    if (piece[x1][y1] != null) {
                        // check if the the piece does not overlap another and is on the board
                        if ((boardStates[x + x1][y + y1] != EMPTY)) {
                            return false;
                            // check if piece is outside the board
                        } else if (((y + y1) > 8) || ((x+x1) > 4)) {
                            return false;
                        }
                    }
                }
            }
        }*/

        //n is Number of pieces
        int n = placement.length() / 4;
        String[] pieces = new String[n];

        //Create an array of Strings where each element of it is a well-formed piece placement
        for(int i = 0; i < n; i++){
            pieces[i] = placement.substring(4 * i, 4 * i + 4);
            //Check whether each element of the array is within the board
            if(!isPieceOnBoard(pieces[i])){
                return false;
            }
        }

        //Does any two of the pice placements overlap?
        for(int i = 0; i < n; i++){
            for(int j = i + 1; j < n; j++){
                //if they overlap, return false
                if(doesPlacementOverlap(pieces[i], pieces[j])){
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Given a piece, return true if it fits the challenge specified by the challenge string, false otherwise
     * @param piece A well-formed piece placement string
     * @param challenge A challenge stirng consists of 9 characters of 4 types, i.e. 'W', 'R', 'B', 'G'
     * @param col The col of the piece to be placed
     * @param row the row of the piece to be placed
     * @return a boolean value
     */
    static boolean fitChallenge(String piece, String challenge, int col, int row){
        int width = getWidth(piece);
        int height = getHeight(piece);
        return true;
    }

    /**
     * Given a placement string and a pice placement string, return true if the pice placement does not
     * overlap with the placement string and false otherwise.
     * @param placement A placement string consists of multiple piece placements
     * @param piece A piece placement string
     * @return A boolean value
     */
    static boolean fitPlacement(String placement, String piece){
        return true;
    }

    /**
     * Given a placement string and a piece placement string, return true if the piece
     * to be placed is already included in placement string.
     * @param placement A valid placement string
     * @param piece A well-formed piece placement
     * @return A boolean value
     */
    static boolean contains(String placement, String piece){
        return true;
    }
    /**
     * Given a string describing a placement of pieces and a string describing
     * a challenge, return a set of all possible next viable piece placements
     * which cover a specific board cell.
     * <p>
     * For a piece placement to be viable
     * - it must be valid
     * - it must be consistent with the challenge
     *
     * @param placement A viable placement string
     * @param challenge The game's challenge is represented as a 9-character string
     *                  which represents the color of the 3*3 central board area
     *                  squares indexed as follows:
     *                  [0] [1] [2]
     *                  [3] [4] [5]
     *                  [6] [7] [8]
     *                  each character may be any of
     *                  - 'R' = RED square
     *                  - 'B' = Blue square
     *                  - 'G' = Green square
     *                  - 'W' = White square
     * @param col       The cell's column.
     * @param row       The cell's row.
     * @return A set of viable piece placements, or null if there are none.
     */
    static Set<String> getViablePiecePlacements(String placement, String challenge, int col, int row) {
        // FIXME Task 6: determine the set of all viable piece placements given existing placements and a challenge
        // Placement string is valid
        if (!isPlacementStringValid(placement)) {
            return null;
        }

        Set pieces = new HashSet();
        for(char a = 'a'; a <= 'j'; a++){
            for(int ori = 0; ori < 4; ori++){
                String piece = String.valueOf(a) + String.valueOf(col) + String.valueOf(row) + String.valueOf(ori);

                if(fitChallenge(piece, challenge, col, row)){
                    pieces.add(piece);
                }
            }
        }
        // array of challenge colours
        char[] colours = challenge.toCharArray();

        // check if colour matches challenge square

        return pieces;
    }

    /**
     * Return the canonical encoding of the solution to a particular challenge.
     * <p>
     * A given challenge can only solved with a single placement of pieces.
     * <p>
     * Since some piece placements can be described two ways (due to symmetry),
     * you need to use a canonical encoding of the placement, which means you
     * must:
     * - Order the placement sequence by piece IDs
     * - If a piece exhibits rotational symmetry, only return the lowest
     * orientation value (0 or 1)
     *
     * @param challenge A challenge string.
     * @return A placement string describing a canonical encoding of the solution to
     * the challenge.
     */
    public static String getSolution(String challenge) {
        // FIXME Task 9: determine the solution to the game, given a particular challenge
        return null;
    }

}

