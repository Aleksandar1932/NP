package mk.ukim.finki.np.vezbanjekol2;

import java.util.*;
import java.util.stream.Collectors;

class Names {
    Map<String, Integer> names; //Counting map for the names;

    public Names() {
        this.names = new TreeMap<>();
    }

    public void addName(String name) {
        names.putIfAbsent(name, 0);
        names.put(name, names.get(name) + 1);
    }

    public void printN(int n) {
        names.entrySet().
                stream()
                .filter(entry -> entry.getValue() >= n)
                .forEach(entry -> System.out.println(String.format("%s (%d) %d", entry.getKey(), entry.getValue(), countUniqueLettersInName(entry.getKey()))));
    }

    private static int countUniqueLettersInName(String name) {
        Map<Character, Integer> letterCountingMap = new TreeMap<>();
        for (Character c : name.toLowerCase().toCharArray()) {
            letterCountingMap.putIfAbsent(c, 0);
            letterCountingMap.put(c, (letterCountingMap.get(c) + 1));
        }
        return letterCountingMap.size();
    }

    public String findName(int len, int index) {
        List<String> namesSmallerThanLen = names.keySet()
                .stream()
                .filter(name -> name.length() < len)
                .collect(Collectors.toList());
        return namesSmallerThanLen.get(index % namesSmallerThanLen.size());
    }
}

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}
