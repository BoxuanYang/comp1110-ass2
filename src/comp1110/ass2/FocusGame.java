package comp1110.ass2;

import java.util.Arrays;
import java.util.Set;

import static comp1110.ass2.State.*;

/**
 * This class provides the text interface for the IQ Focus Game
 * <p>
 * The game is based directly on Smart Games' IQ-Focus game
 * (https://www.smartgames.eu/uk/one-player-games/iq-focus)
 */


public class FocusGame {


    private State[][] boardstates = {
        {EMPTY, EMPTY, EMPTY,EMPTY, EMPTY, EMPTY,EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY,EMPTY, EMPTY, EMPTY,EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY,EMPTY, EMPTY, EMPTY,EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY,EMPTY, EMPTY, EMPTY,EMPTY, EMPTY, EMPTY},
            {null, EMPTY, EMPTY,EMPTY, EMPTY, EMPTY,EMPTY, EMPTY, null},

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
        // FIXME Task 2: determine whether a piece placement is well-formed

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
        if (!(piecePlacement.charAt(3) <= '3' && piecePlacement.charAt(3) >= '0')) {
            return false;
        }
        return true;
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

        // FIXME Task 3: determine whether a placement is well-formed

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
        // is placement string well wormed
        if (!isPlacementStringWellFormed(placement)) {
            return false;
        }

        // is each piece entirely on the board


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
