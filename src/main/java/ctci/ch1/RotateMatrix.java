package ctci.ch1;

import java.util.Arrays;
import java.util.Random;

/**
 * Rotate Matrix: Given an image represented by an NxN matrix, where each pixel in the image is 4
 * bytes, write a method to rotate the image by 90 degrees. Can you do this in place?
 */
public class RotateMatrix {
    private static final boolean debug = true;

    interface MatrixRotator {
        int[][] rotate90(int[][] mat);
    }

    private static void pr(String st, Object... s) {
        if (debug) System.out.printf((st) + "%n", s);
    }

    private static String lPad(Object o, int length) {
        return String.format("%1$" + length + "s", o);
    }

    public static class NewMatrixRotator implements MatrixRotator {
        @Override
        public int[][] rotate90(int[][] mat) {
            int n = mat.length;
            if (n == 0 || n == 1) return mat;

            int[][] rotated = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    rotated[i][n - j - 1] = mat[j][i];
                }
            }

            return rotated;
        }
    }

    public static class InPlaceMatrixRotator implements MatrixRotator {

        public int[][] rotate90(int[][] mat, int l) {
            int n = mat.length;
            int remN = n - 2 * l; // remaining size

            if (remN == 0 || remN == 1) return mat;  // base case

            // rotate layer
            for (int i = l; i < n - l - 1; i++) {
                int tmp = mat[l][i];
                mat[l][i] = mat[n - i - 1][l];                  // 4
                mat[n - i - 1][l] = mat[n - l - 1][n - i - 1];  // 3
                mat[n - l - 1][n - i - 1] = mat[i][n - l - 1];  // 2
                mat[i][n - l - 1] = tmp;                        // 1
            }

            // recursive call
            return rotate90(mat, l + 1);
        }

        @Override
        public int[][] rotate90(int[][] mat) {
            rotate90(mat, 0);
            return mat;
        }
    }

    private static int[][] generateRandomSquareMatrix(int n) {
        Random rand = new Random();
        int[][] mat = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] = rand.nextInt();
            }
        }
        return mat;
    }
    private static int[][] generateRangeSquareMatrix(int n) {
        int[][] mat = new int[n][n];
        int val = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] = val++;
            }
        }
        return mat;
    }

    private static int[][] generateRotatedRangeSquareMatrix(int n) {
        int[][] mat = new int[n][n];
        int val = 0;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                mat[j][i] = val++;
            }
        }
        return mat;
    }

    private static int getMaxVal(int[][] mat) {
        int max = Integer.MIN_VALUE;
        for (int[] row : mat) {
            for (int val : row) {
                if (val > max) max = val;
            }
        }
        return max;
    }
    public static void printMatrix(int[][] mat) {
        StringBuilder b = new StringBuilder();
        int pad = String.valueOf(getMaxVal(mat)).length() + 1;
        b.append("[\n");
        for (int[] row : mat) {
            b.append("  [");
            for (int val : row) {
                b.append(lPad(val, pad));
            }
            b.append(" ]\n");
        }
        b.append("]\n");
        System.out.println(b);
    }

    private static boolean test(MatrixRotator mr) {
        int[][][] inputs = {
                generateRangeSquareMatrix(0),
                generateRangeSquareMatrix(1),
                generateRangeSquareMatrix(2),
                generateRangeSquareMatrix(3),
                generateRangeSquareMatrix(4),
                generateRangeSquareMatrix(10),
        };
        int[][][] expectedResults = {
                generateRotatedRangeSquareMatrix(0),
                generateRotatedRangeSquareMatrix(1),
                generateRotatedRangeSquareMatrix(2),
                generateRotatedRangeSquareMatrix(3),
                generateRotatedRangeSquareMatrix(4),
                generateRotatedRangeSquareMatrix(10),
        };
        boolean passed = true;
        for (int i = 0; i < expectedResults.length; i++) {
            int[][] input = inputs[i];
            System.out.println("input____");
            printMatrix(input);

            int[][] res = mr.rotate90(input);
            System.out.println("result___");
            printMatrix(res);

            System.out.println("expected_");
            printMatrix(expectedResults[i]);

            if (Arrays.deepEquals(res, expectedResults[i])) {
                System.out.println("Passed");
            } else {
                System.out.println("Failed");
                passed = false;
            }
        }
        return passed;
    }


    public static void main(String[] args) {
        // printMatrix(generateRangeSquareMatrix(6));
        // printMatrix(generateRotatedRangeSquareMatrix(6));

        MatrixRotator mr1 = new NewMatrixRotator();
        System.out.println((test(mr1) ? "PASSED" : "FAILED") + " NewMatrixRotator");

        MatrixRotator mr2 = new InPlaceMatrixRotator();
        System.out.println((test(mr2) ? "PASSED" : "FAILED") + " InPlaceMatrixRotator");
    }
}
