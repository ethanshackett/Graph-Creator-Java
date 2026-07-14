package GraphCreator;

public class Vertex {
    private final String name;
    private final double x;
    private final double y;

    /**
     * Creates a Vertex object with the specified x, y, and name.
     *
     * @param x the x-coordinate of the vertex
     * @param y the y-coordinate of the vertex
     * @param name the label/name of the vertex
     */
    public Vertex(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;

    }

    /**
     * getter for Vertex object's name.
     *
     * @return the name of the current vertex object.
     */
    public String getName() {
        return this.name;

    }

    /**
     * getter for Vertex object's x coordinate.
     *
     * @return the x coordinate of the current vertex object.
     */
    public double getX() {
        return this.x;

    }

    /**
     * getter for Vertex object's y coordinate.
     *
     * @return the y coordinate of the current vertex object.
     */
    public double getY() {
        return this.y;

    }
}


