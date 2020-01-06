package mk.ukim.finki.np.lab7;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class TermFrequency {
    Set<String> wordsToIgnore;
    Map<String, Integer> wordsCountTable;
    Integer totalWords;

    public TermFrequency(InputStream in, String[] stop) {
        StringBuilder rawInput = new StringBuilder();
        Scanner sc = new Scanner(in);

        while (sc.hasNextLine()) {
            rawInput.append(sc.nextLine()).append(" ");
        }

        this.totalWords = 0;
        wordsToIgnore = new HashSet<>();
        wordsToIgnore.addAll(Arrays.asList(stop));
        wordsCountTable = new TreeMap<>();

        processRawInput(rawInput.toString());
    }

    public void processRawInput(String rawInput) {
        String[] tokenizedRawInput = rawInput.split("\\s+");

        Arrays.stream(tokenizedRawInput)
                .map(this::removePunctuation)
                .map(String::toLowerCase)
                .filter(word -> !wordsToIgnore.contains(word.toLowerCase()))
                .forEach(word -> {
                    if (!word.equals("")) {
                        this.totalWords++;
                    }
                    wordsCountTable.putIfAbsent(word, 1);
                    wordsCountTable.computeIfPresent(word, (w, count) -> count + 1);
                });
    }


    public String removePunctuation(String word) {
        return word.replaceAll("[.,“]*", "");
    }

    public int countTotal() {
        return this.totalWords;
    }

    public int countDistinct() {
        return wordsCountTable.size();
    }

    public List<String> mostOften(int k) {
        return wordsCountTable.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .limit(k)
                .collect(Collectors.toList());
    }
}

public class TermFrequencyTest {
    public static void main(String[] args) {
        String[] stop = new String[]{"во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја"};
        TermFrequency tf = new TermFrequency(System.in, stop);
        System.out.println(tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}