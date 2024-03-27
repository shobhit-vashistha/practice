package ctci.ch2;


import java.util.Stack;

/**
 * Palindrome: Implement a function to check if a linked list is a palindrome.
 */
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
            if (head == null || head.next == null) return true; // return true in case of 0 or 1 length

            Stack<Node> stack = new Stack<>();
            Node slow = head;
            Node fast = head;

            while (fast != null && fast.next != null) {
                stack.add(slow);
                slow = slow.next;
                fast = fast.next.next;
            }

            // if length was odd, skip the middle node
            if (fast != null) {
                slow = slow.next;
            }

            while (slow != null) {
                if (slow.data != stack.pop().data) return false;
                slow = slow.next;
            }

            return true;
        }
    }

    public static class RecPalindromeChecker implements PalindromeChecker {

        static class Result {
            Node node;
            boolean res;

            public Result(Node node, boolean res) {
                this.node = node;
                this.res = res;
            }
        }

        private int length(Node head) {
            Node curr = head;
            int len = 0;
            while (curr != null) {
                curr = curr.next;
                len++;
            }
            return len;
        }

        @Override
        public boolean isPalindrome(Node head) {
            if (head == null || head.next == null) return true;
            int len = length(head);
            return isSubListPalindrome(head, len).res;
        }

        private Result isSubListPalindrome(Node n, int len) {
            if (n == null || len == 0) return new Result(n, true);
            if (len == 1) return new Result(n.next, true);

            Result result = isSubListPalindrome(n.next, len - 2);

            if (!result.res || result.node == null) return result;

            result.res = n.data == result.node.data;
            result.node = result.node.next;

            return result;
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

        PalindromeChecker pc2 = new StackPalindromeChecker();
        System.out.print("StackPalindromeChecker -> ");
        System.out.println(test(pc2) ? "PASSED" : "FAILED");

        PalindromeChecker pc3 = new RecPalindromeChecker();
        System.out.print("RecPalindromeChecker -> ");
        System.out.println(test(pc3) ? "PASSED" : "FAILED");
    }
}
