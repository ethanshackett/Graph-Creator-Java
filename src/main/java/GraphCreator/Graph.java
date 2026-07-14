package GraphCreator; //change

import java.util.*; //change

public class Graph {
    /**
     * Hashmap containing Vertex object keys and Integer ArrayList values.
     *
     * <p> The ArrayList values are indices representing other vertices. In other words,
     * each index/vertex in the arraylist represents on edge between itself and the key vertex.</p>
     *
     */
    private final HashMap<Vertex, ArrayList<Integer>> edges = new HashMap<>();

    /**
     * ArrayList consisting of every vertex in creation order. Mostly used to reference a previously
     * created Vertex object and also to check its edges in the instance variable: edges.
     *
     */
    private final ArrayList<Vertex> vertices = new ArrayList<>();

    /** instance variable keeping track of how many vertices are in the graph. */
    private int numOfVertices;

    /**
     * Creates an empty Graph object. Initializes 3 instance variables.
     *
     */
    public Graph() {

    }

    /**
     * Adds a vertex to the current Graph object as specified x and y coordinates.
     * Also picks a name for the vertex based on base-26
     *
     * @param x the x coordinate of the vertex to be created.
     * @param y the y coordinate of the vertex to be created.
     * @return a reference to the created vertex.
     */
    public Vertex addVertex(double x, double y) {
        numOfVertices++;

        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray(); //Represents base-26 symbols
        int placeHolder = numOfVertices;
        StringBuilder name = new StringBuilder(); //Used to build base-26 number in

        //conversion of the number of vertices from base 10 to base 26
        while (placeHolder > 0) {
            name.insert(0, alphabet[(placeHolder - 1) % 26]);
            placeHolder = (placeHolder - 1) / 26;

        }

        //vertex creation
        Vertex v = new Vertex(x, y, name.toString());
        vertices.add(v);
        edges.put(v, new ArrayList<>());

        return v;
    }

    /**
     * Adds an edge between two vertices given their indices. It adds the edge once forwards and backwards
     * to assist with recognizing an edge from the perspective of both vertices.
     *
     * @param v1 an index associated with a specific vertex in instance variable: vertices
     * @param v2 an index associated with a specific vertex in instance variable: vertices
     */
    public void addEdge(int v1, int v2) {
        edges.get(vertices.get(v1)).add(v2);
        edges.get(vertices.get(v2)).add(v1);

    }

    /**
     * Uses depth first search recursive method to check how many Vertices are connected
     * to first Vertex. Returns prematurely if graph is empty.
     *
     * @return result of if number of vertices found connected to the first Vertex is equal to the total vertices.
     */
    public boolean hasCycles() {
        if (numOfVertices == 0) {
            return false;
        }
        return cycleHelper(0, new HashSet<>(), new HashSet<>());

    }

    /**
     * Recursively traverses the current graph to answer if it contains at least one cycle.
     *
     * @param start Represents the current vertex while recursing.
     * @param targets A set of vertices already encountered that if encountered again through a unique edge,
     *                it means that there are cycles.
     * @param seenEdges A set of integer lists representing all the edges already passed.
     *
     * @return result of whether a cycle was found.
     */
    private boolean cycleHelper(int start, Set<Integer> targets, Set<List<Integer>> seenEdges) {
        targets.add(start);
        boolean found = false;

        //recursively finds new edges to traverse in a dfs-like search
        for (Integer i: edges.get(vertices.get(start))) {
            //If new edge
            if (!seenEdges.contains(List.of(start, i))) {
                //adds both directions of edge to seenEdges set
                seenEdges.add(List.of(start, i));
                seenEdges.add(List.of(i, start));

                //If repeat Vertex found, cycle found. If not, method calls itself on new Vertex
                if (targets.contains(i)) {
                    return true;

                } else {
                    found = cycleHelper(i, targets, seenEdges);
                    if (found) {
                        break;

                    }
                }
            }
        }
        return found;

    }

    /**
     * Recursively traverses the current graph to check if it is connected. Returns true prematurely if
     * graph is empty.
     *
     * @return result of recursive helper method which checks if current graph is connected.
     */
    public boolean isConnected() {
        if (numOfVertices == 0) {
            return true;
        }
        return numOfVertices == dfsHelper(0, new LinkedHashSet<>()).size();

    }

    /**
     * Calls recursive helper method and returns an ordered collection of the path followed.
     * Returns empty list prematurely if the graph is empty.
     *
     * @return returns result of recursive helper method dfsHelper.
     */
    public LinkedHashSet<Integer> dfs() {
        LinkedHashSet<Integer> seen = new LinkedHashSet<>();
        if (numOfVertices == 0) {
            return seen;

        }
        return dfsHelper(0, seen);
    }

    /**
     * Recursive helper method returning the pathing of a depth-first search starting from vertex A.
     *
     * @param start The current vertex the recursive method is at.
     * @param seen LinkedHashSet keeping record of all Vertices seen and in what order.
     * @return the order in which the depth first search found all the vertices it could.
     */
    private LinkedHashSet<Integer> dfsHelper(int start, LinkedHashSet<Integer> seen) {
        //adds current Vertex
        seen.add(start);

        //recursively calls itself on the first Vertex it hasn't traversed
        for (Integer i: edges.get(vertices.get(start))) {
            if (!seen.contains(i)) {
                dfsHelper(i, seen);

            }
        }
        return seen;

    }

    /**
     * Calls recursive helper method and returns ordered collection of the path followed.
     * Returns empty list prematurely if graph is empty.
     *
     * @return returns result of recursive helper method bfsHelper.
     */
    public LinkedHashSet<Integer> bfs() {
        LinkedHashSet<Integer> seen = new LinkedHashSet<>();
        ArrayDeque<Integer> next = new ArrayDeque<>();
        if (numOfVertices == 0) {
            return seen;

        }
        seen.add(0);
        next.add(0);

        return bfsHelper(0, seen, next);
    }

    /**
     * Recursive helper method returning the pathing of a breadth-first search starting from vertex A.
     *
     * @param start The current vertex the recursive method is at.
     * @param seen A LinkedHashSet representing all the vertices encountered in order.
     * @param next A Deque representing, in order, what Vertices the method will call itself on next.
     * @return returns result of recursive helper method dfsHelper.
     */
    private LinkedHashSet<Integer> bfsHelper(int start, LinkedHashSet<Integer> seen, Deque<Integer> next) {
        //Removes current Vertex from next list
        next.removeFirst();

        //adds each not-seen vertex to seen set and next list
        for (Integer i: edges.get(vertices.get(start))) {
            if (!seen.contains(i)) {
                next.addLast(i);
                seen.add(i);

            }
        }

        //Until there are no more new vertices found, method calls itself
        //on the first remaining Vertex in next list
        while(!next.isEmpty()) {
            bfsHelper(next.getFirst(), seen, next);

        }
        return seen;
    }

    /**
     * getter method for Vertex ArrayList
     *
     * @return private variable vertices
     */
    public ArrayList<Vertex> getVertices() {
        return vertices;

    }

    /**
     * getter method for edge hashmap
     *
     * @return private variable edges
     */
    public HashMap<Vertex, ArrayList<Integer>> getEdges() {
        return edges;
    }


    /**
     * getter method for int numOfVertices
     *
     * @return private variable numOfVertices
     */
    public int getNumOfVertices() {
        return numOfVertices;
    }
}


