package comp1110.ass2.gui;

import comp1110.ass2.Solution;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

import static comp1110.ass2.Solution.SOLUTIONS;
import static comp1110.ass2.gui.Viewer.*;
import static comp1110.ass2.FocusGame.*;


public class Board extends Application {

    // FIXME Task 7: Implement a basic playable Focus Game in JavaFX that only allows pieces to be placed in valid places

    // window layout
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;

    // playable board layout
    private static final int SQUARE_SIZE = 60;

    // playable board position
    private static final int MARGIN_X = 45;
    private static final int MARGIN_Y = 45;

    private static final int PIECE_START_Y = SQUARE_SIZE;
    private static final int PIECE_START_X = 30;


    private static final int BOARD_START_X = MARGIN_X * 2 + 4 * SQUARE_SIZE;
    private static final int BOARD_END_X = BOARD_WIDTH - MARGIN_X;
    private static final int BOARD_END_Y = MARGIN_Y + SQUARE_SIZE * 5;

    long lastRotationTime = System.currentTimeMillis();
    private static final long ROTATION_THRESHOLD = 50; // allow rotation every 50 milliseconds


    private static final String URI_BASE = "assets/";

    // placement containing all pieces on board
    private String placement = "";
    // column for placement string
    private int col;
    // row for placement string
    private int row;
    // x location on board
    private int bx;
    // y location on board
    private int by;

    // node groups
    private final Group root = new Group();
    private final Group bpieces = new Group();
    private final Group board = new Group();
    //The author of the above code is Nicole Wang.


    private void board() {
        Image img = new Image(Board.class.getResource(URI_BASE + "board.png").toString());
        ImageView iv = new ImageView();
        iv.setImage(img);
        iv.setFitHeight(6.7*SQUARE_SIZE);
        iv.setFitWidth(10.25*SQUARE_SIZE);
        iv.setX(BOARD_START_X-0.6*SQUARE_SIZE);
        iv.setY(-SQUARE_SIZE/2);
        board.getChildren().add(iv);
    }

    /**
     * The author of this method is Nicole Wang
     * returns y location for a piece in a given row
     */
    private int rowPosition(int row) {
        return PIECE_START_Y + MARGIN_Y * (row-1) + SQUARE_SIZE * (2 * (row - 1));
    }

    /**
     * The author of this method is Nicole Wang
     * returns x location for each piece in a given column
     */
    private int colPosition(int col) {
        int margins = PIECE_START_X + MARGIN_X * (col-1);
        switch (col) {
            case 1:
                return margins;
            case 2:
                return margins + 3 * SQUARE_SIZE;
            case 3:
                return margins + 7 * SQUARE_SIZE;
            case 4:
                return margins + 9 * SQUARE_SIZE;
        }
        return margins;
    }

    //the row location of each piece where index 0 is a, 1 is b, etc.
    int[] rows = {3, 3, 1, 4, 4, 4, 4, 3, 3, 2};
    //the column location of each piece where index 0 is a, 1 is b, etc.
    int[] cols = {1, 2, 1, 3, 1, 4, 2, 4, 3, 1};
    //The author of the above arrays is Nicole Wang.


    /**
     * The author of this class is Nicole Wang
     * Inspiration from comp1110 assignment 1
     * Graphical representation of the pieces
     */
    class BoardPiece extends ImageView {
        int pieceID;

        /**
         * This method was taken from comp1110 assignment 1
         * Construct a particular playing piece that is placed on the board at the start
         * of the game
         *
         * @param piece       The letter representing the piece to be created.
         * @param orientation The piece rotation
         */
        BoardPiece(char piece, int orientation) {
            if (piece > 'j' || piece < 'a') {
                throw new IllegalArgumentException("Bad tile: \"" + piece + "\"");
            }
            this.pieceID = piece - 'a';
        }

