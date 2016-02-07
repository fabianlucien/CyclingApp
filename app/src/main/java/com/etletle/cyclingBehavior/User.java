package com.etletle.cyclingBehavior;

import com.google.android.gms.location.sample.activityrecognition.LatestActivities;

import java.util.Date;

/**
 * Created by FabianLucien on 2/1/16.
 */
public class User {

//    String userId = String.valueOf(UUID.randomUUID());
    public long userId;
    Date userDate = new Date();

    boolean appIsOn = false;

    int amountOfUserSessions = 0;
    int amountOfUserSessionEntries = 0;
    int amountOfNotificationsSend = 0;

    boolean cyclingThreadStartedForUser = false;
    boolean userReceivedNotificationForSession = false;

    LatestActivities userLatestActivitiesList = new LatestActivities();

    Thread cyclingThread;
    Thread assessCyclingSessionThread;

    // for testing purposes

    int amountOfFalsePositives;
    int amountOfTruePositives;
    int amountOfFalseNegatives;
    int amountOfTrueNegatives;

    // Threads getters and setters

    public Thread getAssessCyclingSessionThread() {
        return assessCyclingSessionThread;
    }

    public void setAssessCyclingSessionThread(Thread assessCyclingSessionThread) {
        this.assessCyclingSessionThread = assessCyclingSessionThread;
    }

    public Thread getCyclingThread() {
        return cyclingThread;
    }

    public void setCyclingThread(Thread cyclingThread) {
        this.cyclingThread = cyclingThread;
    }

    // Getter and setter for activities list

    public LatestActivities getUserLatestActivitiesList() {
        return userLatestActivitiesList;
    }

    public void setUserLatestActivitiesList(LatestActivities userLatestActivitiesList) {
        this.userLatestActivitiesList = userLatestActivitiesList;
    }

    // Getters and setters for userId and date created

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getUserDate() {
        return userDate;
    }

    public void setUserDate(Date userDate) {
        this.userDate = userDate;
    }

    // Getter and setter for app state

    public boolean isAppIsOn() {
        return appIsOn;
    }

    public void setAppIsOn(boolean appIsOn) {
        this.appIsOn = appIsOn;
    }

    // Getters and setters for app data related to sessions, entries and notifications send

    public int getAmountOfUserSessions() {
        return amountOfUserSessions;
    }

    public void setAmountOfUserSessions(int amountOfUserSessions) {
        this.amountOfUserSessions = amountOfUserSessions;
    }

    public int getAmountOfUserSessionEntries() {
        return amountOfUserSessionEntries;
    }

    public void setAmountOfUserSessionEntries(int amountOfUserSessionEntries) {
        this.amountOfUserSessionEntries = amountOfUserSessionEntries;
    }

    public int getAmountOfNotificationsSend() {
        return amountOfNotificationsSend;
    }

    public void setAmountOfNotificationsSend(int amountOfNotificationsSend) {
        this.amountOfNotificationsSend = amountOfNotificationsSend;
    }


    // Getter and setter for cycling thread boolean

    public boolean isCyclingThreadStartedForUser() {
        return cyclingThreadStartedForUser;
    }

    public void setCyclingThreadStartedForUser(boolean cyclingThreadStartedForUser) {
        this.cyclingThreadStartedForUser = cyclingThreadStartedForUser;
    }

    // Getter and setter for notification boolean


    public boolean isUserReceivedNotificationForSession() {
        return userReceivedNotificationForSession;
    }

    public void setUserReceivedNotificationForSession(boolean userReceivedNotificationForSession) {
        this.userReceivedNotificationForSession = userReceivedNotificationForSession;
    }

    // Getters and setters for testing purposes

    public int getAmountOfFalsePositives() {
        return amountOfFalsePositives;
    }

    public void setAmountOfFalsePositives(int amountOfFalsePositives) {
        this.amountOfFalsePositives = amountOfFalsePositives;
    }

    public int getAmountOfTruePositives() {
        return amountOfTruePositives;
    }

    public void setAmountOfTruePositives(int amountOfTruePositives) {
        this.amountOfTruePositives = amountOfTruePositives;
    }

    public int getAmountOfFalseNegatives() {
        return amountOfFalseNegatives;
    }

    public void setAmountOfFalseNegatives(int amountOfFalseNegatives) {
        this.amountOfFalseNegatives = amountOfFalseNegatives;
    }

    public int getAmountOfTrueNegatives() {
        return amountOfTrueNegatives;
    }

    public void setAmountOfTrueNegatives(int amountOfTrueNegatives) {
        this.amountOfTrueNegatives = amountOfTrueNegatives;
    }
}

