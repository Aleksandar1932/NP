package mk.ukim.finki.np.lab4;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class LocalDateTest {
    public static void main(String[] args) {
        System.out.println(create());
        System.out.println(parse());
        System.out.println(with().getYear());
        System.out.println(withAdjuster());
        System.out.println(plus());
        System.out.println(minus());
        System.out.println(plusPeriod());
        System.out.println(isAfter());
        System.out.println(until());
    }

    private static LocalDate create() {
        return LocalDate.of(2015, 6, 18);
    }

    private static LocalDate parse() {
        return LocalDate.parse("2015-06-18");
    }

    private static LocalDate with() {
        LocalDate ld = DateAndTimes.LD_20150618;
        return ld.withYear(2015);
    }

    private static LocalDate withAdjuster() {
        LocalDate ld = DateAndTimes.LD_20150618;
        return ld.with(TemporalAdjusters.firstDayOfNextYear());
    }

    private static LocalDate plus() {
        LocalDate ld = DateAndTimes.LD_20150618;
        return ld.plusMonths(10);
    }

    private static LocalDate minus() {
        LocalDate ld = DateAndTimes.LD_20150618;
        return ld.minusDays(10);
    }

    private static LocalDate plusPeriod() {
        LocalDate ld = DateAndTimes.LD_20150618;
        return ld.plus(1, ChronoUnit.YEARS).plus(2, ChronoUnit.MONTHS).plus(3, ChronoUnit.DAYS);
    }

    private static boolean isAfter() {
        LocalDate ld = DateAndTimes.LD_20150618;
        LocalDate ld2 = DateAndTimes.LD_20150807;
        return ld2.isAfter(ld);
    }

    private static Period until() {
        LocalDate ld = DateAndTimes.LD_20150618;
        LocalDate ld2 = DateAndTimes.LD_20150807;
        return ld.until(ld2);
    }

}

class DateAndTimes {
    static final LocalDate LD_20150618 = LocalDate.of(2015, 6, 18);
    static final LocalDate LD_20150807 = LocalDate.of(2015, 8, 7);
}