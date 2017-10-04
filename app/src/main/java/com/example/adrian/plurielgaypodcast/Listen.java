package com.example.adrian.plurielgaypodcast;

import android.content.Intent;
import android.os.Bundle;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;

public class Listen extends AppCompatActivity {

    Button buttonPlay;
    Button buttonPause;

    int mediaLength;

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
        TextView auditionTitle = (TextView) findViewById(R.id.AuditionTitle);
        auditionTitle.setText(title);


        TextView descriptionView = (TextView) findViewById(R.id.textViewDescription);
        descriptionView.setText(description);
        final ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        //descriptionView.setMovementMethod(new ScrollingMovementMethod());
        // TODO: 02/09/2017 make the description scrollable 
        // TODO: 04/10/2017 make the icons show on the notification 
        // TODO: 04/10/2017 when clicked on notification it should show current audition page with stop button 
         

       buttonPlay = (Button) findViewById(R.id.playButton);





        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constants.getIsPlaying()==-1) {
                    Intent startServiceIntent = new Intent(Listen.this, BackgroundService.class);
                    startServiceIntent.putExtra("URL", URL);
                    startServiceIntent.putExtra("Title", title);
                    startServiceIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                    startService(startServiceIntent);
                    Constants.changeIsPlaying();
                    constraintLayout.setBackgroundResource(R.drawable.background_stop);
                } else {
                    Intent stopServiceIntent = new Intent(Listen.this, BackgroundService.class);
                    stopServiceIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
                    startService(stopServiceIntent);
                    Constants.changeIsPlaying();
                    constraintLayout.setBackgroundResource(R.drawable.background_play2);
                }

                Log.i("ListenActivity", "MediaLength set to: " + Integer.toString(Constants.getMediaLength()));

            }
        });

    }

}
