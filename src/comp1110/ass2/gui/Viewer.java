package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField textField;

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {
        int n = placement.length() / 4;
        char[] pieces = new char[n];
        char[] orien  =new char[n];
        int[]  xPos = new int[n];
        int[]  yPos = new int[n];
        for(int i = 0; i < n; i++){
            pieces[i] = placement.charAt(4*i);
            orien[i] = placement.charAt(3 + 4*i);
            xPos[i] = placement.charAt(1 + 4*i) - '0';
            yPos[i] = placement.charAt(2+4*i) - '0';
        }

        Image[] image = new Image[n];
        ImageView iv1 = new ImageView();

        for(int i = 0; i < n; i++){
            image[i] = new Image(URI_BASE + pieces[i] + ".png");
            iv1.setImage(new Image(Viewer.class.getResource(URI_BASE + pieces[i] + ".png").toString()));
        }

        // FIXME Task 4: implement the simple placement viewer

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
