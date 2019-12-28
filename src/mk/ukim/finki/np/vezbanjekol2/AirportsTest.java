package mk.ukim.finki.np.vezbanjekol2;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

class Flight {
    String fromCode;
    String toCode;
    Integer time;
    Integer duration;

    public Flight(String fromCode, String toCode, Integer time, Integer duration) {
        this.fromCode = fromCode;
        this.toCode = toCode;
        this.time = time;
        this.duration = duration;
    }

    public String getFromCode() {
        return fromCode;
    }

    public String getToCode() {
        return toCode;
    }

    public Integer getTime() {
        return time;
    }

    public Integer getDuration() {
        return duration;
    }

    private String getDepartureTimeInHours() {
        Integer hour = (int) time / 60;
        Integer minutes = time - hour * 60;
        return String.format("%02d:%02d", hour, minutes);
    }

    private String getArrivalTimeInHours() {
        Integer hour = (int) ((time + duration) / 60);
        if (hour >= 24) {
            hour = hour - 24;
        }
        Integer minutes = (time + duration) - ((time + duration) / 60) * 60;
        return String.format("%02d:%02d", hour, minutes);
    }

    private String getDurationInHours() {
        Boolean days = false;
        if (time + duration > 1440) {
            days = true;
        }


        Integer hour = duration / 60;
        Integer minutes = duration - hour * 60;
        if (!days) {
            return String.format("%dh%02dm", hour, minutes);
        } else {
            return String.format("+1d %dh%02dm", hour, minutes);
        }
    }

    @Override
    public String toString() {
        return String.format("%s-%s %s-%s %s",
                fromCode, toCode,
                getDepartureTimeInHours(), getArrivalTimeInHours()
                , getDurationInHours());
    }
}

class Airport {
    String name;
    String country;
    String airportCode;
    Integer passengersPerYear;

    Set<Flight> fromFlights;
    Set<Flight> toFlights;

    public Airport(String name, String country, String airportCode, Integer passengersPerYear) {
        this.name = name;
        this.country = country;
        this.airportCode = airportCode;
        this.passengersPerYear = passengersPerYear;

        fromFlights = new TreeSet<>(Comparator.comparing(Flight::getToCode).thenComparing(Flight::getTime));
        toFlights = new TreeSet<>(Comparator.comparing(Flight::getToCode).thenComparing(Flight::getTime));
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public Integer getPassengersPerYear() {
        return passengersPerYear;
    }

    public void addFlightFrom(Flight flight) {
        fromFlights.add(flight);
    }

    public void addFlightTo(Flight flight) {
        toFlights.add(flight);
    }

    public void showFlightsFrom() {
        AtomicInteger counter = new AtomicInteger(0);

        System.out.println(String.format(
                "%s (%s)\n" +
                        "%s\n" +
                        "%d",
                name, airportCode, country, passengersPerYear));


        fromFlights.stream().forEach(flight -> {
            System.out.println(
                    String.format("%d. ", counter.incrementAndGet()) +

                            flight.toString());
        });
    }

    public Set<Flight> getFromFlights() {
        return fromFlights;
    }

    public Set<Flight> getToFlights() {
        return toFlights;
    }
}

class Airports {

    Map<String, Airport> airports;

    public Airports() {
        airports = new HashMap<>();
    }

    public void addAirport(String name, String country, String code, int passengers) {
        Airport airportToAdd = new Airport(name, country, code, passengers);
        airports.put(airportToAdd.getAirportCode(), airportToAdd);
    }

    public void addFlights(String from, String to, int time, int duration) {
        Flight flightToAdd = new Flight(from, to, time, duration);

        Airport airportFrom = airports.get(from);
        Airport airportTo = airports.get(to);

        airportFrom.addFlightFrom(flightToAdd);
        airportTo.addFlightTo(flightToAdd);
    }

    public void showFlightsFromAirport(String code) {
        Airport airportToShowFlightsFrom = airports.get(code);
        airportToShowFlightsFrom.showFlightsFrom();
    }

    public void showDirectFlightsFromTo(String from, String to) {
    /*
    - метод кој ги прикажува сите директни летови од аеродромот со код from до аеродромот со код to.
     */
        Airport airportToGetFlights = airports.get(from);

        airportToGetFlights.getFromFlights()
                .stream()
                .filter(flight -> flight.toCode.equals(to))
                .forEach(flight -> {
                    System.out.println(flight.toString());
                });

        if (airportToGetFlights.getFromFlights().stream().noneMatch(flight -> flight.toCode.equals(to))) {
            System.out.println(String.format("No flights from %s to %s",from,to));
        }
    }

    public void showDirectFlightsTo(String to) {

        Airport airportToShowFlightsTo = airports.get(to);

        airportToShowFlightsTo.getToFlights().forEach(flight -> {
            System.out.println(flight.toString());
        });

    }
}

public class AirportsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Airports airports = new Airports();
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] codes = new String[n];
        for (int i = 0; i < n; ++i) {
            String al = scanner.nextLine();
            String[] parts = al.split(";");
            airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            codes[i] = parts[2];
        }
        int nn = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < nn; ++i) {
            String fl = scanner.nextLine();
            String[] parts = fl.split(";");
            airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        int f = scanner.nextInt();
        int t = scanner.nextInt();
        String from = codes[f];
        String to = codes[t];
        System.out.printf("===== FLIGHTS FROM %S =====\n", from);
        airports.showFlightsFromAirport(from);
        System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
        airports.showDirectFlightsFromTo(from, to);
        t += 5;
        t = t % n;
        to = codes[t];
        System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
        airports.showDirectFlightsTo(to);
    }
}
