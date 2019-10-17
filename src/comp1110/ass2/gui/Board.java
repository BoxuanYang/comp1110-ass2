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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

import static comp1110.ass2.Solution.SOLUTIONS;
import static comp1110.ass2.gui.Viewer.*;
import static comp1110.ass2.FocusGame.*;

// This class was written by Nicole Wang
public class Board extends Application {

    // FIXME Task 7: Implement a basic playable Focus Game in JavaFX that only allows pieces to be placed in valid places

    // window layout
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;

    // playable board layout
    private static final int SQUARE_SIZE = 55;

    // playable board position
    private static final int MARGIN_X = 60;
    private static final int MARGIN_Y = 45;

    // positions for the starting positions of pieces on the board
    private static final int PIECE_START_Y = SQUARE_SIZE + 30;
    private static final int PIECE_START_X = 45;

    // Board positions
    private static final int BOARD_START_X = MARGIN_X * 2 + 4 * SQUARE_SIZE + 15;
    private static final int BOARD_START_Y = -SQUARE_SIZE/2;
    private static final int BOARD_END_X = BOARD_WIDTH - MARGIN_X;
    private static final int BOARD_END_Y = MARGIN_Y + SQUARE_SIZE * 5;

    long lastRotationTime = System.currentTimeMillis();
    private static final long ROTATION_THRESHOLD = 50; // allow rotation every 50 milliseconds


    private static final String URI_BASE = "assets/";

    // Variables

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
    private final Group challenges = new Group();
    private final Group hint = new Group();
    //The author of the above code is Nicole Wang.

    // challenge of the game
    String challenge = startPlacement();

    // game solution to the challenge
    private String gameSolution = gameSolution(challenge);

    // The definitions below correspond to the layout of the pieces when displayed in the game at their home state
    //the row location of each piece where index 0 is a, 1 is b, etc.
    int[] rows = {3, 3, 1, 4, 4, 4, 4, 3, 3, 2};
    //the column location of each piece where index 0 is a, 1 is b, etc.
    int[] cols = {1, 2, 1, 3, 1, 4, 2, 4, 3, 1};
    //The author of the above arrays is Nicole Wang.


    // The following methods extract information from a piece string
    public char getPiece(String piece) {
        return piece.charAt(0);
    }

    public int getOrientation(String piece) {
        return piece.charAt(3)-48;
    }

    public int getX(String piece) {
        return piece.charAt(1) - 48;
    }

    public int getY(String piece) {
        return piece.charAt(2) - 48;
    }

    /** the author of this method is Nicole Wang
     * Builds the board
     */
    private void board() {
        // obtain board image
        Image img = new Image(Board.class.getResource(URI_BASE + "board.png").toString());
        ImageView iv = new ImageView();
        iv.setImage(img);

        // set to appropriate size
        iv.setFitHeight(6.7*SQUARE_SIZE);
        iv.setFitWidth(10.25*SQUARE_SIZE);

        // set to appropriate location
        iv.setX(BOARD_START_X-0.6*SQUARE_SIZE);
        iv.setY(BOARD_START_Y);

        // add to node group
        board.getChildren().add(iv);
    }

    /**
     * The author of this method is Nicole Wang
     * returns y location for a piece in a given row for the home starting position
     */
    private int rowPosition(int row) {
        return PIECE_START_Y + MARGIN_Y * (row-1) + SQUARE_SIZE * (2 * (row - 1));
    }