        /**
         * The author of this method is Nicole Wang
         * Build the pieces on the board
         */
        BoardPiece() {
            // build each piece
            for (int i = 0; i < 10; i++) {
                BoardPiece pieces = new BoardPiece((char) ('a' + i), 0);
                root.getChildren().add(pieces);
            }
        }
    }

    /**
     * The author of this class is Nicole Wang.
     * Inspiration for this class was taken from comp1110 Assignment 1 and lab 6
     * class extending piece with the ability to be dragged and dropped and snapped onto the board
     **/
    class DraggablePiece extends BoardPiece {
        double X, Y; // position where tile initially begins
        double mouseX, mouseY; // last known mouse positions
        int orientation; // piece orientation
        char piece; // piece type
        Image img;
        int width;
        int height;

        /**
         * The author of this method is Nicole Wang
         * Constructs a draggable piece
         **/
        DraggablePiece(char piece) {
            this.piece = piece;
            // creates image of piece
            img = new Image(Board.class.getResource(URI_BASE + piece + ".png").toString());
            setImage(img);

            // initial piece orientation
            orientation = 0;

            // Sets the initial position of the piece
            X = colPosition(cols[piece - 'a']);
            Y = rowPosition(rows[piece - 'a']);

            // shift piece d and f separately (does not follow pattern)
            if (piece == 'd') {
                X = X - SQUARE_SIZE;
            }
            if (piece == 'f') {
                Y = Y + SQUARE_SIZE;
            }
            setLayoutX(X);
            setLayoutY(Y);

            // Sets the piece to the correct size
            width = getSquaresOfWidth(piece, orientation);
            height = getSquaresOfHeight(piece, orientation);
            setFitWidth(SQUARE_SIZE * width);
            setFitHeight(SQUARE_SIZE * height);

            /* event handlers */

            // find points of mouse at beginning of drag
            setOnMousePressed(event -> {
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            });
            // track mouse movement as it is being dragged
            setOnMouseDragged(event -> {
                toFront();
                double movementX = event.getSceneX() - mouseX;
                double movementY = event.getSceneY() - mouseY;
                setLayoutX(getLayoutX() + movementX);
                setLayoutY(getLayoutY() + movementY);
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
                event.consume();
            });
            // snap into place upon completed drag
            setOnMouseReleased(event -> {
                snapToGrid();
            });

            /* event handlers */
            setOnScroll(event -> {            // scroll to change orientation
                if (System.currentTimeMillis() - lastRotationTime > ROTATION_THRESHOLD) {
                    lastRotationTime = System.currentTimeMillis();
                    orientation = (orientation + 1) % 4;
                    rotate();
                    event.consume();
                }
            });

        }

        /**
         * The author of this method is Nicole Wang
         * This method rotates the image of the piece
         */
        private void rotate() {
            setFitWidth(getSquaresOfWidth(piece, orientation) * SQUARE_SIZE);
            setFitHeight(getSquaresOfHeight(piece, orientation) * SQUARE_SIZE);
            toFront();
            // turn piece character into corresponding arraylist (pieces) index
            int p = piece - 97;
            pieces.get(p).setRotate(orientation * 90);
        }


        /**
         * Authored by Nicole Wang
         * Find closest column to the current piece
         */
        private int closestColumn() {
            // pieces of height 2 and width 3 must be offset by half a square size to remain within the board
            if ((orientation == 1 || orientation == 3) && width == 3 && height == 2) {
                return (int) (Math.round((getLayoutX() - SQUARE_SIZE / 2 - BOARD_START_X) / SQUARE_SIZE));
            }
            return (int) (Math.round((getLayoutX() - BOARD_START_X) / SQUARE_SIZE));

        }

        /**
         * Authored by Nicole Wang
         * Find closest row to the current piece
         */
        private int closestRow() {
            // pieces of height 2 and width 3 must be offset by half a square size to remain within the board
            if ((orientation == 1 || orientation == 3) && width == 3 && height == 2) {
                return (int) (Math.round((getLayoutY() - SQUARE_SIZE / 2 - MARGIN_Y) / SQUARE_SIZE));
            }
            return (int) (Math.round((getLayoutY() - MARGIN_Y) / SQUARE_SIZE));
        }

