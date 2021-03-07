/**
 * Name         : Yap Kai Herng
 * Matric. No   : A0199729A
 */

import java.util.*;

public class Editor {
    private void run() {
        Scanner sc = new Scanner(System.in);
        //create the linkedlist
        String input = sc.nextLine();
        pointerLinkedList<String> curr = new pointerLinkedList<>();
        for (int x = 0; x < input.length(); x++) {
            String toAdd = String.valueOf(input.charAt(x));
            curr.add(toAdd);
        }

        //process the edits
        int numEdits = sc.nextInt();
        pointerLinkedList<String> buffer = new pointerLinkedList<>();
        while(numEdits > 0){
            numEdits--;
            String currCommand = sc.next();
            //deal with R (moving right)
            if (currCommand.equals("R")) {
                curr.moveForwards();
            } else if (currCommand.equals("L")) {
            //deal with L (moving left)
                curr.moveBackwards();
            } else if (currCommand.equals("C")) {
            //deal with C k (buffering to the right)
                int dist = sc.nextInt();
                buffer = curr.collectOnRight(dist);
            } else if (currCommand.equals("P")) {
            //deal with P (pasting on left)
                curr.insertOnLeft(buffer);
                buffer = new pointerLinkedList<>();
            } else if (currCommand.equals("I")) {
            //deal with I (insert one character left)
                String toInsert = sc.next();
                curr.insertOnLeft(toInsert);
            } else if (currCommand.equals("B")) {
            //deal with B (delete on left)
                curr.removeOnLeft();
            }
        }
        sc.close();

        curr.show();   
    }

    public static void main(String[] args) {
        Editor newEditor = new Editor();
        newEditor.run();
    }
}

/**
 * This is an subclass of dLinkedList. 
 * This form is able to track the current position of a 'pointer' in the list, 
 * for quicker splicing and traversal from the current position. 
 * @param <T> type of object to be stored
 */
class pointerLinkedList<T> extends dLinkedList<T> {
    private Node<T> curr;
    
    /**
     * This overrides the default add() method (which adds to the back)
     * Instead, it adds to the right of the current position 
     */
    @Override
    public void add(T t) {
        Node<T> newNode = new Node<>(t);
        if (this.head == null) {
            this.head = this.tail = this.curr =  newNode;
        } else {
            this.tail.setNext(newNode);
            this.tail = newNode;
        }
    }

    /**
     * Moves the position forwards(i.e. towards the tail)
     * The behaviour at the head is the same as dLinkedList
     * i.e. it returns the head. 
     */
    public void moveForwards() {
        this.curr = this.curr.next();
    }

    /**
     * Moves the position backwards(i.e towards the head)
     * The behaviour at the tail is the same as dLinkedList 
     * i.e. it returns the tail. 
     */
    public void moveBackwards() {
        this.curr = this.curr.prev();
    }

    /**
     * Collects a distance's worth of elements towards the tail of the list.
     * If the distance exceeds the tail, collection will stop at the last element.
     * Current position does not change.  
     * @param dist number of elements to collect.
     * @return a pointerLinkedList with collected elements
     */
    public pointerLinkedList<T> collectOnRight(int dist) {
        pointerLinkedList<T> collected = new pointerLinkedList<>();
        Node<T> pointer = this.curr;
        collected.add(pointer.get());
        while (dist > 1 && pointer.hasNext()) {
            dist--;
            pointer = pointer.next();
            T item = pointer.get();
            collected.add(item);
        }
        return collected;
    }

    /**
     * Inserts values to the left(towards the head) of the current position.
     * Precond:Values should come within a dLinkedList. 
     * Postcond: current list is modified with the chunk added to the left of pointer,
     * Current position does not change. 
     * @param chunk a dLinkedList of values to add
     */
    public void insertOnLeft(dLinkedList<T> chunk) {
        //case 1: curr != head
        if (!this.head.equals(this.curr)) {
                Node<T> currPrev = this.curr.prev();
                currPrev.setNext(chunk.head);
                chunk.tail.setNext(this.curr);
        } else {
            this.head = chunk.head;
            chunk.tail.setNext(this.curr);
        }
    }

    /**
     * Overloaded form of the insertOnLeft() method. 
     * Precond: object must be the same type as the list. 
     * Postcond: adds a single element to the left of the current position. 
     * Current position does not change. 
     * @param t element to be inserted
     */
    public void insertOnLeft(T t) {
        Node<T> newNode = new Node<>(t);
        //case 1: curr == head
        if (this.head.equals(this.curr)){
            this.head = newNode;
            this.head.setNext(this.curr);
        } else {
        //case 2: curr != head
            Node<T> currPrev = this.curr.prev();
            currPrev.setNext(newNode);
            newNode.setNext(this.curr);
        }
    }
    
    /**
     * Removes the element on the left of the current position. 
     * Does nothing if the current position is the head. 
     * Postcond: Current position does not change, element on left of pointer is removed
     */
    public void removeOnLeft() {
        //case 1: curr == head
        if(this.curr.equals(this.head)) {}
        //case 2: curr.prev() == head
        else if (this.curr.prev().equals(this.head)) {
            this.head = this.curr;
            this.head.setAsHead();
        } else {
        //case 3: all other positions
            Node<T> twoBefore = curr.prev().prev();
            twoBefore.setNext(this.curr);
        }
    }
}


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
     * Postcond: Prints to console the contents of the dLinkedList.
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