    /**
     * The author of this method is Nicole Wang
     * returns x location for each piece in a given column for the home starting position
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
        Image img; // image of the piece
        int width; // width of the piece
        int height; // piece height

        /**
         * The author of this method is Nicole Wang
         * Constructs a draggable piece
         **/
        DraggablePiece(char piece) {
            this.piece = piece;
            // obtains image of piece
            img = new Image(Board.class.getResource(URI_BASE + piece + ".png").toString());
            setImage(img);

            // initial piece orientation
            orientation = 0;

            // variables for piece's home starting positions
            X = colPosition(cols[piece - 'a']);
            Y = rowPosition(rows[piece - 'a']);

            // shift piece d and f separately (does not follow pattern)
            if (piece == 'd') {
                X = X - SQUARE_SIZE;
            }
            if (piece == 'f') {
                Y = Y + SQUARE_SIZE;
            }

            // set piece's home starting position
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
                // make piece being moved at the front
                toFront();
                // track how much the piece has moved from where it was first dragged
                double movementX = event.getSceneX() - mouseX;
                double movementY = event.getSceneY() - mouseY;
                // set new position
                setLayoutX(getLayoutX() + movementX);
                setLayoutY(getLayoutY() + movementY);
                // updates position of mouse
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
                event.consume();
            });
            // snap into place upon completed drag
            setOnMouseReleased(event -> {
                snapToGrid();
            });

            // setOnScroll event taken from comp1110 assignment 1
            // rotates the piece when scrolled
            setOnScroll(event -> {
                if (System.currentTimeMillis() - lastRotationTime > ROTATION_THRESHOLD) {
                    lastRotationTime = System.currentTimeMillis();
                    // update orientation
                    orientation = (orientation + 1) % 4;
                    // rotate the piece
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
            // update piece's width and height
            setFitWidth(getSquaresOfWidth(piece, orientation) * SQUARE_SIZE);
            setFitHeight(getSquaresOfHeight(piece, orientation) * SQUARE_SIZE);
            // bring piece being rotated to the front
            toFront();
            // rotate piece by 90 degrees clockwise
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
                // closest column and row to the current piece
                col = closestColumn();
                row = closestRow();

                // closest location on the board to the current piece
                bx = (BOARD_START_X + (SQUARE_SIZE * col));
                by = (MARGIN_Y + (SQUARE_SIZE * row));

                // shift snapping position for pieces of width 3 and height 2 to offset rotation behaviour
                if ((orientation == 1 || orientation == 3) && width == 3 && height == 2) {
                    bx = bx + SQUARE_SIZE / 2;
                    by = by + SQUARE_SIZE / 2;
                }

            } else {
                // if not on board, send the piece back home
                snapToHome();
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
            // remove piece being snapped home from the placement string containing all pieces on the board
            removeBoardState();
            // set to orientation of 0
            orientation = 0;
            rotate();
            // send back to home location
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
            // remove piece being snapped home from the placement string containing all pieces on the board
            removeBoardState();
            // add the current piece to the placement string
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
     * This method was taken from the makeTiles() method of comp1110 assignment 1
     * place all tiles in their starting positions */
    ArrayList<DraggablePiece> pieces = new ArrayList<>();
    private void makePieces() {
        // clear all pieces in bpieces
        bpieces.getChildren().clear();
        // create a draggable piece for pieces a-j
        for (char m = 'a'; m <= 'j'; m++) {
            pieces.add(new DraggablePiece(m));
            bpieces.getChildren().add(pieces.get(m-97));
        }

    }

    /** The author of this method is Nicole Wang
     * Create a button to reset all pieces back to original place */
    private void resetButton() {
        // create reset button
        Button button = new Button("Reset");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // send all pieces back home
            resetPieces();
            }
        });
        controls.getChildren().add(button);
        // set button location
        button.setLayoutX(10);
        button.setLayoutY(10);
    }

    /** The author of this method is Nicole Wang
     * reset all pieces by sending them back to their home state */
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

    // gets a starting placement from SOLUTIONS in TestUtility.Solution
    public String startPlacement() {
        Random random = new Random();
        int index = random.nextInt(120);
        return SOLUTIONS[index].objective;
    }

    // adds the placement challenge to the board
    public void placeStart(String challenge) {
        for (int i = 0; i < challenge.length(); i++) {
            // obtains image of one piece of the challenge
            char piece = Character.toLowerCase(challenge.charAt(i));
            Image image = new Image(Board.class.getResource(URI_BASE + "sq-" + piece + ".png").toString());
            ImageView iv1 = new ImageView();
            iv1.setImage((image));

            // sets to correct size
            iv1.setFitHeight(SQUARE_SIZE);
            iv1.setFitWidth(SQUARE_SIZE);

            // x coordinate for challenge square
            if (i < 3) {
                iv1.setX(BOARD_START_X + 3 * SQUARE_SIZE + SQUARE_SIZE * i);
            } else {
                iv1.setX(BOARD_START_X + 3 * SQUARE_SIZE + SQUARE_SIZE * ((i) % 3));
            }

            // y coordinate for challenge square
            if (i < 3) {
                    iv1.setY(MARGIN_Y + SQUARE_SIZE);
            } else if (i < 6) {
                    iv1.setY(MARGIN_Y + SQUARE_SIZE * 2);
            } else if (i < 9) {
                    iv1.setY(MARGIN_Y + SQUARE_SIZE * 3);
            }

            challenges.getChildren().add(iv1);
            }

        }


    // gets a solution that matches the challenge from SOLUTIONS in TestUtility.Solution
    public String gameSolution(String challenge) {
        Solution solution = SOLUTIONS[0];
        for(int i = 0; i < 120; i++){
            solution = SOLUTIONS[i];
            if(solution.objective.equals(challenge))
                return solution.getPlacement();

        }
        return solution.getPlacement();
    }

    /** The author of this method is Nicole Wang
     * Creates a button that shows/hides the challenge */
    private void challengeButton() {
        Button button = new Button("Hide/Show Challenge");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // show challenge if challenge is not on the board
                if(!root.getChildren().contains(challenges)) {
                    root.getChildren().add(challenges);
                    bpieces.toFront();
                } else {
                    // remove challenge from the board if no existing challenge there
                    root.getChildren().remove(challenges);
                }
            }
        });
        controls.getChildren().add(button);
        // set button location
        button.setLayoutX(70);
        button.setLayoutY(10);
    }


    // gets an appropriate hint according to the pieces the user has already placed on the board
    private String getHints() {

        int n = gameSolution.length();
        for(int i=0; i<n; i=i+4) {
            String solution = gameSolution.substring(i,i+4);
            // if the solution piece can be added to the current pieces on the board and be valid, return that as the hint
            if(isPlacementStringValid(placement+solution)) {
                System.out.println(placement+solution);
                return solution;
            }
        }
        return null;
    }

    private void displayHint(String piece) {
        // remove any of the existing hints
        hint.getChildren().removeAll();

        // piece information
        char p = getPiece(piece);
        int o = getOrientation(piece);
        int x = getX(piece);
        int y = getY(piece);

        // obtain appropriate piece image
        Image img = new Image(Board.class.getResource(URI_BASE + p + ".png").toString());
        ImageView iv = new ImageView(img);
        hint.getChildren().add(iv);

        // set image to correct orientation
        iv.setRotate(o * 90);

        // set image to correct size;
        int width = getSquaresOfWidth(p, o);
        int height = getSquaresOfHeight(p, o);
        iv.setFitHeight(height*SQUARE_SIZE);
        iv.setFitWidth(width*SQUARE_SIZE);

        // variables for hint location
        int setX = BOARD_START_X+(SQUARE_SIZE*x);
        int setY = MARGIN_Y+(SQUARE_SIZE*y);

        // Offset locations according to general piece shape when at orientation 1 or 3
        if (o==1||o==3) {
            if (width == 3 && height == 2) {
                System.out.println((BOARD_START_X) + (SQUARE_SIZE * x) + (SQUARE_SIZE / 2));
                setX = setX - (SQUARE_SIZE / 2);
                setY = setY + SQUARE_SIZE / 2;
            } else if ((height == 2 && width == 4) || (height == 1 && width == 3)) {
                setX = setX - SQUARE_SIZE;
                setY = setY + SQUARE_SIZE;
            }
        }

        // set hint location
        iv.setLayoutX(setX);
        iv.setLayoutY(setY);

        // add hint to root to display
        if (!hint.getChildren().isEmpty()) {
            root.getChildren().add(hint);
        }

    }

    /** The author of this method is Nicole Wang
     * Creates a hint button */
    private void hintButton() {
        // create hint button
        Button button = new Button("Hint");

        // when user presses the hint button, display a possible hint on the board
        button.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
            @Override
                    public void handle(MouseEvent e) {
                String h = getHints();
                if (h != null) {
                    System.out.println(gameSolution);
                    System.out.println(placement);
                    System.out.println(getHints());
                    displayHint(h);
                } else { // if no possible hints left, outline the board with red

                    // rectangle to outline the board
                    Rectangle r = new Rectangle(BOARD_END_X-BOARD_START_X+1.6*SQUARE_SIZE,BOARD_END_Y-BOARD_START_Y+SQUARE_SIZE);

                    // set colour of rectangle to dark red
                    r.setFill(Color.INDIANRED);
                    r.setArcHeight(SQUARE_SIZE*2);
                    r.setArcWidth(SQUARE_SIZE*2);

                    // rectangle to the correct position (outlining the board)
                    r.setX(BOARD_START_X-SQUARE_SIZE);
                    r.setY(BOARD_START_Y-SQUARE_SIZE/4);

                    // add into the window
                    hint.getChildren().add(r);
                    root.getChildren().add(hint);

                    // send to the back of all objects
                    hint.toBack();
                }
            }

                });

        // when user lets go of the hint button, remove the hint
        button.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        // removes the hint
                        hint.getChildren().clear();
                        root.getChildren().remove(hint);
                    }

                });
        controls.getChildren().add(button);
        // sets button location
        button.setLayoutX(225);
        button.setLayoutY(10);
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
        makePieces();
        // create reset button
        resetButton();
        // place the challenge on the board in the beginning
        placeStart(challenge);
        // create button that hides/shows the challenge
        challengeButton();
        // create hint button
        hintButton();


       // placeStart(startPlacement());
        System.out.println(challenge);

        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);
        root.getChildren().add(board);
        root.getChildren().add(challenges);
        root.getChildren().add(controls);
        root.getChildren().add(bpieces);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
