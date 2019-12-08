package mk.ukim.finki.np.vezbanjekol1;

import java.util.Scanner;

class MinMax<T extends Comparable<T>> {
    private T min;
    private T max;
    private int total;
    private int minCount;
    private int maxCount;

    MinMax() {
        total = 0;
        minCount = 0;
        maxCount = 0;
    }

    void update(T element) {
        if (total == 0) {
            min = element;
            max = element;
        }

        if (element.compareTo(min)<0) {
            min = element;
            minCount = 1;
        }
        else if(element.compareTo(min)==0){
            minCount++;
        }

        if(element.compareTo(max)>0){
            max = element;
            maxCount=1;
        }
        else if(element.compareTo(max)==0){
            maxCount++;
        }

        total++;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d\n",min.toString(),max.toString(),total-(maxCount+minCount));
    }
}

public class MinAndMax {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<>();
        for(int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
        System.out.println(strings);
        MinMax<Integer> ints = new MinMax<>();
        for(int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
    }
}
