package ctci.ch1;

import ctci.Helper;

import java.util.Arrays;

public class StringRotation {

    interface RotationChecker {
        boolean isRotation(String s1, String s2);
    }

    public static class SubRotationChecker implements RotationChecker {

        @Override
        public boolean isRotation(String s1, String s2) {
            int n = s1.length();
            if (n != s2.length()) return false;

            if (n == 0) return true;
            if (n == 1) return s1.charAt(0) == s2.charAt(0);

            return isSubstring(s2, s1 + s1);
        }
    }

    private static boolean test(RotationChecker rc) {
        String[][] inputs = {
                {"", ""},
                {"a", "a"},
                {"a", "b"},
                {"abc", "abc"},
                {"cab", "abc"},
                {"abcd", "abdc"},
                {"waterbottle", "erbottlewat"},
        };
        boolean[] expResults = {
                true, true, false, true, true, false, true
        };
        boolean passed = true;
        for (int i = 0; i < expResults.length; i++) {
            if (rc.isRotation(inputs[i][0], inputs[i][1]) == expResults[i]) {
                System.out.println("Passed");
            } else {
                System.out.println("Failed");
                passed = false;
            }
        }
        return passed;
    }

    public static boolean isSubstring(String sb, String st) {
        return st.indexOf(sb) != -1;
    }


    public static void main(String[] args) {
         RotationChecker rc = new SubRotationChecker();
         System.out.println(test(rc) ? "PASSED" : "FAILED");
    }
}
