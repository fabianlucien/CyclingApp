package com.etletle.cyclingBehavior;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.sample.activityrecognition.R;

/**
 * Created by FabianLucien on 2/1/16.
 */
public class ProcessNotification extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        Thread thread = new Thread(){
//
//            int testInt = 0;
//            @Override
//            public void run(){
//                // you should see the log message if the thread runs
//                try {
//
//                    while (!isAvailable()) {
//                        sleep(1000);
//                    }
//
//                    Log.i("Thread", String.valueOf(testInt));
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//        thread.start();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        onNewIntent(getIntent());

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(1); // this cancels the notification
    }

    @Override
    public void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey("NotificationMessage")) {

                String msg = extras.getString("NotificationMessage");

                if (msg.equals("correct")){
                    sendDataToDb("true positive");
                } else {
                    sendDataToDb("false positive");
                }

                // log button press text
                Log.i("Testlog", "input from notification is: " + msg);

            }
        }

        finish();
    }

    public Boolean isAvailable() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1    www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            if(reachable){
                System.out.println("Internet access");
                Log.i("Testlog", "network available!");

                return reachable;
            }
            else{
                System.out.println("No Internet access");
                Log.i("Testlog", "No network available!");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public void sendDataToDb(String result){

        switch (result) {
            case "true positive": ; // a notification is correctly send while performing a cycling activity
                break;
            case "false positive": ; // a notification is send while not performing cycling activity
                break;
            case "true negative": ; // a notification is not not send while not performing a cycling activity
                break;
            case "false negative": ; // a notification is not send while performing a cycling activity
                break;
        }
    }
}
