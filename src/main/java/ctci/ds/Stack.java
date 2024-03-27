package ctci.ds;

import java.util.EmptyStackException;

public class Stack<T> {

    public static class Node<T> {
        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }

    private Node<T> top;

    public T pop() {
        if (top == null) throw new EmptyStackException();
        T item = top.data;
        top = top.next;
        return item;
    }

    public void push(T data) {
        Node<T> n = new Node<>(data);
        if (top != null) n.next = top;
        top = n;
    }

    public T peek() {
        if (top == null) throw new EmptyStackException();
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Node<T> curr = top;
        while (curr != null) {
            stringBuilder.append(curr.data);
            curr = curr.next;
            if (curr != null) stringBuilder.append(" | ");
        }
        return stringBuilder.toString();
    }
}
