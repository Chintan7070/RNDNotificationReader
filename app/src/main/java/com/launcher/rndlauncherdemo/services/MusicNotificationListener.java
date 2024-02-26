package com.launcher.rndlauncherdemo.services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import com.launcher.rndlauncherdemo.R;
import com.launcher.rndlauncherdemo.utils.ConstantVal;

public class MusicNotificationListener extends NotificationListenerService {

    private String TAG = "MusicNotificationListener";

    @SuppressLint("ForegroundServiceType")
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("checkService", "onCreate: chek come to start servcide : ++++" );
        startForeground(ConstantVal.NOTIFICATION_ID, buildNotification());

    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String pack = sbn.getPackageName();
        Notification notification = sbn.getNotification();
        CharSequence title = notification.extras.getCharSequence(Notification.EXTRA_TITLE);
        CharSequence text = notification.extras.getCharSequence(Notification.EXTRA_TEXT);
        if (isMusicPlayerNotification(sbn)) {
            String title1 = sbn.getNotification().extras.getString("android.title");
            String text1 = sbn.getNotification().extras.getString("android.text");
            // Do something with the music notification information
            Log.e("NotificationListener", "Music Notification - Title: " + title1 + ", Text: " + text1);
        }else{
            Log.d("NotificationListener", " Non Music Notification - Title: " + title + ", Text: " + text);
        }

        if (notification.actions != null) {
            for (Notification.Action action : notification.actions) {
                // Check for play/pause action
                if ("play".equals(action.title.toString().toLowerCase())) {
                    Log.d(TAG, "Play action clicked");
                    ConstantVal.notiMusicIsPlaying = true;

                } else if ("pause".equals(action.title.toString().toLowerCase())) {
                    ConstantVal.notiMusicIsPlaying = false;
                    Log.d(TAG, "Pause action clicked");

                }
            }
        }



        // You can extract more details from sbn if needed
    }

    private boolean isMusicPlayerNotification(StatusBarNotification sbn) {
        // Implement your logic to identify music player notifications
        // You can check for specific patterns, keywords, or any other criteria
        // For example, check if the notification title or text contains keywords related to music
        String title = sbn.getNotification().extras.getString("android.title");
        String text = sbn.getNotification().extras.getString("android.text");
        return (title != null && title.toLowerCase().contains("music")) ||
                (text != null && text.toLowerCase().contains("music"));
    }


    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // Handle notification removal if needed
    }

    private Notification buildNotification() {
        // Create and return a Notification object
        // You can customize this notification based on your app's requirements

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    ConstantVal.CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ConstantVal.CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("Running")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        return builder.build();
    }



/*
    @Override
    public void onNotificationActionClicked(Context context, StatusBarNotification sbn, String actionKey) {
        // Handle the click event for notification actions
        Log.d(TAG, "Notification action clicked: " + actionKey);

        // You can add additional logic based on the action key if needed

        // Example: Handle play action click
        if ("play".equals(actionKey)) {
            // Handle play action click
            isMusicPlaying = true;
            // Add your logic to control music playback (e.g., resume playback)
        }
    }
*/

}