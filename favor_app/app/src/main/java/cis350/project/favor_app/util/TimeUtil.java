package cis350.project.favor_app.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {
    private static final SimpleDateFormat MONGO_DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private static final SimpleDateFormat APP_FORMAT =
            new SimpleDateFormat("MM-dd-yyyy hh:mm a");

    public static String convertFromMongoToAppFormat(String date) {
        MONGO_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT"));
        APP_FORMAT.setTimeZone(TimeZone.getDefault());
        try {
            Date fromMongo = MONGO_DATE_FORMAT.parse(date);

            return APP_FORMAT.format(fromMongo);
        } catch (Exception e) {
            e.printStackTrace();
            return date;
        }
    }

    public static String getDateInAppFormat(Date date) {
        APP_FORMAT.setTimeZone(TimeZone.getDefault());

        return APP_FORMAT.format(date);
    }


}
