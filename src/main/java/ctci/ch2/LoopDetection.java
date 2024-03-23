package ctci.ch2;

import java.util.HashSet;
import java.util.Set;

public class LoopDetection {

    public static class Node {
        Node next;
        int data;

        public Node(int data) {
            this.data = data;
        }
    }

    interface LoopDetector {
        Node detect(Node head);
    }

    public static class SetLoopDetector implements LoopDetector {
        @Override
        public Node detect(Node head) {
            Set<Integer> set = new HashSet<>();
            Node curr = head;
            while (curr != null) {
                int id = System.identityHashCode(curr);
                if (set.contains(id)) {
                    return curr;
                } else {
                    set.add(id);
                }
                curr = curr.next;
            }
            return null;
        }
    }

    public static class RunnerLoopDetector implements LoopDetector {
        @Override
        public Node detect(Node head) {
            Node fast = head;
            Node slow = head;
            boolean hasLoop = false;
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
                if (fast == slow) {
                    hasLoop = true;
                    break;
                }
            }
            if (!hasLoop) return null;

            // our pointers have met each other, at this point we can say that
            // if the slow pointer has covered l1 distance outside the loop and l2 inside the loop
            // then the fast pointer has covered l1 + l2 + n.C distance, where C is the cycle length and n is a positive integer
            // which should be double the distance slow pointer has covered, so
            // => 2 * (l1 + l2) = l1 + l2 + n.C
            // => l1 = n.C - l2
            // i.e. the length outside the loop is equal to the distance slow pointer will have to reach back to the cycle start (plus some constant times cycle length)
            fast = head;
            while (fast != slow) {
                fast = fast.next;
                slow = slow.next;
            }
            return slow;
        }
    }

    private static Node makeLoopingLinkedLists(int[] arr, int loopIndex) {
        Node[] ht = makeSinglyLinkedList(arr);
        Node head = ht[0];
        Node tail = ht[1];

        Node curr = head;
        for (int i = 0; i < loopIndex; i++) curr = curr.next;
        tail.next = curr;
        return head;
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

    private static boolean test(LoopDetector ld) {
        Node[] inputs = {
                null,
                makeSinglyLinkedList(new int[] {0, 1, 2, 3, 4})[0],
                makeLoopingLinkedLists(new int[] {0}, 0),
                makeLoopingLinkedLists(new int[] {0, 1, 2, 3, 4}, 0),
                makeLoopingLinkedLists(new int[] {0, 1, 2, 3, 4}, 2),
                makeLoopingLinkedLists(new int[] {0, 1, 2, 3, 4}, 4),
        };
        Node[] expResults = {
                null,
                null,
                new Node(0),
                new Node(0),
                new Node(2),
                new Node(4),
        };

        boolean passed = true;
        for (int i = 0; i < expResults.length; i++) {
            Node res = ld.detect(inputs[i]);
            if ((res == null && expResults[i] == null) || res.data == expResults[i].data) {
                System.out.println("Passed");
            } else {
                System.out.println("Failed");
                passed = false;
            }
        }
        return passed;
    }

    public static void main(String[] args) {
        LoopDetector ld1 = new SetLoopDetector();
        System.out.println("SetLoopDetector");
        System.out.println(test(ld1) ? "PASSED" : "FAILED");

        LoopDetector ld2 = new RunnerLoopDetector();
        System.out.println("RunnerLoopDetector");
        System.out.println(test(ld1) ? "PASSED" : "FAILED");
    }
}
