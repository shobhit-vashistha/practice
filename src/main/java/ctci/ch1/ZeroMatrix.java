package ctci.ch1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Zero Matrix: Write an algorithm such that if an element in an MxN matrix is 0, its entire row and
 * column are set to 0.
 */
public class ZeroMatrix {

    interface MatrixModifier {
        int[][] makeZeroMatrix(int[][] mat);
    }

    public static class MatrixModifierNoList implements MatrixModifier {
        @Override
        public int[][] makeZeroMatrix(int[][] mat) {
            if (mat.length == 0 || mat[0].length == 0) return mat;

            boolean firstRowHasZero = false;
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[0][j] == 0) {
                    firstRowHasZero = true;
                    break;
                }
            }

            boolean firstColHasZero = false;
            for (int i = 0; i < mat.length; i++) {
                if (mat[i][0] == 0) {
                    firstColHasZero = true;
                    break;
                }
            }

            for (int i = 1; i < mat.length; i++) {
                for (int j = 1; j < mat[0].length; j++) {
                    if (mat[i][j] == 0) {
                        mat[0][j] = 0;
                        mat[i][0] = 0;
                    }
                }
            }

            for (int j = 1; j < mat[0].length; j++) {
                if (mat[0][j] == 0) {
                    for (int i = 1; i < mat.length; i++) {
                        mat[i][j] = 0;
                    }
                }
            }

            for (int i = 1; i < mat.length; i++) {
                if (mat[i][0] == 0) {
                    for (int j = 1; j < mat[0].length; j++) {
                        mat[i][j] = 0;
                    }
                }
            }

            if (firstRowHasZero) {
                for (int j = 0; j < mat[0].length; j++) {
                    mat[0][j] = 0;
                }
            }

            if (firstColHasZero) {
                for (int i = 0; i < mat.length; i++) {
                    mat[i][0] = 0;
                }
            }

            return mat;
        }
    }

    public static class MatrixModifierList implements MatrixModifier {
        @Override
        public int[][] makeZeroMatrix(int[][] mat) {

            // collect zero positions O(m.n)
            List<Integer> zeroRows = new ArrayList<>();
            List<Integer> zeroCols = new ArrayList<>();
            for (int i = 0; i < mat.length; i++) {
                int[] row = mat[i];
                for (int j = 0; j < row.length; j++) {
                    if (row[j] == 0) {
                        zeroRows.add(i);
                        zeroCols.add(j);
                    }
                }
            }

            // set row values to zero
            for (int row : zeroRows) {
                for (int i = 0; i < mat[row].length; i++) {
                    mat[row][i] = 0;
                }
            }

            // set col values to zero
            for (int col : zeroCols) {
                for (int i = 0; i < mat.length; i++) {
                    mat[i][col] = 0;
                }
            }

            return mat;
        }
    }

    private static int[][] makeRangeMatrix(int m, int n) {
        int[][] mat = new int[m][n];
        int val = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mat[i][j] = val++;
            }
        }
        return mat;
    }

    private static int[][] makeRangeMatrixWithZeros(int m, int n, int[][] zeroPos) {
        int[][] mat = makeRangeMatrix(m, n);
        for (int[] pos: zeroPos) {
            mat[pos[0]][pos[1]] = 0;
        }
        return mat;
    }

    private static int[][] makeRangeMatrixWithZeroedColsAndRows(int m, int n, int[] zeroRows, int[] zeroCols) {
        int[][] mat = makeRangeMatrix(m, n);
        for (int row: zeroRows) {
            for (int i = 0; i < n; i++) {
                mat[row][i] = 0;
            }
        }
        for (int col: zeroCols) {
            for (int i = 0; i < m; i++) {
                mat[i][col] = 0;
            }
        }
        return mat;
    }

    private static boolean test(MatrixModifier mm) {
        int[][][] inputs = {
                makeRangeMatrixWithZeros(0, 0, new int[][]{}),
                makeRangeMatrixWithZeros(1, 1, new int[][]{}),
                makeRangeMatrixWithZeros(1, 1, new int[][]{{0, 0}}),
                makeRangeMatrixWithZeros(1, 5, new int[][]{{0, 2}}),
                makeRangeMatrixWithZeros(5, 1, new int[][]{{2, 0}}),
                makeRangeMatrixWithZeros(2, 2, new int[][]{}),
                makeRangeMatrixWithZeros(6, 8, new int[][]{{1, 2}, {4, 5}}),
        };
        int[][][] expResults = {
                makeRangeMatrixWithZeroedColsAndRows(0, 0, new int[]{}, new int[]{}),
                makeRangeMatrixWithZeroedColsAndRows(1, 1, new int[]{}, new int[]{}),
                makeRangeMatrixWithZeroedColsAndRows(1, 1, new int[]{0}, new int[]{0}),
                makeRangeMatrixWithZeroedColsAndRows(1, 5, new int[]{0}, new int[]{2}),
                makeRangeMatrixWithZeroedColsAndRows(5, 1, new int[]{2}, new int[]{0}),
                makeRangeMatrixWithZeroedColsAndRows(2, 2, new int[]{}, new int[]{}),
                makeRangeMatrixWithZeroedColsAndRows(6, 8, new int[]{1, 4}, new int[]{2, 5}),
        };
        boolean passed = true;
        for (int i = 0; i < expResults.length; i++) {
            System.out.println("input____");
            RotateMatrix.printMatrix(inputs[i]);

            int[][] res = mm.makeZeroMatrix(inputs[i]);
            System.out.println("result___");
            RotateMatrix.printMatrix(res);

            System.out.println("expected_");
            RotateMatrix.printMatrix(expResults[i]);

            if (Arrays.deepEquals(res, expResults[i])) {
                System.out.println("Passes");
            } else {
                System.out.println("Failed");
                passed = false;
            }
        }
        return passed;
    }

    public static void main(String[] args) {
        // RotateMatrix.printMatrix(makeRangeMatrix(6, 8));
        // RotateMatrix.printMatrix(makeRangeMatrixWithZeros(6, 8, new int[][]{{1, 2}, {4, 5}}));
        // RotateMatrix.printMatrix(makeRangeMatrixWithZeroedColsAndRows(6, 8, new int[]{1, 4}, new int[]{2, 5}));

        MatrixModifier mm1 = new MatrixModifierList();
        System.out.println((test(mm1) ? "PASSED" : "FAILED") + " MatrixModifierList");

        MatrixModifier mm2 = new MatrixModifierNoList();
        System.out.println((test(mm2) ? "PASSED" : "FAILED") + " MatrixModifierNoList");
    }
}
