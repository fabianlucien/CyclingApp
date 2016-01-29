package com.google.android.gms.location.sample.activityrecognition;

import java.util.Arrays;
import java.util.List;


public class LatestActivities {

   public List<Integer> latestActivitiesList = Arrays.asList(0, 0); // The two latest activities are logged. 0 is false, 1 is true

    public List<Integer> getLatestActivitiesList() {
        return latestActivitiesList;
    }

    public void updateLatestActivities(int newActivityValue) {

        int listSize = latestActivitiesList.size();

        for (int i = 0; i < listSize - 1; i++) { // listSize - 1 makes sure the loop does executes the last time
            int oldIndex = listSize - 2 - i;
            int targetIndex = listSize - 1 - i;
            int valueToBeMoved = latestActivitiesList.get(oldIndex);
            latestActivitiesList.set(targetIndex, valueToBeMoved);
        }

        latestActivitiesList.set(0, newActivityValue);
    }

    public void resetLatestActivitiesList(){
        for (int i = 0; i < latestActivitiesList.size(); i++){
            latestActivitiesList.set(i, 0);
        }
    }

    public Integer sumOfActivities(){
        int sum = 0;

        for (int i=0; i < latestActivitiesList.size(); i++){
            sum += latestActivitiesList.get(i);
        }

        return sum;
    }

}

