package mk.ukim.finki.np.vezbanjekol2;

import java.util.*;

class Participant {
    String code;
    String name;
    Integer age;

    public Participant(String code, String name, Integer age) {
        this.code = code;
        this.name = name;
        this.age = age;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
      return String.format("%s %s %d",code,name,age);
    }
}

class Audition {

    Map<String, TreeSet<Participant>> participants;

    public Audition() {
        this.participants = new HashMap<>();
    }

    void addParticipant(String city, String code, String name, int age) {
        participants.putIfAbsent(
                city,
                new TreeSet<>(
                        Comparator.comparing(Participant::getCode)
                ));

        Participant participantToAdd = new Participant(code, name, age);

        TreeSet<Participant> set = participants.get(city);
        set.add(participantToAdd);
        participants.put(city, set);
    }

    void listByCity(String city) {
       participants.get(city).stream()
               .sorted(
                       Comparator.comparing(Participant::getName)
                                .thenComparing(Participant::getAge)
               )
               .forEach(System.out::println);
    }
}

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticipant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}
