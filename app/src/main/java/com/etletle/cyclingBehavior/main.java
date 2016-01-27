package com.etletle.cyclingBehavior;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;

import com.google.android.gms.location.sample.activityrecognition.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FabianLucien on 1/25/16.
 */
public class Main extends Activity {

    public static Thread thread;
    public static Thread arrayThread;
    public static PowerManager pm;

    public static Boolean screenState;
    public static Boolean notificationSendForSession = false;
    public static Boolean screenStateCycleStarted = false;
    public static Context contextTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void startCyclingBehaviorListener(final Context context, PowerManager powerManager) {
        Log.i("GeneralTestLog", "I want to be called once"); // this is called once, then not, then once again
        pm = powerManager;
        screenStateCycleStarted = true;
        contextTest = context;

         thread = new Thread() {
            @Override
            public void run() {
                try {
                    while(screenStateCycleStarted) {
                        sleep(1000);
                        screenState = ScreenState.returnScreenState(pm);

                        Log.i("General", "This is the screenstate: " + String.valueOf(screenState));

                        // if screen is on and no notification has been send yet, send a notification and
                        // assess if the notification has been successful.
                        if (screenState && !notificationSendForSession){

                            notificationSendForSession = true;
                            Notification notification = new Notification();
                            notification.showNotification(context, "Please stop cycling", "Quickly!");

                            List<Boolean> screenStates = new ArrayList<Boolean>();
                            screenStates.add(screenState);
//                            Log.i("GeneralTestLog", "ScreenStatefunction called because of :" + screenState);
                            logSuccessOfNotification();

                        } else if (!screenState && notificationSendForSession){
                            //reset the boolean for sending a notification
                            notificationSendForSession = false;
                        }

//                        assessCyclingBehavior();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        };

        thread.start();
        MainActivity.cyclingThreadIsRunning = true;
    }

    public static void logSuccessOfNotification(){

        final List<Boolean> screenStates = new ArrayList<Boolean>();
        final int maxSavedScreenStates = 5;

        // we set max screenstates at 5. We find a notification successfull if at index 3 and 4 the screenstate is false

        arrayThread = new Thread() {
            @Override
            public void run() {

               try {
                    while (screenStates.size() < maxSavedScreenStates) {
                        screenStates.add(screenState);
                        sleep(1000);    // this is working as every five seconds for an id the array gets bigger
                    }
                   Log.i("General 5 screenStates", String.valueOf(screenStates));
                   assessSuccessOfNotification(screenStates);

//
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        };

        arrayThread.start();
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

            Vibrator v = (Vibrator) contextTest.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);
            Log.i("General", "I was called");
//             the screen was on for the last two seconds
//             send unsuccessful to the db
        } else {
            // the screen was not on for the last two seconds (mea
            // send success to the db
            return;
        }
    }


    public static void stopCyclingBehaviorListener(Context context){
        MainActivity.cyclingThreadIsRunning = false;
        notificationSendForSession = false;
        Notification.cancelNotification(context, 1);
        screenStateCycleStarted = false;
        return;
    }
}
