import java.util.ArrayList;

public class DisjointSet {
    private ArrayList<Node> upTree;

    // only needs to handle integers
    public DisjointSet(int size) {
        upTree = new ArrayList<>();
        for (int i = 0; i < (size); i++) {
            Node temp = new Node(i);
            upTree.add(temp);
        }
    }

    public void union(int node1, int node2) {
        // make sure node1 and node2 aren't the same tree
        if (find(node1) != find(node2)) {
            // make sure references are at the top of the tree
            node1 = find(node1);
            node2 = find(node2);
            // find larger tree
            if (upTree.get(node1).size < upTree.get(node2).size) { // node1 has more children because it's size is more negative
                upTree.get(node2).parent = upTree.get(node1); // point smaller tree at root of bigger tree
                upTree.get(node1).size += upTree.get(node2).size; // recalculate size
            } else { // node2 has more children
                upTree.get(node1).parent = upTree.get(node2); // point smaller tree at root of bigger tree
                upTree.get(node2).size += upTree.get(node1).size; // recalculate size
            }
        }
    }

    public int find(int node) {
        // must use a smart union find with path compression to track items in the same group
        if (upTree.get(node).parent != null) {
            return find(upTree.get(node).parent.value);
        } else {
            return upTree.get(node).value;
        }
    }

    @Override
    public String toString() {
        String result = "[\n";
        for (Node node : upTree) {
            result += node.toString() + "\n";
        }
        result += "\n]";
        return result;
    }


    private class Node {
        private Node parent;
        private int value;
        private int size;

        public Node(int val) {
            this.value = val;
            this.parent = null;
            this.size = -1;
        }

        @Override
        public String toString() {
            String result = "";
            if (this.parent != null) {
                result += String.format("[ value: %d, size: %d pValue: %d]", this.value, this.size, this.parent.value);
            } else {
                result += String.format("[ value: %d, size: %d ]", this.value, this.size);
            }
            return result;
        }
    }
}
