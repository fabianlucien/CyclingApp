/**
 * Copyright 2014 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.gms.location.sample.activityrecognition;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;
import java.util.List;

/**
 *  IntentService for handling incoming intents that are generated as a result of requesting
 *  activity updates using
 *  {@link com.google.android.gms.location.ActivityRecognitionApi#requestActivityUpdates}.
 */

public class DetectedActivitiesIntentService extends IntentService  {

    protected static final String TAG = "DetectedActivitiesIS";

    /**
     * This constructor is required, and calls the super IntentService(String)
     * constructor with the name for a worker thread.
     */

    public DetectedActivitiesIntentService() {
        // Use the TAG to name the worker thread.
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    /**
     * Handles incoming intents.
     *
     * @param intent The Intent is provided (inside a PendingIntent) when requestActivityUpdates()
     *               is called.
     */

    @Override
    protected void onHandleIntent(Intent intent) {

        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
        Intent localIntent = new Intent(Constants.BROADCAST_ACTION);

        // Get the list of the probable activities associated with the current state of the
        // device. Each activity is associated with a confidence level, which is an int between
        // 0 and 100.
        ArrayList<DetectedActivity> detectedActivities = (ArrayList) result.getProbableActivities();
        ArrayList<RegisteredActivity> registeredActivitiesArr = new ArrayList<RegisteredActivity>();

        List<Integer> testList = LatestActivities.latestActivitiesList;

        Log.i("List", String.valueOf(testList));

        for (DetectedActivity da : detectedActivities) {

            String activityType = Constants.getActivityString(getApplicationContext(), da.getType()); // get activity type
            int activityConfidence = da.getConfidence();                                              // get confidence

            RegisteredActivity registeredActivity = new RegisteredActivity(); // create registeredActivity object
            registeredActivity.setName(activityType);              // add name to object
            registeredActivity.setPercentage(activityConfidence); // add confidence percentage to object

            registeredActivitiesArr.add(registeredActivity); // add the registered activity to an array
        }

        // Logging all activities in one entry

            for (int i = 0; i < registeredActivitiesArr.size(); i++) {
                Log.i("ListLog", registeredActivitiesArr.get(i).getName() + " " + registeredActivitiesArr.get(i).getPercentage());
            }

        isScreenOn();

        // Old data entry is moved up one index. The oldest entry is removed by moving up the indexes.

        testList.set(2, testList.get(1)); // move index 0 to index 1
        testList.set(1, testList.get(0)); // move index 1 to index 2

        Log.i("List", registeredActivitiesArr.get(0).getName().getClass().getName());
        String activityWithHighestValue = registeredActivitiesArr.get(0).getName();

        if (activityWithHighestValue.equals("On a bicycle")){
            Log.i("Listtest", "Cycling!");
            testList.set(0, 1);  // if the newest activity is our target activity, set true
        } else {
            testList.set(0, 0); // if the newest activity is not our target activity, set false
            Log.i("Listtest", "Not cycling!");
        }

        Log.i("ListLog", String.valueOf(testList));

        // Now we have three indexes which contain either true or false for our target activity. If
        // the threshold of 1 gets surpassed, and screen is on, we going to send a notification, as this means
        // that the target activity is being performed by the user for the past few seconds

        // Broadcast the list of detected activities.
        localIntent.putExtra(Constants.ACTIVITY_EXTRA, detectedActivities);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }

    private void isScreenOn() {
        // create var context
        Context context = getBaseContext();

//        KeyguardManager myKM = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
//        if( myKM.inKeyguardRestrictedInputMode()) {
//            //it is locked
//            Log.i("ListLog","locked");
//        } else {
//            //it is not locked
//            Log.i("ListLog","not locked");
//        }

    }

    public void createNotification(){

        Log.i("Noti", "Send");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);  //If set, the activity will not be launched if it is already running at the top of the history stack.
        PendingIntent intent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(intent);


        builder.setTicker("Please stop cycling"); // normally, a xml resource would be here
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setAutoCancel(true); // remove all previous notifications
        builder.setPriority(android.app.Notification.PRIORITY_MAX); // notification is hovering and on lock screen

        android.app.Notification notification = builder.build();

        RemoteViews contentview = new RemoteViews(getPackageName(), R.layout.notification);

        final String text = "This is text"; // Nexus6, api23: this is the text that is set for the notification
        contentview.setTextViewText(R.id.textView, text);

        notification.contentView = contentview;

        if (Build.VERSION.SDK_INT >=16){
            RemoteViews expendedView =
                    new RemoteViews(getPackageName(), R.layout.notification_expanded);
            notification.bigContentView = expendedView;
        }
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0, notification);
    }
}


