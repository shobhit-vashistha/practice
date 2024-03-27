package ctci.ch3;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Stack Min: How would you design a stack which, in addition to push and pop, has a function min
 * which returns the minimum element? Push, pop and min should ail operate in 0(1) time.
 */
public class MinimumStack {

    interface MinStack<T extends Comparable<T>> {
        void push(T data);
        T pop();
        T peek();
        T min();
        boolean isEmpty();
    }

    public static class SimpleMinStack<T extends Comparable<T>> implements MinStack<T> {

        private Node<T> min;
        private Node<T> top;

        private static class Node<T> {
            T data;
            Node<T> next;
            Node<T> prevMin;

            public Node(T data) {
                this.data = data;
            }
        }

        @Override
        public void push(T data) {
            Node<T> n = new Node<>(data);
            n.next = top;
            n.prevMin = min;
            top = n;
            if (min == null || data.compareTo(min.data) <= 0) min = n;
        }

        @Override
        public T pop() {
            if (top == null) throw new EmptyStackException();
            T data = top.data;
            min = top.prevMin;
            top = top.next;
            return data;
        }

        @Override
        public T peek() {
            if (top == null) throw new EmptyStackException();
            return top.data;
        }

        @Override
        public T min() {
            if (top == null) throw new EmptyStackException();
            return min.data;
        }

        @Override
        public boolean isEmpty() {
            return top == null;
        }
    }

    public static class BetterMinStack<T extends Comparable<T>> implements MinStack<T> {

        private final Stack<T> minStack = new Stack<>();
        private Node<T> top;

        private static class Node<T> {
            T data;
            Node<T> next;

            public Node(T data) {
                this.data = data;
            }
        }

        @Override
        public void push(T data) {
            Node<T> n = new Node<>(data);
            n.next = top;
            top = n;
            if (minStack.isEmpty() || data.compareTo(minStack.peek()) <= 0) minStack.push(data);
        }

        @Override
        public T pop() {
            if (top == null) throw new EmptyStackException();
            T data = top.data;
            if (data.compareTo(minStack.peek()) == 0) minStack.pop();
            top = top.next;
            return data;
        }

        @Override
        public T peek() {
            if (top == null) throw new EmptyStackException();
            return top.data;
        }

        @Override
        public T min() {
            if (top == null) throw new EmptyStackException();
            return minStack.peek();
        }

        @Override
        public boolean isEmpty() {
            return top == null;
        }
    }

    private static boolean test(MinStack<Integer> minStack) {

        boolean passed;
        boolean currTestRes;

        minStack.push(10);
        currTestRes = minStack.min() == 10;
        System.out.println(currTestRes ? "Passed" : "Failed");
        passed = currTestRes;

        minStack.push(20);
        currTestRes = minStack.min() == 10;
        System.out.println(currTestRes ? "Passed" : "Failed");
        passed = passed && currTestRes;

        minStack.push(5);
        currTestRes = minStack.min() == 5;
        System.out.println(currTestRes ? "Passed" : "Failed");
        passed = passed && currTestRes;

        minStack.push(3);
        currTestRes = minStack.min() == 3;
        System.out.println(currTestRes ? "Passed" : "Failed");
        passed = passed && currTestRes;

        minStack.push(4);
        currTestRes = minStack.min() == 3;
        System.out.println(currTestRes ? "Passed" : "Failed");
        passed = passed && currTestRes;

        minStack.push(1);
        currTestRes = minStack.min() == 1;
        System.out.println(currTestRes ? "Passed" : "Failed");
        passed = passed && currTestRes;

        minStack.pop(); // 1
        currTestRes = minStack.min() == 3;
        System.out.println(currTestRes ? "Passed" : "Failed");
        passed = passed && currTestRes;

        minStack.pop();  // 4
        currTestRes = minStack.min() == 3;
        System.out.println(currTestRes ? "Passed" : "Failed");
        passed = passed && currTestRes;

        minStack.pop(); // 3
        currTestRes = minStack.min() == 5;
        System.out.println(currTestRes ? "Passed" : "Failed");
        passed = passed && currTestRes;

        minStack.pop(); // 5
        currTestRes = minStack.min() == 10;
        System.out.println(currTestRes ? "Passed" : "Failed");
        passed = passed && currTestRes;

        minStack.pop(); // 20
        currTestRes = minStack.min() == 10;
        System.out.println(currTestRes ? "Passed" : "Failed");
        passed = passed && currTestRes;

        return passed;
    }

    public static void main(String[] args) {
        MinStack<Integer> minStack1 = new SimpleMinStack<>();
        System.out.println("SimpleMinStack");
        System.out.println(test(minStack1) ? "PASSED" : "FAILED");

        MinStack<Integer> minStack2 = new BetterMinStack<>();
        System.out.println("BetterMinStack");
        System.out.println(test(minStack2) ? "PASSED" : "FAILED");
    }

}
