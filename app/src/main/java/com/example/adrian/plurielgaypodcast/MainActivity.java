package com.example.adrian.plurielgaypodcast;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity {

    ProgressDialog mProgressDialog ;
    String website = "http://audioblog.arteradio.com";
    ArrayList<String> listEmissions = new ArrayList<>();
    ArrayList<String> listMP3 = new ArrayList<>();
    ArrayList<String> listDescription = new ArrayList<>();
    //List<String> tags;
    SharedPreferences sharedPreferences;




    class DownloadWebSite extends AsyncTask <Void, Void, String> {

        // TODO: 30/09/2017 process tags

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
                //read number of available pages
                String websiteToUse = website + "/blog/3043558/plurielgay?pageNumber=0";
                Document document = Jsoup.connect(websiteToUse).get();
                List<String> pages = document.select("div[class=audioblog-pagination] a").eachAttr("href");
                Log.i("List of pages", pages.toString());
                for (String page:pages) {
                    websiteToUse = website + page;
                    document = Jsoup.connect(websiteToUse).get();
                    listEmissions.addAll(document.select("article[class^=song-id]").eachAttr("data-post-title"));
                    //Log.i("List of emissions", listEmissions.toString());
                    listMP3.addAll(document.select("a[class=audioblog-sound-download audioblog-button]").eachAttr("href"));
                    Elements listDescriptionElements = document.select("div[class=audioblog-sound-details-text]");
                    listDescriptionElements = listDescriptionElements.select("span[class=more-toggle-text preformatted]");
                    listDescription.addAll(listDescriptionElements.eachText());
                    sharedPreferences.edit().putString("listEmissions",ObjectSerializer.serialize(listEmissions)).apply();
                    sharedPreferences.edit().putString("listMP3",ObjectSerializer.serialize(listMP3)).apply();
                    sharedPreferences.edit().putString("listDescription",ObjectSerializer.serialize(listDescription)).apply();
                    Date date = new Date(System.currentTimeMillis());
                    sharedPreferences.edit().putLong("refreshDate", date.getTime() ).apply();
                }
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
        // TODO: 31/08/2017 instead of loading all just add nes items on update
        // TODO: 31/08/2017 keep track of listened tracks and progress in each
        // TODO: 30/09/2017 add options next previous to switch to new audition

        ListView listEmissionsView = (ListView) findViewById(R.id.ListEmissions);
        sharedPreferences = this.getSharedPreferences("com.example.adrian.plurielgaypodcast", Context.MODE_PRIVATE);
        setTitle("Pluriel Gay Podcast");

        try {
            if (sharedPreferences.contains("refreshDate")){
                long currentDate = new Date(System.currentTimeMillis()).getTime();
                long refreshedDate = sharedPreferences.getLong("refreshDate", 0);
                long days = (refreshedDate - currentDate) / (24 * 60 * 60 * 1000);

                if (days > 7) {
                    String str_result= new DownloadWebSite().execute().get();
                }
            } else {
            String str_result= new DownloadWebSite().execute().get();}
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //populateListEmissions();
        try {
            listEmissions = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("listEmissions", ObjectSerializer.serialize(new ArrayList<String>())));
            Constants.setListEmissions(listEmissions);
            listDescription = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("listDescription", ObjectSerializer.serialize(new ArrayList<String>())));
            Constants.setlistDescription(listDescription);
            listMP3 = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("listMP3", ObjectSerializer.serialize(new ArrayList<String>())));
            Constants.setListMP3(listMP3);
        } catch (IOException e) {
            e.printStackTrace();
        }


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listEmissions);
        listEmissionsView.setAdapter(arrayAdapter);


        listEmissionsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Listen.class);
                Constants.setPositionPlaying(position);
                //intent.putExtra("position", position);
                //intent.putExtra("URL", listMP3.get(position));
                //intent.putExtra("auditionTitle", listEmissions.get(position));
                //intent.putExtra("auditionDesc", listDescription.get(position));
                startActivity(intent);
            }
        });



    }
}
