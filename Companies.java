/**
 * Name         : Yap Kai Herng
 * Matric. No   : A0199729A
 */

import java.util.*;

public class Companies {
    private void run() {
        Scanner sc = new Scanner(System.in);
        int numEngin = sc.nextInt();
        int numCoys = sc.nextInt();
        
        //create coys
        Company[] coys = new Company[numCoys];
        for (int n = 0; n < numCoys; n++) {
            int numEnginInCoy = sc.nextInt();
            Company newCoy = new Company(n);
            while (numEnginInCoy > 0) {
                numEnginInCoy--;
                newCoy.add(sc.nextInt());
            }
            coys[n] = newCoy;
        }

        //takeovers
        int numT = sc.nextInt();
        for (int x = 0; x < numT; x++) {
            Company parent = coys[sc.nextInt()];
            Company child = coys[sc.nextInt()];
            numCoys--;
            parent.takeover(child);
        }

        //print out parent companies
        System.out.println(numCoys);
        for (Company c : coys) {
            if (c.isParent()) {
                c.details();
            }
        }

        sc.close();
    }

    public static void main(String args[]) {
        Companies companies = new Companies();
        companies.run();
    }
}


class Company extends dLinkedList<Integer> {
    private final int id;
    private boolean parent = true;

    public Company(int id) {
        this.id = id;
    }

    public int id() {
        return this.id;
    }

    public void details() {
        System.out.print(this.id + " " + this.size());
        List<Integer> ids = new ArrayList<>();
        if (this.head != null) {
            Node<Integer> curr = this.head;
            ids.add(curr.get());
            while (curr.hasNext()) {
                curr = curr.next();
                ids.add(curr.get());
            }
            Collections.sort(ids);
        }
        for(int i : ids) {
            System.out.print(" " + i);
        }
        System.out.println("");
    }

    public void takeover(Company other) {
        this.add(other);
        other.parent = false;
    }

    public boolean isParent() {
        return this.parent;
    }

}

class dLinkedList<T> {
    protected Node<T> head;
    protected Node<T> tail;

    protected class Node<T> {
        private Node<T> prev;
        private Node<T> next;
        private T value;

        Node(T t) {this.value = t;}

        void setNext(Node<T> next) {
            this.next = next;
            next.prev = this;
        }

        boolean hasNext() {
            return this.next != null;
        }

        Node<T> next() {
            if (this.next == null) return this;
            else return this.next;
        }

        Node<T> prev() {
            if (this.prev == null) return this;
            else return this.prev;
        }

        T get() {
            return this.value;
        }

        void setAsHead() {
            this.prev = null;
        }

        @Override
        public String toString() {
            return this.value.toString();
        }
    }

    public void add(T t) {
        Node<T> newNode = new Node<>(t);
        if (this.head == null) {
            this.head = this.tail =  newNode;
        } else {
            this.tail.setNext(newNode);
            this.tail = newNode;
        }
    }

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