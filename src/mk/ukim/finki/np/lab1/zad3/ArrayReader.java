package mk.ukim.finki.np.lab1.zad3;

import java.io.InputStream;
import java.util.Scanner;

public class ArrayReader {

    public static IntegerArray readIntegerArray(InputStream input) {
        Scanner scanner = new Scanner(input);
        int arraySize = scanner.nextInt();
        int readArray[] = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            readArray[i] = scanner.nextInt();
        }

        return new IntegerArray(readArray);
    }
}
