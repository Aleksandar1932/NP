package mk.ukim.finki.np.vezbanje1;

interface Printable<T> {
    void print(T a, T b);
}


public class LambdaExpressionTest {

    public static void main(String[] args) {
        Printable obj;

        obj = (a, b) -> System.out.println("Hello " + a + " " + b);

        obj.print("Aleksandar","Ivanovski");
    }
}
