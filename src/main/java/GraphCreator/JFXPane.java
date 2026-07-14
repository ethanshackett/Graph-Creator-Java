package GraphCreator;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.control.Label;

import java.util.LinkedHashSet;

public class JFXPane extends Pane {
    private final Graph graph = new Graph();

    /**
     * Creates a Pane object, resizes it, gives it a border, and then adds an event that creates a
     * vertex at the location where it is clicked.
     *
     */
    public JFXPane() {
        super();

        //Pane size and border change
        this.setMaxWidth(300);
        this.setPrefSize(300, 300);
        addBorder(this);

        //On-click event which creates a circle to represent the location of a circle and
        //creates a label of its name directly above it.
        this.setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();

            Label name = new Label(graph.addVertex(x, y).getName());
            name.setAlignment(Pos.CENTER);
            name.relocate(x, y - 30);

            this.getChildren().addAll(name, new Circle(x, y, 5, Color.BLACK));

        });


    }

    /**
     * A static method that creates and sets a border to the pane that calls it. It is made static so that
     * the constructor can call it.
     *
     * @param pane the object to add a border to.
     */
    private static void addBorder(Pane pane) {
        BorderStroke border = new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                BorderWidths.DEFAULT
        );

        pane.setBorder(new Border(border));
    }

    /**
     * This method takes the names of two vertices, checks them for proper format, adds an edge via addEdge(),
     * creates a Line object, and adds the line to the current pane connecting the two vertices. If 0 is returned,
     * an edge was successfully added. If 1 or 2 is returned, then v1Name or v2Name was not in the proper format. If
     * 3 is returned, then it means the edge was already there and no extra edge was added.
     *
     * @param v1Name First of the two vertices getting connected to each other.
     * @param v2Name Second of the two vertices getting connected to each other.
     * @return integer that is 0-3 representing what happened.
     */
    public int addEdge(String v1Name, String v2Name) {
        v1Name = v1Name.trim();
        v2Name = v2Name.trim();

        //Checks if user inputs are capital letters and returns a fail indication if not
        if (!v1Name.matches("[A-Z]+")) {
            return 1;
        }
        if (!v2Name.matches("[A-Z]+")) {
            return 2;
        }

        //gets indices representing the vertices to which the names belonged.
        int v1 = getIndex(v1Name);
        int v2 = getIndex(v2Name);

        //Checks to make sure user did not try to add an edge between vertices not created yet
        //returns failure indication if too large
        if (v1 > graph.getVertices().size()) {
            return 1;
        }
        if (v2 > graph.getVertices().size()) {
            return 2;
        }

        //Checks if edge-to-create already exists and returns failure indication if so
        if (graph.getEdges().get(graph.getVertices().get(v1)).contains(v2)) {
            return 3;
        }

        //Edge creation and Line object added to pane visually connecting two vertices
        graph.addEdge(v1, v2);
        Line line = new Line(graph.getVertices().get(v1).getX(), graph.getVertices().get(v1).getY(),
                graph.getVertices().get(v2).getX(), graph.getVertices().get(v2).getY());
        line.setStrokeWidth(2);

        this.getChildren().add(line);
        return 0;

    }

    /**
     * A helper method that takes the string name of a vertex and gives the index used to reference the
     * vertex.
     *
     * @param name The name of the vertex to get the index of.
     * @return Integer used to reference the vertex which owns the name.
     */
    private int getIndex(String name) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] cArray = name.toCharArray();

        //base 26 name conversion to base 10 index
        int index = 0;
        for (int i = 0; i < cArray.length; i++) {
            index += (int) Math.pow(26, cArray.length - i - 1) * (alphabet.indexOf(cArray[i]) + 1);
        }

        return index - 1;
    }

    /**
     * When a button besides "Add Edge" is pressed, it calls this method, which responds differently based
     * on the button, and gets the text to change the bottom text field of the Pane to.
     *
     * @param input a number telling this method which button called it and essentially
     *              what logic to use.
     * @return String which is the answer to the request and is to be shown to user.
     */
    public String inputs(int input) {
        if (input == 2) {
            if (graph.isConnected()) {
                return "The graph is connected.";
            } else {
                return "The graph is not connected.";
            }
        } else if (input == 3) {
            if (graph.hasCycles()) {
                return "The graph has cycles.";
            } else {
                return "The graph doesn't have cycles.";
            }
        } else if (input == 4) { //returns depth first search path
            StringBuilder dfsString = new StringBuilder();
            LinkedHashSet<Integer> dfs = graph.dfs();
            for (int i: dfs) {
                dfsString.append(graph.getVertices().get(i).getName());

            }
            return dfsString.toString();
        } else if (input == 5) { //returns breadth first search path
            StringBuilder bfsString = new StringBuilder();
            LinkedHashSet<Integer> bfs = graph.bfs();
            for (int i: bfs) {
                bfsString.append(graph.getVertices().get(i).getName());

            }
            return bfsString.toString();
        }

        return "False input.";
    }

}
