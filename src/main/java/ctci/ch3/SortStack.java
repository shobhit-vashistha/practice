package ctci.ch3;


import ctci.ds.Stack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Sort Stack: Write a program to sort a stack such that the smallest items are on the top. You can use
 * an additional temporary stack, but you may not copy the elements into any other data structure
 * (such as an array). The stack supports the following operations: push, pop, peek, and is Empty.
 */
public class SortStack {

    interface StackSorter<T extends Comparable<T>> {
        Stack<T> sort(Stack<T> stack);
    }

    public static class SimpleStackSorter<T extends Comparable<T>> implements StackSorter<T> {

        @Override
        public Stack<T> sort(Stack<T> stack) {
            if (stack.isEmpty()) return stack;
            Stack<T> temp = new Stack<>();
            while (!stack.isEmpty()) {
                T curr = stack.pop();  // get top element
                if (temp.isEmpty()) {
                    temp.push(curr);
                    continue;
                }
                // remove elements from the temp stack and push to original stack until the top element is less or equal to curr
                while (!temp.isEmpty() && curr.compareTo(temp.peek()) < 0) stack.push(temp.pop());
                temp.push(curr);  // now that we are in right place push current element in temp stack
            }
            // we should have reverse order in the temp stack, reverse it again to get it in correct order
            while (!temp.isEmpty()) stack.push(temp.pop());

            return stack;
        }
    }

    public static class MergeStackSorter<T extends Comparable<T>> implements StackSorter<T> {
        // TODO
        @Override
        public Stack<T> sort(Stack<T> stack) {
            return stack;
        }
    }

    public static class QuickStackSorter<T extends Comparable<T>> implements StackSorter<T> {
        // TODO
        @Override
        public Stack<T> sort(Stack<T> stack) {
            return stack;
        }
    }


    private static <T extends Comparable<T>> boolean isSorted(Stack<T> stack) {
        if (stack.isEmpty()) return true;
        Stack<T> temp = new Stack<>();
        T prev = stack.pop();
        temp.push(prev);
        while (!stack.isEmpty()) {
            T curr = stack.pop();
            if (curr.compareTo(prev) < 0) return false;
            temp.push(curr);
            prev = curr;
        }
        // restore stack to former glory
        while (!temp.isEmpty()) stack.push(temp.pop());
        return true;
    }

    @SafeVarargs
    private static <T> Stack<T> makeStack(T... objs) {
        Stack<T> stack = new Stack<>();
        for (int i = objs.length - 1; i >= 0 ; i--) stack.push(objs[i]);
        return stack;
    }

    private static Stack<Integer> makeRandomIntStack(int size, int min, int max) {
        Stack<Integer> stack = new Stack<>();
        Random rand = new Random();
        for (int i = 0; i < size; i++) stack.push(rand.nextInt(max - min + 1) + min);
        return stack;
    }

    private static void testMakeStack() {
        Stack<Integer> stack0 = makeStack(0, 1, 2, 3, 4);
        System.out.println(stack0);
    }

    private static boolean test(StackSorter<Integer> ss) {
        List<Stack<Integer>> inputs = Arrays.asList(
                makeRandomIntStack(0, 0, 1000),
                makeRandomIntStack(1, 0, 1000),
                makeRandomIntStack(2, 0, 1000),
                makeRandomIntStack(5, 0, 1000),
                makeRandomIntStack(10, 0, 1000),
                makeRandomIntStack(50, 0, 1000)
        );
        boolean passed = true;
        for (Stack<Integer> input : inputs) {
            System.out.print("inp -> ");
            System.out.println(input);
            Stack<Integer> res = ss.sort(input);
            System.out.print("res -> ");
            System.out.println(res);
            if (isSorted(res)) {
                System.out.println("Passed");
            } else {
                System.out.println("Failed");
                passed = false;
            }
        }
        return passed;
    }

    private static void testIsSorted() {
        Stack<Integer> stack1 = makeStack(0, 1, 2, 3, 4);
        System.out.println("stack1 sorted = " + isSorted(stack1));

        Stack<Integer> stack2 = makeStack(0, 1, 2, 4, 3);
        System.out.println("stack2 sorted = " + isSorted(stack2));
    }

    public static void main(String[] args) {
        // testMakeStack();
        // testIsSorted();
        StackSorter<Integer> ss = new SimpleStackSorter<>();
        System.out.println(test(ss) ? "PASSED" : "FAILED");
    }

}
