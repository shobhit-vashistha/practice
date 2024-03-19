package ctci.ch1;

import java.util.Arrays;

public class OneAway {

    interface OneAwayChecker {
        boolean isOneAway(String s1, String s2);
    }

    public static class OneAwayCheckerShortCircuit implements OneAwayChecker {
        @Override
        public boolean isOneAway(String s1, String s2) {
            int n1 = s1.length();
            int n2 = s2.length();
            if (Math.abs(n1 - n2) > 1) return false;

            if (n1 > n2) { // make it so that s1 is the smaller string
                String tempStr = s1;
                s1 = s2;
                s2 = tempStr;
                int tempN = n1;
                n1 = n2;
                n2 = tempN;
            }

            int currEditCount = 0;
            int i1 = 0;
            int i2 = 0;

            while (i1 < n1) {
                if (s1.charAt(i1) == s2.charAt(i2)) {
                    i1++;
                    i2++;
                } else {
                    currEditCount++;
                    if (currEditCount == 2) return false;

                    if (n1 - i1 == n2 - i2) { // replace
                        i1++;
                        i2++;
                    } else { // more chars in s2, i.e. insertion is s2
                        i2++;
                    }
                }
            }
            return true;
        }
    }

    /*
    adding/removing/replacing chars counts as one edit
     */
    public static class OneAwayCheckerCountEdits implements OneAwayChecker {  // t = O(n1 + n2), m = O(n1 + n2)

        @Override
        public boolean isOneAway(String s1, String s2) {
            if (Math.abs(s1.length() - s2.length()) > 1) return false;
            int edits = numEdits(s1, s2, 0, 0);
            System.out.println("s1=" + s1 + " s2=" + s2  + " edits=" + edits);
            return edits <= 1;
        }

        public int numEdits(String s1, String s2, int start1, int start2) {
            int n1 = s1.length() - start1;
            int n2 = s2.length() - start2;

            // base cases
            if (n1 == 0 && n2 == 0) return 0;
            if (n1 == 1 && n2 == 0) return 1;  // one added or removed
            if (n1 == 0 && n2 == 1) return 1;  // one added or removed

            if (s1.charAt(start1) != s2.charAt(start2)) {
                if (n1 == n2) { // remaining lengths are same, i.e. this can be assumed to be a replacement
                    return 1 + numEdits(s1, s2, start1 + 1, start2 + 1);
                } else if (n1 > n2) { // a char could have been added to s1
                    return 1 + numEdits(s1, s2, start1 + 1, start2);
                } else { // a char could have been added to s2
                    return 1 + numEdits(s1, s2, start1, start2 + 1);
                }
            } else {
                return numEdits(s1, s2, start1 + 1, start2 + 1);
            }

        }
    }

    private static boolean test(OneAwayChecker oac) {
        String[][] inputs = {
                {"pale", "ple"},
                {"ple", "pale"},

                {"pales", "pale"},
                {"pale", "pales"},

                {"pale", "bale"},
                {"bale", "pale"},

                {"pale", "bake"},
                {"bake", "pale"},

                {"spell", "pell"},
                {"pell", "spell"},

                {"a", "a"},
                {"", ""},
                {"a", ""},
                {"", "a"},

                {"a", "ab"},
                {"ab", "a"},

                {"blast", "blast"},

                {"lbast", "blast"},
                {"blast", "lbast"},

                {"a", "abc"},

                {"denormalize", "deforesting"}
        };
        boolean[] expectedResults = {
                true, true,
                true, true,
                true, true,
                false, false,
                true, true,
                true, true, true, true,
                true, true,
                true,
                false, false,
                false,
                false
        };
        boolean passed = true;
        for (int i = 0; i < expectedResults.length; i++) {
            if (oac.isOneAway(inputs[i][0], inputs[i][1]) == expectedResults[i]) {
                System.out.println("Passed");
            } else {
                System.out.println("Failed");
                passed = false;
            }
        }
        return passed;
    }

    public static void main(String[] args) {
        OneAwayChecker oac1 = new OneAwayCheckerCountEdits();
        System.out.println((test(oac1) ? "PASSED" : "FAILED") + " " + "OneAwayCheckerCountEdits");

        OneAwayChecker oac2 = new OneAwayCheckerShortCircuit();
        System.out.println((test(oac2) ? "PASSED" : "FAILED") + " " + "OneAwayCheckerShortCircuit");
    }
}
