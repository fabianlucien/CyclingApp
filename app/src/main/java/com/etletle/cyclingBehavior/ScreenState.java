//package com.etletle.cyclingBehavior;
//
//import android.os.PowerManager;
//
///**
// * Created by FabianLucien on 1/19/16.
// */
//public class ScreenState {
//
//    public boolean screenOn;
//
//    public boolean returnScreenState(PowerManager pm){
//        // With context, you get global information about an application's environment
//        // getSystemService retrieves information from a class
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
//            screenOn = pm.isInteractive();
//
//        } else {
//            screenOn = pm.isScreenOn();
//        }
//
//        return screenOn;
//    }
//}