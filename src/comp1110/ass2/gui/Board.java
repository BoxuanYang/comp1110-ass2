package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static comp1110.ass2.gui.Viewer.*;


public class Board extends Application {

    // FIXME Task 7: Implement a basic playable Focus Game in JavaFX that only allows pieces to be placed in valid places

    // window layout
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;

    // playable board layout
    private static final int PLAY_WIDTH = 540;
    private static final int PLAY_HEIGHT = 300;

    // playable board position
    private static final int PLAY_X = BOARD_WIDTH/2 - PLAY_WIDTH/2;
    private static final int MARGIN = 10;

    // node groups
    private final Group root = new Group();


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



    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Board");
        // draw board
        drawBoard(PLAY_X,MARGIN);
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        root.getChildren().add(controls);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
