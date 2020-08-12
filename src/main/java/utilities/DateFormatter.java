package utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateFormatter {




    public static Date convertToDate(String dateS) throws ParseException {
        SimpleDateFormat sdf =new SimpleDateFormat("MM/dd/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        Date date=sdf.parse(dateS);
        return date;
    }
}
