package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Board extends Application {

    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;


    // FIXME Task 7: Implement a basic playable Focus Game in JavaFX that only allows pieces to be placed in valid places

    // TASK 7: checks if location is on the board
    public boolean isLocationValid() {
        return false;
    }

    // TASK 7: checks if location does not have a tile already on the board in that place
    public boolean isLocationEmpty() {
        return false;
    }

    // TASK 7: Adds piece to the board if isLocationEmpty and isLocationValid are both true
    public void addPiece() {

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


    @Override
    public void start(Stage primaryStage) {

    }
}
