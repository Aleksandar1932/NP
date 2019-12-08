package mk.ukim.finki.np.lab2.zad1;

public class InsufficientElementsException extends Exception {
    public InsufficientElementsException(){
        super("Insufficient number of elements");
    }
}
