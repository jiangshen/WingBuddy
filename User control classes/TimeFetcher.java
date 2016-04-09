import java.util.Date;
import java.util.Formatter;
import java.text.SimpleDateFormat;

public class TimeFetcher {
    public static void main(String[] args) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        long longDateValue = (long) 1460215891284L;
        Date date = new Date(longDateValue);
            String strDate = formatter.format(date);
            formatter = new SimpleDateFormat("hh:mm");
            String strTime = formatter.format(date);
            System.out.println("At " + strTime + " on " + strDate);
    }
}