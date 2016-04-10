package codemonkey.wingbuddy;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.telephony.gsm.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button refreshButton;
    TextView tvRoomStatus;
    TextView tvLastSeen;
    TextView tvTimeElapsed;

    Switch musicSwitch;

    String userName;

    String friendNumber;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the message from the intent
        Intent intent = getIntent();
        userName = intent.getStringExtra("user_name");

        Firebase.setAndroidContext(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final long longDateValue[] = new long[1];

        final Firebase firebase = new Firebase("https://wingbuddy.firebaseio.com/");
        tvRoomStatus = (TextView) findViewById(R.id.tv_wingman_status);
        tvLastSeen = (TextView) findViewById(R.id.tv_wingman_last_seen);
        tvTimeElapsed = (TextView) findViewById(R.id.tv_time_elapsed);

        firebase.child("lastSeen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                longDateValue[0] = (Long) dataSnapshot.getValue();
                updateDateText(longDateValue[0], tvRoomStatus, tvLastSeen, tvTimeElapsed);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //Bad things happen
            }
        });

        firebase.child("Users").child(userName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                longDateValue[0] = (Long) dataSnapshot.getValue();
//                updateDateText(longDateValue[0], tvRoomStatus, tvLastSeen, tvTimeElapsed);
                currentUser = new User((Map) dataSnapshot.getValue());
                friendNumber = currentUser.getRoommatePhoneNumber();
                System.out.println("Friend Number: " + friendNumber);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //Bad things happen
            }
        });

        refreshButton = (Button) findViewById(R.id.button_refresh_status);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDateText(longDateValue[0], tvRoomStatus, tvLastSeen, tvTimeElapsed);
            }
        });

        musicSwitch = (Switch) findViewById(R.id.sw_play);
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    firebase.child("music").setValue("True");
                    Snackbar.make(compoundButton.getRootView(), "Have some music for your soul eh?", Snackbar.LENGTH_SHORT).show();
                } else {
                    firebase.child("music").setValue("False");
                }
            }
        });

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        logoutAction();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                logoutAction();
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    public void notifyRoommate(final View view) {

        final String message = "Hey roomie, I would greatly appreciate if you could leave the room for a while, I have something important to do ;)";

        new android.app.AlertDialog.Builder(MainActivity.this)
                .setTitle("Wing Buddy")
                .setMessage("This will send an SMS to:\n\n" + " Your roommate: " + currentUser.getRoommatePhoneNumber() + "\n\nShall I continue?")
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(("+1"+currentUser.getRoommatePhoneNumber()), null, message, null, null);
                                    Snackbar.make(view, "SMS sent", Snackbar.LENGTH_SHORT).show();
                                }
                                catch (Exception e) {
                                    Snackbar.make(view, "SMS failed, please try again.", Snackbar.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                            }
                        })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }

    public void summonWingman(final View view) {
        Intent myIntent = new Intent(MainActivity.this, WingmanListActivity.class);
        myIntent.putExtra("user_name", userName);
        startActivity(myIntent);
    }

    /**
     * Perform logout
     */
    private void logoutAction() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
                                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
                                startActivity(myIntent);
                                finish();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();
    }

    public void updateDateText(long longDateValue, TextView roomStatus, TextView lastSeen, TextView timeGone) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        Date date = new Date(longDateValue);
        String strDate = formatter.format(date);
        formatter = new SimpleDateFormat("hh:mm:ss");
        String strTime = formatter.format(date);
        String dateText = "Movement last sensed at " + strTime + " on " + strDate;

        boolean roomIsEmpty = System.currentTimeMillis() - longDateValue > 60000;

        String status = roomIsEmpty ? "Probably Empty" : "Probably Occupied";
        int color = roomIsEmpty ? Color.parseColor("#009688") : Color.parseColor("#F44336");
        String last = (roomIsEmpty) ? "More than 1 minute ago" : "Less than a minute ago";
        String timeElapsed = "";

        if (System.currentTimeMillis() - longDateValue > 86400000) {
            last = "\nYour room has been empty for more than a day.\nAre you sure your roommate isn't dead?";
        } else {
            System.out.println(System.currentTimeMillis() - longDateValue);
            long systemDateValue = System.currentTimeMillis();
            Date differenceDate = new Date(systemDateValue - longDateValue + 2000);
            Date systemDate = new Date(systemDateValue);
            formatter = new SimpleDateFormat("hh");

            long hour = (systemDateValue - longDateValue) / 3600000;
            long minute = differenceDate.getMinutes();
            long second = differenceDate.getSeconds();

            timeElapsed += hour + ((hour > 1) ? " hours, " : " hour, ")
                    + minute + ((minute > 1) ? " minutes and " : " minute and ")
                    + second + ((second > 1) ? " seconds" : " second");

            //            dateText += "\nMovement last sensed " + formatter.format(new Date(systemDate.getHours()*3600000 - date.getHours()*3600000));
//            formatter = new SimpleDateFormat("mm");
//            formatter = new SimpleDateFormat("ss");
        }

        roomStatus.setText(status);
        roomStatus.setTextColor(color);
        lastSeen.setText(last);
        timeGone.setText(timeElapsed);

    }
}