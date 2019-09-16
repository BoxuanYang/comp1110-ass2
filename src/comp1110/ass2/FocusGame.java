package comp1110.ass2;

import java.util.Arrays;
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
            {null, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, null},

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
        // is placement string well formed
        if (!isPlacementStringWellFormed(placement)) {
            return false;
        }

        // need some code that rotates the pieces -- make a method in the orientation


        // checks each piece in the placement
        int length = placement.length();


        for (int i = 0; i + 4 <= length; i = i + 4) {
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
                        } else if ((y + y1) > 4) {
                            return false;
                        } else if ((x + x1) > 8) {
                            return false;
                        }
                    }
                }
            }
        }


        // FIXME Task 5: determine whether a placement string is valid
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

        // array of challenge colours
        char[] colours = challenge.toCharArray();

        // check if colour matches challenge square


        return null;
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
