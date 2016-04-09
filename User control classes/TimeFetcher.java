import java.util.Date;
import java.util.Formatter;
import java.text.SimpleDateFormat;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class TimeFetcher {
    public static void main(String[] args) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        long longDateValue = (long) 1460217469071L;
        Date date = new Date(longDateValue);
        String strDate = formatter.format(date);
        formatter = new SimpleDateFormat("hh:mm");
        String strTime = formatter.format(date);
        System.out.println("At " + strTime + " Hours on " + strDate);
    }
}