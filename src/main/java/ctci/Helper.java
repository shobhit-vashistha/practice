package ctci;

import java.util.Random;

public class Helper {

    public static String randomString(int length, int min, int max) {
        char[] st = new char[length];
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            st[i] = (char) (rand.nextInt(max - min + 1) + min);
        }
        return new String(st);
    }

    public static String randomString(int length) {
        return randomString(length, 32, 126);
    }

    public static String shuffleString(String st) {
        char[] c = st.toCharArray();
        shuffleArray(c);
        return new String(c);
    }

    public static void shuffleArray(char[] ar) {  // write a shuffle method because java
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            char a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public static String countSort(String st) {  // t = O(n), m = O(n)
        int len = st.length();
        if (len == 0 || len == 1) return st;

        int minOrd = Integer.MAX_VALUE;
        int maxOrd = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            int ord = st.charAt(i);
            if (ord > maxOrd) maxOrd = ord;
            if (ord < minOrd) minOrd = ord;
        }

        int size = maxOrd - minOrd + 1;
        int[] count = new int[size];

        for (int i = 0; i < len; i++) {
            int ord = st.charAt(i);
            int index = ord - minOrd;
            count[index]++;
        }
        char[] sorted = new char[len];
        int sortedIndex = 0;
        for (int i = 0; i < size; i++) {
            int charCount = count[i];
            char c = (char) (i + minOrd);
            for (int j = 0; j < charCount; j++) {
                sorted[sortedIndex] = c;
                sortedIndex++;
            }
        }
        return new String(sorted);
    }


    public static void testCountSort() {
        String[][] testCases = {
                {"", ""},
                {"a", "a"},
                {"aaa", "aaa"},
                {"ba", "ab"},
                {"abc", "abc"},
                {"cab", "abc"},
                {"abacus", "aabcsu"}
        };
        for (String[] testCase: testCases) {
            if (testCase[1].equals(countSort(testCase[0]))) {
                System.out.println("Passed!");
            } else {
                System.out.println("Failed!");
            }
        }
    }


    public static void main(String[] args) {
        testCountSort();
    }
}
