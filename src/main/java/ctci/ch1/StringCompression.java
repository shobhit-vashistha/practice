package ctci.ch1;

import java.util.ArrayList;
import java.util.List;

public class StringCompression {

    interface Compressor {
        String compressed(String st);
    }

    private static class CharCount {
        char c;
        int count;
        public CharCount(char c, int count) {
            this.c = c;
            this.count = count;
        }
    }

    public static class StringCompressor implements Compressor {
        @Override
        public String compressed(String st) {
            int n = st.length();
            if (n == 0 || n == 1 || n == 2) return st;

            // O(n)
            List<CharCount> charCountList = new ArrayList<>();
            char prevChar = st.charAt(0);
            int count = 1;
            for (int i = 1; i < n; i++) {
                char c = st.charAt(i);
                if (c == prevChar) {
                    count++;
                } else {
                    charCountList.add(new CharCount(prevChar, count));
                    prevChar = c;
                    count = 1;
                }
            }
            charCountList.add(new CharCount(prevChar, count));

            // t_build =
            StringBuilder b = new StringBuilder();
            for (CharCount cc: charCountList) { // k times
                b.append(cc.c);  // O(1)
                b.append(cc.count);  // O(log(count))
            }
            // = O(k) + k * O(log(c1) + log(c2) + ... + log(ck))
            // max k = n, then ci = 1 => t_build = O(n)
            // min k = 1, then ci = n => t_build = O(log(n))

            String res = (b.length() >= n) ? st : b.toString();
            System.out.println("original: " + st + " compressed: " + res);
            return res;
        }
    }

    private static boolean test(Compressor c) {
        String[][] testCases = {
                {"", ""},
                {"a", "a"},
                {"abc", "abc"},
                {"aabbcc", "aabbcc"},
                {"aaaccbb", "a3c2b2"},
                {"aabcccccaaa", "a2b1c5a3"},
        };
        boolean passed = true;
        for (String[] testCase : testCases) {
            boolean res = testCase[1].equals(c.compressed(testCase[0]));
            if (res) {
                System.out.println("Passed");
            } else {
                System.out.println("Failed");
                passed = false;
            }
        }
        return passed;
    }

    public static void main(String[] args) {
        Compressor c = new StringCompressor();
        System.out.println(test(c) ? "PASSED" : "FAILED");
    }
}
