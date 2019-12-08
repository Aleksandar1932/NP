package mk.ukim.finki.np.vezbanjekol1;

import java.util.Scanner;

class ZeroDenominatorException extends Exception {
    ZeroDenominatorException() {
        super("Denominator cannot be zero");
    }
}

class GenericFraction<T extends Number, M extends Number> {
    private T numerator;
    private M denominator;


    GenericFraction(T numerator, M denominator) throws ZeroDenominatorException {
        if (denominator.doubleValue() == 0) {
            throw new ZeroDenominatorException();
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }


    GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf) throws ZeroDenominatorException {

        Double resultNumerator = this.numerator.doubleValue() * gf.denominator.doubleValue()
                + gf.numerator.doubleValue() * this.denominator.doubleValue();
        Double resultDenominator = this.denominator.doubleValue() * gf.denominator.doubleValue();

        return new GenericFraction<>(resultNumerator, resultDenominator);
    }

    double toDouble() {
        return this.numerator.doubleValue() / this.denominator.doubleValue();
    }

    @Override
    public String toString() {
        int gcd = gcdFinder(this.numerator.doubleValue(), this.denominator.doubleValue());
        return String.format("%.2f / %.2f", this.numerator.doubleValue() / gcd, this.denominator.doubleValue() / gcd);
    }

    private int gcdFinder(double d1, double d2) {
        int n1 = (int) d1, n2 = (int) d2;
        while (n1 != n2) {
            if (n1 > n2) {
                n1 -= n2;
            } else {
                n2 -= n1;
            }
        }
        return n1;
    }

}

public class GenericFractionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double n1 = scanner.nextDouble();
        double d1 = scanner.nextDouble();
        float n2 = scanner.nextFloat();
        float d2 = scanner.nextFloat();
        int n3 = scanner.nextInt();
        int d3 = scanner.nextInt();
        try {
            GenericFraction<Double, Double> gfDouble = new GenericFraction<Double, Double>(n1, d1);
            GenericFraction<Float, Float> gfFloat = new GenericFraction<Float, Float>(n2, d2);
            GenericFraction<Integer, Integer> gfInt = new GenericFraction<Integer, Integer>(n3, d3);
            System.out.printf("%.2f\n", gfDouble.toDouble());
            System.out.println(gfDouble.add(gfFloat));
            System.out.println(gfInt.add(gfFloat));
            System.out.println(gfDouble.add(gfInt));
            gfInt = new GenericFraction<Integer, Integer>(n3, 0);
        } catch (ZeroDenominatorException e) {
            System.out.println(e.getMessage());
        }
        scanner.close();
    }

}
