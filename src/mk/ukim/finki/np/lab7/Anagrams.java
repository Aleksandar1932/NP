package mk.ukim.finki.np.lab7;

import java.io.InputStream;
import java.util.*;

public class Anagrams {
    public static void main(String[] args) {
        findAll(System.in);
    }

    public static void findAll(InputStream inputStream) {
        ArrayList<String> rawInput = new ArrayList<>();
        Scanner in = new Scanner(inputStream);
        while (in.hasNextLine()) {
            rawInput.add(in.nextLine());
        }

        processRawInput(rawInput);
    }

    public static void processRawInput(ArrayList<String> rawInput) {
        Map<Integer, ArrayList<String>> anagramGroups = new HashMap<>();

        rawInput.forEach(s -> {
            int intValue = calculateIntegerValueOfString(s);

            if (!anagramGroups.containsKey(intValue)) {
                ArrayList<String> temp = new ArrayList<>();
                temp.add(s);
                anagramGroups.put(intValue, temp);
            } else {
                ArrayList<String> temp = anagramGroups.get(intValue);
                temp.add(s);
                anagramGroups.put(intValue, temp);
            }
        });

        printAnagrams(anagramGroups);
    }

    public static void printAnagrams(Map<Integer, ArrayList<String>> anagramGroups) {
        Map<String, ArrayList<String>> printMap = new TreeMap<>(); //Kluc prviot zbor, value listata;

        /*
        Za da go ispolnam baranjeto za da gi pecatam sortirani spored prviot zbor vo grupata anagrami
        kreiram edna mapa vo koja sto kluc e prviot zbor a vrednost e celata grupa, pa bidejki e treeMap sortirano
        mi e spored klucot, t.e. spored prviot zbor od grupata, pa baranjeto mi e ispolneto.
         */

        anagramGroups.values().forEach(list -> printMap.put(list.get(0), list));
        printMap.values().stream()
                .filter(list -> list.size() >= 5)
                .forEach(list -> System.out.println(arrayStringFormatter(list)));
    }

    private static String arrayStringFormatter(ArrayList<String> array) {
        /*
            Example: [a, b, c, d] => a b c d
            Warning: Bez prazno mesto na kraj;
         */
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.size() - 1; i++) {
            sb.append(array.get(i)).append(" ");
        }
        sb.append(array.get(array.size() - 1));
        return sb.toString();
    }

    private static int calculateIntegerValueOfString(String string) {
        int retValue = 1;
        for (int i = 0; i < string.length(); i++) {
            retValue *= string.charAt(i);
        }
        return retValue;
    }
}
