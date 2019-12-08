package mk.ukim.finki.np.lab4;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;

/**
 * LocalTime API tests
 */
public class LocalTimeTest {
    public static void main(String[] args) {
        System.out.println(localTimeOfHourToMinute());
        System.out.println(localTimeOfHourToNanoSec());
        System.out.println(localTimeParse());
        System.out.println(localTimeWith());
        System.out.println(localTimePlus());
        System.out.println(localTimeMinus());
        System.out.println(localTimeMinusDuration());
        System.out.println(localDateIsBefore());
        System.out.println(localTimeTruncatedTo());
    }

    static LocalTime localTimeOfHourToMinute() {
        return LocalTime.of(23, 7);
    }

    static LocalTime localTimeOfHourToNanoSec() {
        return LocalTime.of(23, 7, 3, 100000000);
    }

    static LocalTime localTimeParse() {
        return LocalTime.parse("23:07:03.1");
    }

    static LocalTime localTimeWith() {
        LocalTime lt = DateAndTimes.LT_23073050;
        return lt.withHour(21);
    }

    static LocalTime localTimePlus() {
        LocalTime lt = DateAndTimes.LT_23073050;
        return lt.plusMinutes(30);
    }

    static LocalTime localTimeMinus() {
        LocalTime lt = DateAndTimes.LT_23073050;
        return lt.minusHours(3);
    }


    static LocalTime localTimeMinusDuration() {
        LocalTime lt = DateAndTimes.LT_23073050;
        Duration d = Duration.ofMillis((3 * 3600 + 30 * 60 + 20) * 1000 + 200);
        return lt.minus(d);
    }

    static boolean localDateIsBefore() {
        LocalTime lt = DateAndTimes.LT_23073050;
        LocalTime lt2 = DateAndTimes.LT_12100000;
        return lt2.isBefore(lt);
    }

    static LocalTime localTimeTruncatedTo() {
        LocalTime lt = DateAndTimes.LT_23073050;
        return lt.truncatedTo(ChronoUnit.MINUTES);
    }

    static class DateAndTimes {
        static final LocalTime LT_23073050 = LocalTime.of(23, 7, 30, 500000000);
        static final LocalTime LT_12100000 = LocalTime.of(12, 10);
    }

}