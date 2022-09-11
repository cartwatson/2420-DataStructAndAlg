public class queue<E extends Comparable> {
    private class node {
        public E value;
        public node next;

        public node() {}

        public node(E o){
            this.value = o;
        }
    } //end node

    //code from lecture slides
    private node head = null;
    private node tail = null;
    private int size;

    public queue() { this.size = 0; }
    public int getSize() { return this.size; }
    public boolean isEmpty() { return this.size == 0; }
    //end code from lecture slides

    public void enqueue(E value) {
        size += 1;
        node newNode = new node(value);
        // cover empty queue case
        if (head == null) {
            head = newNode;
            tail = newNode;
            return;
        }
        tail.next = newNode; //point previous tail at new tail
        newNode.next = null; //point new last element at null
        tail = newNode; //move tail to end of list
    }

    public E dequeue() {
        size -= 1; //decrement size
        node temp = head; //grab first element
        head = head.next; //move head to second element
        temp.next = null; //remove ptr from first element
        return temp.value; //return value from first element
    }
}
