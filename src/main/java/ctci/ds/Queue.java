package ctci.ds;

import java.util.NoSuchElementException;

public class Queue<T> {

    public static class Node<T> {
        T data;
        Node<T> next;
        public Node(T data) {
            this.data = data;
        }
    }

    private Node<T> head;
    private Node<T> tail;

    public void add(T data) {
        Node<T> n = new Node<>(data);
        if (tail != null) tail.next = n;
        tail = n;
        if (head == null) head = tail;
    }

    public T remove() {
        if (head == null) throw new NoSuchElementException();
        T data = head.data;
        head = head.next;
        if (head == null) tail = null;
        return data;
    }

    public T peek() {
        if (head == null) throw new NoSuchElementException();
        return head.data;
    }

    public boolean isEmpty() {
        return head == null;
    }

}
