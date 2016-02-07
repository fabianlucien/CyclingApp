package com.etletle.threads;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

import com.etletle.cyclingBehavior.Notification;
import com.etletle.cyclingBehavior.ScreenState;
import com.google.android.gms.location.sample.activityrecognition.MainActivity;

import java.util.Set;

/**
 * Created by FabianLucien on 2/7/16.
 */
public class cyclingThread {

    public PowerManager pm;
    public Thread cyclingThread;


    public void startCyclingThread (final Context context, PowerManager powerManager) {

        pm = powerManager;

        MainActivity.user.setCyclingThreadStartedForUser(true);
        MainActivity.user.setUserReceivedNotificationForSession(false);    // at the beginning of the session, we did not receive any notifications yet

        Log.i("Testlog", "The session has started"); // this is called once, then not, then once again

        cyclingThread = new Thread() {
            @Override
            public void run() {

                try {

                    while (!Thread.currentThread().isInterrupted()){

                    }

                    while(cyclingThreadStartedForUser && !Thread.currentThread().isInterrupted()) {
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

                        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
                        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);


                        Log.i("Testlog", String.valueOf(threadArray.length));
                        MainActivity.logHeap();


                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }}};

        MainActivity.user.cyclingThread.start();
    }
}
