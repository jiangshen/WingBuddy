package codemonkey.wingbuddy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

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


}
