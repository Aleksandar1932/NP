//package mk.ukim.finki.np.lab7;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class TermFrequency {
    Set<String> wordsToIgnore;
    Map<String, Integer> wordsCountTable;
    Integer totalWords;

    public TermFrequency(InputStream in, String[] stop) {
        String rawInput = "";
        Scanner sc = new Scanner(in);

        while (sc.hasNextLine()) {
            rawInput += sc.nextLine() + " ";
        }

        this.totalWords = 0;

        wordsToIgnore = new HashSet<>();
        wordsToIgnore.addAll(Arrays.asList(stop));

        wordsCountTable = new TreeMap<>();

        processRawInput(rawInput);
    }

    public void processRawInput(String rawInput) {
        String[] tokenizedRawInput = rawInput.split("\\s+");
        this.totalWords = tokenizedRawInput.length;
        //System.out.println(Arrays.toString(tokenizedRawInput));
        Arrays.stream(tokenizedRawInput)
                .filter(word -> !wordsToIgnore.contains(word.toLowerCase()))
                .filter(this::isWord)
                .map(this::removeInterpunction)
                .map(String::toLowerCase)
//                .map(this::removeInterpunction)
//                .forEach(word -> {
//                    wordsCountTable.putIfAbsent(word.toLowerCase(), 1);
//                    wordsCountTable.computeIfPresent(word, (w, count) -> count++);
//                });
                .forEach(word -> {
                    wordsCountTable.putIfAbsent(word, 1);
                    wordsCountTable.computeIfPresent(word, (w, count) -> count + 1);
                });
    }

    public boolean isWord(String optionalWord){
        return !optionalWord.contains("0")
                && !optionalWord.contains("1")
                && !optionalWord.contains("2")
                && !optionalWord.contains("3")
                && !optionalWord.contains("4")
                && !optionalWord.contains("5")
                && !optionalWord.contains("6")
                && !optionalWord.contains("7")
                && !optionalWord.contains("8")
                && !optionalWord.contains("9")
                && !optionalWord.contains("-") && !optionalWord.equals(".") && !optionalWord.equals(",") && !optionalWord.equals("//");
    }


    public String removeInterpunction(String word) {
//        if (word.charAt(word.length() - 1) == '.' || word.charAt(word.length() - 1) == ',') {
//            return word.substring(0, word.length() - 1);
//        } else {
//            return word;
//        }

       return word.replaceAll("[.,“]*", "");
    }

    public int countTotal() {
       // System.out.println(wordsCountTable);
       //return wordsCountTable.values().stream().mapToInt(i -> i).sum();
        return wordsCountTable.size();
    }

    public int countDistinct() {
        return 0;
    }

    public List<String> mostOften(int k) {
        return null;
    }
}

public class TermFrequencyTest {
    public static void main(String[] args) throws FileNotFoundException {
        String[] stop = new String[]{"во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја"};
        TermFrequency tf = new TermFrequency(System.in, stop);
        System.out.println("Total map size:\n" + tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}
// vasiot kod ovde
