package codemonkey.wingbuddy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Deb Banerji on 09-Apr-16.
 */
public class UserListAdapter extends ArrayAdapter<HashMap> {

    private List users;

    public UserListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        users = objects;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.user_item, parent, false);
        }

        User user = (User) users.get(position);

        TextView usernameText = (TextView) convertView.findViewById(R.id.usernameView);
        usernameText.setText(user.getName());

        TextView phoneNumberText = (TextView) convertView.findViewById(R.id.numberView);
        phoneNumberText.setText("Phone number: " + user.getPhoneNumber());

    //        if (article instanceof HashMap) {
    //            HashMap<String, Object> m = (HashMap) article;
    //            TextView titleText = (TextView) convertView.findViewById(com.example.SnapNews.R.id.titleText);
    //            //titleText.setText((String) (m.get("title"))+(m.get("seen")).toString()); //debug statement
    //            titleText.setText((String) (m.get("name")));
    //            TextView summaryText = (TextView) convertView.findViewById(com.example.SnapNews.R.id.summaryText);
    //            summaryText.setText((String) (m.get("summary")));
    //            TextView timeText = (TextView) convertView.findViewById(com.example.SnapNews.R.id.timeText);
    //            Date resultDate = new Date((long) (m.get("postTime")));
    //            String stringDate = resultDate.toString();
    //            String[] splitDate = stringDate.split(" ");
    //            stringDate = "Shared: " + splitDate[0] + " " + splitDate[1] + " " + splitDate[2] + ", " + splitDate[3] + " ";
    //            timeText.setText(stringDate);
    //        }
        return convertView;
    }
}
