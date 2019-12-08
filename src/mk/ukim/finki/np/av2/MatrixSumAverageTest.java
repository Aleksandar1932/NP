package mk.ukim.finki.np.av2;

import java.util.Arrays;

public class MatrixSumAverageTest {

    public static double sum(double[][] a) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                sum += a[i][j];
            }
        }
        return sum;
    }

    public static double average(double[][] a) {
        return sum(a) / (a.length * a[0].length);
    }

    public static double sumStream(double[][] a) {
        return Arrays.stream(a)
                .mapToDouble(row -> Arrays.stream(row).sum())
                .sum();
    }

    public static void main(String[] args) {

    }
}
