import java.util.*;

public class Graph {
    private final GraphNode[] vertices;  // Adjacency list for graph.
    private final String name;  //The file from which the graph was created.
    private final int[][] residual; // **** NOT SOURCE CODE ****
    private final ArrayList<ArrayList<Integer>> paths = new ArrayList<>();
    private final ArrayList<ArrayList<Integer>> minEdges = new ArrayList<>();

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

    /** Algorithm to find max-flow in a network */
    public int findMaxFlow(int source, int sink, boolean report) {
        // init total flow
        int totalFlow = 0;
        while (this.hasAugmentingPath(source, sink)) {
            // set available flow to the largest possible integer java can represent
            int availableFlow = Integer.MAX_VALUE;
            // currVertex is current vertex
            int currVertex = sink;
            // init path tracker
            ArrayList<Integer> path = new ArrayList<>();
            // follow the augmenting path from sink to source
            while (currVertex != source) {
                // calculate residual flow
                int residualFlow = 0;
                boolean residualFlowSet = false;
                for (GraphNode.EdgeInfo e : vertices[vertices[currVertex].parent].successor) {
                    if (e.to == currVertex) {
                        residualFlow = e.capacity - residual[vertices[currVertex].parent][currVertex];
                        residualFlowSet = true;
                    }
                }
                // work with backflow
                if (!residualFlowSet) {
                    residualFlow = Math.abs(residual[vertices[currVertex].parent][currVertex]);
                }

                // available flow = min of available flow or residual flow at vertex currVertex
                availableFlow = Math.min(availableFlow, residualFlow);
                // track path
                path.add(currVertex);
                // update currVertex to the parent of itself
                currVertex = vertices[currVertex].parent;
            }
            path.add(source);
            // follow the augmenting path from sink to source
            // update residual graph
            for (int currV = path.size() - 1; currV > 0; currV--) {
                // subtract available flow at vertex currVertex in direction of source to sink
                residual[path.get(currV - 1)][path.get(currV)] -= availableFlow;
                // add available flow at vertex currVertex in direction of sink to source
                residual[path.get(currV)][path.get(currV - 1)] += availableFlow;
            }

            // add to class scoped paths
            path.add(availableFlow);
            paths.add(path);

            // add available flow to total flow
            totalFlow += availableFlow;
        }

        // abstracted report
        if (report) {
            this.findMaxReport(totalFlow);
        }

        // return total flow
        return totalFlow;
    }

    /** Print out flow paths and edges */
    private void findMaxReport(int totalFlow) {
        // print header
        System.out.println("-- Max Flow: " + this.name + " --");
        // print paths
        for (ArrayList<Integer> path : paths) {
            System.out.print("Flow " + path.get(path.size() - 1) + ": ");
            for (int i = path.size() - 2; i >= 0; i--) {
                System.out.print(path.get(i) + " ");
            }
            System.out.println();
        }
        System.out.println();
        // print edges
        for (int i = 0; i < residual.length; i++) {
            for (int j = 0; j < residual.length; j++) {
                if (residual[i][j] > 0 ) {
                    System.out.println("Edge(" + i + ", " + j + ") transports " + residual[i][j] + " items");
                }
            }
        }
        /******************************************** NOTE TO GRADER ***************************************************
         * not sure if you wanted the totalFlow reporting inside or outside this function...
         * comment the next line if totalFlow reporting is to be done inside the graph class
         **************************************************************************************************************/
        System.out.printf("Total Flow: %d\n", totalFlow);
        System.out.println();
    }

    /** Algorithm to find an augmenting path in a network */
    private boolean hasAugmentingPath(int source, int sink) {
        // reset the vertex that flow comes from on all vertices
        for (GraphNode i : vertices) {
            i.parent = -1;
        }
        // init queue
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        // while queue is not empty and vertex sink does not have a parent
        while (!queue.isEmpty() && vertices[sink].parent == -1) {
            int currVertex = queue.remove();
            // for all successor edges from currVertex // this checks all real edges only, does not check backflow
            for (GraphNode.EdgeInfo e : vertices[currVertex].successor) {
                int toVertex = e.to;
                // if (there is residual capacity from currVertex to toVertex) and (not already part of the augmenting path) and (it isn't vertex source) then it can be used
                if (e.capacity - residual[currVertex][toVertex] != 0 && vertices[toVertex].parent == -1 && toVertex != source) {
                    // remember the path; set parent of toVertex to currVertex
                    vertices[toVertex].parent = currVertex;
                    queue.add(toVertex);
                }
            }
            // check back flow
            for (int j = 0; j < vertices.length; j++) {
                if (residual[currVertex][j] < 0) { // opportunity for backflow
                    int toVertex = j;
                    if (vertices[toVertex].parent == -1 && toVertex != source) {
                        vertices[toVertex].parent = currVertex;
                        queue.add(toVertex);
                    }
                }
            }
        }

        // if vertex sink has a parent: an augmenting path exists
        // else: no augmenting path
        return (vertices[sink].parent != -1);
    }

    /** Algorithm to find the min-cut edges in a network */
    // NOTE: added report to parameters
    public void findMinCut(int s, boolean report) {
        // init set
        ArrayList<Integer> reachableFromSource = new ArrayList<>();
        reachableFromSource.add(s);
        // init queue
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);

        // find all vertices reachable from s, add to set
        while (!queue.isEmpty()) {
            int currVertex = queue.remove();
            // residual is graph with forward and backward topology
            for (GraphNode.EdgeInfo edge : vertices[currVertex].successor) {
                // node that used all forward capacity // topologically, faces backwards from original orientation
                if (edge.capacity == residual[currVertex][edge.to]) {
                    // not reachable from current node // thus don't add to queue
                    continue;
                }
                // node that only used some forward capacity // topologically, faces both directions
                else if (edge.capacity > residual[currVertex][edge.to] && residual[currVertex][edge.to] > 0) {
                    // reachable from current node
                    reachableFromSource.add(edge.to);
                    // add to queue
                    queue.add(edge.to);
                }
            }

            // check back flow
            for (int j = 0; j < vertices.length; j++) { // j will be toVertex
                if (residual[currVertex][j] < 0) {
                    if (vertices[j].parent == currVertex) {
                        reachableFromSource.add(j);
                    }
                }
            }
        }

        // cut all paths that are used between set and not set
        for (Integer i : reachableFromSource) {
            int currVertex = i;
            for (GraphNode.EdgeInfo edge : vertices[currVertex].successor) {
                if (!reachableFromSource.contains(edge.to)) {
                    ArrayList<Integer> e = new ArrayList<>();
                    e.add(currVertex);
                    e.add(edge.to);
                    e.add(residual[currVertex][edge.to]);
                    minEdges.add(e);
                }
            }
        }

        // abstracted report
        if (report) {
            this.findMinCutReport();
        }
    }

    /** Print out edges to cut */
    private void findMinCutReport() {
        System.out.println("-- Min Cut: " + this.name + " --");
        for (ArrayList<Integer> e : minEdges) {
            System.out.println("Min Cut Edge: (" + e.get(0) + ", " + e.get(1) + "): " + e.get(2));
        }
        System.out.println();
    }
}