        /**
         * The author of this method is Nicole Wang
         * Snap piece to the nearest grid position
         */
        private void snapToGrid() {
            // find nearest x grid or snap to home if not on board
            if (onBoard()) {
                col = closestColumn();
                row = closestRow();


                bx = (BOARD_START_X + (SQUARE_SIZE * col));
                by = (MARGIN_Y + (SQUARE_SIZE * row));

                // shift snapping position for pieces of width 3 (gets rid of a bug that makes these pieces not in line with the board)
                if ((orientation == 1 || orientation == 3) && width == 3 && height == 2) {
                    bx = bx + SQUARE_SIZE / 2;
                    by = by + SQUARE_SIZE / 2;
                }
            } else {
                snapToHome();
                System.out.println("not on board");
            }

            // create temporary string of current pieces on the board
            String placementtmp = placement;

            int p = placementtmp.indexOf(piece);
            // if piece being snapped is already on the board, remove it from the placement string of existing pieces
            if (p != -1) {
                placementtmp = placement.substring(0, p) + placement.substring(p + 4);
            }


            // if the current piece along with the other pieces on the board is still valid then update placement
            // and set on board. Otherwise the piece goes back to its initial position.
            if (isPlacementStringValid(placementtmp + currentState())) {
                setLayoutX(bx);
                setLayoutY(by);
                updateBoardStates();
            } else {
                snapToHome();
            }

        }

        /**
         * The author of this method is Nicole Wang
         * Checks to see if piece is on the board
         */
        private boolean onBoard() {
            // check if it is in the correct x location to be on board
            if (!(mouseX >= BOARD_START_X && mouseX < (BOARD_END_X))) {
                return false;
            }
            // check if it is in the correct y location to be on board
            if (!(mouseY >= MARGIN_Y && mouseY < (BOARD_END_Y))) {
                return false;
            }
            // check it is not in the bottom left corner
            if (mouseX >= BOARD_START_X && mouseX < BOARD_START_X + SQUARE_SIZE && mouseY >= MARGIN_Y + SQUARE_SIZE * 4 && mouseY < (BOARD_END_Y)) {
                return false;
            }
            // check it is not in the bottom right corner
            if (mouseY >= BOARD_START_X + SQUARE_SIZE * 8 && mouseY < BOARD_END_X && mouseY >= MARGIN_Y + SQUARE_SIZE * 4 && mouseY < (BOARD_END_Y)) {
                return false;
            }
            return true;
        }

        /**
         * The author of this method is Nicole Wang
         * Snap tile back to home position (if not on the grid
         **/
        private void snapToHome() {
            removeBoardState();
            orientation = 0;
            rotate();
            setLayoutX(X);
            setLayoutY(Y);
        }

        /**
         * Authored by Nicole Wang
         * Removes the current piece from the current board states string
         */
        private void removeBoardState() {
            int p = placement.indexOf(piece);
            // if piece being snapped is already on the board, remove it from the placement string of existing pieces
            if (p != -1) {
                placement = placement.substring(0, p) + placement.substring(p + 4);
            }
        }

        /**
         * Authored by Nicole Wang
         * Updates the boardstates so that the current piece is updated
         */
        private void updateBoardStates() {
            removeBoardState();
            placement = placement + currentState();
        }

