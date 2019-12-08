package mk.ukim.finki.np.lab1.zad3;

import java.util.Arrays;
import java.util.Optional;

public final class IntegerArray {
    private int a[];

    public IntegerArray(int[] a) {
        this.a = Arrays.copyOf(a,a.length);
    }

    public int length() {
        return a.length;
    }

    public int getElementAt(int i) {
        return a[i];
    }

    public int sum() {
        return Arrays.stream(a).sum();
    }

    public double average() {
        return Arrays.stream(a).average().getAsDouble();
    }

    public IntegerArray getSorted() {
        int[] retArray = new int[this.length()];

        for(int i = 0;i<this.length();i++){
            retArray[i] = this.getElementAt(i);
        }

        sortArray(retArray);

        return new IntegerArray(retArray);
    }

    public void sortArray(int[] retArray) {
        for (int i = 0; i < retArray.length; i++) {
            for (int j = i; j < retArray.length; j++) {
                if (retArray[i] > retArray[j]) {
                    swapArrayValues(retArray, i, j);
                }
            }
        }
    }

    private void swapArrayValues(int[] retArray, int i, int j) {
        int temp = retArray[i];
        retArray[i] = retArray[j];
        retArray[j] = temp;
    }

    public IntegerArray concat(IntegerArray ia) {
        int retArraySize = this.a.length + ia.length();
        int[] retArray = new int[retArraySize];
        int i=0,k=0;
        for(i = 0;i<this.length();i++){
            retArray[i] = this.getElementAt(i);
        }

        for(k=0;k<ia.length();k++){
            retArray[i]=ia.getElementAt(k);
            i++;
        }

        return new IntegerArray(retArray);
    }

    @Override
    public String toString() {
        String retString = "";
        retString += "[";
        int i;
        for (i = 0; i < this.length()-1; i++) {
            retString += a[i];
            retString += ", ";
        }
        retString += a[i];
        retString += "]";

        return retString;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IntegerArray array2 = (IntegerArray)obj;
        if (this.length() != ((IntegerArray) obj).length())
            return false;
        for (int i=0; i<length(); i++) {
            if (a[i] != array2.getElementAt(i))
                return false;
        }
        return true;
    }
}
