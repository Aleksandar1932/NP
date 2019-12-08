package mk.ukim.finki.np.av2;

import java.math.BigDecimal;

class BigComplex {
    private BigDecimal real;
    private BigDecimal imag;

    public BigComplex(BigDecimal real, BigDecimal imag) {
        this.real = real;
        this.imag = imag;
    }

    public BigComplex add(BigComplex complex){
        BigDecimal real = this.real.add(complex.real);
        BigDecimal imag = this.imag.add(complex.imag);
        return new BigComplex(real,imag);
    }

    @Override
    public String toString() {
        return "BigComplex{" +
                "real=" + real +
                ", imag=" + imag +
                '}';
    }
}

public class BigComplexTest {
    public static void main(String[] args) {
        BigComplex c1 = new BigComplex(BigDecimal.valueOf(11.111111),BigDecimal.valueOf(10.999999));
        BigComplex c2 = new BigComplex(BigDecimal.valueOf(100.100), BigDecimal.valueOf(15.0000));

        System.out.println(c1.toString());
        System.out.println(c2.toString());
        System.out.println(c1.add(c2).toString());
    }
}
