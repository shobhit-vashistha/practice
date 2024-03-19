package ctci.ch2;


public class Palindrome {

    public static class Node {
        Node next;
        int data;

        public Node(int data) {
            this.data = data;
        }
    }

    interface PalindromeChecker {
        boolean isPalindrome(Node head);
    }

    public static class RevPalindromeChecker implements PalindromeChecker {
        @Override
        public boolean isPalindrome(Node head) {
            if (head == null || head.next == null) return true;

            // create a new reversed list
            Node revHead = new Node(head.data);
            Node curr = head.next;
            while (curr != null) {
                Node n = new Node(curr.data);
                n.next = revHead;
                revHead = n;
                curr = curr.next;
            }

            // check if all elements are equal in the both lists
            Node c1 = head;
            Node c2 = revHead;
            while (c1 != null && c2 != null) {
                if (c1.data != c2.data) return false;
                c1 = c1.next;
                c2 = c2.next;
            }

            return true;
        }
    }

    public static class StackPalindromeChecker implements PalindromeChecker {
        @Override
        public boolean isPalindrome(Node head) {
            // TODO: implement stack way
            return false;
        }
    }

    public static class RecPalindromeChecker implements PalindromeChecker {
        @Override
        public boolean isPalindrome(Node head) {
            // TODO: implement recursive way, but why?
            if (head == null || head.next == null) return true;

            return false;
        }

        public boolean isSubListPalindrome(Node n, int length) {
            if (length == 0 || length == 1) return true;

            boolean res = isSubListPalindrome(n, length - 2);
            return res;
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

    private static boolean test(PalindromeChecker pc) {
        Node[] inputs = {
                makeSinglyLinkedList(new int[] {}),
                makeSinglyLinkedList(new int[] {0}),
                makeSinglyLinkedList(new int[] {0, 1, 0}),
                makeSinglyLinkedList(new int[] {0, 1, 1, 0}),
                makeSinglyLinkedList(new int[] {0, 1, 2, 1, 0}),
                makeSinglyLinkedList(new int[] {0, 1, 2, 2, 1, 0}),

                makeSinglyLinkedList(new int[] {0, 1, 2, 3, 1, 0}),
                makeSinglyLinkedList(new int[] {0, 1, 2, 1, 1}),
                makeSinglyLinkedList(new int[] {0, 1, 1, 1}),
                makeSinglyLinkedList(new int[] {0, 1, 1}),
                makeSinglyLinkedList(new int[] {0, 1}),
        };
        boolean[] expRes = {
                true, true, true, true, true, true, false, false, false, false, false
        };
        boolean passed = true;
        for (int i = 0; i < expRes.length; i++) {
            printLL(inputs[i]);
            if (pc.isPalindrome(inputs[i]) == expRes[i]) {
                System.out.println("Passed");
            } else {
                System.out.println("Failed");
                passed = false;
            }
        }
        return passed;
    }

    public static void main(String[] args) {
        PalindromeChecker pc1 = new RevPalindromeChecker();
        System.out.print("RevPalindromeChecker -> ");
        System.out.println(test(pc1) ? "PASSED" : "FAILED");

//        PalindromeChecker pc2 = new RevPalindromeChecker();
//        System.out.print("RevPalindromeChecker -> ");
//        System.out.println(test(pc1) ? "PASSED" : "FAILED");
    }
}
