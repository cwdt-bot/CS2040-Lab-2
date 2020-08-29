/**
 * This is an implementation of a doubly linked list, with the ability to track the 
 * head and tail. 
 * @param <T> type of object to store in the list
 */


class dLinkedList<T> {
    protected Node<T> head;
    protected Node<T> tail;

    /**
     * Each node 'carries' one value and points to the 
     * node before and after itself.
     * 
     * If it is the first node, the previous node is a null.
     * Likewise for the last node, the next node is a null.
     * @param <T>
     */
    protected class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        Node(T t) {this.value = t;}

        /**
         * Changes both pointers for both nodes
         * @param next the node to be place AFTER this node
         */
        void setNext(Node<T> next) {
            this.next = next;
            next.prev = this;
        }

        boolean hasNext() {
            return this.next != null;
        }

        /**
         * 
         * @return the next node, or if current node is the tail, return itself
         */
        Node<T> next() {
            if (this.next == null) return this;
            else return this.next;
        }

        /**
         * 
         * @return the previous node, or if the current node is the head, return itself
         */
        Node<T> prev() {
            if (this.prev == null) return this;
            else return this.prev;
        }

        T get() {
            return this.value;
        }

        /**
         * This method is to remove the previous node.
         * Using this on a node makes it the head of a new list
         * as it removes any reference to the prev node
         */
        void setAsHead() {
            this.prev = null;
        }

        @Override
        public String toString() {
            return this.value.toString();
        }
    }

    /**
     * Adds an element to the back of the list. 
     * @param t element to be added to the back
     */
    public void add(T t) {
        Node<T> newNode = new Node<>(t);
        if (this.head == null) {
            this.head = this.tail =  newNode;
        } else {
            this.tail.setNext(newNode);
            this.tail = newNode;
        }
    }

    /**
     * This is an overloaded form of the add() method for
     * adding multiple values. These values must come stored 
     * in a dLinkedList.
     * @param newTail a dLinkedList with its own values
     */
    public void add(dLinkedList<T> newTail) {
        if (newTail.head == null) {return;}
        if (this.head == null) {
            this.head = newTail.head;
            this.tail = newTail.tail;
        } else {
            this.tail.setNext(newTail.head);
            this.tail = newTail.tail;
        }
    }

    /**
     * Prints to console the contents of the dLinkedList.
     */
    public void show() {
        if (this.head == null) {}
        else {
            Node<T> curr = this.head;
            System.out.print(curr);
            while (curr.hasNext()) {
                curr = curr.next();
                System.out.print(curr);
            }
        }
        System.out.println();
    }

    /**
     * Runs down the whole chain to find out the size
     * of the dLinkedList
     * @return int representing the size of the list. 
     */
    public int size() {
        if (this.head == null) return 0;
        int size = 1;
        Node<T> curr = this.head;
        while (curr.hasNext()) {
            curr = curr.next();
            size++;
        }
        return size;
    }

}