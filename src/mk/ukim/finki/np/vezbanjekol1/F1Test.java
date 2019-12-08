package mk.ukim.finki.np.vezbanjekol1;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Driver implements Comparable<Driver> {
    private String driverName;
    private String bestLap;

    Driver(String driverName, String bestLap) {
        this.driverName = driverName;
        this.bestLap = bestLap;
    }

    @Override
    public int compareTo(Driver driver) {
        return (this.bestLap.compareTo(driver.bestLap));
    }

    public String toString(int i) {
        return String.format("%d. %-10s%10s", i + 1, driverName, bestLap);
    }
}

class F1Race {
    private ArrayList<Driver> drivers;

    F1Race() {
        drivers = new ArrayList<>();
    }

    void readResults() {
        Scanner in = new Scanner(System.in);

        while (in.hasNextLine()) {
            String[] parts = in.nextLine().split("\\s+");
            String minimum = parts[1];
            for (int i = 2; i < parts.length; i++) {
                if (minimum.compareTo(parts[i]) > 0) {
                    minimum = parts[i];
                }
            }

            drivers.add(new Driver(parts[0], minimum));
        }
        in.close();
    }

    void printSorted() {
        PrintWriter out = new PrintWriter(System.out);
        Collections.sort(drivers);

        for (int i = 0; i < drivers.size(); i++) {
            System.out.println(drivers.get(i).toString(i));
        }

        out.close();
    }
}

public class F1Test {
    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults();
        f1Race.printSorted();
    }
}
