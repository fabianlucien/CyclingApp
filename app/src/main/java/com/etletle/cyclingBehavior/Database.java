package com.etletle.cyclingBehavior;

import android.content.Context;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.location.sample.activityrecognition.MainActivity;

/**
 * Created by FabianLucien on 2/3/16.
 */
public class Database {

    public Firebase mRef;

    public void onCreate(Context context) {
        Firebase.setAndroidContext(context);
        mRef = new Firebase("https://amber-inferno-1350.firebaseio.com/");
    }

    /*

    CHECK IF THERE'S INTERNET !! ALWAYS !!

     */

    // this sets a new amount of users, from then on, the userId can be found in the User object
    public void updateAmountOfUsers() {

        mRef.child("users").child("amountOfUsers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Object oldAmountOfUsers = snapshot.getValue();
                Log.i("TestLog", "This is the previous amount of users: " + String.valueOf(oldAmountOfUsers));
                long newAmountOfUsers = (Long) oldAmountOfUsers;
                newAmountOfUsers += 1;
                Firebase newAmountOfUsersFb = mRef.child("users").child("amountOfUsers");
                newAmountOfUsersFb.setValue(newAmountOfUsers);
                Log.i("TestLog", "This is the new amount of users: " + String.valueOf(newAmountOfUsers));
                MainActivity.user.setUserId(newAmountOfUsers);
                Log.i("TestLog", String.valueOf(MainActivity.user.getUserId()));

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }
}