        /**
         * Authored by Nicole Wang
         *
         * @return the placement piece string for the current piece
         */
        private String currentState() {
            // Creates placement piece strings for pieces that have been rotated (Take into account their offsets)
            if (orientation == 1 || orientation == 3) {
                // Pieces of width 4 or width 3 with height 1
                if (width == 4 || (width == 3 && height == 1)) {
                    System.out.println("Piece " + piece + (col + 1) + (row - 1) + orientation + "");
                    return Character.toString(piece) + (col + 1) + (row - 1) + orientation + "";
                }
                // Pieces of width 3 and height 2
                if (width == 3 && height == 2) {
                    System.out.println("Placement: " + placement);
                    System.out.println("Piece " + piece + (col + 1) + row + orientation + "");
                    return Character.toString(piece) + (col + 1) + row + orientation + "";

                }
            }
            // Normal pieces without rotation
            return Character.toString(piece) + col + row + orientation + "";
        }
    }



    /**
     * The author of this method is Nicole Wang
     * place all tiles in their starting positions */
    ArrayList<DraggablePiece> pieces = new ArrayList<>();
    private void makeTiles() {
        bpieces.getChildren().clear();
        for (char m = 'a'; m <= 'j'; m++) {
            pieces.add(new DraggablePiece(m));
            bpieces.getChildren().add(pieces.get(m-97));
        }

    }

    /** The author of this method is Nicole Wang
     * Create a button to reset all pieces back to original place */
    private void resetButton() {
        Button button = new Button("Reset");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            resetPieces();
            }
        });
        controls.getChildren().add(button);
        button.setLayoutX(MARGIN_X);
        button.setLayoutY(MARGIN_Y);
    }

    /** The author of this method is Nicole Wang
     * reset all pieces */
    private void resetPieces() {
        // snap each piece back to home
        bpieces.toFront();
        for (int i=0; i < 10; i++) {
            pieces.get(i).snapToHome();
        }
        // empty the placement string
        placement = "";
    }


    // FIXME Task 8: Implement challenges (you may use challenges and assets provided for you in comp1110.ass2.gui.assets: sq-b.png, sq-g.png, sq-r.png & sq-w.png)

    // TASK 8: get a starting placement from SOLUTIONS in TestUtility.Solution
    public String startPlacement() {
        Random random = new Random();
        int index = random.nextInt(120);
        return SOLUTIONS[index].objective;
    }

    // TASK 8: add the startPlacement to the board
    public void placeStart(String objective) {
        for(int i = 0; i < objective.length(); i++){
            char piece = Character.toLowerCase(objective.charAt(i));
            Image image = new Image(Viewer.class.getResource(URI_BASE + "sq-" + piece + ".png").toString());
            ImageView iv1 = new ImageView();
            iv1.setImage((image));
            root.getChildren().add(iv1);
        }
    }


    // TASK 8: return a solution for the game
    public String gameSolution(String objective) {
        Solution solution = SOLUTIONS[0];
        for(int i = 0; i < 120; i++){
            solution = SOLUTIONS[i];
            if(solution.objective.equals(objective))
                return solution.getPlacement();

        }
        return solution.getPlacement();
    }


    // FIXME Task 10: Implement hints

    // TASK 10: get possible piece placements for the next move
    public String getPossiblePlacements() {
        return "";
    }

    // TASK 10:
    public String implementHints() {
        return "";
    }

    // FIXME Task 11: Generate interesting challenges (each challenge may have just one solution)

    // TASK 11: set difficulty of challenges
    public String setDifficulty() {
        return "";
    }

    // TASK 11: get a start placement randomly
    public String getStartPlacement(String difficulty) {
       return "";
    }

    //TASK 11: Finds solutions
    public String findSolutions(String placement) {
        return"";
    }

    //TASK 11: Checks if startPlacement has one solution
    public boolean hasOneSolution() {
        return false;
    }

    //TASK 11: Finds a start placement with one solution
    public String getChallenge() {
        return "";
    }

    /**
     * The author of this method is Nicole Wang
     * Snap the tile to its home position (if it is not on the grid)
     */

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Board");
        // draw board
        board();
        // place all pieces on board
        makeTiles();
        // create reset button
        resetButton();

        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);
        root.getChildren().add(board);
        root.getChildren().add(controls);
        root.getChildren().add(bpieces);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
