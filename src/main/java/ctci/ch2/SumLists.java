package ctci.ch2;

import java.util.List;

public class SumLists {

    public static class Node {
        Node next;
        int data;

        public Node(int data) {
            this.data = data;
        }
    }

    interface Adder {
        Node sum(Node h1, Node h2);
    }

    public static class ListAdderRev implements Adder {

        private static class PartSum {
            Node head;
            int carry;
            public PartSum(Node head, int carry) {
                this.head = head;
                this.carry = carry;
            }
        }

        @Override
        public Node sum(Node h1, Node h2) {
            int l1 = length(h1);
            int l2 = length(h2);

            if (l1 > l2) {
                h2 = leftPadBy(h2, l1 -l2);
            } else if (l2 > l1) {
                h1 = leftPadBy(h1, l2 -l1);
            }

            PartSum ps = partSum(h1, h2);
            Node sumHead = ps.head;

            if (ps.carry != 0) {
                Node h = new Node(ps.carry);
                h.next = sumHead;
                sumHead = h;
            }

            return sumHead;
        }

        private PartSum partSum(Node n1, Node n2) {
            int carry = 0;

            // base case
            if (n1.next == null && n2.next == null) {
                int digitSum = n1.data + n2.data;
                if (digitSum >= 10) {
                    digitSum = digitSum % 10;
                    carry = 1;
                }
                return new PartSum(new Node(digitSum), carry);
            }

            PartSum ps = partSum(n1.next, n2.next);
            Node head = ps.head;
            int digitSum = n1.data + n2.data + ps.carry;
            if (digitSum >= 10) {
                digitSum = digitSum % 10;
                carry = 1;
            }
            Node n = new Node(digitSum);
            n.next = head;
            head = n;

            return new PartSum(head, carry);
        }

        int length(Node head) {
            int l = 0;
            Node curr = head;
            while (curr != null) {
                l++;
                curr = curr.next;
            }
            return l;
        }

        Node leftPadBy(Node head, int padding) {
            if (padding == 0) return head;

            for (int i = 0; i < padding; i++) {
                Node n = new Node(0);
                n.next = head;
                head = n;
            }
            return head;
        }

    }

    public static class ListAdder implements Adder {
        @Override
        public Node sum(Node h1, Node h2) {
            Node sumHead = null;
            Node sumTail = null;

            Node c1 = h1;
            Node c2 = h2;
            int carry = 0;
            while (c1 != null || c2 != null || carry != 0) {
                int digitSum = (c1 == null ? 0 : c1.data) + (c2 == null ? 0 : c2.data) + carry;
                if (digitSum >= 10) {
                    digitSum = digitSum % 10;
                    carry = 1;
                } else {
                    carry = 0;
                }
                Node n = new Node(digitSum);
                if (sumHead == null) {
                    sumHead = n;
                } else {
                    sumTail.next = n;
                }
                sumTail = n;

                if (c1 != null) c1 = c1.next;
                if (c2 != null) c2 = c2.next;
            }

            return sumHead;
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


    private static boolean test(Adder a) {
        Node[][] inputs = {
                {makeSinglyLinkedList(new int[] {0}), makeSinglyLinkedList(new int[] {0})},
                {makeSinglyLinkedList(new int[] {0}), makeSinglyLinkedList(new int[] {1})},
                {makeSinglyLinkedList(new int[] {9}), makeSinglyLinkedList(new int[] {6})},
                {makeSinglyLinkedList(new int[] {2, 1}), makeSinglyLinkedList(new int[] {3, 1})},
                {makeSinglyLinkedList(new int[] {7, 1, 6}), makeSinglyLinkedList(new int[] {5, 9, 2})},
                {makeSinglyLinkedList(new int[] {7, 1, 6}), makeSinglyLinkedList(new int[] {5, 9, 3})},
                {makeSinglyLinkedList(new int[] {7, 1, 6}), makeSinglyLinkedList(new int[] {5})},
        };
        Node[] expRes = {
                makeSinglyLinkedList(new int[] {0}),
                makeSinglyLinkedList(new int[] {1}),
                makeSinglyLinkedList(new int[] {5, 1}),
                makeSinglyLinkedList(new int[] {5, 2}),
                makeSinglyLinkedList(new int[] {2, 1, 9}),
                makeSinglyLinkedList(new int[] {2, 1, 0, 1}),
                makeSinglyLinkedList(new int[] {2, 2, 6}),
        };
        boolean passed = true;
        for (int i = 0; i < expRes.length; i++) {
            System.out.println("inputs_________");
            printLL(inputs[i][0]);
            printLL(inputs[i][1]);
            Node res = a.sum(inputs[i][0], inputs[i][1]);
            System.out.println("result_________");
            printLL(res);
            System.out.println("expected_______");
            printLL(expRes[i]);
            if (isEqual(res, expRes[i])) {
                System.out.println("Passed!");
            } else {
                System.out.println("Failed!");
                passed = false;
            }
        }
        return passed;
    }

    private static boolean testRev(Adder a) {
        Node[][] inputs = {
                {makeSinglyLinkedList(new int[] {0}), makeSinglyLinkedList(new int[] {0})},
                {makeSinglyLinkedList(new int[] {0}), makeSinglyLinkedList(new int[] {1})},
                {makeSinglyLinkedList(new int[] {9}), makeSinglyLinkedList(new int[] {6})},
                {makeSinglyLinkedList(new int[] {1, 2}), makeSinglyLinkedList(new int[] {1, 3})},
                {makeSinglyLinkedList(new int[] {6, 1, 7}), makeSinglyLinkedList(new int[] {2, 9, 5})},
                {makeSinglyLinkedList(new int[] {6, 1, 7}), makeSinglyLinkedList(new int[] {3, 9, 5})},
                {makeSinglyLinkedList(new int[] {6, 1, 7}), makeSinglyLinkedList(new int[] {5})},
        };
        Node[] expRes = {
                makeSinglyLinkedList(new int[] {0}),
                makeSinglyLinkedList(new int[] {1}),
                makeSinglyLinkedList(new int[] {1, 5}),
                makeSinglyLinkedList(new int[] {2, 5}),
                makeSinglyLinkedList(new int[] {9, 1, 2}),
                makeSinglyLinkedList(new int[] {1, 0, 1, 2}),
                makeSinglyLinkedList(new int[] {6, 2, 2}),
        };
        boolean passed = true;
        for (int i = 0; i < expRes.length; i++) {
            System.out.println("inputs_________");
            printLL(inputs[i][0]);
            printLL(inputs[i][1]);
            Node res = a.sum(inputs[i][0], inputs[i][1]);
            System.out.println("result_________");
            printLL(res);
            System.out.println("expected_______");
            printLL(expRes[i]);
            if (isEqual(res, expRes[i])) {
                System.out.println("Passed!");
            } else {
                System.out.println("Failed!");
                passed = false;
            }
        }
        return passed;
    }

    public static void main(String[] args) {
        Adder a1 = new ListAdder();
        System.out.print("ListAdder -> ");
        System.out.println(test(a1) ? "PASSED" : "FAILED");

        Adder a2 = new ListAdderRev();
        System.out.print("ListAdderRev -> ");
        System.out.println(testRev(a2) ? "PASSED" : "FAILED");
    }
    
}
