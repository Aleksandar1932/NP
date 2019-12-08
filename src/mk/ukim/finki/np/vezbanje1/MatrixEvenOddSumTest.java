/*
Да се направат функции за збир на парните и не парните елементи на матрицата, и резултатот да се отпечати на стандарден излез,
со помош на Streams, а валидноста на резултатот да се провери со помош на итеративна метода.
 */

package mk.ukim.finki.np.vezbanje1;

import java.util.Arrays;

public class MatrixEvenOddSumTest {
    public static int sumEven(int[][] matrica) {
        int retValue = 0;
        for (int i = 0; i < matrica.length; i++) {
            for (int j = 0; j < matrica[0].length; j++) {
                if (matrica[i][j] % 2 == 0) {
                    retValue += matrica[i][j];
                }
            }
        }
        return retValue;
    }

    public static int sumEvenStreams(int[][] matrica) {
        int retValue = 0;

        retValue = Arrays.stream(matrica)
                .mapToInt(row -> Arrays.stream(row).filter(k -> k % 2 == 0).sum())
                .sum();

        return retValue;
    }

    public static int sumOdd(int[][] matrica) {
        int retValue = 0;
        for (int i = 0; i < matrica.length; i++) {
            for (int j = 0; j < matrica[0].length; j++) {
                if (matrica[i][j] % 2 == 1) {
                    retValue += matrica[i][j];
                }
            }
        }
        return retValue;
    }

    public static int sumOddStreams(int[][] matrica) {


        return Arrays.stream(matrica)
                .mapToInt(row -> Arrays.stream(row).filter(k -> k % 2 == 1).sum())
                .sum();

    }

    public static void main(String[] args) {
        int[][] matrica = {{1, 2, 3}
                , {1, 2, 3}
                , {1, 6, 2}};

        System.out.println("Iterative sum of even elements = " + sumEven(matrica));
        System.out.println("Streams sum of even elements = " + sumEvenStreams(matrica));
        System.out.println("Iterative sum of odd elements = " + sumOdd(matrica));
        System.out.println("Streams sum of odd elements = " + sumOddStreams(matrica));
    }
}
