package mk.ukim.finki.np.vezbanjekol1;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

class SuperString {
    private LinkedList<String> superString = new LinkedList<>();

    public SuperString() {
        this.superString = new LinkedList<>();
    }

    public void append(String string) {
        superString.addLast(string);
    }

    public void insert(String string) {
        superString.addFirst(string);
    }

    public boolean contains(String string) {
        StringBuilder mergedSuperString = new StringBuilder();
        for (String s : superString) {
            mergedSuperString.append(s);
        }
        return mergedSuperString.toString().contains(string);
    }

    public void reverse() {
        Collections.reverse(superString);

        for (int i = 0; i < superString.size(); i++) {
            String tempString = superString.get(i);
            String reversedTempString = "";

            for (int j = 0; j < tempString.length(); j++) {
                reversedTempString += tempString.charAt(tempString.length() - 1 - j);
            }

            superString.remove(i);
            superString.add(i, reversedTempString);
        }
    }

    public void removeLast(int k) {
        for (int i = 0; i < k; i++) {
            superString.removeLast();
        }
    }

    @Override
    public String toString() {
        StringBuilder retValue = new StringBuilder();

        for(int i = 0; i< superString.size();i++){
            retValue.append(superString.get(i));
        }

        return retValue.toString();
    }
}

public class SuperStringTest {
    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (  k == 0 ) {
            SuperString s = new SuperString();
            while ( true ) {
                int command = jin.nextInt();
                if ( command == 0 ) {//append(String s)
                    s.append(jin.next());
                }
                if ( command == 1 ) {//insert(String s)
                    s.insert(jin.next());
                }
                if ( command == 2 ) {//contains(String s)
                    System.out.println(s.contains(jin.next()));
                }
                if ( command == 3 ) {//reverse()
                    s.reverse();
                }
                if ( command == 4 ) {//toString()
                    System.out.println(s);
                }
                if ( command == 5 ) {//removeLast(int k)
                    s.removeLast(jin.nextInt());
                }
                if ( command == 6 ) {//end
                    break;
                }
            }
        }
    }

}
