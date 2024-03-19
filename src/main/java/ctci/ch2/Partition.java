package ctci.ch2;

import java.util.Arrays;

public class Partition {

    public static class Node {
        Node next;
        int data;

        public Node(int data) {
            this.data = data;
        }
    }

    interface Partitioner {
        Node partition(Node head, int x);
    }

    public static class Partitioner1 implements Partitioner {
        @Override
        public Node partition(Node head, int x) {

            Node curr = head;
            Node prev = null;
            while (curr != null) {
                if (curr.data < x && prev != null) {
                    // we need to move this node to the head
                    Node toMove = curr;
                    // deleting the node
                    prev.next = curr.next;
                    // advancing the loop
                    curr = curr.next;

                    // moving to head
                    toMove.next = head;
                    head = toMove;
                } else {
                    // do nothing just advance the loop
                    prev = curr;
                    curr = curr.next;
                }
            }
            return head;
        }
    }

    public static class Partitioner2 implements Partitioner {
        @Override
        public Node partition(Node head, int x) {

            Node tail = head;
            Node curr = head;

            while (curr != null) {
                Node next = curr.next;
                if (curr.data < x) {
                    curr.next = head;
                    head = curr;
                } else {
                    tail.next = curr;
                    tail = curr;
                }
                curr = next;
            }
            tail.next = null;
            return head;
        }
    }

    public static class StablePartitioner implements Partitioner {
        @Override
        public Node partition(Node head, int x) {
            Node lessHead = null;
            Node lessTail = null;

            Node moreHead = null;
            Node moreTail = null;

            Node curr = head;

            while (curr != null) {
                Node next = curr.next;
                if (curr.data < x) {
                    if (lessHead == null) {
                        lessHead = curr;
                    } else {
                        lessTail.next = curr;
                    }
                    lessTail = curr;
                } else {
                    if (moreHead == null) {
                        moreHead = curr;
                    } else {
                        moreTail.next = curr;
                    }
                    moreTail = curr;
                }
                curr = next;
            }
            lessTail.next = moreHead;
            moreTail.next = null;
            return lessHead;
        }
    }

    private static void printLL(String msg, Node head) {
        System.out.print(msg);
        printLL(head);
    }
    private static void printLL(Node head) {
        Node curr = head;
        int i = 0;
        while (curr != null) {
            System.out.printf("N%d(%d) -> ", i, curr.data);
            i++;
            curr = curr.next;
        }
        System.out.printf("null%n");
    }

    private static Node makeSinglyLinkedList(int[] arr) {
        if (arr.length == 0) return null;

        Node head = new Node(arr[0]);

        Node curr = head;
        for (int i = 1; i < arr.length; i++) {
            curr.next = new Node(arr[i]);
            curr = curr.next;
        }
        return head;
    }

    public static void main(String[] args) {
        Partitioner[] ps = Arrays.asList(new Partitioner1(), new Partitioner2(), new StablePartitioner()).toArray(new Partitioner[3]);
        for (Partitioner p : ps) {
            System.out.println(p.getClass().getSimpleName());
            Node head = makeSinglyLinkedList(new int[]{3, 5, 8, 5, 10, 2, 1});
            printLL("inp = ", head);
            head = p.partition(head, 5);
            printLL("out = ", head);
        }
    }
}
