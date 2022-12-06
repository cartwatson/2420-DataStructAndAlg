import java.util.*;

public class Graph {
    private final GraphNode[] vertices;  // Adjacency list for graph.
    private final String name;  //The file from which the graph was created.
    private int[][] residual; // **** NOT SOURCE CODE ****

    public Graph(String name, int vertexCount) {
        this.name = name;

        vertices = new GraphNode[vertexCount];
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            vertices[vertex] = new GraphNode(vertex);
        }

        residual = new int[vertices.length][vertices.length];

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

    /*
       ----  STARTER CODE ABOVE ----
       Starting code may be modified
       Representation as adjacency list may not be changed
       ---- PERSONAL CODE BELOW ----
       Private methods can be added to help
       Function signatures may be changed
    */

    /*
      The display of the augmenting paths and edges is done as part of the findMaxFlow method.
      Following the call of findMaxflow, the total flow is displayed.
      For the findMinCut, the display of which edges to cut is performed during the findMinCut method.
      I prefer to have those data items returned from the methods and then separate reporting,
      but that begins to distract from the core of the assignment.
    */

    /** Algorithm to find max-flow in a network */
    public int findMaxFlow(int s, int t, boolean report) {
        // TODO: abstract reporting (STRETCH GOAL)
        // print header
        if (report) {
            System.out.println("-- Max Flow: " + this.name + " --");
        }
        // set total flow to zero
        int totalFlow = 0;
        // while hasAugmentingPath(s, t)
        while (this.hasAugmentingPath(s, t)) {
            // set available flow to the largest possible integer java can represent
            int availableFlow = Integer.MAX_VALUE;
            // v is current vertex
            int v = t;
            // init path tracker
            ArrayList<Integer> path = new ArrayList<>();
            // follow the augmenting path from t to s
            while (v != s) {
                // calculate residual flow
                int residualFlow = 0;
                for (GraphNode.EdgeInfo e : vertices[vertices[v].parent].successor) {
                    if (e.to == v) {
                        residualFlow = e.capacity - residual[vertices[v].parent][v];

                    }
                }
                // available flow = min of available flow or residual flow at vertex v
                availableFlow = Math.min(availableFlow, residualFlow);
                // track path
                path.add(v);
                // update v to the parent of itself
                v = vertices[v].parent;
            }
            path.add(s);
            // follow the augmenting path from t to s
            // update residual graph
            for (int currV = path.size() - 1; currV > 0; currV--) {
                // subtract available flow at vertex v in direction of s to t
                residual[path.get(currV - 1)][path.get(currV)] -= availableFlow;
                // add available flow at vertex v in direction of t to s
                residual[path.get(currV)][path.get(currV - 1)] += availableFlow;
            }
            // print out flow
            if (report) {
                System.out.print("Flow "  + availableFlow + ": ");
                for (int i = path.size() - 1; i >= 0; i--) {
                    System.out.print(path.get(i) + " ");
                }
                System.out.println();
            }

            // add available flow to total flow
            totalFlow += availableFlow;
        }

        if (report) {
            System.out.println();
            for (int i = 0; i < residual.length; i++) {
                for (int j = 0; j < residual.length; j++) {
                    if (residual[i][j] > 0 ) {
                        System.out.println("Edge(" + i + ", " + j + ") transports " + residual[i][j] + " items");
                    }
                }
            }
            System.out.println();
            /** NOTE TO GRADER
             * not sure if you wanted the totalFlow reporting inside or outside this function...
             * uncomment the next two lines if totalFlow reporting is to be done inside the graph class
             */
            // System.out.printf("Total Flow: %d\n", totalFlow);
            // System.out.println();
        }

        // return total flow
        return totalFlow;
    }

    /** Algorithm to find an augmenting path in a network */
    private boolean hasAugmentingPath(int s, int t) {
        // reset the vertex that flow comes from on all vertices
        for (GraphNode i : vertices) {
            i.parent = -1;
        }
        // init queue
        Queue<Integer> q = new LinkedList<>();
        q.add(s);
        // while queue is not empty and vertex t does not have a parent
        while (!q.isEmpty() && vertices[t].parent == -1) {
            int v = q.remove();
            // for all successor edges from v
            for (GraphNode.EdgeInfo e : vertices[v].successor) {
                int w = e.to;
                // if (there is residual capacity from v to w) and (not already part of the augmenting path) and (it isn't vertex s) then it can be used
                if (e.capacity - residual[v][w] != 0 && vertices[w].parent == -1 && w != s) {
                    // remember the path; set parent of w to v
                    vertices[w].parent = v;
                    q.add(w);
                }
            }
        }
        // if vertex t has a parent: an augmenting path exists
        // else: no augmenting path
        return (vertices[t].parent != -1);
    }

    /** Algorithm to find the min-cut edges in a network */
    // NOTE: added report to parameters
    public void findMinCut(int s, boolean report) {
        // TODO: abstract reporting (STRETCH GOAL)
        // print header
        if (report) {
            System.out.println("-- Min Cut: " + this.name + " --");
        }
        // Based on the max flow algorithm, compute the final residual graph
        // Find the set of vertices that are reachable from the source vertex in the residual graph; the set R includes s.  Call those vertices R.
        // All edges from a vertex in R to a vertex not in R are the minimum cut edges

        // print each edge to cut
        if (report) {
            //System.out.println("-- Min Cut Edge: (" + v1 + ", " + v2 + ")"); // v1/v2 are vertexes between which the edge is cut
        }
    }
}
