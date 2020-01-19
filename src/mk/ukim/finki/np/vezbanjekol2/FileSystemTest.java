package mk.ukim.finki.np.vezbanjekol2;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class File {
    String name;
    Integer size;
    LocalDateTime timestamp;

    public File(String name, Integer size, LocalDateTime timestamp) {
        this.name = name;
        this.size = size;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public Integer getSize() {
        return size;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isHiddenFile() {
        return this.name.startsWith(".");
    }

    public int getYear() {
        return timestamp.getYear();
    }

    @Override
    public String toString() {
        return String.format("%-10s %5dB %s", name, size, timestamp);
    }

}

class FileSystem {
    Map<Character, TreeSet<File>> folders;
    Comparator<File> fileComparator = Comparator
            .comparing(File::getTimestamp)
            .thenComparing(File::getName)
            .thenComparing(File::getSize);

    public FileSystem() {
        this.folders = new HashMap<>();
    }

    public void addFile(char folder, String name, int size, LocalDateTime createdAt) {
        folders.putIfAbsent(folder, new TreeSet<>(fileComparator)
        );

        folders.get(folder).add(new File(name, size, createdAt));
    }

    public List<File> findAllHiddenFilesWithSizeLessThen(int size) {
        return folders.values().stream()
                .flatMap(Collection::stream)
                .filter(File::isHiddenFile)
                .filter(file -> file.getSize() < size)
                .collect(Collectors.toList());
    }

    public int totalSizeOfFilesFromFolders(List<Character> foldersNames) {
        return folders.entrySet().stream()
                .filter(characterTreeSetEntry -> foldersNames.contains(characterTreeSetEntry.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .map(file -> file.size)
                .mapToInt(Integer::intValue).sum();
    }

    public Map<Integer, Set<File>> byYear() {
        Map<Integer, Set<File>> returnMap = new TreeMap<>();
        folders.values().stream().flatMap(Collection::stream).forEach(file -> {
            returnMap.putIfAbsent(file.getYear(), new TreeSet<>(fileComparator));
            returnMap.get(file.getYear()).add(file);
        });

        return returnMap;
    }

    public Map<String, Long> sizeByMonthAndDay() {
        Map<String, Long> returnMap = new TreeMap<>();

        folders.values().stream()
                .flatMap(Collection::stream)
                .forEach(file -> {
                    String key = file.getTimestamp().getMonth().toString() + "-" + file.getTimestamp().getDayOfMonth();
                    returnMap.putIfAbsent(key, 0L);
                    returnMap.computeIfPresent(key, (k, v) -> v += file.getSize());
                });
        return returnMap;
    }

}

public class FileSystemTest {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            fileSystem.addFile(parts[0].charAt(0), parts[1],
                    Integer.parseInt(parts[2]),
                    LocalDateTime.of(2016, 12, 29, 0, 0, 0).minusDays(Integer.parseInt(parts[3]))
            );
        }
        int action = scanner.nextInt();
        if (action == 0) {
            scanner.nextLine();
            int size = scanner.nextInt();
            System.out.println("== Find all hidden files with size less then " + size);
            List<File> files = fileSystem.findAllHiddenFilesWithSizeLessThen(size);
            files.forEach(System.out::println);
        } else if (action == 1) {
            scanner.nextLine();
            String[] parts = scanner.nextLine().split(":");
            System.out.println("== Total size of files from folders: " + Arrays.toString(parts));
            int totalSize = fileSystem.totalSizeOfFilesFromFolders(Arrays.stream(parts)
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toList()));
            System.out.println(totalSize);
        } else if (action == 2) {
            System.out.println("== Files by year");
            Map<Integer, Set<File>> byYear = fileSystem.byYear();
            byYear.keySet()
                    .forEach(key -> {
                        System.out.printf("Year: %d\n", key);
                        Set<File> files = byYear.get(key);
                        files.forEach(System.out::println);
                    });
        } else if (action == 3) {
            System.out.println("== Size by month and day");
            Map<String, Long> byMonthAndDay = fileSystem.sizeByMonthAndDay();
            byMonthAndDay.keySet().stream().sorted()
                    .forEach(key -> System.out.printf("%s -> %d\n", key, byMonthAndDay.get(key)));
        }
        scanner.close();
    }
}
