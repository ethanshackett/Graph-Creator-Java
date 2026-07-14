package GraphCreator;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class JFXMain extends Application {
    /**
     * A method which creates and handles the logic of the stage, scene, and controls.
     *
     * @param stage The object representing an instance of the top level class of JavaFX
     */
    @Override
    public void start(Stage stage) {
        //Creation, positioning, and adding of top HBox controls
        Button b1 = new Button("Add Edge");
        Label l1 = new Label("Vertex 1");
        TextField t1 = new TextField();
        Label l2 = new Label("Vertex 2");
        TextField t2 = new TextField();

        t1.setPrefWidth(60);
        t2.setPrefWidth(60);

        HBox top = new HBox(10);
        top.setAlignment(Pos.CENTER);
        top.getChildren().addAll(b1, l1, t1, l2, t2);

        JFXPane pane = new JFXPane(); //Pane creation

        //Bottom button Hbox creation, positioning, and adding of controls
        Button b2 = new Button("Is Connected?");
        Button b3 = new Button("Has Cycles?");
        Button b4 = new Button("Depth First Search");
        Button b5 = new Button("Breadth First Search");

        HBox bottom1 = new HBox(10);
        bottom1.setAlignment(Pos.CENTER);
        bottom1.getChildren().addAll(b2, b3, b4, b5);

        //Bottom text field Hbox creation, positioning, and adding of controls
        TextField output = new TextField();
        output.setEditable(false);
        output.setPrefWidth(435);

        HBox bottom2 = new HBox(10);
        bottom2.setAlignment(Pos.CENTER);
        bottom2.getChildren().addAll(output);

        //Vertical box containing all 3 Hbox's and the Pane
        VBox all = new VBox(10);
        all.setAlignment(Pos.CENTER);
        all.getChildren().addAll(top, pane, bottom1, bottom2);

        //Add Edge button press event
        b1.setOnAction(event -> {
            int added = pane.addEdge(t1.getText(), t2.getText());
            if (added == 1) {
                output.setText("Non-existent Vertex Name for Vertex 1: " + t1.getText());
            } else if (added == 2) {
                output.setText("Non-existent Vertex Name for Vertex 2: " + t2.getText());
            } else if (added == 3) {
                output.setText("This edge already exists.");
            }
        });

        //Button: Is Connected?
        b2.setOnAction(event -> output.setText(pane.inputs(2)));

        //Button: Has Cycles?
        b3.setOnAction(event -> output.setText(pane.inputs(3)));

        //Button: Depth First Search
        b4.setOnAction(event -> output.setText(pane.inputs(4)));

        //Button: Breadth First Search
        b5.setOnAction(event -> output.setText(pane.inputs(5)));

        //Sizing and starting of the JavaFX application
        Scene scene = new Scene(all, 500, 450);
        stage.setTitle("Project 4");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method which launches an application.
     *
     * @param args Main method arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
