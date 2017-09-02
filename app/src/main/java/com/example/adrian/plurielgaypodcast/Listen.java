package com.example.adrian.plurielgaypodcast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Listen extends AppCompatActivity {

    FloatingActionButton buttonPlay;
    FloatingActionButton buttonPause;
    MediaPlayer mediaPlayer;
    boolean intialStage = true;
    int mediaLength;
    SeekBar progressBar;
    // TODO: 31/08/2017 new button 
    // TODO: 31/08/2017 progress bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen);

        Intent intent = getIntent();
        final String URL = intent.getStringExtra("URL");
        String title = intent.getStringExtra("auditionTitle");
        String description = intent.getStringExtra("auditionDesc");
        setTitle(title);


        TextView descriptionView = (TextView) findViewById(R.id.textViewDescription);
        descriptionView.setText(description);
        descriptionView.setMovementMethod(new ScrollingMovementMethod());
        // TODO: 02/09/2017 make the description scrollable 

        buttonPlay = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        buttonPause = (FloatingActionButton) findViewById(R.id.floatingActionButton2);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);



        progressBar = (SeekBar) findViewById(R.id.seekBar);
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    buttonPlay.setVisibility(View.VISIBLE);
                    buttonPause.setVisibility(View.INVISIBLE);
                }
            }
        });

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (intialStage) {
                    new Player().execute(URL);

                   /* new Timer().scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            progressBar.setProgress(mediaPlayer.getCurrentPosition());
                        }
                    }, 0, 1000);*/

                }

                else {
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();

                    }
                }
                buttonPause.setVisibility(View.VISIBLE);
                buttonPlay.setVisibility(View.INVISIBLE);
            }
        });

    }
    class Player extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog progress;

        @Override
        protected Boolean doInBackground(String... params) {
            Boolean prepared;
            try {

                mediaPlayer.setDataSource(params[0]);

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        intialStage = true;
                        mp.stop();
                        mp.reset();
                    }
                });
                mediaPlayer.prepare();
                prepared = true;
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                Log.d("IllegarArgument", e.getMessage());
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
            if (progress.isShowing()) {
                progress.cancel();
            }
            Log.d("Prepared", "//" + result);
            mediaLength = mediaPlayer.getDuration();
            Log.i("Duration", Integer.toString(mediaLength));
            progressBar.setMax(mediaLength);
            mediaPlayer.start();

            intialStage = false;
        }

        public Player() {
            progress = new ProgressDialog(Listen.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.progress.setMessage("Buffering...");
            this.progress.show();

        }


    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
