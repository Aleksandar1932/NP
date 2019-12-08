package mk.ukim.finki.np.vezbanjekol1;

import java.text.DecimalFormat;
import java.util.*;

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}

class InvalidTimeException extends Exception {
    private int hour;
    private int minutes;

    InvalidTimeException(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }

    @Override
    public String getMessage() {
        return "" + hour + minutes;
    }
}

class UnsupportedFormatException extends Exception {
    private String unsupportedTime;

    UnsupportedFormatException(String unsupportedTime) {
        this.unsupportedTime = unsupportedTime;
    }

    @Override
    public String getMessage() {
        return "" + unsupportedTime;
    }
}

class Time implements Comparable<Time> {
    private int hour;
    private int minute;

    Time(int hour, int minute) throws InvalidTimeException {
        if (hour > 23 || hour < 0 || minute > 59 || minute < 0) {
            throw new InvalidTimeException(hour, minute);
        }

        this.hour = hour;
        this.minute = minute;
    }

    String time24() {
        DecimalFormat formatter = new DecimalFormat("00");
        return String.format("%2d:%s", hour, formatter.format(minute));
    }

    String time12() {
        DecimalFormat formatter = new DecimalFormat("00");
        String retValue = "";
        if (hour == 0) {
            retValue = String.format("%2d:%s AM", hour + 12, formatter.format(minute));
        } else if (hour >= 1 && hour <= 11) {
            retValue = String.format("%2d:%s AM", hour, formatter.format(minute));
        } else if (hour == 12) {
            retValue = String.format("%2d:%s PM", hour, formatter.format(minute));
        } else if (hour >= 13 && hour < 24) {
            retValue = String.format("%2d:%s PM", hour - 12, formatter.format(minute));
        }
        return retValue;
    }

    @Override
    public int compareTo(Time m) {
        if (this.hour != m.hour) {
            return Integer.compare(this.hour, m.hour);
        }
        return Integer.compare(this.minute, m.minute);
    }
}

class TimeTable {
    private ArrayList<Time> timesList;


    TimeTable() {
        this.timesList = new ArrayList<>();
    }

    void readTimes() throws UnsupportedFormatException, InvalidTimeException {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String[] tokenizedLine = sc.nextLine().split("\\s+");
            for (String s : tokenizedLine) {
                timesList.add(timeParser(s));
            }

        }
    }

    private Time timeParser(String timeToken) throws UnsupportedFormatException, InvalidTimeException {
        String[] hourMinuteTime;
        if (timeToken.contains(".")) {
            hourMinuteTime = timeToken.split("\\.");
        } else if (timeToken.contains(":")) {
            hourMinuteTime = timeToken.split(":");
        } else {
            throw new UnsupportedFormatException(timeToken);
        }
        return new Time(Integer.parseInt(hourMinuteTime[0]), Integer.parseInt(hourMinuteTime[1]));
    }

    void writeTimes(TimeFormat format) {
        Collections.sort(timesList);
        if (format == TimeFormat.FORMAT_24) {
            for (Time time : timesList) {
                System.out.println(time.time24());
            }
        }

        if (format == TimeFormat.FORMAT_AMPM) {
            for (Time time : timesList) {
                System.out.println(time.time12());
            }
        }
    }
}

public class TimesTest {
    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes();
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(TimeFormat.FORMAT_AMPM);

    }
}
