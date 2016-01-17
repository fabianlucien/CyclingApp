package com.google.android.gms.location.sample.activityrecognition;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by FabianLucien on 1/17/16.
 */
public class ScreenActivity extends AppCompatActivity {

    public void recognizeScreenState() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                    Log.d("Screen", Intent.ACTION_SCREEN_OFF);
                } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                    Log.d("Screen", Intent.ACTION_SCREEN_ON);
                    Log.d("Screen", "I've send a notification on screen_on");
                }
            }
        }, intentFilter);
    }
}
