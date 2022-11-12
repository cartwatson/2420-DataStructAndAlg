public class PriorityQueue<E extends Comparable<E>> {
    private Node<E> root;

    public PriorityQueue() {
        root = null;
    }

    public void enqueue(E value) {
        this.root = merge(this.root, new Node<>(value));
    }

    // remove min
    public E dequeue() {
        // catch empty tree case
        if (this.isEmpty()) {
            return null;
        }
        // all other cases
        E returnVal = this.root.value; // store minVal
        this.root = merge(this.root.left, this.root.right); // merge remainder of tree
        return returnVal;
    }

    public boolean isEmpty() {
        return root == null;
    }

    private void swapKids(Node<E> t) {
        Node<E> tempRight = t.right;
        t.right = t.left;
        t.left = tempRight;
    }

    private void setNPL(Node<E> t) {
        if (t.right == null) {
            t.npl = 0;
            return;
        }
        t.npl = t.right.npl + 1;
    }

    // ---- code from lecture slides ----
    private int getNPL(Node<E> t) {
        if (t == null) {
            return -1;
        }
        return t.npl;
    }

    private Node<E> merge(Node<E> t1, Node<E> t2) {
        Node<E> small;
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        if (t1.value.compareTo(t2.value) < 0) {
            t1.right = merge(t1.right, t2);
            small = t1;
        } else {
            t2.right = merge(t2.right, t1);
            small = t2;
        }
        if (getNPL(small.left) < getNPL(small.right)) {
            swapKids(small);
        }

        setNPL(small);

        return small;
    }

    private class Node<E extends Comparable<E>> {
        private E value;
        private Node<E> left;
        private Node<E> right;
        private int npl;

        Node(E value) {
            this(value, null, null);
        }
        Node(E value, Node<E> left, Node<E> right) {
            this.value = value;
            this.left = left;
            this.right = right;
            npl = 0;
        }
    } // ---- end of code from lecture slides ----
}
