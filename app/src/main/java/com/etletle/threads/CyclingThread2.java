package com.etletle.threads;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

import com.etletle.cyclingBehavior.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FabianLucien on 2/10/16.
 */
public class CyclingThread2 {

    //* researching Runnable *//

    public PowerManager pm;
    public Thread thread;
    public Context context;

    public void startCyclingThread (PowerManager powerManager, Context context) {

        this.context = context;
        this.pm = powerManager;

        thread = new Thread(new CyclingTask());
        thread.start();

        Log.i("TestLog", "A new cycling thread is started");
    }

    class CyclingTask implements Runnable {

        public boolean screenOn;
        public boolean saveScreenStates;
        public boolean notificationHasBeenSend;

        public List<Boolean> screenStatesList = new ArrayList<Boolean>();
        public int maxSavedScreenStatesInList = 5;

        public void run() {
            try {
                while(!thread.isInterrupted()) {

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
                            thread.sleep(1000);
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
        }
    }



        public static void assessSavedScreenStates(List screenStates){

            boolean last = (Boolean) screenStates.get(screenStates.size() - 1);
            boolean secondLast = (Boolean) screenStates.get(screenStates.size() - 2);

            if (last && secondLast || !last && secondLast || last && !secondLast){
                Log.i("TestLog", "Unsuccessful notification");
            } else {
                Log.i("TestLog", "Successful notification");
                return;
            }
        }

        public void stopScreenstateThread() throws InterruptedException {

            if (thread != null && !thread.isInterrupted()){
                thread.sleep(100);
                thread.interrupt();
                thread.join();
            }
        }
}
