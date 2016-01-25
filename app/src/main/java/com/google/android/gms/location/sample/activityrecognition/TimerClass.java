package com.google.android.gms.location.sample.activityrecognition;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by FabianLucien on 1/23/16.
 */
class TimerClass extends TimerTask {

    public static TimerClass myTask = new TimerClass();
    public static Timer myTimer = new Timer();

    public static void start(){
        myTimer.schedule(myTask, 1000, 1000); // this is working, but giving an error because it says it's scheduled already 
    }

    @Override
    public void run() {
        String screen = String.valueOf(ScreenState.returnScreenState(DetectedActivitiesIntentService.powerManager));
        Log.i("GeneralTestLog time", screen);
    }
}
