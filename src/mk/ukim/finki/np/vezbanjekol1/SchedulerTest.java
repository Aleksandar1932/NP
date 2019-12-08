package mk.ukim.finki.np.vezbanjekol1;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class TimeStamp<T> implements Comparable<TimeStamp<T>> {
    private final LocalDateTime time;
    private final T element;

    public TimeStamp(LocalDateTime time, T element) {
        this.time = time;
        this.element = element;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public T getElement() {
        return element;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != this.getClass()) {
            return false;
        }

        TimeStamp<T> toCompare = (TimeStamp<T>) obj;

        return this.getTime().equals(toCompare.getTime());


    }

    @Override
    public String toString() {
        return String.format("%s %s", time.toString(), element.toString());
    }


    @Override
    public int compareTo(TimeStamp<T> other) {
        return this.getTime().compareTo(other.getTime());
    }
}

class Scheduler<T> {
    ArrayList<TimeStamp<T>> timeStamps = new ArrayList<>();

    void add(TimeStamp<T> t) {
        timeStamps.add(t);
    }

    boolean remove(TimeStamp<T> t) {
        if (timeStamps.contains(t)) {
            timeStamps.remove(t);
            return true;
        } else {
            return false;
        }
    }

    public TimeStamp<T> next() {
        TimeStamp<T> time = null;
        LocalDateTime currentTime = LocalDateTime.now();

        for(int i = 0 ; i < timeStamps.size();i++){
            if(timeStamps.get(i).getTime().isAfter(currentTime)){
                if(time == null){
                    time = timeStamps.get(i);
                }
                else if(timeStamps.get(i).getTime().isBefore(time.getTime())){
                    time = timeStamps.get(i);
                }

            }
        }

        return time;
    }


    public TimeStamp<T> last() {
        TimeStamp<T> time = null;
        LocalDateTime currentTime = LocalDateTime.now();

        for(int i = 0 ; i < timeStamps.size();i++){
            if(timeStamps.get(i).getTime().isBefore(currentTime)){
                if(time == null){
                    time = timeStamps.get(i);
                }
                else if(timeStamps.get(i).getTime().isAfter(time.getTime())){
                    time = timeStamps.get(i);
                }

            }
        }
        return time;
    }

    List<TimeStamp<T>> getAll(LocalDateTime begin, LocalDateTime end){
       return timeStamps.stream()
               .filter(timeStamp -> timeStamp.getTime().isAfter(begin) && timeStamp.getTime().isBefore(end))
               .collect(Collectors.toList());
    }

}

public class SchedulerTest {

    static final LocalDateTime TIME = LocalDateTime.of(2016, 10, 25, 10, 15);

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test TimeStamp with String
            TimeStamp<String> t = new TimeStamp<>(TIME, jin.next());
            System.out.println(t);
            System.out.println(t.getTime());
            System.out.println(t.getElement());
        }
        if (k == 1) { //test TimeStamp with ints
            TimeStamp<Integer> t1 = new TimeStamp<>(TIME, jin.nextInt());
            System.out.println(t1);
            System.out.println(t1.getTime());
            System.out.println(t1.getElement());
            TimeStamp<Integer> t2 = new TimeStamp<>(TIME.plusDays(10), jin.nextInt());
            System.out.println(t2);
            System.out.println(t2.getTime());
            System.out.println(t2.getElement());
            System.out.println(t1.compareTo(t2));
            System.out.println(t2.compareTo(t1));
            System.out.println(t1.equals(t2));
            System.out.println(t2.equals(t1));
        }
        if (k == 2) {//test TimeStamp with String, complex
            TimeStamp<String> t1 = new TimeStamp<>(ofEpochMS(jin.nextLong()), jin.next());
            System.out.println(t1);
            System.out.println(t1.getTime());
            System.out.println(t1.getElement());
            TimeStamp<String> t2 = new TimeStamp<>(ofEpochMS(jin.nextLong()), jin.next());
            System.out.println(t2);
            System.out.println(t2.getTime());
            System.out.println(t2.getElement());
            System.out.println(t1.compareTo(t2));
            System.out.println(t2.compareTo(t1));
            System.out.println(t1.equals(t2));
            System.out.println(t2.equals(t1));
        }
        if (k == 3) { //test Scheduler with String
            Scheduler<String> scheduler = new Scheduler<>();
            LocalDateTime now = LocalDateTime.now();
            scheduler.add(new TimeStamp<>(now.minusHours(2), jin.next()));
            scheduler.add(new TimeStamp<>(now.minusHours(1), jin.next()));
            scheduler.add(new TimeStamp<>(now.minusHours(4), jin.next()));
            scheduler.add(new TimeStamp<>(now.plusHours(2), jin.next()));
            scheduler.add(new TimeStamp<>(now.plusHours(4), jin.next()));
            scheduler.add(new TimeStamp<>(now.plusHours(1), jin.next()));
            scheduler.add(new TimeStamp<>(now.plusHours(5), jin.next()));
            System.out.println(scheduler.next().getElement());
            System.out.println(scheduler.last().getElement());
            List<TimeStamp<String>> result = scheduler.getAll(now.minusHours(3), now.plusHours(4).plusMinutes(15));
            String out = result.stream()
                    .sorted()
                    .map(TimeStamp::getElement)
                    .collect(Collectors.joining(", "));
            System.out.println(out);
        }
        if (k == 4) {//test Scheduler with ints complex
            Scheduler<Integer> scheduler = new Scheduler<>();
            int counter = 0;
            ArrayList<TimeStamp<Integer>> forRemoval = new ArrayList<>();
            while (jin.hasNextLong()) {
                TimeStamp<Integer> ti = new TimeStamp<>(ofEpochMS(jin.nextLong()), jin.nextInt());
                if ((counter & 7) == 0) {
                    forRemoval.add(ti);
                }
                scheduler.add(ti);
                ++counter;
            }
            jin.next();

            while (jin.hasNextLong()) {
                LocalDateTime left = ofEpochMS(jin.nextLong());
                LocalDateTime right = ofEpochMS(jin.nextLong());
                List<TimeStamp<Integer>> res = scheduler.getAll(left, right);
                Collections.sort(res);
                System.out.println(left + " <: " + print(res) + " >: " + right);
            }
            System.out.println("test");
            List<TimeStamp<Integer>> res = scheduler.getAll(ofEpochMS(0), ofEpochMS(Long.MAX_VALUE));
            Collections.sort(res);
            System.out.println(print(res));
            forRemoval.forEach(scheduler::remove);
            res = scheduler.getAll(ofEpochMS(0), ofEpochMS(Long.MAX_VALUE));
            Collections.sort(res);
            System.out.println(print(res));
        }
    }

    private static LocalDateTime ofEpochMS(long ms) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(ms), ZoneId.systemDefault());
    }

    private static <T> String print(List<TimeStamp<T>> res) {
        if (res == null || res.size() == 0) return "NONE";
        return res.stream()
                .map(each -> each.getElement().toString())
                .collect(Collectors.joining(", "));
    }
}
