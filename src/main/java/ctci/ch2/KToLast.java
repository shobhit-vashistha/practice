package ctci.ch2;

/**
 * Return Kth to Last: Implement an algorithm to find the kth to last element of a singly linked list.
 */
public class KToLast {

    public static class Node {
        Node next;
        int data;

        public Node(int data) {
            this.data = data;
        }

    }

    public static boolean equals(Node n1, Node n2) {
        if (n1 == null) {
            return n2 == null;
        }
        if (n2 == null) {
            return n1 == null;
        }
        return n1.data == n2.data;
    }

    interface KthToLastFinder {
        Node findKthToLast(Node head, int k);
    }

    public static class KthToLast implements KthToLastFinder {
        @Override
        public Node findKthToLast(Node head, int k) {
            Node curr = head;
            Node kth = null;
            int currIndex = 0;
            while (curr != null) {
                if (currIndex == k) {
                    kth = head;
                } else if (currIndex > k) {
                    kth = kth.next;
                }

                curr = curr.next;
                currIndex++;
            }

            return kth;
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


    private static boolean test(KthToLastFinder kf) {
        Node[] inputs = {
                makeSinglyLinkedList(new int[]{}),
                makeSinglyLinkedList(new int[]{0}),
                makeSinglyLinkedList(new int[]{0}),
                makeSinglyLinkedList(new int[]{0, 1}),
                makeSinglyLinkedList(new int[]{0, 1}),
                makeSinglyLinkedList(new int[]{0, 1, 2, 3, 4, 5, 6}),
                makeSinglyLinkedList(new int[]{0, 1, 2, 3, 4, 5, 6}),
                makeSinglyLinkedList(new int[]{0, 1, 2, 3, 4, 5, 6}),
                makeSinglyLinkedList(new int[]{0, 1, 2, 3, 4, 5, 6}),

        };
        int[] ks = {
                0, 0, 1, 0, 1, 0, 6, 7, 3
        };
        Node[] expRes = {
                null, new Node(0), null, new Node(1), new Node(0),
                new Node(6), new Node(0), null, new Node(3)
        };
        boolean passed = true;
        for (int i = 0; i < expRes.length; i++) {
            System.out.print("inp = ");
            printLL(inputs[i]);
            Node res = kf.findKthToLast(inputs[i], ks[i]);

            if (res == null) {
                System.out.println("res = null");
            } else {
                System.out.printf("res = N(data=%d)%n", res.data);
            }
            if (expRes[i] == null) {
                System.out.println("exp = null");
            } else {
                System.out.printf("exp = N(data=%d)%n", expRes[i].data);
            }
            System.out.println("________________________");
            if (equals(expRes[i], res)) {
                System.out.println("Passed");
            } else {
                System.out.println("Failed");
                passed = false;
            }
        }
        return passed;
    }

    public static void main(String[] args) {
        KthToLastFinder kf = new KthToLast();
        System.out.println(test(kf) ? "PASSED" : "FAILED");
    }
}
