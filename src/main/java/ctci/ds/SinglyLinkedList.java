package ctci.ds;

import java.util.Objects;

public class SinglyLinkedList<T> {

    Node<T> head = null;

    public static class Node<T> {
        Node<T> next;
        T data;

        public Node(T data) {
            this.data = data;
        }
    }

    /**
     * append data to the tail
     * @param data
     */
    public void append(T data) {
        Node<T> node = new Node<>(data);
        if (head == null) {
            head = node;
        } else {
            Node<T> curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = node;
        }
    }

    /**
     * deletes the first occurrence of this data
     * @param data
     */
    public void delete(T data) {
        if (Objects.equals(head.data, data)) {
            head = head.next;
        } else {
            Node<T> curr = head;
            while (curr.next != null) {
                if (Objects.equals(curr.next.data, data)) {
                    curr.next = curr.next.next;
                    break;
                }
                curr = curr.next;
            }
        }
    }


}
