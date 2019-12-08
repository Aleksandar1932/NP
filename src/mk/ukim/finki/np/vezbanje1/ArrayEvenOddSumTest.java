package mk.ukim.finki.np.vezbanje1;

/*
Да се направат функции за збир на парните и не парните елементи на низата, и резултатот да се отпечати на стандарден излез
со помош на Streams, а валидноста на резултатот да се провери со помош на итеративна метода.
 */

import java.util.Arrays;
import java.util.Scanner;

public class ArrayEvenOddSumTest {

    public static int sumEvenStreams(int[] niza) {
        int retValue = 0;
        retValue = Arrays.stream(niza)
                .filter(k -> k % 2 == 0).sum();
        return retValue;
    }

    public static int sumEven(int[] niza) {
        int retValue = 0;
        for (int i = 0; i < niza.length; i++) {
            if (niza[i] % 2 == 0) {
                retValue += niza[i];
            }
        }
        return retValue;
    }

    public static int sumOddStreams(int[] niza) {
        int retValue = 0;
        retValue = Arrays.stream(niza)
                .filter(k -> k % 2 == 1)
                .sum();
        return retValue;
    }

    public static int sumOdd(int[] niza){
        int retValue = 0;
        for (int i = 0; i < niza.length; i++) {
            if (niza[i] % 2 == 1) {
                retValue += niza[i];
            }
        }
        return retValue;
    }
    public static void main(String[] args) {
        int[] niza = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        System.out.println("Iterative sum of even elements = " + sumEven(niza));
        System.out.println("Streams sum of even elements = " + sumEvenStreams(niza));
        System.out.println("Iterative sum of odd elements = " + sumOdd(niza));
        System.out.println("Streams sum of odd elements = " + sumOddStreams(niza));
    }
}
