package com.google.android.gms.location.sample.activityrecognition;

/**
 * Created by FabianLucien on 1/9/16.
 */
public class RegisteredActivity {

    String name;
    int percentage;

    public void setData(String name, int percentage){
        this.name = name;
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public int getPercentage() {
        return percentage;
    }

}



