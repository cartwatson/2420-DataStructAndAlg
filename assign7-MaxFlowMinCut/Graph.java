import java.util.*;

public class Graph {
    private final GraphNode[] vertices;  // Adjacency list for graph.
    private final String name;  //The file from which the graph was created.

    public Graph(String name, int vertexCount) {
        this.name = name;

        vertices = new GraphNode[vertexCount];
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            vertices[vertex] = new GraphNode(vertex);
        }
    }

    public boolean addEdge(int source, int destination, int capacity) {
        // A bit of validation
        if (source < 0 || source >= vertices.length) return false;
        if (destination < 0 || destination >= vertices.length) return false;

        // This adds the actual requested edge, along with its capacity
        vertices[source].addEdge(source, destination, capacity);

        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("The Graph " + name + " \n");
        for (var vertex : vertices) {
            sb.append((vertex.toString()));
        }
        return sb.toString();
    }

    /* ----  STARTER CODE ABOVE ----
       Starting code may be modified
       Representation as adjacency list may not be changed
       ---- PERSONAL CODE BELOW ----
       Private methods can be added to help
    */

    /*
    * The display of the augmenting paths and edges is done as part of the findMaxFlow method.
    * Following the call of findMaxflow, the total flow is displayed.
    * For the findMinCut, the display of which edges to cut is performed during the findMinCut method.
    * I prefer to have those data items returned from the methods and then separate reporting,
    * but that begins to distract from the core of the assignment.
    * */

    /** Algorithm to find max-flow in a network */
    public int findMaxFlow(int s, int t, boolean report) {
        // print header
        System.out.println("-- Max Flow: " + this.name + " --");
        // set total flow to zero
        int totalFlow = 0;
        // while hasAugmentingPath(s, t)
        while (this.hasAugmentingPath(s, t)) {
            // set available flow to the largest possible integer java can represent
            int availableFlow = Integer.MAX_VALUE;
            // compute augmenting path from t to s

            // follow the augmenting path from t to s
                // v is current vertex
                // available flow = min of available flow or residual flow at vertex v
                // update residual graph
                    // subtract available flow at vertex v in direction of s to t
                    // add available flow at vertex v in direction of t to s
            // add available flow to total flow
            totalFlow += availableFlow;
        }
        // return total flow
        return totalFlow;
    }

    /** Algorithm to find an augmenting path in a network */
    private boolean hasAugmentingPath(int s, int t) {
        // reset the "parent" on all vertices; parent is the vertex that flow comes from, computed during this algorithm
        // add s to the queue
        // while queue is not empty and vertex t does not have a parent
            // remove from queue as vertex v
            // for all successor edges from v
                // for the edge, call the other vertex w
                // if there is residual capacity from v to w and not already part of the augmenting path, and it isn't vertex s, then it can be used
                    // remember the path; set parent of w to v
                    // add w to the queue
        // if vertex t has a parent, then there is an augmenting path from s to t
        return false;
    }

    /** Algorithm to find the min-cut edges in a network */
    public void findMinCut(int s) {
        // print header
        System.out.println("-- Min Cut: " + this.name + " --");
        // Based on the max flow algorithm, compute the final residual graph
        // Find the set of vertices that are reachable from the source vertex in the residual graph; the set R includes s.  Call those vertices R.
        // All edges from a vertex in R to a vertex not in R are the minimum cut edges
    }
}
