package ctci.ch2;

import java.util.HashSet;
import java.util.Set;

/**
 * Remove Dups: Write code to remove duplicates from an unsorted linked list.
 * FOLLOW UP
 * How would you solve this problem if a temporary buffer is not allowed?
 */
public class RemoveDupes {

    public static class Node {
        Node next;
        int data;

        public Node(int data) {
            this.data = data;
        }
    }

    interface UniqueMaker {
        Node makeUnique(Node head);
    }

    public static class SetUniqueMaker implements UniqueMaker {
        @Override
        public Node makeUnique(Node head) {
            Set<Integer> set = new HashSet<>();
            Node curr = head;
            Node prev = head;
            while (curr != null) {
                if (set.contains(curr.data)) {
                    // remove curr node
                    prev.next = curr.next;
                } else {
                    set.add(curr.data);
                    prev = curr;
                }
                curr = curr.next;
            }
            return head;
        }
    }

    public static class NoBufUniqueMaker implements UniqueMaker {
        @Override
        public Node makeUnique(Node head) {
            Node curr = head;
            while (curr != null) {
                Node curr2 = curr.next;
                Node prev2 = curr;
                while (curr2 != null) {
                    if (curr.data == curr2.data) {
                        prev2.next = curr2.next;
                    } else {
                        prev2 = curr2;
                    }
                    curr2 = curr2.next;
                }
                curr = curr.next;
            }
            return head;
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

    private static boolean isEqual(Node head1, Node head2) {
        Node curr1 = head1;
        Node curr2 = head2;
        while (curr1 != null || curr2 != null) {
            if (curr1 == null) return false;
            if (curr2 == null) return false;
            if (curr1.data != curr2.data) return false;
            curr1 = curr1.next;
            curr2 = curr2.next;
        }
        return true;
    }

    private static boolean test(UniqueMaker um) {
        Node[] inputs = {
                makeSinglyLinkedList(new int[]{}),
                makeSinglyLinkedList(new int[]{0}),
                makeSinglyLinkedList(new int[]{1, 1}),
                makeSinglyLinkedList(new int[]{1, 1, 2, 2, 3, 3}),
                makeSinglyLinkedList(new int[]{1, 1, 2, 2, 3}),
                makeSinglyLinkedList(new int[]{1, 2, 3, 4, 5, 6}),
                makeSinglyLinkedList(new int[]{1, 2, 3, 4, 4, 5, 6}),
                makeSinglyLinkedList(new int[]{1, 2, 3, 4, 4, 5, 6, 6}),
                makeSinglyLinkedList(new int[]{1, 2, 3, 4, 5, 6, 6}),
                makeSinglyLinkedList(new int[]{4, 2, 3, 4, 5, 6, 6}),
                makeSinglyLinkedList(new int[]{4, 6, 3, 4, 5, 6, 6}),
        };
        Node[] expRes = {
                makeSinglyLinkedList(new int[]{}),
                makeSinglyLinkedList(new int[]{0}),
                makeSinglyLinkedList(new int[]{1}),
                makeSinglyLinkedList(new int[]{1, 2, 3}),
                makeSinglyLinkedList(new int[]{1, 2, 3}),
                makeSinglyLinkedList(new int[]{1, 2, 3, 4, 5, 6}),
                makeSinglyLinkedList(new int[]{1, 2, 3, 4, 5, 6}),
                makeSinglyLinkedList(new int[]{1, 2, 3, 4, 5, 6}),
                makeSinglyLinkedList(new int[]{1, 2, 3, 4, 5, 6}),
                makeSinglyLinkedList(new int[]{4, 2, 3, 5, 6}),
                makeSinglyLinkedList(new int[]{4, 6, 3, 5}),
        };
        boolean passed = true;
        for (int i = 0; i < expRes.length; i++) {
            printLL("inp = ", inputs[i]);
            Node res = um.makeUnique(inputs[i]);
            printLL("res = ", res);
            printLL("exp = ", expRes[i]);
            System.out.println("________________________");
            if (isEqual(res, expRes[i])) {
                System.out.println("Passed");
            } else {
                System.out.println("Failed");
                passed = false;
            }
        }
        return passed;
    }

    public static void main(String[] args) {
        // printLL(makeSinglyLinkedList(new int[] {0, 1, 2, 3, 4}));
//        UniqueMaker um = new SetUniqueMaker();
//        System.out.println((test(um) ? "PASSED" : "FAILED") + " SetUniqueMaker");

        UniqueMaker um2 = new NoBufUniqueMaker();
        System.out.println((test(um2) ? "PASSED" : "FAILED") + " NoBufUniqueMaker");
    }

}
