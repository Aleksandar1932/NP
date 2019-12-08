package mk.ukim.finki.np.vezbanjekol1;

import java.util.*;

class Measurement {
    private int day;
    private double minTemp;
    private double maxTemp;
    private double avgTemp;
    private int count;

    Measurement(int day, double minTemp, double maxTemp, double avgTemp, int count) {
        this.day = day;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.avgTemp = avgTemp;
        this.count = count;
    }

    String toStringCF(char scale) {
        if (scale == 'C') {
            return String.format("%3d: Count:  %2d Min: %6.2fC Max: %6.2fC Avg: %6.2fC", day, count, minTemp, maxTemp, avgTemp);

        } else {
            return String.format("%3d: Count:  %2d Min: %6.2fF Max: %6.2fF Avg: %6.2fF", day, count, convertToF(minTemp), convertToF(maxTemp), convertToF(avgTemp));
        }
    }

    int getDay() {
        return day;
    }

    private double convertToF(double tempC) {
        return ((tempC * 9) / 5) + 32;
    }
}

class DailyTemperatures {
    private ArrayList<Measurement> dataSheet;

    DailyTemperatures() {
        this.dataSheet = new ArrayList<>();
    }

    void readTemperatures() {
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            String[] input = in.nextLine().split("\\s++");
            if (input[0].equals("")) {
                break;
            }
            //Vo input[0] e brojot na denot;
            //Vo input[1,......input.length()] se naogjaat merenjata na temperaturite;
            double[] measurments = new double[input.length - 1];

            for (int i = 0; i < input.length - 1; i++) {
                String temp = input[i + 1].substring(0, input[i + 1].length() - 1);
                char sign = input[i + 1].charAt(input[i + 1].length() - 1);
                double tempC;
                if (sign == 'F') {
                    tempC = convertToC(Double.parseDouble(temp));
                } else {
                    tempC = Double.parseDouble(temp);
                }
                measurments[i] = tempC;
            }
            int count = measurments.length;
            Measurement tempMeasurement = new Measurement(Integer.parseInt(input[0]), findMin(measurments), findMax(measurments), findAverage(measurments), count);
            dataSheet.add(tempMeasurement);
        }
    }

    private double findMin(double[] array) {
        double min = array[0];
        for (double v : array) {
            if (v < min) {
                min = v;
            }
        }
        return min;
    }

    private double findMax(double[] array) {
        double max = array[0];
        for (double v : array) {
            if (v > max) {
                max = v;
            }
        }
        return max;
    }

    private double findAverage(double[] array) {
        OptionalDouble retValue = Arrays.stream(array).average();
        if (retValue.isPresent()) {
            return retValue.getAsDouble();
        } else {
            return -1;
        }
    }

    void writeDailyStats(char scale) {
        sortList();
        if (scale == 'C') {
            for (Measurement measurement : dataSheet) {
                System.out.println(measurement.toStringCF('C'));
            }
        } else {
            for (Measurement measurement : dataSheet) {
                System.out.println(measurement.toStringCF('F'));
            }
        }
    }

    private double convertToC(double tempF) {
        return ((tempF - 32) * 5) / 9;
    }

    private void sortList() {
        for (int i = 0; i < dataSheet.size(); i++) {
            for (int j = i; j < dataSheet.size(); j++) {
                if (dataSheet.get(i).getDay() > dataSheet.get(j).getDay()) {
                    Collections.swap(dataSheet, i, j);

                }
            }
        }
    }
}

public class DailyTemperatureTest {

    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures();
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats('C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats('F');
    }
}