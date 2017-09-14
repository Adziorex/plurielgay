package com.example.adrian.plurielgaypodcast;

/**
 * Created by Adrian on 13/09/2017.
 */

public class Constants {
    public interface ACTION {
        public static String MAIN_ACTION = "com.example.adrian.plurielgaypodcast.action.main";
        public static String PREV_ACTION = "com.example.adrian.plurielgaypodcast.action.prev";
        public static String PLAY_ACTION = "com.example.adrian.plurielgaypodcast.action.play";
        public static String NEXT_ACTION = "com.example.adrian.plurielgaypodcast.action.next";
        public static String STARTFOREGROUND_ACTION = "com.example.adrian.plurielgaypodcast.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.example.adrian.plurielgaypodcast.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }
}