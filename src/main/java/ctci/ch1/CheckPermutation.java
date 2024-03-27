package ctci.ch1;

import ctci.Helper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Check Permutation: Given two strings, write a method to decide if one is a permutation of the
 * other.
 */
public class CheckPermutation {

    interface PermuteChecker {
        boolean isPermutation(String s1, String s2);
    }

    public static class MapPermuteChecker implements PermuteChecker {

        @Override
        public boolean isPermutation(String s1, String s2) {
            if (s1.length() != s2.length()) return false;

            Map<Character, Integer> map = new HashMap<>();
            for (int i = 0; i < s1.length(); i++) {
                Character c = s1.charAt(i);
                map.put(c, map.getOrDefault(c, 0) + 1);
            }

            for (int i = 0; i < s2.length(); i++) {
                Character c = s2.charAt(i);
                if (!map.containsKey(c)) return false;
                Integer val = map.get(c);
                if (val == 1) {
                    map.remove(c);
                } else {
                    map.put(c, val - 1);
                }
            }

            return true;
        }
    }

    public static class ArrayPermuteChecker implements PermuteChecker {

        @Override
        public boolean isPermutation(String s1, String s2) {
            int n = s1.length();
            if (n != s2.length()) return false;

            int minOrd = Integer.MAX_VALUE;
            int maxOrd = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                int ord = s1.charAt(i);
                if (ord > maxOrd) maxOrd = ord;
                if (ord < minOrd) minOrd = ord;
            }
            for (int i = 0; i < n; i++) {
                int ord = s2.charAt(i);
                if (ord > maxOrd) maxOrd = ord;
                if (ord < minOrd) minOrd = ord;
            }
            int numChars = maxOrd - minOrd + 1;

            int[] charCounts = new int[numChars];

            for (int i = 0; i < n; i++) {
                char c = s1.charAt(i);
                charCounts[c - minOrd]++;
            }
            for (int i = 0; i < n; i++) {
                char c = s2.charAt(i);
                if (charCounts[c - minOrd] == 0) {
                    return false;
                } else {
                    charCounts[c - minOrd]--;
                }
            }
            return true;
        }
    }

    public static class SortPermuteChecker implements PermuteChecker {

        @Override
        public boolean isPermutation(String s1, String s2) {
            if (s1.length() != s2.length()) return false;

            String s1Sorted = Helper.countSort(s1);
            String s2Sorted = Helper.countSort(s2);

            return Objects.equals(s1Sorted, s2Sorted);
        }
    }

    private static boolean testIsPermutation(PermuteChecker pc) {
        String[][] testCases = {
                {"", ""},
                {"a", "a"},
                {"aaa", "aaa"},
                {"aaa", "aaaa"},
                {"ab", "ba"},
                {"abc", "ab"},
                {"a", "b"},
                {"apple", "pale"},
                {"apple", "paple"}
        };
        boolean[] results = {true, true, true, false, true, false, false, false, true};
        boolean passed = true;
        for (int i = 0; i < results.length; i++) {
            String[] inputs = testCases[i];
            boolean result = results[i];
            if (pc.isPermutation(inputs[0], inputs[1]) == result) {
                System.out.println("Passed!");
            } else {
                System.out.println("Failed!");
                passed = false;
            }
        }

        return passed;
    }

    private static PermuteChecker[] getAllPermuteCheckers() {
        return Arrays.asList(new MapPermuteChecker(), new SortPermuteChecker(), new ArrayPermuteChecker()).toArray(new PermuteChecker[3]);
    }

    private static void testIsPermutationAll() {
        for(PermuteChecker pc: getAllPermuteCheckers()) {
            System.out.println((testIsPermutation(pc) ? "PASSED": "FAILED") + "!! " + pc.getClass().getName());
        }
    }

    private static void timePermutationsAll(int length, int times) {
        String s1 = Helper.randomString(length);
        String s2 = Helper.shuffleString(s1);

        for(PermuteChecker pc: getAllPermuteCheckers()) {
            long start = System.currentTimeMillis();
            for (int i = 0; i < times; i++) pc.isPermutation(s1, s2);
            double duration = (System.currentTimeMillis() - start) / 1e3;
            System.out.println(pc.getClass().getName() + " duration = " + duration);
        }
    }

    public static void main(String[] args) {
        testIsPermutationAll();
        timePermutationsAll(5000, 1000);
    }
}
