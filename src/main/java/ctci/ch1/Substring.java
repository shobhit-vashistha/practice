package ctci.ch1;

import java.util.Arrays;

public class Substring {

    interface SubstringChecker {
        boolean isSubstring(String sb, String st);
    }

    public static class SubstringChecker1 implements SubstringChecker { // O(mn)
        @Override
        public boolean isSubstring(String sb, String st) {
            int m = sb.length();
            int n = st.length();

            if (m > n) return false; // substring cant be bigger then the string
            if (m == 0) return true; // 0 length string is substring of all strings

            for (int i = 0; i <= n - m; i++) {
                int remaining = n - i;
                if (remaining < m) return false; // don't bother if remaining chars are less then the substring length
                char c = st.charAt(i);
                if (c == sb.charAt(0)) {
                    // check if rest of the string matches
                    boolean matching = true;
                    for (int j = 1; j < m; j++) {
                        if (sb.charAt(j) != st.charAt(i + j)) {
                            matching = false;
                            break;
                        }
                    }
                    // if the rest of the string matches return
                    if (matching) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public static class SubstringChecker2 implements SubstringChecker {

        @Override
        public boolean isSubstring(String sb, String st) {
            return st.indexOf(sb) != -1;
        }
    }


    private static boolean test(SubstringChecker sc) {
        String[][] inputs = {
                {"", ""},
                {"a", "a"},
                {"a", "b"},
                {"abc", "abc"},
                {"cab", "abc"},
                {"ped", "pedantic"},
                {"tic", "pedantic"},
                {"dan", "pedantic"},
                {"ports", "sports"},
                {"", "class"},
                {"curse", "cursing"},
                {"cursing", "curse"},
        };
        boolean[] expResults = {
                true, true, false, true, false, true, true, true, true, true, false, false
        };
        boolean passed = true;
        for (int i = 0; i < expResults.length; i++) {
            if (sc.isSubstring(inputs[i][0], inputs[i][1]) == expResults[i]) {
                System.out.println("Passed");
            } else {
                System.out.println("Failed");
                passed = false;
            }
        }
        return passed;
    }

    private static SubstringChecker[] getAllSubCheckers() {
        return Arrays.asList(new SubstringChecker1(), new SubstringChecker2()).toArray(new SubstringChecker[2]);
    }

    private static void timeIsSubstringAll(int times) {
        String s1 = "aaaaaaaaab";
        String s2 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";

        for(SubstringChecker sc: getAllSubCheckers()) {
            long start = System.currentTimeMillis();
            for (int i = 0; i < times; i++) sc.isSubstring(s1, s2);
            double duration = (System.currentTimeMillis() - start) / 1e3;
            System.out.println(sc.getClass().getName() + " duration = " + duration);
        }
    }


    public static void main(String[] args) {
        SubstringChecker sc = new SubstringChecker1();
        System.out.println(test(sc) ? "PASSED" : "FAILED");

        timeIsSubstringAll(10000);

    }
}
