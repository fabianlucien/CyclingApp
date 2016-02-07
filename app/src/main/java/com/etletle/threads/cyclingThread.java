package com.etletle.threads;

import android.os.PowerManager;
import android.util.Log;
import android.content.Context;

/**
 * Created by FabianLucien on 2/7/16.
 */
public class CyclingThread {

    public PowerManager pm;
    public ScreenstateThread screenstateThread;


    public void startCyclingThread (PowerManager powerManager, Context context) {
        pm = powerManager;
        screenstateThread = new ScreenstateThread();
        screenstateThread.startScreenstateThread(powerManager, context);
        Log.i("TestLog", "A new cycling thread is started");
    }

    public void stopCyclingThread() throws InterruptedException {
        if (screenstateThread != null){
            screenstateThread.stopScreenstateThread();
        }
    }
}
