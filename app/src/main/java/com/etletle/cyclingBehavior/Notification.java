package com.etletle.cyclingBehavior;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.location.sample.activityrecognition.MainActivity;
import com.google.android.gms.location.sample.activityrecognition.R;

/**
 * Created by FabianLucien on 1/24/16.
 */
public class Notification {

    public static void showNotification(Context context, String title, String message) {

        int icon = R.drawable.cast_ic_notification_on;
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        android.app.Notification notification = builder.setSmallIcon(icon)
                .setTicker(title)
                .setWhen(0)
                .setAutoCancel(true) // remove previous notifications
                .setContentTitle(title)
                .setStyle(inboxStyle)
                .setContentIntent(resultPendingIntent)
                .setContentText(message)
                .setPriority(android.app.Notification.PRIORITY_HIGH)
                .build();

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
