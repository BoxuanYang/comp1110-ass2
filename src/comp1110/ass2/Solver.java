package comp1110.ass2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;
import static comp1110.ass2.FocusGame.*;
public class Solver {
    public static String challenge;

    public Solver(String challenge){
        this.challenge = challenge;
    }


    /**The author of this method is Apoorva Sajja, Nicole Wang and Boxuan Yang
     *
     * Return the canonical encoding of the solution to a particular challenge.
     * <p>
     * A given challenge can only solved with a single placement of pieces.
     * <p>
     * Since some piece placements can be described two ways (due to symmetry),
     * you need to use a canonical encoding of the placement, which means you
     * must:
     * - Order the placement sequence by piece IDs
     * - If a piece exhibits rotational symmetry, only return the lowest
     *   orientation value (0 or 1)
     *
     *
     * @return A placement string describing a canonical encoding of the solution to
     * the challenge.
     */
    public static String getSolution() {
        // FIXME Task 9: determine the solution to the game, given a particular challenge
        String mySolution = "";
        //get an ArrayList with all possible moves for getViablePiecePlacements("", challenge, 4, 2)
        ArrayList<String> firstMoves = new ArrayList<>();
        firstMoves.addAll(getViablePiecePlacements("", challenge, 4, 2));

        int n = firstMoves.size();

        //create an array of trees whose root correspond to the elements in firstMoves
        TreeNode[] solutionTree = new TreeNode[n];
        for(int i = 0; i < n; i++){
            solutionTree[i] = new TreeNode(firstMoves.get(i));
            buildSolutionTree(solutionTree[i], challenge);
            ArrayList<String> solutionList = new ArrayList<>();
            getSolutionFromTree(solutionTree[i], solutionList);

            if(solutionList.size() != 0){
                mySolution = solutionList.get(0);
                break;
            }
        }

        //order the solution
        mySolution = orderSolution(mySolution);

        return mySolution;

    }

    /**The author of this method is Boxuan Yang, with inspirations from:
     * https://github.com/Aditya-B-Sharma/comp1110-assignment2-IQSteps/blob/master/src/comp1110/ass2/StepsGame.java?fbclid=IwAR0PN5OIKSRrhXatyN6khlMgzCTVw4_PaHbX4hxs3amdrB3QyUHBaJC_DrM
     *
     * Given an initial tree, an ArrayList locationsCovered and a challenge string. Change
     * the initial tree to a tree which contains a solution.
     * @param initialNode initial tree
     * @param challenge challenge string
     * @return nothing
     */
    public static void buildSolutionTree(TreeNode initialNode, String challenge) {
        Point point = getAdjacentEmptySquare(initialNode.placement);

        //stop building the tree if the initialNode is already a solution
        if(point == null) {
            return;

        }

        Set<String> possibleMoves = getViablePiecePlacements(initialNode.placement, challenge, point.x, point.y);
        //stop building the tree if there is no way to proceed
        if(possibleMoves == null){
            return;
        }


        for(String piece : possibleMoves){
            TreeNode child = new TreeNode(initialNode.placement + piece);
            initialNode.addChild(child);
            buildSolutionTree(child, challenge);
        }

    }

    /**The author of this method is Boxuan Yang, with inspirations from:
     * https://github.com/Aditya-B-Sharma/comp1110-assignment2-IQSteps/blob/master/src/comp1110/ass2/StepsGame.java?fbclid=IwAR0PN5OIKSRrhXatyN6khlMgzCTVw4_PaHbX4hxs3amdrB3QyUHBaJC_DrM
     *
     * Given a tree which contains a solution, return that solution placemenr string.
     * We assume that the tree given already contains a solution.
     * @param tree a tree which contains a solution
     * @return a placement string
     */
    public static void getSolutionFromTree(TreeNode tree, ArrayList<String> solutionList) {
        if(tree.placement.length() == 40){
            solutionList.add(tree.placement);
        }

        for(TreeNode child : tree.children){
            getSolutionFromTree(child, solutionList);
        }

        return;
    }

    /**The author of this method is Boxuan Yang
     *
     * Given a placement, return an Arraylist of points that this placement covers
     * @param placement a well-formed placement
     * @return an array list of points (all the locations that the placement covers)
     */
    public static ArrayList<Point> coveredLocations(String placement) {
        ArrayList<Point> locations = new ArrayList<>();
        //the number of pieces
        int n = placement.length() / 4;
        //Find the available squares for each piece.
        String piece = "";
        for(int i = 0; i < n; i++){
            piece = placement.substring(4 * i, 4 * i + 4);
            int x = piece.charAt(1) - '0';
            int y = piece.charAt(2) - '0';
            int width = getWidth(piece);
            int height = getHeight(piece);
            //loop through every piece
            for(int xoff = 0; xoff < width; xoff++){
                for(int yoff = 0; yoff < height; yoff++){
                    State s = getState(piece, xoff, yoff);
                    //Add it to the ArrayList if the square is not null
                    if(s != null){
                        locations.add(new Point(x + xoff, y + yoff));
                    }
                }
            }

        }

        if(locations.size() == 0){
            return null;
        }

        return locations;
    }


