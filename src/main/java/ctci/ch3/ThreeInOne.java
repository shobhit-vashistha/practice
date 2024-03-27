package ctci.ch3;

import java.util.EmptyStackException;

/**
 * Three in One: Describe how you could use a single array to implement three stacks.
 */
public class ThreeInOne {

    interface MultiStack {
        void push(int index, int data);
        int pop(int index);
        int peek(int index);
        boolean isEmpty(int index);
    }

    public static class FixedMultiStack implements MultiStack {

        int stackSize;
        int stackCount;
        int[] arr;
        int[] tops;

        public FixedMultiStack(int stackSize, int stackCount) {
            this.stackSize = stackSize;
            this.stackCount = stackCount;
            arr = new int[stackSize * stackCount];
            tops = new int[stackCount];
            for (int i = 0; i < stackCount; i++) tops[i] = i * stackSize;
        }

        private void validateIndex(int index) {
            if (index < 0 || index >= stackCount) throw new IllegalArgumentException();
        }

        @Override
        public void push(int index, int data) {
            validateIndex(index);
            if (tops[index] == (index + 1) * stackSize) throw new StackOverflowError();
            arr[tops[index]] = data;
            tops[index]++;
        }

        @Override
        public int pop(int index) {
            validateIndex(index);
            if (tops[index] == index * stackSize) throw new EmptyStackException();
            int val = arr[tops[index] - 1];
            tops[index]--;
            return val;
        }

        @Override
        public int peek(int index) {
            validateIndex(index);
            if (tops[index] == index * stackSize) throw new EmptyStackException();
            return arr[tops[index] - 1];
        }

        @Override
        public boolean isEmpty(int index) {
            validateIndex(index);
            return tops[index] == index * stackSize;
        }
    }

    public static class FlexMultiStack implements MultiStack {
        // TODO implementation is relatively simple but hectic, revisit later
        int[] arr;
        int[] starts;
        int[] sizes;

        public FlexMultiStack(int totalCapacity, int stackCount) {
            arr = new int[totalCapacity];
            starts = new int[stackCount];
            sizes = new int[stackCount];
            for (int i = 0; i < stackCount; i++) {
                starts[i] = i * (totalCapacity / stackCount);
            }
        }

        @Override
        public void push(int index, int data) {

        }

        @Override
        public int pop(int index) {
            return 0;
        }

        @Override
        public int peek(int index) {
            return 0;
        }

        @Override
        public boolean isEmpty(int index) {
            return false;
        }
    }

    public static void main(String[] args) {

    }

}
