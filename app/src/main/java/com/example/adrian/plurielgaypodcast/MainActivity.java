package com.example.adrian.plurielgaypodcast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ProgressDialog mProgressDialog ;
    String website = "http://audioblog.arteradio.com/blog/3043558/plurielgay?pageNumber=0";
    List<String> listEmissions;
    List<String> listMP3;
    List<String> listDescription;


    class DownloadWebSite extends AsyncTask <Void, Void, String> {
        // TODO: 02/09/2017 reverse all the pages 

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setMessage("Loading list of emissions...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();

        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(website).get();
                listEmissions = document.select("article[class^=song-id]").eachAttr("data-post-title");
                //Log.i("List of emissions", listEmissions.toString());
                listMP3 = document.select("a[class=audioblog-sound-download audioblog-button]").eachAttr("href");
                Elements listDescriptionElements = document.select("div[class=audioblog-sound-details-text]");
                listDescriptionElements =  listDescriptionElements.select("span[class=more-toggle-text preformatted]");
                listDescription = listDescriptionElements.eachText();
                return null;









            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO: 31/08/2017 descriptions in the list
        // TODO: 31/08/2017  downloading the mp3
        // TODO: 31/08/2017 instead of reloading list each time save it and check for new items on start
        // TODO: 31/08/2017 keep track of listened tracks and progress in each

        ListView listEmissionsView = (ListView) findViewById(R.id.ListEmissions);
        setTitle("Pluriel Gay Podcast");

        try {
            String str_result= new DownloadWebSite().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //populateListEmissions();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listEmissions);
        listEmissionsView.setAdapter(arrayAdapter);




        listEmissionsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Listen.class);
                intent.putExtra("URL", listMP3.get(position));
                intent.putExtra("auditionTitle", listEmissions.get(position));
                intent.putExtra("auditionDesc", listDescription.get(position));
                startActivity(intent);
            }
        });



    }
}
