package ctci.ch1;

import java.util.Arrays;

/**
 * URLify: Write a method to replace all spaces in a string with '%20'. You may assume that the string
 * has sufficient space at the end to hold the additional characters, and that you are given the "true"
 * length of the string. (Note: If implementing in Java, please use a character array so that you can
 * perform this operation in place.)
 * EXAMPLE
 * Input: "Mr John Smith 13
 * Output: "Mr%20John%20Smith"
 */
public class URL {

    interface URLMaker {
        void urlEncode(char[] st, int actualLen);
    }

    public static class ShiftURLMaker implements URLMaker {  // t = O(n), m = O(n)
        private boolean debug;
        public ShiftURLMaker() {

        }
        public ShiftURLMaker(boolean debug) {
            this.debug = debug;
        }
        @Override
        public void urlEncode(char[] st, int actualLen) {
            if (debug) {
                System.out.println("Original");
                System.out.println(Arrays.toString(st));
            }

            int numSpaces = 0;
            for (int i = 0; i < actualLen; i++) {
                if (st[i] == ' ') numSpaces++;
            }
            if (debug) System.out.println("numSpaces = " + numSpaces);

            for (int i = actualLen - 1; i >= 0; i--) {
                char c = st[i];
                int shiftTo = i + numSpaces * 2;
                if (c != ' ') {
                    st[shiftTo] = st[i];
                } else {
                    st[shiftTo - 2] = '%';
                    st[shiftTo - 1] = '2';
                    st[shiftTo] = '0';
                    numSpaces--;
                }
            }
            if (debug) {
                System.out.println("Encoded");
                System.out.println(Arrays.toString(st));
            }
        }
    }

    private static char[] getURLCharArray(String st) {
        st = st.trim(); // make sure there are no spaces at the front or end
        int spaceCount = (int) st.chars().filter(ch -> ch == ' ').count();
        char[] chars = new char[st.length() + spaceCount * 2];
        for (int i = 0; i < st.length(); i++) {
            chars[i] = st.charAt(i);
        }
        return chars;
    }

    private static boolean isEqual(String st, char[] chars) {
        int n = st.length();
        if (chars.length != n) return false;
        for (int i = 0; i < n; i++) {
            if (st.charAt(i) != chars[i]) return false;
        }
        return true;
    }

    private static boolean testUrlEncode(URLMaker um) {
        String[] inputs = {"A B C D E", "A B", "A", "", "Mr John Smith", "Harry  Potter", "John Snow     "};
        int[] actualLengths = {9, 3, 1, 0, 13, 13, 9};
        String[] expectedResults = {"A%20B%20C%20D%20E", "A%20B", "A", "", "Mr%20John%20Smith", "Harry%20%20Potter", "John%20Snow"};
        boolean passed = true;
        for (int i = 0; i < expectedResults.length; i++) {
            char[] resultArr = getURLCharArray(inputs[i]);
            um.urlEncode(resultArr, actualLengths[i]);
            if (isEqual(expectedResults[i], resultArr)) {
                System.out.println("Passed!");
            } else {
                System.out.println("Failed!");
                passed = false;
            }
        }
        return passed;
    }

    public static void main(String[] args) {
        testUrlEncode(new ShiftURLMaker());
    }
}
