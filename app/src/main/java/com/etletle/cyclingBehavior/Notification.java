package com.etletle.cyclingBehavior;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.location.sample.activityrecognition.R;

/**
 * Created by FabianLucien on 1/24/16.
 */
public class Notification {

    public void showNotification(Context context, String title, String message) {

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.cancel(1);

        Log.i("TestLog", "Notification send!");

        int icon = R.drawable.ic_launcher;
        Intent intent = new Intent(context, ProcessNotification.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent intent1 = new Intent(context, ProcessNotification.class);
        String notificationMessage = "correct";
        intent1.putExtra("NotificationMessage", notificationMessage);
        PendingIntent pIntent1 = PendingIntent.getActivity(context, 1, intent1, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent intent2 = new Intent(context, ProcessNotification.class);
        String notificationMessage2 = "incorrect";
        intent2.putExtra("NotificationMessage", notificationMessage2);
        PendingIntent pIntent2 = PendingIntent.getActivity(context, 2, intent2, PendingIntent.FLAG_CANCEL_CURRENT);

        // create actions for sdk => 23

        NotificationCompat.Action correctAction =
                new NotificationCompat.Action.Builder(R.drawable.ic_launcher, "Correct", pIntent1)
                        .build();

        NotificationCompat.Action incorrectAction =
                new NotificationCompat.Action.Builder(R.drawable.ic_launcher, "Incorrect", pIntent2)
                        .build();


        int currentApiVersion = android.os.Build.VERSION.SDK_INT;

        if (currentApiVersion >= Build.VERSION_CODES.M) {
            Log.i("General", "above 22 is calleded");

            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            android.app.Notification notification = builder.setSmallIcon(icon)
                    .setTicker(title)
                    .setWhen(0)
                    .addAction(correctAction)
                    .addAction(incorrectAction)
                    .setAutoCancel(true) // on tap, remove the notification
                    .setContentTitle(title)
                    .setStyle(inboxStyle)
                    .setContentIntent(resultPendingIntent)
                    .setContentText(message)
                    .setPriority(android.app.Notification.PRIORITY_HIGH)
                    .setVisibility(android.app.Notification.VISIBILITY_PUBLIC)
                    .build();
            notificationManager.notify(1, notification);

        } else {

            Log.i("General", "below 23 is called");
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            android.app.Notification notification = builder.setSmallIcon(icon)
                    .setTicker(title)
                    .setWhen(0)
                    .addAction(R.drawable.ic_launcher, "Correct", pIntent1)
                    .addAction(R.drawable.ic_launcher, "Incorrect", pIntent2)
                    .setAutoCancel(true) // on tap, remove the notification
                    .setContentTitle(title)
                    .setStyle(inboxStyle)
                    .setContentIntent(resultPendingIntent)
                    .setContentText(message)
                    .setPriority(android.app.Notification.PRIORITY_HIGH)
                    .build();
            notificationManager.notify(1, notification);

        }
    }

    public static void cancelNotification(Context ctx, int notifyId) {

        if (notifyId == 0) {
            return;
        } else {
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
            nMgr.cancel(notifyId);
        }
    }
}
