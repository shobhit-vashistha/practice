package ctci.ch2;

import java.util.HashSet;
import java.util.Set;

/**
 * Intersection; Given two (singly) linked lists, determine if the two lists intersect. Return the intersecting node. Note that the intersection is defined based on reference, not value. That is, if the kth
 * node of the first linked list is the exact same node (by reference) as the jt h node of the second
 * linked list, then they are intersecting
 */
public class Intersection {

    public static class Node {
        Node next;
        int data;

        public Node(int data) {
            this.data = data;
        }
    }

    interface IntersectionChecker {
        Node find(Node h1, Node h2);
    }

    private static class SetIntersectionChecker implements IntersectionChecker {
        @Override
        public Node find(Node h1, Node h2) {
            Node n1 = h1;
            Node n2 = h2;

            Set<Integer> set1 = new HashSet<>();
            while (n1 != null) {
                int hash = System.identityHashCode(n1);
                set1.add(hash);
                n1 = n1.next;
            }

            while (n2 != null) {
                int hash = System.identityHashCode(n2);
                if (set1.contains(hash)) return n2;
                n2 = n2.next;
            }

            return null;
        }
    }


    private static class NoSetIntersectionChecker implements IntersectionChecker {
        @Override
        public Node find(Node h1, Node h2) {
            if (h1 == null || h2 == null) return null;

            Node n1 = h1;
            int len1 = 1;
            while (n1.next != null) {
                n1 = n1.next;
                len1++;
            }

            Node n2 = h2;
            int len2 = 1;
            while (n2.next != null) {
                n2 = n2.next;
                len2++;
            }

            if (n1 != n2) return null;

            n1 = h1;
            n2 = h2;
            if (len1 > len2) {
                for (int i = 0; i < len1 - len2; i++) n1 = n1.next;
            } else if (len2 > len1) {
                for (int i = 0; i < len2 - len1; i++) n2 = n2.next;
            }

            while (n1 != null && n2 != null) {
                if (n1 == n2) return n1;
                n1 = n1.next;
                n2 = n2.next;
            }

            return null;
        }
    }

    private static boolean test(IntersectionChecker ic) {
        Node single = new Node(0);
        Node[][] inputs = {
                {null, null},
                {single, single},
                makeIntersectingLinkedLists(new int[] {0}, new int[] {1}, new int[] {2}),
                makeIntersectingLinkedLists(new int[] {0, 1, 2}, new int[] {3, 4}, new int[] {5}),
                makeIntersectingLinkedLists(new int[] {0, 1, 2}, new int[] {3, 4}, new int[] {5, 6, 7}),
                {makeSinglyLinkedList(new int[] {0})[0], makeSinglyLinkedList(new int[] {0})[0]},
                {makeSinglyLinkedList(new int[] {0, 1, 2})[0], makeSinglyLinkedList(new int[] {0, 1, 2})[0]},
        };
        Node[] expResults = {
                null, single, new Node(2), new Node(5), new Node(5), null, null
        };
        boolean passed = true;
        for (int i = 0; i < expResults.length; i++) {
            printLL("in1 -> ", inputs[i][0]);
            printLL("in2 -> ", inputs[i][1]);
            Node res = ic.find(inputs[i][0], inputs[i][1]);
            printLL("res -> ", res);
            printLL("exp -> ", expResults[i]);

            if ((res == null && expResults[i] == null) || res.data == expResults[i].data) {
                System.out.println("Passed");
            } else {
                System.out.println("Failed");
                passed = false;
            }
        }
        return passed;
    }
    
    private static Node[] makeIntersectingLinkedLists(int[] part1, int[] part2, int[] common) {
        Node[] ht1 = makeSinglyLinkedList(part1);
        Node[] ht2 = makeSinglyLinkedList(part2);
        Node[] htc = makeSinglyLinkedList(common);
        ht1[1].next = htc[0];
        ht2[1].next = htc[0];
        return new Node[] {ht1[0], ht2[0]};
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

    private static Node[] makeSinglyLinkedList(int[] arr) {
        if (arr.length == 0) return null;

        Node head = new Node(arr[0]);

        Node curr = head;
        for (int i = 1; i < arr.length; i++) {
            curr.next = new Node(arr[i]);
            curr = curr.next;
        }
        return new Node[] {head, curr};
    }

    public static void main(String[] args) {
        IntersectionChecker ic1 = new SetIntersectionChecker();
        System.out.println("SetIntersectionChecker");
        System.out.println(test(ic1) ? "PASSED" : "FAILED");

        IntersectionChecker ic2 = new NoSetIntersectionChecker();
        System.out.println("NoSetIntersectionChecker");
        System.out.println(test(ic2) ? "PASSED" : "FAILED");
    }
}
