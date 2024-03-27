package ctci.ch1;

import ctci.Helper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Is Unique: Implement an algorithm to determine if a string has all unique characters. What if you
 * cannot use additional data structures?
 */
public class IsUnique {

    interface UniqueChecker {
        boolean hasUniqueChars(String st);
    }

    static class SetUniqueChecker implements UniqueChecker {  // t = O(n), m = O(n)

        @Override
        public boolean hasUniqueChars(String st) {
            int n = st.length();
            if (n == 0 || n == 1) return true;

            Set<Character> charSet = new HashSet<>();
            for (int i = 0; i < n; i++) {
                Character c = st.charAt(i);
                if (charSet.contains(c)) {
                    return false;
                } else {
                    charSet.add(c);
                }
            }
            return true;
        }
    }

    static class ArrayUniqueChecker implements UniqueChecker {  // t = O(n), m = O(n + numChars)

        @Override
        public boolean hasUniqueChars(String st) {
            int n = st.length();
            if (n == 0 || n == 1) return true;

            int minOrd = Integer.MAX_VALUE;
            int maxOrd = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                int ord = st.charAt(i);
                if (ord > maxOrd) maxOrd = ord;
                if (ord < minOrd) minOrd = ord;
            }
            int numChars = maxOrd - minOrd + 1;

            // number of chars are less the length of the string, all chars can't possibly be unique
            if (numChars < n) return false;

            boolean[] charPresent = new boolean[numChars];
            for (int i = 0; i < n; i++) {
                int ord = st.charAt(i);
                int index = ord - minOrd;
                if (charPresent[index]) {
                    return false;
                } else {
                    charPresent[index] = true;
                }
            }
            return true;
        }
    }

    static class NoDsUniqueChecker implements UniqueChecker {  // t = O(n^2), m = O(1)

        @Override
        public boolean hasUniqueChars(String st) {
            int n = st.length();
            if (n == 0 || n == 1) return true;

            for (int i = 0; i < n; i++) {
                char c1 = st.charAt(i);
                for (int j = i + 1; j < n; j++) {
                    char c2 = st.charAt(j);
                    if (c1 == c2) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    static class CountSortUniqueChecker implements UniqueChecker {  // t = O(n), m = O(n)

        @Override
        public boolean hasUniqueChars(String st) {
            int n = st.length();
            if (n == 0 || n == 1) return true;

            st = Helper.countSort(st);
            char prev = st.charAt(0);
            for (int i = 1; i < n; i++) {
                char c = st.charAt(i);
                if (c == prev) {
                    return false;
                } else {
                    prev = c;
                }
            }
            return true;
        }
    }

    private static boolean testIsUnique(UniqueChecker uc) {
        String[] testInputs = {"", "a", "aa", "abcdefghij", "apple", "banana", "audio", "baba"};
        boolean[] expectedResults = {true, true, false, true, false, false, true, false};
        boolean passed = true;
        for (int i = 0; i < expectedResults.length; i++) {
            String input = testInputs[i];
            boolean expected = expectedResults[i];
            if (uc.hasUniqueChars(input) == expected) {
                System.out.println("Passed!");
            } else {
                System.out.println("Failed!");
                passed = false;
            }
        }
        return passed;
    }

    private static UniqueChecker[] allUniqueCheckerInstances() {
        UniqueChecker sc = new SetUniqueChecker();
        UniqueChecker ac = new ArrayUniqueChecker();
        UniqueChecker nc = new NoDsUniqueChecker();
        UniqueChecker cc = new CountSortUniqueChecker();
        return Arrays.asList(sc, ac, nc, cc).toArray(new UniqueChecker[4]);
    }

    private static void testIsUniqueAll() {
        for (UniqueChecker uc: allUniqueCheckerInstances()) {
            System.out.println((testIsUnique(uc) ? "PASSED": "FAILED") + "!! " + uc.getClass().getName());
        }
    }

    private static void timeUniqueCheckers(int times) {
        char[] chars = new char[127 - 32];
        for (int i = 32; i <= 126; i++) {
            chars[i - 32] = (char) i;
        }
        String worstCaseStr = new String(chars);

        for (UniqueChecker uc: allUniqueCheckerInstances()) {
            long start = System.currentTimeMillis();
            for (int i = 0; i < times; i++) uc.hasUniqueChars(worstCaseStr);
            double duration = (System.currentTimeMillis() - start) / 1e3;
            System.out.println(uc.getClass().getName() + " duration = " + duration);
        }

    }

    public static void main(String[] args) {
        // testIsUniqueAll();
        timeUniqueCheckers(10000);
    }
}
