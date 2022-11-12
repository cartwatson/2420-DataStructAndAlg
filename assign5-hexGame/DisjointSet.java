import java.util.ArrayList;

public class DisjointSet {
    // these should be in the set
    private ArrayList<Integer> groupID;

    // only needs to handle integers
    public DisjointSet(int size) {
        groupID = new ArrayList<>();
        for (int i = 0; i < (size); i++) {
            groupID.add(i);
        }
//        System.out.println(groupID);
    }

    public void union(int node1, int node2) {
        // TODO: implement find first
        // make sure node1 and node2 aren't the same tree
        if (find(node1) != find(node2)) {
            // negative number is size, positive number is parent
            // start of code from lecture ------------------------------------------------------------------------------
            if (groupID[node1] < groupID[node2]) {
                // node2 is larger because it is more negative
                groupID[node2] += groupID[node1]; // add size from node1 to node2
                groupID[node1] = node2; // node2 new root
            } else {
                // root1 is equal or larger
                groupID[node1] += groupID[node2]; // add size from node2 to node1
                groupID[node2] = node1; // node1 new root
            }
            // end of code from lecture --------------------------------------------------------------------------------
        }
    }

    public int find(int node) {
        // must use a smart union find with path compression to track items in the same group
        // TODO: Implement
        return 0;
    }


}
