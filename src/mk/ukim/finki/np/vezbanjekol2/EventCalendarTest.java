package mk.ukim.finki.np.vezbanjekol2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

class WrongDateException extends Exception {
    String wrongDate;

    public WrongDateException(Date wrongDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.wrongDate = dateFormat.format(wrongDate);
    }

    @Override
    public String getMessage() {
        return String.format("Wrong date: %s", wrongDate);
    }
}

class Event {
    String name;
    String location;
    Date date;

    public Event(String name, String location, Date date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    private String getDateInSpecificFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy HH:mm");
        return dateFormat.format(date);
    }

    @Override
    public String toString() {
        return String.format("%s at %s, %s",
                getDateInSpecificFormat()
                , location
                , name
        );
    }
}

class EventCalendar {
    Integer calendarYear;

    Set<Event> events;
    Map<Integer, Integer> eventsPerMonth;

    public EventCalendar(Integer calendarYear) {
        this.calendarYear = calendarYear;
        events = new TreeSet<>(Comparator.comparing(Event::getDate).thenComparing(Event::getName));
        eventsPerMonth = new HashMap<>();
        IntStream.range(1, 13).forEach(i -> eventsPerMonth.put(i, 0));
    }

    public void addEvent(String name, String location, Date date) throws WrongDateException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.YEAR) != calendarYear) {
            throw new WrongDateException(date);
        }

        Event event = new Event(name, location, date);
        Integer monthIndex = calendar.get(Calendar.MONTH) + 1;

        events.add(event);
        eventsPerMonth.put(monthIndex, (eventsPerMonth.get(monthIndex) + 1));

    }

    public void listEvents(Date date) {
        AtomicInteger counter = new AtomicInteger();
        events.stream()
                .filter(event -> compareToEventsByDate(event.getDate(), date))
                .forEach(event -> {
                    counter.getAndIncrement();
                    System.out.println(event.toString());
                });

        if (counter.intValue() == 0) {
            System.out.println("No events on this day!");
        }
    }

    public void listByMonth() {
        eventsPerMonth.forEach((key, value) -> System.out.println(String.format("%d : %d", key, value)));
    }

    private static boolean compareToEventsByDate(Date d1, Date d2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(d1);
        cal2.setTime(d2);

        return cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);

    }


}

public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = df.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        Date date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}
