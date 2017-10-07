package com.example.adrian.plurielgaypodcast;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Adrian on 14/09/2017.
 */

public class Player extends AsyncTask<String, Void, Boolean> {
    private static final String LOG_TAG = "Player";

    MediaPlayer mediaPlayer;
    int mediaLength;
    //ProgressDialog progress;
    // TODO: 07/10/2017 buffer more data 



        @Override
        protected Boolean doInBackground(String... params) {
            Boolean prepared;
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            Log.i(LOG_TAG, "Player started");
            try {

                mediaPlayer.setDataSource(params[0]);
                Log.i(LOG_TAG, "URL:" + params[0]);

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        //intialStage = true;
                        mp.stop();
                        mp.reset();
                    }
                });
                mediaPlayer.prepare();
                prepared = true;
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                Log.d("IllegalArgument", e.getMessage());
                prepared = false;
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            }
            return prepared;

}
    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        //if (progress.isShowing()) {
          //  progress.cancel();
        //}
        Log.d("Prepared", "//" + result);
        Constants.setMediaLength(mediaPlayer.getDuration());

        mediaPlayer.start();

    }

    //public Player() {
      //  progress = new ProgressDialog();
    //}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       // this.progress.setMessage("Buffering...");
       // this.progress.show();

    }


}