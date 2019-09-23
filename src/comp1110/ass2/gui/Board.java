package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import static comp1110.ass2.gui.Viewer.*;


public class Board extends Application {

    // FIXME Task 7: Implement a basic playable Focus Game in JavaFX that only allows pieces to be placed in valid places

    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    private static final int SQUARE_SIZE = 60;
    private static final int VIEWER_WIDTH = 720;
    private static final int VIEWER_HEIGHT = 480;

    private static final int COLUMNS = 9;
    private static final int ROWS = 5;

    private static final String URI_BASE = "assets/";

    // node groups
    private final Group root = new Group();

    /*void drawHorizontal(){
        int initialX = (VIEWER_WIDTH - SQUARE_SIZE * COLUMNS) / 2;
        int initialY = (VIEWER_HEIGHT - SQUARE_SIZE * ROWS) / 2;
        //Draw the horizontal lines
        for(int i = 0; i < 6; i++){
            //Last line should be of length 7 SQUARE_SIZE
            if(i == 5){
                Line horizontal = new Line(initialX + SQUARE_SIZE, initialY + 5 * SQUARE_SIZE, initialX + SQUARE_SIZE * 8, initialY + 5 * SQUARE_SIZE);
                controls.getChildren().add(horizontal);
                continue;
            }
            Line horizontal = new Line(initialX, initialY + i * SQUARE_SIZE, initialX + SQUARE_SIZE * COLUMNS, initialY + i * SQUARE_SIZE);
            controls.getChildren().add(horizontal);
        }
    }

    void drawVertical(){
        int initialX = (VIEWER_WIDTH - SQUARE_SIZE * COLUMNS) / 2;
        int initialY = (VIEWER_HEIGHT - SQUARE_SIZE * ROWS) / 2;
        //Draw the vertical lines
        for(int i = 0; i < 10; i++){
            //First and Last vertical line should be of length SQUARE_SIZE * 4
            if(i == 0 || i == 9){
                Line vertical = new Line(initialX + i * SQUARE_SIZE, initialY, initialX + i * SQUARE_SIZE, initialY + 4 * SQUARE_SIZE);
                controls.getChildren().add(vertical);
                continue;
            }

            Line vertical = new Line(initialX + i * SQUARE_SIZE, initialY, initialX + i * SQUARE_SIZE, initialY + 5 * SQUARE_SIZE);
            controls.getChildren().add(vertical);
        }
    }

    void drawBoard() {
        drawHorizontal();
        drawVertical();
    }
*/



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
        drawBoard();
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        root.getChildren().add(controls);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
