package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
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
    private static final int MARGIN_X = 43;
    private static final int MARGIN_Y = 45;
    private static final int PLAY_X = MARGIN_X * 2 + 4 * SQUARE_SIZE;


    private static final String URI_BASE = "assets/";
    private static final int ASCII = 97;

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

    private TextField textField;

    // node groups
    private final Group root = new Group();
    private final Group bpieces = new Group();

    // creates shadow effect on piece
    private static DropShadow dropShadow;

    /** Static initializer to initialize dropShadow */ {
        dropShadow = new DropShadow();
        dropShadow.setOffsetX(2.0);
        dropShadow.setOffsetY(2.0);
        dropShadow.setColor(Color.color(0, 0, 0, .4));
    }


    /**
     * returns y location for a piece in a given row
     */
    private int rowPosition(int row) {
        return MARGIN_Y * row + SQUARE_SIZE * (2 * (row - 1));
    }

    /**
     * returns x location for each piece in a given column
     */
    private int colPosition(int col) {
        int margins = MARGIN_X * col;
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
     * the row location of each piece where index 0 is a, 1 is b, etc.
     **/
    int[] rows = {3, 3, 1, 4, 4, 4, 4, 3, 3, 2};
    /**
     * the column location of each piece where index 0 is a, 1 is b, etc.
     **/
    int[] cols = {1, 2, 1, 3, 1, 4, 2, 4, 3, 1};

    /**
     * Graphical representation of the pieces
     */
    class BoardPiece extends ImageView {
        int pieceID;

        /**
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
     * class extending piece with the ability to be dragged and dropped and snapped onto the board
     **/
    class DraggablePiece extends BoardPiece {
        double X, Y; // position where tile initially begins
        double mouseX, mouseY; // last known mouse positions
        int orientation; // piece orientation

        /**
         * Constructs a draggable piece
         **/
        DraggablePiece(char piece) {
            // creates image of piece

            setImage(new Image(Viewer.class.getResource(URI_BASE + piece + ".png").toString()));

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
            int width = getSquaresOfWidth(piece, orientation);
            int height = getSquaresOfHeight(piece, orientation);
            setFitWidth(SQUARE_SIZE * width);
            setFitHeight(SQUARE_SIZE * height);

            // gives shadow effect
            setEffect(dropShadow);

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
                snapToGrid(piece);
            });

        }

        /**
         * Snap piece to the nearest grid position
         */
        private void snapToGrid(char piece) {
            // find nearest x grid or snap to home if not on board
            if (onBoard()) {
                for (int x = 0; x < 9; x++) {
                    if (getLayoutX() >= PLAY_X + (SQUARE_SIZE * x) && getLayoutX() < (PLAY_X + (SQUARE_SIZE * (x + 1)))) {
                        col = x;
                        bx = (PLAY_X + (SQUARE_SIZE * x));
                    }
                }
                // find nearest y grid or snap to home if not on board
                for (int y = 0; y < 5; y++) {
                    if (getLayoutY() >= MARGIN_Y + (SQUARE_SIZE * y) && getLayoutY() < (MARGIN_Y + (SQUARE_SIZE * (y + 1)))) {
                        row = y;
                        by = (MARGIN_Y + (SQUARE_SIZE * y));
                    }
                }
            } else {
                snapToHome();
            }

            String placementtmp=placement;
            // find index of piece character in placement (containing all pieces on the board)
            int p = placementtmp.indexOf(piece);
            int length = placementtmp.length();

            // if piece being snapped is already on the board, remove it from the placement string of existing pieces
            if (p != -1) {
                placementtmp = placement.substring(0,p) + placement.substring(p+4,length);
            }

            String tmp = Character.toString(piece) + col + row + orientation + "";
            if (isPlacementStringValid(placementtmp + tmp)) {
                setLayoutX(bx);
                setLayoutY(by);
                placement = placementtmp + tmp;
            } else {
                snapToHome();
            }

        }
            /**
             * Checks to see if piece is on the board
             */
            private boolean onBoard() {
                // check if it is in the correct x location to be on board
                if(!(getLayoutX() >= PLAY_X && getLayoutX() < (BOARD_WIDTH-MARGIN_X))) {
                    return false;
                }
                // check if it is in the correct y location to be on board
                if(!(getLayoutY() >= MARGIN_Y && getLayoutY() < (MARGIN_Y + SQUARE_SIZE*5))) {
                    return false;
                }
                // check it is not in the bottom left corner
                if(getLayoutX() >= PLAY_X && getLayoutX() < PLAY_X+SQUARE_SIZE && getLayoutY() >= MARGIN_Y+SQUARE_SIZE*4 && getLayoutY() < (MARGIN_Y + SQUARE_SIZE*5)) {
                    return false;
                }
                // check it is not in the bottom right corner
                if(getLayoutX() >= PLAY_X+SQUARE_SIZE*8 && getLayoutX() < BOARD_WIDTH- MARGIN_X && getLayoutY() >= MARGIN_Y+SQUARE_SIZE*4 && getLayoutY() < (MARGIN_Y + SQUARE_SIZE*5)) {
                    return false;
                }
                return true;
            }

    /**
     * Snap tile back to home position (if not on the grid
     **/
    private void snapToHome() {
        setLayoutX(X);
        setLayoutY(Y);
        orientation = 0;
    }

}

    /** place all tiles in their starting positions */
    private void makeTiles() {
        bpieces.getChildren().clear();
        for (char m = 'a'; m <= 'j'; m++) {
            bpieces.getChildren().add(new DraggablePiece(m));
        }
    }

    /** Create a button to reset all pieces back to original place */
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

    /** reset all pieces */
    private void resetPieces() {
        // snap each piece back to home
        bpieces.toFront();
        for (Node n : bpieces.getChildren()) {
            ((DraggablePiece) n).snapToHome();
        }
        // empty the placement string
        placement = "";
    }


    // FIXME Task 8: Implement challenges (you may use challenges and assets provided for you in comp1110.ass2.gui.assets: sq-b.png, sq-g.png, sq-r.png & sq-w.png)

    // TASK 8: get a starting placement from SOLUTIONS in TestUtility.Solution
    public String startPlacement() {
        return "";
    }

    // TASK 8: add the startPlacement to the board
    public void placeStart() {
    }

    // TASK 8: return a solution for the game
    public String gameSolution() {
        return "";
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
     * Snap the tile to its home position (if it is not on the grid)
     */

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Board");
        // draw board
        drawBoard(PLAY_X,MARGIN_Y);
        // place all pieces on board
        makeTiles();
        // create reset button
        resetButton();

        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);
        root.getChildren().add(controls);
        root.getChildren().add(bpieces);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
