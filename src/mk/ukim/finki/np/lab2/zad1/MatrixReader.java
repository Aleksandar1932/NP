package mk.ukim.finki.np.lab2.zad1;

import java.io.InputStream;
import java.util.Scanner;

public class MatrixReader {
    public static DoubleMatrix read(InputStream input) throws InsufficientElementsException {
        Scanner in = new Scanner(input);
        int m = in.nextInt();
        int n = in.nextInt();

        int length=m*n;
        double [] d=new double[length];
        for(int i=0;i<length;i++)
        {
            d[i]=in.nextDouble();
        }
        in.close();
        return new DoubleMatrix(d,m,n);
    }
}
