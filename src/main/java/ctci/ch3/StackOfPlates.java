package ctci.ch3;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class StackOfPlates {

    interface SetOfStacks<T> {
        void push(T data);
        T pop();
        T popAt(int index);
    }

    /**
     * Will behave like a stack but middle stacks will be at full capacity if popAt functionality is used
     * I mean if we are assuming these to be a stack of plates and a plate is removed from the middle stack, it is safe
     * to assume that we would not go around pulling plates from other stacks to shove them at the bottom just cause the
     * middle stack is not full
     *
     * @param <T>
     */
    public static class SimpleSetOfStacks<T> implements SetOfStacks<T> {

        protected final int threshold;
        protected final List<Stack<T>> stackList = new ArrayList<>();

        public SimpleSetOfStacks(int threshold) {
            if (threshold < 1) throw new IllegalArgumentException();
            this.threshold = threshold;
        }

        protected Stack<T> getCurrStack() {
            if (stackList.isEmpty()) return null;
            return stackList.get(stackList.size() - 1);
        }

        @Override
        public void push(T data) {
            Stack<T> curr = getCurrStack();
            if (curr == null || curr.size() == threshold) {
                curr = new Stack<>();
                stackList.add(curr);
            }
            curr.push(data);
        }

        @Override
        public T pop() {
            Stack<T> curr = getCurrStack();
            if (curr == null) throw new EmptyStackException();
            T res = curr.pop();
            if (curr.isEmpty()) stackList.remove(stackList.size() - 1);
            return res;
        }

        @Override
        public T popAt(int index) {
            if (index < 0 || index >= stackList.size()) throw new IndexOutOfBoundsException();
            Stack<T> stack = stackList.get(index);
            T res = stack.pop();
            if (stack.isEmpty()) stackList.remove(index);
            return res;
        }
    }

    public static class RollingSetOfStacks<T> extends SimpleSetOfStacks<T> {
        // TODO: We will need to implement this with a doubly linked list, it is tedious though, and unnecessary IMO

        public RollingSetOfStacks(int threshold) {
            super(threshold);
        }

        @Override
        public T popAt(int index) {
            if (index < 0 || index >= stackList.size()) throw new IndexOutOfBoundsException();
            Stack<T> stack = stackList.get(index);
            T res = stack.pop();
            if (stack.isEmpty()) stackList.remove(index);
            return res;
        }


    }

}