    /**The author of this method is Boxuan Yang
     *
     * Given a placement string, return an ArrayList of all the locations not covered by it.
     * @param  placement String
     * @return an arraylist including only locations that are not covered)
     */
    public static ArrayList<Point> notCoveredLocations(String placement){
        ArrayList<Point> points = coveredLocations(placement);
        ArrayList<Point> complement = new ArrayList();

        //return an ArrayList of all points on board if placement is ""
        if(points == null){
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
            return allPoints;
        }

        //loop through every square on board
        for(int x = 0; x < 9; x++){
            for(int y = 0; y < 5; y++){
                //continue the loop if it is (0,4) or (8,4)
                if((x == 0 || x == 8) && y == 4){
                    continue;
                }

                Point point = new Point(x, y);
                //add it to the ArrayList complement if the point is not in points
                if(!points.contains(point)){
                    complement.add(point);
                }
            }
        }

        if(complement.size() == 0){
            return null;
        }
        return complement;
    }



    /**The author of the method is Boxuan Yang
     *
     * Given a solution string, order the placement sequence by piece IDs.
     * Ordering rules:
     *   1.Order by piece ID.
     *   2.If a piece exhibits rotational symmetry, only return the lowest orientation value (0 or 1).
     */
    public static String orderSolution(String placement) {
        String[] ordered = new String[10];
        for(int i = 0; i < 10; i++){
            String str = placement.substring(4* i, 4 * i + 4);
            char piece = str.charAt(0);
            int index = piece - 97;
            if(piece == 'g' || piece == 'f'){
                int ori = str.charAt(3) - '0';
                ori %= 2;
                str = ""  + str.charAt(0) + str.charAt(1) + str.charAt(2) + (char)(ori + '0');
                ordered[index] = str;
            }

            else
                ordered[index] = str;
        }

        String order = "";
        for(int i = 0; i < 10; i++){
            order += ordered[i];
        }
        return order;
    }

    /**The author of this method is Boxuan Yang
     *
     * Given a placement string, return a Point which is adjacent to the exsisting placement and is empty.
     * @param placement a placement string
     * @return a point
     */
    public static Point getAdjacentEmptySquare(String placement){
        ArrayList<Point> squaresCovered = coveredLocations(placement);
        ArrayList<Point> squaresNotCovered = notCoveredLocations(placement);

        //return null if the placement string is already a solution
        if(squaresNotCovered == null){
            return null;
        }

        //return central point if the placement is ""
        if(placement == ""){
            return new Point(4, 2);
        }


        return getAdjacentEmptySquares(placement).get(0);
    }

    /**The author of this method is Bouan Yang
     *
     * Given a placement string, return an ArrayList of type Point which is adjacent to the exsisting placement and is empty.
     * @param placement a placement string
     * @return an ArrayList of Point
     */
    public static ArrayList<Point> getAdjacentEmptySquares(String placement){
        ArrayList<Point> adjacentEmptySquares = new ArrayList<>();
        ArrayList<Point> squaresCovered = coveredLocations(placement);
        ArrayList<Point> squaresNotCovered = notCoveredLocations(placement);

        if(squaresNotCovered == null){
            return null;
        }

        if(placement == ""){
            adjacentEmptySquares.add(new Point(4, 2));
        }

        for(Point point : squaresNotCovered){
            if(adjacentToPlacement(point, placement)){
                adjacentEmptySquares.add(point);
            }
        }
        return adjacentEmptySquares;
    }


    /**The author of this method is Boxuan Yang
     *
     * Given a point and a placement string, return true if the point is adajacent to the placement
     * @param point an object of type Point
     * @param placement a placement string
     * @return a boolean value
     */
    public static boolean adjacentToPlacement(Point point, String placement){
        ArrayList<Point> squaresCovered = coveredLocations(placement);
        ArrayList<Point> squaresNotCovered = notCoveredLocations(placement);
        int x = point.x;
        int y = point.y;

        //return false if the point covered by placement
        if(squaresCovered.contains(point)){
            return false;
        }

        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if(i == 0 && j == 0){
                    continue;
                }

                if(squaresCovered.contains(new Point(x + i, y + j))){
                    return true;
                }
            }
        }

        return false;
    }
}
