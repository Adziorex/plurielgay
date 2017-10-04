package com.example.adrian.plurielgaypodcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.List;

/**
 * Created by Adrian on 03/09/2017.
 */

public class BackgroundService extends Service {


    private static final String LOG_TAG = "BackgroundService";
    Player player;
    SeekBar progressBar;
    String streamAdress;
    String auditionTitle;
    PendingIntent pendingIntent;
    PendingIntent ppreviousIntent;
    PendingIntent pplayIntent;
    Bitmap icon = null;
    PendingIntent pnextIntent;
    PendingIntent ppauseIntent;
    Notification notification;

    public void createPlayingNotification() {
        icon = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher);
        notification = new NotificationCompat.Builder(this)
                .setContentTitle("Pluriel Gay Podcast")
                .setTicker("Pluriel Gay Podcast")
                .setContentText(auditionTitle)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(
                        Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .addAction(android.R.drawable.ic_media_previous,
                        "Previous", ppreviousIntent)
                .addAction(android.R.drawable.ic_media_pause, "Pause",
                        ppauseIntent)
                .addAction(android.R.drawable.ic_media_next, "Next",
                        pnextIntent).build();


    }
    public void createPausedNotifcation() {
        icon = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher);
        notification = new NotificationCompat.Builder(this)
                .setContentTitle("Pluriel Gay Podcast")
                .setTicker("Pluriel Gay Podcast")
                .setContentText(auditionTitle)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(
                        Bitmap.createScaledBitmap(icon, 128, 128, false))
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .addAction(android.R.drawable.ic_media_previous,
                        "Previous", ppreviousIntent)
                .addAction(android.R.drawable.ic_media_play, "Play",
                        pplayIntent)
                .addAction(android.R.drawable.ic_media_next, "Next",
                        pnextIntent).build();


    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Start Foreground Intent ");
            Log.i(LOG_TAG, intent.getStringExtra("URL"));
            streamAdress = intent.getStringExtra("URL");
            auditionTitle = intent.getStringExtra("Title");
            player = (Player) new Player().execute(streamAdress);


            Intent notificationIntent = new Intent(this, Listen.class);
            notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);


            Intent previousIntent = new Intent(this, BackgroundService.class);
            previousIntent.setAction(Constants.ACTION.PREV_ACTION);
            ppreviousIntent = PendingIntent.getService(this, 0,
                    previousIntent, 0);

            Intent playIntent = new Intent(this, BackgroundService.class);
            playIntent.setAction(Constants.ACTION.PLAY_ACTION);
            pplayIntent = PendingIntent.getService(this, 0,
                    playIntent, 0);

            Intent nextIntent = new Intent(this, BackgroundService.class);
            nextIntent.setAction(Constants.ACTION.NEXT_ACTION);
            pnextIntent = PendingIntent.getService(this, 0,
                    nextIntent, 0);

            Intent pauseIntent = new Intent(this, BackgroundService.class);
            pauseIntent.setAction(Constants.ACTION.PAUSE_ACTION);
            ppauseIntent = PendingIntent.getService(this, 0, pauseIntent, 0);

            createPlayingNotification();

            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                    notification);
        } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
            Log.i(LOG_TAG, "Clicked Previous");
        } else if (intent.getAction().equals(Constants.ACTION.PAUSE_ACTION)) {
            Log.i(LOG_TAG, "Clicked Pause");
            player.mediaPlayer.pause();
            createPausedNotifcation();
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, notification);


        } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
            Log.i(LOG_TAG, "Clicked Next");
        } else if (intent.getAction().equals(
                Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Stop Foreground Intent");
            player.mediaPlayer.stop();
            stopForeground(true);
            stopSelf();
        } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
            player.mediaPlayer.start();
            createPlayingNotification();
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, notification);

        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "In onDestroy");


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
