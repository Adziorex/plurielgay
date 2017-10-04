package com.example.adrian.plurielgaypodcast;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Adrian on 13/09/2017.
 */

public class Constants {
    private static int mediaLength = 0;
    private static int isPlaying = -1;
    private static ArrayList<String> listEmissions = new ArrayList<>();
    private static ArrayList<String> listMP3 = new ArrayList<>();
    private static ArrayList<String> listDescription = new ArrayList<>();

    public static ArrayList<String> getListEmissions() {
        return listEmissions;
    }
    public static ArrayList<String> getListMP3() {
        return listMP3;
    }
    public static ArrayList<String> getlistDescription() {
        return listDescription;
    }


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