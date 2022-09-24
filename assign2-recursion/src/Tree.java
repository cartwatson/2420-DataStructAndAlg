import java.util.*;

public class Tree<E extends Comparable<? super E>> {
    private BinaryTreeNode root;  // Root of tree
    private String name;     // Name of tree


    // Create an empty tree
        // @param label Name of tree
    public Tree(String label) {
        name = label;
    }

    // Create BST from ArrayList
        // @param arr   List of elements to be added
        // @param label Name of tree
    public Tree(ArrayList<E> arr, String label) {
        name = label;
        for (E key : arr) {
            insert(key);
        }
    }

    // Create BST from Array
        // @param arr   List of elements to be added
        // @param label Name of  tree
    public Tree(E[] arr, String label) {
        name = label;
        for (E key : arr) {
            insert(key);
        }
    }


    // Return a string containing the tree contents as a tree with one node per line -----------------------------------
    public String toString() {
        // TODO: return a string containing a visual (sideways) view of the tree
        String message = "";
        // return tree name
        message = message.concat(this.name + "\n");
        // TODO: keys/value of tree with parents in square brackets to the right of the node
        message = message.concat(traversal(root, 0));
        // if no nodes, output "Empty Tree"
        if (this.root == null) { message = "Empty Tree"; }
        return message;
    }

    private String traversal(BinaryTreeNode x, int level) {
        String subMessage = "";
        String parentMessage = "";
        String indent = " ".repeat(level);

        // generate parent message
        if (x.parent != null) { parentMessage = " [" + x.parent.key + "]\n"; }
        else { parentMessage = " [No Parent]\n"; }

        // add nodes with no children
        if (x.left == null && x.right == null) {
            subMessage = subMessage.concat(indent + x.key.toString() + parentMessage);
        }
        // run down right side, add nodes after recursion
        if (x.right != null) {
            subMessage = subMessage.concat(traversal(x.right, level + 1));
            subMessage = subMessage.concat(indent + x.key.toString() + parentMessage);
        }
        // run down left side, add nodes before recursion
        if (x.left != null) {
            // check to not double add nodes with two children
            if (x.right == null) { subMessage = subMessage.concat(indent + x.key.toString() + parentMessage);}
            subMessage = subMessage.concat(traversal(x.left, level + 1));
        }
        return subMessage;
    }

    // Return a string containing the tree contents as a single line ---------------------------------------------------
    public String inOrderToString() {
        // TODO: returns a string containing the in order traversal
        String message = "";
        // TODO: includes tree name
        message = message.concat(this.name + ": ");
        // TODO: just a sorted list of all values
        // go through all values, sort and print
        message = message.concat(inOrderTraversal(this.root));
        return message;
    }

    private String inOrderTraversal(BinaryTreeNode x) {
        String subMessage = "";
        // add nodes with no children
        if (x.left == null && x.right == null) {
            subMessage = subMessage.concat(x.key.toString() + " ");
        }
        // run down left side, add nodes after recursion
        if (x.left != null) {
            subMessage = subMessage.concat(inOrderTraversal(x.left));
            subMessage = subMessage.concat(x.key.toString() + " ");
        }
        // run down right side, add nodes before recursion
        if (x.right != null) {
            // check to not double add nodes with two children
            if (x.left == null) { subMessage = subMessage.concat(x.key.toString() + " "); }
            subMessage = subMessage.concat(inOrderTraversal(x.right));
        }
        return subMessage;
    }

    // reverse left and right children recursively ---------------------------------------------------------------------
    public void flip() {
        // TODO: swap left and right children recursively
    }

    // Returns the in-order successor of the specified node ------------------------------------------------------------
        // @param node node from which to find the in-order successor
    public BinaryTreeNode inOrderSuccessor(BinaryTreeNode node) {
        // TODO: find the node with the value after the given node
        // TODO: if right node, then follow that path
        // TODO: else, the successor has to be a parent
            // TODO: if parent.right != givenNode, then return parent
            // TODO: else, traverse up the tree
        // TODO: if parent or not have a successor, return null
        return null;
    }

    // Counts number of nodes in specified level
        // @param level, Level in tree, root is zero
        // @return count of number of nodes at specified level
    public int nodesInLevel(int level) {
        int counter = 0;
        // TODO: returns number of nodes at specific level
        // TODO: if level is deeper than tree, zero is returned
        return counter;
    }

    // Print all paths from root to leaves
    public void printAllPaths() {
        // TODO: prints out all root-leaves paths in tree
        // one per line
    }

    // Counts all non-null binary search trees embedded in tree
        // @return Count of embedded binary search trees
    public int countBST() {
        // TODO:
        return 0;
    }

    // Insert into a bst tree; duplicates are allowed
        // @param x the item to insert.
    public void insert(E x) {
        root = insert(x, root, null);
    }

    public BinaryTreeNode getByKey(E key) {
        // TODO: perform traversal of tree
        // TODO: return node with the associated key
        // assume key exists and that were in a BST
        return null;
    }

    // Balance the tree
    public void balanceTree() {
        // TODO: height balance a tree in a new tree
        // TODO: gather values from original tree, put in ArrayList
        // TODO: binary traversal insert
        // use built in insert
    }

    /**
     * Internal method to insert into a subtree.
     * In tree is balanced, this routine runs in O(log n)
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryTreeNode insert(E x, BinaryTreeNode t, BinaryTreeNode parent) {
        if (t == null) return new BinaryTreeNode(x, null, null, parent);

        int compareResult = x.compareTo(t.key);
        if (compareResult < 0) {
            t.left = insert(x, t.left, t);
        } else {
            t.right = insert(x, t.right, t);
        }

        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * This routine runs in O(log n) as there is only one recursive call that is executed and the work
     * associated with a single call is independent of the size of the tree: a=1, b=2, k=0
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     *          SIDE EFFECT: Sets local variable curr to be the node that is found
     * @return node containing the matched item.
     */
    private boolean contains(E x, BinaryTreeNode t) {
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.key);

        if (compareResult < 0)
            return contains(x, t.left);
        else if (compareResult > 0)
            return contains(x, t.right);
        else {
            return true;    // Match
        }
    }

    // Basic node stored in unbalanced binary trees
    public class BinaryTreeNode {
        E key;            // The data/key for the node
        BinaryTreeNode left;   // Left child
        BinaryTreeNode right;  // Right child
        BinaryTreeNode parent; //  Parent node

        // Constructors
        BinaryTreeNode(E theElement) {
            this(theElement, null, null, null);
        }

        BinaryTreeNode(E theElement, BinaryTreeNode lt, BinaryTreeNode rt, BinaryTreeNode pt) {
            key = theElement;
            left = lt;
            right = rt;
            parent = pt;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Node:");
            sb.append(key);
            if (parent == null) {
                sb.append("<>");
            } else {
                sb.append("<");
                sb.append(parent.key);
                sb.append(">");
            }

            return sb.toString();
        }
    }
}
