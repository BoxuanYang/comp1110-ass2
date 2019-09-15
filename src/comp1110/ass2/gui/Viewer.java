package comp1110.ass2.gui;

import com.sun.javafx.scene.control.TableColumnSortTypeWrapper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A very simple viewer for piece placements in the IQ-Focus game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 */
public class Viewer extends Application {

    /* board layout */
    private static final int SQUARE_SIZE = 60;
    private static final int VIEWER_WIDTH = 720;
    private static final int VIEWER_HEIGHT = 480;

    private static final int COLUMNS = 9;
    private static final int ROWS = 5;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField textField;
    int[][] width_and_height = {
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



    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {
        int initialX = (VIEWER_WIDTH - SQUARE_SIZE * COLUMNS) / 2;
        int initialY = (VIEWER_HEIGHT - SQUARE_SIZE * ROWS) / 2;

        root.getChildren().clear();
        root.getChildren().add(controls);

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

        //Get the piece placement strings
        int n = placement.length() / 4;
        char[] pieces = new char[n];
        int[] orien  =new int[n];
        int[]  xPos = new int[n];
        int[]  yPos = new int[n];


        for(int i = 0; i < n; i++){
            pieces[i] = placement.charAt(0 + 4 * i);
            xPos[i] = placement.charAt(1 + 4 * i) - '0';
            yPos[i] = placement.charAt(2 + 4 * i) - '0';
            orien[i] = placement.charAt(3 + 4 * i) - '0';
            Image image = new Image(Viewer.class.getResource(URI_BASE + pieces[i] + ".png").toString());
            ImageView iv1 = new ImageView();
            iv1.setImage((image));

            //rotate the image
            iv1.setRotate(orien[i] * 90);
            root.getChildren().add(iv1);
            //Get the number of squares of width, with input pieces[i] and orien[i]
            int width = getSquaresOfWidth(pieces[i], orien[i]);
            iv1.setFitWidth(width * SQUARE_SIZE);
            //Get the number of squares of height, with input pieces[i] and orien[i]
            int height = getSquaresOfHeight(pieces[i], orien[i]);
            iv1.setFitHeight(height * SQUARE_SIZE);

            double x;
            double y;
            //The x, y coordinates of where it should be translated to
            x = initialX + xPos[i] * SQUARE_SIZE;
            y = initialY + yPos[i] * SQUARE_SIZE;
            if(orien[i] == 1 || orien[i] == 3) {
                x -= ((width - height) / 2.0) * SQUARE_SIZE;
                y += ((width - height) / 2.0) * SQUARE_SIZE;
            }

            
            iv1.setX(x);
            iv1.setY(y);



        }
        // FIXME Task 4: implement the simple placement viewer

    }

    public int getSquaresOfWidth(char piece, int orientation){
        int pieceIndex = piece - 97;
        return width_and_height[pieceIndex][0];
    }

    public int getSquaresOfHeight(char piece, int orientation){
        int pieceIndex = piece - 97;
        return width_and_height[pieceIndex][1];
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());
                textField.clear();
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FocusGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);


        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
