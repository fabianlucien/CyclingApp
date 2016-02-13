package com.etletle.threads;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

import com.etletle.cyclingBehavior.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FabianLucien on 2/8/16.
 */
public class ScreenstateThread {
    public Thread screenstateThread;

    public boolean screenOn;
    public boolean saveScreenStates;
    public boolean notificationHasBeenSend;

    public List<Boolean> screenStatesList = new ArrayList<Boolean>();
    public int maxSavedScreenStatesInList = 5;

    public void startScreenstateThread(final PowerManager pm, final Context context) {

        screenstateThread = new Thread() {
            @Override
            public void run() {

                try {

                    while(!Thread.currentThread().isInterrupted()) {

//                        long currentThreadId = Thread.currentThread().getId();
//                        Log.i("TestLog", "currentThreadId is: " + String.valueOf(currentThreadId));

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
                            screenOn = pm.isInteractive();

                        } else {
                            screenOn = pm.isScreenOn();
                        }

                        if (screenOn && !notificationHasBeenSend) {
                            Notification notification = new Notification();

                            notification.showNotification(context, "Stop cycling", "Stop cycling");
                            saveScreenStates = true;
                            notificationHasBeenSend = true;

                        } else if (!screenOn){
                            if (notificationHasBeenSend){
                                notificationHasBeenSend = false;
                            }
                        }

                        if (saveScreenStates){

                            if (screenStatesList.size() < maxSavedScreenStatesInList) {
                                screenStatesList.add(screenOn);
                            } else {
                                assessSavedScreenStates(screenStatesList);
                                saveScreenStates = false;
                                Log.i("TestLog", String.valueOf(screenStatesList));
                                screenStatesList.clear();
                            }
                        }
                        sleep(1000);

//                        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
//                        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
//
//                        String testString = "";
//
//                        for (int i = 0; i < threadArray.length; i++){
//                            long iD = threadArray[i].getId();
//
//                            testString += String.valueOf(iD + "  |  ");
//                        }
//                        Log.i("TestLog", String.valueOf(threadArray.length));
//                        Log.i("TestLog", testString);
//                        testString = "";
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }}};

        screenstateThread.start();
    }

    public static void assessSavedScreenStates(List screenStates){

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

            Log.i("TestLog", "Unsuccessful notification");
//             send unsuccessful to the db
        } else {
            Log.i("TestLog", "Successful notification");
            return;
        }
    }

    public void stopScreenstateThread() throws InterruptedException {

        if (screenstateThread != null && !screenstateThread.isInterrupted()){
            screenstateThread.sleep(100);
            screenstateThread.interrupt();
            screenstateThread.join();
        }
    }
}
