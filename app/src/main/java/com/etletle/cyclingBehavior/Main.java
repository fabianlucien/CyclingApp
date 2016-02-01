package com.etletle.cyclingBehavior;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;

import com.google.android.gms.location.sample.activityrecognition.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FabianLucien on 1/25/16.
 */
public class Main extends Activity {

    public static PowerManager pm;
    public static Boolean screenState;
    public static Context contextTest; // this is for testing purposes, remove when done testing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void startCyclingSession(final Context context, PowerManager powerManager) {

        pm = powerManager;

        MainActivity.user.setCyclingThreadStartedForUser(true);
        MainActivity.user.setUserReceivedNotificationForSession(false);    // at the beginning of the session, we did not receive any notifications yet
        contextTest = context;// this context is only used for vibration, can be removed when done with testing

        Log.i("Testlog", "The session has started"); // this is called once, then not, then once again

        MainActivity.user.cyclingThread = new Thread() {
            @Override
            public void run() {

                try {

                    boolean cyclingThreadStartedForUser =  MainActivity.user.isCyclingThreadStartedForUser();

                    while(cyclingThreadStartedForUser) {
                        screenState = ScreenState.returnScreenState(pm);
                        cyclingThreadStartedForUser =  MainActivity.user.isCyclingThreadStartedForUser();

                        Log.i("Testlog", "Screenstate: " + screenState + " and cyclingThreadStarted: " + cyclingThreadStartedForUser);

                        boolean userReceivedNotificationForSession = MainActivity.user.isUserReceivedNotificationForSession();

                        if (screenState && !userReceivedNotificationForSession){

                            Notification notification = new Notification();
                            notification.showNotification(context, "Please stop cycling", "Quickly!");
                            MainActivity.user.setUserReceivedNotificationForSession(true);

                            logSuccessOfNotification();

                        } else if (!screenState && userReceivedNotificationForSession){
                            MainActivity.user.setUserReceivedNotificationForSession(false);
                            Notification.cancelNotification(context, 1);
                        }

                        cyclingThreadStartedForUser =  MainActivity.user.isCyclingThreadStartedForUser();
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }}};

        MainActivity.user.cyclingThread.start();

    }

    public static void stopCyclingSession(Context context){

        boolean cyclingThreadStartedForUser = MainActivity.user.isCyclingThreadStartedForUser();

        if (cyclingThreadStartedForUser){
            MainActivity.user.setCyclingThreadStartedForUser(false);                        // we stop the thread
            Notification.cancelNotification(context, 1);                                    // we cancel the notification
        }
        MainActivity.user.getUserLatestActivitiesList().resetLatestActivitiesList();    // we reset the activities list
        Log.i("Testlog", "stopCyclingSession is called and this is the state of the thread: " + String.valueOf(cyclingThreadStartedForUser));
    }













    public static void logSuccessOfNotification(){

        final List<Boolean> screenStates = new ArrayList<Boolean>();
        final int maxSavedScreenStates = 5;

        // we set max screenstates at 5. We find a notification successfull if at index 3 and 4 the screenstate is false


        MainActivity.user.assessCyclingSessionThread = new Thread() {
            @Override
            public void run() {

                try {
                    while (screenStates.size() < maxSavedScreenStates) {
                        screenStates.add(screenState);
                        sleep(1000);    // this is working as every five seconds for an id the array gets bigger
                    }
                    Log.i("General 5 screenStates", String.valueOf(screenStates));
                    assessSuccessOfNotification(screenStates);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        };

        MainActivity.user.assessCyclingSessionThread.start();
    }

    public static void assessSuccessOfNotification(List screenStates){

        boolean last = (Boolean) screenStates.get(screenStates.size() - 1);
        boolean secondLast = (Boolean) screenStates.get(screenStates.size() - 2);


        //***
        // remove vibration after done testing!
        // the if statements states that the user has three seconds to turn the screen off,
        // if the user is turning of the screen in the last seconds, the user is too late.
        //**

        if (last && secondLast || !last && secondLast || last && !secondLast){

//            Vibrator v = (Vibrator) contextTest.getSystemService(Context.VIBRATOR_SERVICE);
//            v.vibrate(500);
            Log.i("Testlog", "The success of the notification hsa been assessed");
//             the screen was on for the last two seconds
//             send unsuccessful to the db
        } else {
            // the screen was not on for the last two seconds (mea
            // send success to the db
            return;
        }
    }
}
