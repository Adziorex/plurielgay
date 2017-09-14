package com.example.adrian.plurielgaypodcast;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class Listen extends AppCompatActivity {

    FloatingActionButton buttonPlay;
    FloatingActionButton buttonPause;
    //MediaPlayer mediaPlayer;
    //boolean intialStage = true;
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
        final String title = intent.getStringExtra("auditionTitle");
        String description = intent.getStringExtra("auditionDesc");
        setTitle(title);


        TextView descriptionView = (TextView) findViewById(R.id.textViewDescription);
        descriptionView.setText(description);
        descriptionView.setMovementMethod(new ScrollingMovementMethod());
        // TODO: 02/09/2017 make the description scrollable 

        buttonPlay = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        buttonPause = (FloatingActionButton) findViewById(R.id.floatingActionButton2);

    //    mediaPlayer = new MediaPlayer();
      //  mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);



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
                    Intent stopServiceIntent = new Intent(Listen.this, BackgroundService.class);
                    stopServiceIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
                    startService(stopServiceIntent);
                    buttonPlay.setVisibility(View.VISIBLE);
                    buttonPause.setVisibility(View.INVISIBLE);
                }
            }
        );

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startServiceIntent = new Intent(Listen.this, BackgroundService.class);
                startServiceIntent.putExtra("URL", URL);
                startServiceIntent.putExtra("Title", title);
                startServiceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                startService(startServiceIntent);
                buttonPause.setVisibility(View.VISIBLE);
                buttonPlay.setVisibility(View.INVISIBLE);
            }
        });

    }

}
