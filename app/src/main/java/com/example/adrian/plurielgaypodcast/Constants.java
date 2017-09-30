package com.example.adrian.plurielgaypodcast;

import android.util.Log;

/**
 * Created by Adrian on 13/09/2017.
 */

public class Constants {
    private static int mediaLength = 0;
    private static int isPlaying = -1;

    public static int getIsPlaying() {
        Log.i("Constants", "isPlaying send to activity");
        return isPlaying;

    }
    public static void changeIsPlaying() {
        isPlaying = isPlaying * -1;
        Log.i("Constants", "isPlaying set");
    }

    public static void setMediaLength(int length) {
        mediaLength = length;
        Log.i("Constants", "MediaLength set");
    }

    public interface ACTION {
        public static String MAIN_ACTION = "com.example.adrian.plurielgaypodcast.action.main";
        public static String PREV_ACTION = "com.example.adrian.plurielgaypodcast.action.prev";
        public static String PLAY_ACTION = "com.example.adrian.plurielgaypodcast.action.play";
        public static String NEXT_ACTION = "com.example.adrian.plurielgaypodcast.action.next";
        public static String PAUSE_ACTION = "com.example.adrian.plurielgaypodcast.action.pause";
        public static String STARTFOREGROUND_ACTION = "com.example.adrian.plurielgaypodcast.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.example.adrian.plurielgaypodcast.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }


    public static int getMediaLength() {
        Log.i("Constants", "MediaLength send to activity");
        return mediaLength;

    }


}