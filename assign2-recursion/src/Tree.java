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
            if (x.right == null) { subMessage = subMessage.concat(indent + x.key.toString() + parentMessage); }
            subMessage = subMessage.concat(traversal(x.left, level + 1));
        }
        return subMessage;
    }

    // Return a string containing the tree contents as a single line ---------------------------------------------------
        // returns a string containing the in order traversal
    public String inOrderToString() {
        String message = "";
        message = message.concat(this.name + ": ");
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
    public void flip() { this.root = invertTree(this.root); }

    private BinaryTreeNode invertTree(BinaryTreeNode x) {
        if (x == null) {
            return null;
        }
        swap(root);
        invertTree(x.left);
        invertTree(x.right);
        return root;
    }

    private void swap(BinaryTreeNode x) {
        if (x == null) {
            return;
        }
        BinaryTreeNode temp = x.left;
        x.left = x.right;
        x.right = temp;
    }

    // Returns the in-order successor of the specified node ------------------------------------------------------------ NOT WORKING
        // @param node node from which to find the in-order successor
    public BinaryTreeNode inOrderSuccessor(BinaryTreeNode node) {
        return inOrderSuccessorHelper(node);
    }

    private BinaryTreeNode inOrderSuccessorHelper(BinaryTreeNode x) {
        if (x.right != null) {
            if (x.right.left != null) { return x.right.left; } else { return x.right; }
        } else {
            if (x.parent != null && x.parent.right != x) { return x.parent.right; }
            else { return x.parent.parent; }
        }
    }

    // Counts number of nodes in specified level -----------------------------------------------------------------------
        // @param level, Level in tree, root is zero
        // @return count of number of nodes at specified level
    public int nodesInLevel(int level) {
        return levelHelper(this.root, 0, level);
    }

    private int levelHelper(BinaryTreeNode x, int level, int targetLevel) {
        int ctr = 0;
        if (targetLevel == 0) { return 1; } // catch root case
        if (x.left == null && x.right == null) { if (level == targetLevel) { ctr += 1; } }
        if (x.right != null) {
            ctr += levelHelper(x.right, level + 1, targetLevel);
            if (level == targetLevel) { ctr += 1; }
        }
        if (x.left != null) {
            // don't double add nodes with two children
            if (x.right == null) { if (level == targetLevel) { ctr += 1; } }
            ctr += levelHelper(x.left, level + 1, targetLevel);
        }
        return ctr;
    }

    // Print all paths from root to leaves -----------------------------------------------------------------------------  NOT WORKING
    public void printAllPaths() {
        System.out.println(printAllPathsHelper(this.root));
    }

    private String printAllPathsHelper(BinaryTreeNode x) {
        String message = "";
        String subMessage = x.key.toString() + " ";
        // add nodes with no children
        if (x.left == null && x.right == null) {
            message = message.concat(subMessage + "\n");
            subMessage = "";
        }
        // run down left side, add nodes after recursion
        if (x.left != null) {
            subMessage = subMessage.concat(printAllPathsHelper(x.left));
        }
        // run down right side, add nodes before recursion
        if (x.right != null) {
            subMessage = subMessage.concat(printAllPathsHelper(x.right));
        }
        return subMessage;
    }

    // Counts all non-null binary search trees embedded in tree -------------------------------------------------------- NOT WORKING
        // @return Count of embedded binary search trees
    public int countBST() {
        return countHelp(this.root);
    }

    private int countHelp(BinaryTreeNode x) {
        if (x == null)
            return 0;
        int i = 0;
        if (x.left != null && x.right != null)
        i++;
        i += (countHelp(x.left) + countHelp(x.right));
        return i;
    }

    // Insert into a bst tree; duplicates are allowed
        // @param x the item to insert.
    public void insert(E x) {
        root = insert(x, root, null);
    }

    // returning the node with the associated key ----------------------------------------------------------------------
    public BinaryTreeNode getByKey(E key) {
        BinaryTreeNode node = getByKeyHelper(this.root, key);
        return node;
    }

    private BinaryTreeNode getByKeyHelper(BinaryTreeNode x, E key) {
        if (x == null) return null;
        if (x.key == key) {
            return x;
        } else {
            BinaryTreeNode left = getByKeyHelper(x.left, key);
            BinaryTreeNode right = getByKeyHelper(x.right, key);
            if (left != null) {return left; } else { return right; }
        }
    }

    // Balance the tree ------------------------------------------------------------------------------------------------  NOT WORKING
        // height balance a tree in a new tree
    public void balanceTree() {
        // get all values
        String val = inOrderToString();
        String[] values;
        values = val.split(" ");
        // TODO: binary traversal insert
        int middle = values.length / 2;
        BinaryTreeNode newRoot = new BinaryTreeNode((E) values[middle]);
        
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
