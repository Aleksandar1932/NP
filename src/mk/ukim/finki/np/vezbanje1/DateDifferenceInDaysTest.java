package mk.ukim.finki.np.vezbanje1;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateDifferenceInDaysTest {
    public static void main(String[] args) throws ParseException {

        SimpleDateFormat myFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        String inputString1 = "23.01.2019 00:02:30";
        String inputString2 = "23.01.2019 00:00:00";

        Date d1 = myFormat.parse(inputString1);
        Date d2 = myFormat.parse(inputString2);

        long diff = d2.getTime() - d1.getTime();
        int seconds = (int) TimeUnit.SECONDS.convert(diff,TimeUnit.MILLISECONDS);
//        System.out.println ("Minutes: " + TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS));
        System.out.println(seconds);

    }
}
