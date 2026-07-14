package GraphCreator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.List;

class GraphTest {
    Graph graph;

    @BeforeEach
    void setUp() {
        graph = new Graph();
        for (int i = 0; i < 9; i++) {
            graph.addVertex(0, 0);
        }
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(2, 3);
        graph.addEdge(2, 7);
        //graph.addEdge(2, 8);
        graph.addEdge(3, 4);
        graph.addEdge(4, 6);
        graph.addEdge(5, 7);

    }

    @Nested
    @DisplayName("addVertex() Tests")
    class addVertexTests {
        @Test
        @DisplayName("Test 1- Proper Vertex counting")
        void addVertexT1() {
            for (int i = 0; i < 10; i++) {
                graph.addVertex(0,0);
            }
            assertEquals(19, graph.getNumOfVertices());
        }

        @Test
        @DisplayName("Test 2 - Proper naming past 26 vertices")
        void addVertexT2() {
            for (int i = 0; i < 18; i++) {
                graph.addVertex(0,0);
            }
            assertEquals("AA", graph.getVertices().get(26).getName());
        }

        @Test
        @DisplayName("Test 3 - Proper key creation in edges hashmap")
        void addVertexT3() {
            graph.addVertex(0,0);
            assertTrue(graph.getEdges().containsKey(graph.getVertices().get(8)));
        }
    }

    @Nested
    @DisplayName("addEdge() Tests")
    class addEdgeTests {
        @Test
        @DisplayName("Test 1 - Properly counting edges")
        void addEdgeT1() {
            assertEquals(3, graph.getEdges().get(graph.getVertices().get(2)).size());
        }

        @Test
        @DisplayName("Test 2 - Adding Edges from both sides")
        void addEdgeT2() {
            Vertex v1 = graph.getVertices().get(0);
            Vertex v2 = graph.getVertices().get(1);

            assertTrue(graph.getEdges().get(v1).contains(1) && graph.getEdges().get(v2).contains(0));
        }
    }

    @Nested
    @DisplayName("hasCycles() Tests")
    class hasCyclesTests {
        @Test
        @DisplayName("Test 1 - empty graph --> false")
        void hasCyclesT1() {
            Graph emptyGraph = new Graph();
            assertFalse(emptyGraph.hasCycles());
        }

        @Test
        @DisplayName("Test 2 - single node graph --> false")
        void hasCyclesT2() {
            Graph oneNodeGraph = new Graph();
            oneNodeGraph.addVertex(0,0);
            assertFalse(oneNodeGraph.hasCycles());
        }

        @Test
        @DisplayName("Test 3 - no cycles --> false")
        void hasCyclesT3() {
            assertFalse(graph.hasCycles());
        }

        @Test
        @DisplayName("Test 4 - contains cycles --> true")
        void hasCyclesT4() {
            graph.addEdge(5, 6);
            assertTrue(graph.hasCycles());
        }

        @Test
        @DisplayName("Test 5 - contains cycles --> true")
        void hasCyclesT5() {
            graph.addEdge(1, 6);
            assertTrue(graph.hasCycles());
        }
    }

    @Nested
    @DisplayName("isConnected() Tests")
    class isConnectedTests {
        @Test
        @DisplayName("Test 1 - empty graph --> true")
        void isConnectedT1() {
            Graph emptyGraph = new Graph();
            assertTrue(emptyGraph.isConnected());
        }

        @Test
        @DisplayName("Test 2 - single vertex graph --> true")
        void isConnectedT2() {
            Graph oneNodeGraph = new Graph();
            oneNodeGraph.addVertex(0,0);
            assertTrue(oneNodeGraph.isConnected());
        }

        @Test
        @DisplayName("Test 3 - disconnected graph --> false")
        void isConnectedT3() {
            assertFalse(graph.isConnected());
        }

        @Test
        @DisplayName("Test 4 - connected graph --> true")
        void isConnectedT4() {
            graph.addEdge(5, 6);
            assertTrue(graph.hasCycles());
        }

        @Test
        @DisplayName("Test 5 - connected graph --> true")
        void isConnected5() {
            graph.addEdge(6, 5);
            assertTrue(graph.hasCycles());
        }
    }

    @Nested
    @DisplayName("dfs() Tests")
    class dfsTests {
        @Test
        @DisplayName("Test 1 - empty graph --> empty set")
        void dfsT1() {
            Graph emptyGraph = new Graph();
            assertEquals(List.of(), List.copyOf(emptyGraph.dfs()));
        }

        @Test
        @DisplayName("Test 2 - single node graph --> single item set")
        void dfsT2() {
            Graph oneNodeGraph = new Graph();
            oneNodeGraph.addVertex(0,0);
            assertEquals(List.of(0), List.copyOf(oneNodeGraph.dfs()));
        }

        @Test
        @DisplayName("Test 3 - Disconnected, Acyclic Graph")
        void dfsT3() {
            assertEquals(List.of(0, 1, 2, 3, 4, 6, 7, 5), List.copyOf(graph.dfs()));
        }

        @Test
        @DisplayName("Test 4 - Connected, Acyclic Graph")
        void dfsT4() {
            graph.addEdge(2, 8);
            assertEquals(List.of(0, 1, 2, 3, 4, 6, 7, 5, 8), List.copyOf(graph.dfs()));
        }

        @Test
        @DisplayName("Test 5 - Disconnected, cyclic graph")
        void dfsT5() {
            graph.addEdge(5, 6);
            assertEquals(List.of(0, 1, 2, 3, 4, 6, 5, 7), List.copyOf(graph.dfs()));
        }

        @Test
        @DisplayName("Test 6 - Connected, cyclic graph")
        void dfsT6() {
            graph.addEdge(2, 8);
            graph.addEdge(5, 6);
            assertEquals(List.of(0, 1, 2, 3, 4, 6, 5, 7, 8), List.copyOf(graph.dfs()));
        }
    }

    @Nested
    @DisplayName("bfs() Tests")
    class bfsTests {
        @Test
        @DisplayName("Test 1 - empty graph")
        void bfsT1() {
            Graph emptyGraph = new Graph();
            assertEquals(List.of(), List.copyOf(emptyGraph.bfs()));
        }

        @Test
        @DisplayName("Test 2 - single node graph")
        void bfsT2() {
            Graph oneNodeGraph = new Graph();
            oneNodeGraph.addVertex(0,0);
            assertEquals(List.of(0), List.copyOf(oneNodeGraph.bfs()));
        }

        @Test
        @DisplayName("Test 3 - Disconnected, Acyclic Graph")
        void bfsT3() {
            assertEquals(List.of(0, 1, 2, 3, 7, 4, 5, 6), List.copyOf(graph.bfs()));
        }

        @Test
        @DisplayName("Test 4 - Connected, Acyclic Graph")
        void bfsT4() {
            graph.addEdge(2, 8);
            assertEquals(List.of(0, 1, 2, 3, 7, 8, 4, 5, 6), List.copyOf(graph.bfs()));
        }

        @Test
        @DisplayName("Test 5 - Disconnected, cyclic graph")
        void bfsT5() {
            graph.addEdge(5, 6);
            assertEquals(List.of(0, 1, 2, 3, 7, 4, 5, 6), List.copyOf(graph.bfs()));
        }

        @Test
        @DisplayName("Test 6 - Connected, cyclic graph")
        void bfsT6() {
            graph.addEdge(2, 8);
            graph.addEdge(5, 6);
            assertEquals(List.of(0, 1, 2, 3, 7, 8, 4, 5, 6), List.copyOf(graph.bfs()));
        }
    }
}