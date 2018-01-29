package ice.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by lla on 17-4-10.
 */
public class TimeUtil {

    static public final String FORMAT_DATE_ONLY         = "yyyyMMdd";

    public static long getRandomTimeBetweenTwoDates (long beginTime, long endTime) {
        long diff = endTime - beginTime + 1;
        return beginTime + (long) (Math.random() * diff);
    }

    public static Date parse(String str, String format)
    {
        try
        {
            SimpleDateFormat sf = new SimpleDateFormat(format);
            sf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            return sf.parse(str);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String getRandomDate(String beginDate, String endDate){
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(parse(beginDate, FORMAT_DATE_ONLY));
        long beginTime = calendar.getTimeInMillis();

        calendar.setTime(parse(endDate, FORMAT_DATE_ONLY));
        long endTime = calendar.getTimeInMillis();

        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_ONLY);
        Date randomDate = new Date(getRandomTimeBetweenTwoDates(beginTime, endTime));

        return dateFormat.format(randomDate);
    }

    public static void main(String[] args) {
        System.out.println(getRandomDate("20170301", "20170301"));
    }
}
