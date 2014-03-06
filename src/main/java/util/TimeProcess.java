package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 10/11/13
 * Time: 2:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimeProcess {
    Calendar calendar = Calendar.getInstance();

    public String getDate(String time) {
        String ymd_date;
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        calendar.setTimeInMillis(Long.parseLong(time));
        ymd_date = formatter.format(calendar.getTime());
        return ymd_date;
    }

    public String getHour(String time) {
        String h_date;
        DateFormat formatter = new SimpleDateFormat("hh");
        calendar.setTimeInMillis(Long.parseLong(time));
        h_date = formatter.format(calendar.getTime());
        return h_date;
    }

    public static void main(String[] args) {
        TimeProcess timeProcess = new TimeProcess();
        String time = "1381456273688";
        System.out.println(timeProcess.getDate(time));
        System.out.println(timeProcess.getHour(time));
    }
}
