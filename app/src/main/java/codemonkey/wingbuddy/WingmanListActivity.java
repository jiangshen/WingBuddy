package codemonkey.wingbuddy;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WingmanListActivity extends AppCompatActivity {

    String userName;
    Firebase firebase;
    List<User> userList;
    UserListAdapter userListAdapter;
    ListView userListView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context rootContext = this;
        setContentView(R.layout.activity_wingman_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the message from the intent
        intent = getIntent();
        userName = intent.getStringExtra("user_name");

        userList = new ArrayList<>();
        userListAdapter = new UserListAdapter(this, R.layout.user_item, userList);

        userListView = (ListView) findViewById(R.id.user_list_view);
        userListView.setAdapter(userListAdapter);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                User o = (User) userListView.getItemAtPosition(position);
//                System.out.println(o.getName() + ", FUCK: " + position );
                notifyWingMan(arg1, o);
//                String str=(String)o;//As you are using Default String Adapter
//                Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
            }
        });

        Firebase.setAndroidContext(this);
        final Firebase firebase = new Firebase("https://wingbuddy.firebaseio.com/");

        firebase.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    User user = new User((Map) dataSnapshot.getValue());
                    userName = intent.getStringExtra("user_name");
//                    if (!user.getName().equals(userName)) {
                    if (!user.getName().equals(userName)) {

                        //add to list
                        userList.add(user);
                        userListAdapter = new UserListAdapter(rootContext, R.layout.user_item, userList);
                        userListView.setAdapter(userListAdapter);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void notifyWingMan(final View view, final User user) {

        final String message = "Hey " + user.getName() + ", could you be my Wing Buddy? Text me back at this number if you can help me out.";

        new AlertDialog.Builder(WingmanListActivity.this)
                .setTitle("Wing Buddy")
                .setMessage("This will send an SMS to: \n\n" + user.getName() + ": " + user.getPhoneNumber() + "\n\nShall I continue?")
                .setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(user.getPhoneNumber(), null, message, null, null);
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


}
