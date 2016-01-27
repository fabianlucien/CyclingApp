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
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.etletle.cyclingBehavior.Main;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;


/**
 *  IntentService for handling incoming intents that are generated as a result of requesting
 *  activity updates using
 *  {@link com.google.android.gms.location.ActivityRecognitionApi#requestActivityUpdates}.
 */

public class DetectedActivitiesIntentService extends IntentService {

    protected static final String TAG = "DetectedActivitiesIS";


    /** added code */
    public static PowerManager powerManager;
    public static Boolean sessionStarted = false;
    public static Context context;
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
        powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        Log.i("GeneralTestLog", "DetectedAc created");
        context = getApplicationContext();
    }

    /**
     * Handles incoming intents.
     *
     * @param intent The Intent is provided (inside a PendingIntent) when requestActivityUpdates()
     *               is called.
     */

    @Override
    public void onHandleIntent(Intent intent) {

        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
        Intent localIntent = new Intent(Constants.BROADCAST_ACTION);

        // Get the list of the probable activities associated with the current state of the
        // device. Each activity is associated with a confidence level, which is an int between
        // 0 and 100.

        ArrayList<DetectedActivity> detectedActivities = (ArrayList) result.getProbableActivities();
        ArrayList<RegisteredActivity> registeredActivitiesArr = new ArrayList<RegisteredActivity>();

        for (DetectedActivity da : detectedActivities) {

            String activityType = Constants.getActivityString(getApplicationContext(), da.getType()); // get activity type
            int activityConfidence = da.getConfidence();                                              // get confidence

            RegisteredActivity registeredActivity = new RegisteredActivity();
            registeredActivity.setData(activityType, activityConfidence);
            registeredActivitiesArr.add(registeredActivity); // add the registered activity to an array
        }

        // Logging all activities in one entry

        for (int i = 0; i < registeredActivitiesArr.size(); i++) {
            Log.i("GeneralTestLog act", registeredActivitiesArr.get(i).getName() + " " + registeredActivitiesArr.get(i).getPercentage());
        }

        String activityWithHighestValue = registeredActivitiesArr.get(0).getName();
        String targetActivity = "On a bicycle"; // On a bicycle

        if (activityWithHighestValue.equals(targetActivity)){
            MainActivity.latestActivitiesList.updateLatestActivities(1);
        } else {
            MainActivity.latestActivitiesList.updateLatestActivities(0);
        }

        int sumOfActivities = MainActivity.latestActivitiesList.sumOfActivities();

        Log.i("General", "Before if: " + String.valueOf(sessionStarted));

        if (sumOfActivities > 0 && !sessionStarted){
            Main.startCyclingBehaviorListener(context, powerManager);
            sessionStarted = true;
        } else if (sumOfActivities == 0 && sessionStarted){
            Main.stopCyclingBehaviorListener(context);
            sessionStarted = false;
        }

        Log.i("General", "After if: " + String.valueOf(sessionStarted));

        Log.i("GeneralTestLog sum", String.valueOf(sumOfActivities));
        Log.i("GeneralTestLog list", String.valueOf(MainActivity.latestActivitiesList.getLatestActivitiesList()));

        // Broadcast the list of detected activities.
        localIntent.putExtra(Constants.ACTIVITY_EXTRA, detectedActivities);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }
}


