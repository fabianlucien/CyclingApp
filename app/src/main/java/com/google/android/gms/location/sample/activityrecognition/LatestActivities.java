package com.google.android.gms.location.sample.activityrecognition;

import java.util.Arrays;
import java.util.List;


public class LatestActivities {

  public static List<Integer> latestActivitiesList = Arrays.asList(0, 0, 0); // The three latest activities are logged

    public List<Integer> getLatestActivitiesList() {
        return latestActivitiesList;
    }

    public void updateLatestActivity(Integer firstActivity, Integer secondActivity, Integer thirdActivity){
        latestActivitiesList.set(0, firstActivity);
        latestActivitiesList.set(1, secondActivity);
        latestActivitiesList.set(2, thirdActivity);
    }
}

