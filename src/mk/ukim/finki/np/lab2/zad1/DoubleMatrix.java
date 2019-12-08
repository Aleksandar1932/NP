package mk.ukim.finki.np.lab2.zad1;

import java.text.DecimalFormat;
import java.util.Arrays;

public class DoubleMatrix {
    private double[][] a;
    private int m; //Red
    private int n; //Kolona

    public DoubleMatrix(double[] matrix, int rows, int columns) throws InsufficientElementsException {
        if (rows > this.m || columns > this.n) {
            // throw new InsufficientElementsException();
        }
        int index = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = matrix[index];
                index++;
            }
        }
    }

    public String getDimensions() {
        return "[" + this.m + "x" + this.n + "]";
    }

    public int rows() {
        return this.m;
    }

    public int columns() {
        return this.n;
    }

    public double maxElementAtRow(int row) throws InvalidRowNumberException {
        if (row > m || row < 0) {
            throw new InvalidRowNumberException();

        } else {
            double max = a[row][0];

            for (int j = 0; j < n; j++) {
                if (a[row][j] > max) {
                    max = a[row][j];
                }
            }
            return max;
        }
    }

    public double maxElementAtColumn(int column) throws InvalidColumnNumberException {
        if (column > n || column < 0) {
            throw new InvalidColumnNumberException();
        } else {
            double max = a[0][column];

            for (int i = 0; i < m; i++) {
                if (a[i][column] > max) {
                    max = a[i][column];
                }
            }
            return max;
        }
    }

    public double sum() {
        double sum = 0.0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sum += a[i][j];
            }
        }
        return sum;
    }

    public double[] toSortedArray() {
        double[] array = new double[m * n];
        int arrIndex = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                array[arrIndex] = this.a[i][j];
                arrIndex++;
            }
        }

        sortArray(array);
        return array;
    }

    public void sortArray(double[] arrayToSort) {
        int n = arrayToSort.length;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (arrayToSort[j - 1] < arrayToSort[j]) {
                    swapValues(arrayToSort, j);
                }

            }
        }
    }

    private void swapValues(double[] arrayToSort, int j) {
        double temp;
        temp = arrayToSort[j - 1];
        arrayToSort[j - 1] = arrayToSort[j];
        arrayToSort[j] = temp;
    }

    @Override
    public String toString() {
        String retString = "";
        DecimalFormat df2 = new DecimalFormat("#.##");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                retString += df2.format(a[i][j]) + "\t";
            }
            retString += "\n";
        }

        return retString;
    }
}
