package mk.ukim.finki.np.vezbanjekol1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

class Measurment {
    private double temperature;
    private double humidity;
    private double wind;
    private double visibility;
    private Date timestamp;

    Measurment(double temperature, double humidity, double wind, double visibility, Date timestamp) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.wind = wind;
        this.visibility = visibility;
        this.timestamp = timestamp;
    }

    Date getTimestamp() {
        return timestamp;
    }

    double getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s", temperature, wind, humidity, visibility, timestamp);
    }
}

class WeatherStation {
    private ArrayList<Measurment> measurmentsList;
    private int daysToKeep;

    WeatherStation(int daysToKeep) {
        this.measurmentsList = new ArrayList<>();
        this.daysToKeep = daysToKeep;
    }

    void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date) {
        Measurment measurmentToAdd = new Measurment(temperature, humidity, wind, visibility, date);

        if (measurmentsList.size() == 0) {
            measurmentsList.add(measurmentToAdd);
            return;
        }

        boolean cannotBeAdded = false;
        cannotBeAdded = checkMeasurmentValidity(measurmentToAdd, cannotBeAdded); //Proveruva dali merenjeto se razlikuva za 2.5 min;
        if (cannotBeAdded) {
            return;
        }

        removeOlderMeasurments(measurmentToAdd); //Brisenje na site merenja koi ne se spored specifikacijata.
        measurmentsList.add(measurmentToAdd);
    }

    private boolean checkMeasurmentValidity(Measurment measurmentToAdd, boolean cannotBeAdded) {
        for (Measurment measurment : measurmentsList) {
            Date date1 = measurment.getTimestamp();
            Date date2 = measurmentToAdd.getTimestamp();

            long diff = date1.getTime() - date2.getTime();
            int differenceInSeconds = Math.abs((int) TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS));

            if (differenceInSeconds < 150) {
                cannotBeAdded = true;
                break;
            }
        }
        return cannotBeAdded;
    }

    private void removeOlderMeasurments(Measurment measurmentToAdd) {
        for (int i = 0; i < measurmentsList.size(); i++) {
            Date date1 = measurmentsList.get(i).getTimestamp();
            Date date2 = measurmentToAdd.getTimestamp();

            long diff = date1.getTime() - date2.getTime();
            int differenceInDays = Math.abs((int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));

            if (differenceInDays >= daysToKeep) {
                measurmentsList.remove(measurmentsList.get(i));
            }
        }
    }

    int total() {
        return measurmentsList.size();
    }

    void status(Date from, Date to) {
        String returnString = measurmentsList.stream()
                .filter(m -> m.getTimestamp().equals(from)
                        || m.getTimestamp().equals(to)
                        || (m.getTimestamp().after(from) && m.getTimestamp().before(to)))
                .map(m -> String.format("%s", m.toString()))
                .collect(Collectors.joining("\n"));

        if(returnString.length()==0){
            throw new RuntimeException();
        }
        System.out.println(returnString);

        OptionalDouble average = measurmentsList.stream()
                .filter(m -> m.getTimestamp().equals(from)
                    || m.getTimestamp().equals(to)
                    || (m.getTimestamp().after(from) && m.getTimestamp().before(to)))
                .mapToDouble(Measurment::getTemperature).average();

        if(average.isPresent()){
            System.out.println(String.format("Average temperature: %.2f",average.getAsDouble()));
        }
    }
}

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}