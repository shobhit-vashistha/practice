package ctci.ch1;

/**
 * Palindrome Permutation: Given a string, write a function to check if it is a permutation of a palindrome. A palindrome is a word or phrase that is the same forwards and backwards. A permutation
 * is a rearrangement of letters. The palindrome does not need to be limited to just dictionary words.
 * EXAMPLE
 * Input: Tact Coa
 * Output: True (permutations : "tac o cat", "atc o eta", etc.)
 */
public class PalindromePermutation {

    interface PalindromePermutationChecker {
        boolean isPalindromePermutation(String st);
    }

    public static class CountPalindromePermutationChecker implements  PalindromePermutationChecker {

        @Override
        public boolean isPalindromePermutation(String st) {
            st = st.toLowerCase();

            int minOrd = Integer.MAX_VALUE;
            int maxOrd = Integer.MIN_VALUE;
            for (int i = 0; i < st.length(); i++) {
                char c = st.charAt(i);
                if (c == ' ') continue;
                if (c > maxOrd) maxOrd = c;
                if (c < minOrd) minOrd = c;
            }
            int numChars = maxOrd - minOrd + 1;

            int[] counts = new int[numChars];
            for (int i = 0; i < st.length(); i++) {
                char c = st.charAt(i);
                if (c == ' ') continue;
                counts[c - minOrd]++;
            }

            int countOddChars = 0;
            for (int i = 0; i < numChars; i++) {
                if (counts[i] % 2 != 0) countOddChars++;
            }
            return countOddChars < 2;
        }
    }

    private static boolean test(PalindromePermutationChecker ppc) {
        String[] inputs = {"", "A", "Aa", "Aba", "apple", "Lili", "Dog god", "Tact Coa", "color"};
        boolean[] expectedResults = {true, true, true, true, false, true, true, true, false};
        boolean passed = true;
        for (int i = 0; i < expectedResults.length; i++) {
            if (ppc.isPalindromePermutation(inputs[i]) == expectedResults[i]) {
                System.out.println("Passed!");
            } else {
                System.out.println("Failed!");
                passed = false;
            }
        }
        return passed;
    }

    public static void main(String[] args) {
        PalindromePermutationChecker ppc = new CountPalindromePermutationChecker();
        System.out.println(test(ppc) ? "PASSED!" : "FAILED!");
    }
}
