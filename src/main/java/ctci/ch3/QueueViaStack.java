package ctci.ch3;

import java.util.NoSuchElementException;
import java.util.Stack;

public class QueueViaStack {

    interface Queue<T> {
        void add(T data);
        T remove();
        T peek();
        boolean isEmpty();
        int size();
    }

    public static class StackQueue<T> implements Queue<T> {

        private final Stack<T> pushStack;
        private final Stack<T> popStack;

        public StackQueue() {
            pushStack = new Stack<>();
            popStack = new Stack<>();
        }

        private void move(Stack<T> s1, Stack<T> s2) {
            while (!s1.isEmpty()) s2.push(s1.pop());
        }

        @Override
        public void add(T data) {
            pushStack.push(data);
        }

        @Override
        public T remove() {
            if (popStack.isEmpty()) move(pushStack, popStack);
            if (popStack.isEmpty()) throw new NoSuchElementException();
            return popStack.pop();
        }

        @Override
        public T peek() {
            if (popStack.isEmpty()) move(pushStack, popStack);
            if (popStack.isEmpty()) throw new NoSuchElementException();
            return popStack.peek();
        }

        @Override
        public boolean isEmpty() {
            return pushStack.isEmpty() && popStack.isEmpty();
        }

        @Override
        public int size() {
            return pushStack.size() + popStack.size();
        }
    }

}
