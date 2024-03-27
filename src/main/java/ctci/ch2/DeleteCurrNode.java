package ctci.ch2;

/**
 * Delete Middle Node: Implement an algorithm to delete a node in the middle (i.e., any node but
 * the first and last node, not necessarily the exact middle) of a singly linked list, given only access to
 * that node.
 * EXAMPLE
 * Input: the node c from the linked list a - >b- >c - >d - >e- >f
 * Result: nothing is returned, but the new linked list looks like a->b->d->e-> f
 */
public class DeleteCurrNode {
    
    
    public static class Node {
        Node next;
        int data;
        public Node(int data) {
            this.data = data;
        }
    }

    interface CurrNodeDelete {
        void delete(Node n);
    }

    public static class CurrNodeDeleter implements CurrNodeDelete {
        @Override
        public void delete(Node n) {
            n.data = n.next.data;
            n.next = n.next.next;
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

    private static boolean test(CurrNodeDelete cd) {
        Node[] heads = {
                makeSinglyLinkedList(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}),
                makeSinglyLinkedList(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}),
                makeSinglyLinkedList(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
        };
        Node[] inputs = {
                heads[0].next,  // head + 1
                heads[1].next.next.next, // N(3)
                heads[2].next.next.next.next.next.next.next.next // tail - 1
        };
        Node[] expRes = {
                makeSinglyLinkedList(new int[] {0, 2, 3, 4, 5, 6, 7, 8, 9}),
                makeSinglyLinkedList(new int[] {0, 1, 2, 4, 5, 6, 7, 8, 9}),
                makeSinglyLinkedList(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 9})
        };
        boolean passed = true;
        for (int i = 0; i < expRes.length; i++) {
            printLL("inp = ", heads[i]);
            cd.delete(inputs[i]);
            printLL("res = ", heads[i]);
            printLL("exp = ", expRes[i]);
            if (isEqual(heads[i], expRes[i])) {
                System.out.println("Passed");
            } else {
                System.out.println("Failed");
                passed = false;
            }
            
        }
        return passed;
    }

    public static void main(String[] args) {
        CurrNodeDelete cd = new CurrNodeDeleter();
        System.out.println(test(cd) ? "PASSED" : "FAILED");
    }
    

}
