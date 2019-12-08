package mk.ukim.finki.np.vezbanjekol1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

class Triple<T extends Number> {
    private ArrayList<T> numbers = new ArrayList<>();

    Triple(T a, T b, T c) {
        numbers.add(a);
        numbers.add(b);
        numbers.add(c);
    }

    double max() {
        return numbers.stream()
                .mapToDouble(Number::doubleValue)
                .max()
                .getAsDouble();
    }

    double average() {
        return numbers.stream()
                .mapToDouble(Number::doubleValue)
                .average()
                .getAsDouble();
    }

    void sort() {
        numbers.sort(Comparator.comparingDouble(Number::doubleValue));
    }

    @Override
    public String toString() {
       return numbers.stream()
               .map(number -> String.format("%.2f",number.doubleValue()))
               .collect(Collectors.joining(" "));
    }
}

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.average());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.average());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.average());
        tDouble.sort();
        System.out.println(tDouble);
    }
}
