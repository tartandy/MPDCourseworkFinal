//
// Name                 Andrew Hunter
// Student ID           S1512543
// Programme of Study   Computing
//

package com.s1512543.mpdcwahunte206.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.s1512543.mpdcwahunte206.HomePage;
import com.s1512543.mpdcwahunte206.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadTask extends AsyncTask<Void, Integer, Void> {

    private WeakReference<AppCompatActivity> reference;
    private StringBuilder result;
    private URL url;

    //constructor
    public DownloadTask(AppCompatActivity reference) {
        this.reference = new WeakReference<>(reference);
    }

    //checks the current internet status of the device
    public static boolean checkInternetAvailable(AppCompatActivity context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    //runs startup code for the thread
    @Override
    protected void onPreExecute(){
        //setup URL
        result = new StringBuilder();
        try {
            url = new URL("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    //main thread task to download data
    @Override
    protected Void doInBackground(Void... voids) {
        URLConnection yc;
        BufferedReader in = null;
        String inputLine = "";
        try
        {
            //open the connection and create buffered reader
            yc = url.openConnection();
            in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            in.readLine();
            //build the result for every line in the XML file
            while ((inputLine = in.readLine()) != null)
            {
                result.append(inputLine);
            }
            //close connection
            in.close();
        }
        catch (IOException ae)
        {
            Log.e("Error", "ioexception");
        }
        return null;
    }

    //runs after the thread's main task
    //starts new activity and saves the data locally for offline mode
    @Override
    protected void onPostExecute(Void result){
        //check the download worked successfully from the feed
        if(!this.result.toString().equals("")){
            //create new intent and start main activity
            Intent intent = new Intent(reference.get(), MainActivity.class);
            intent.putExtra("data", this.result.toString());
            reference.get().startActivity(intent);
            //delete and re-save data file
            FileHandler.deleteFile();
            FileHandler.saveFile(this.result.toString());

        } else {
            Log.e("Error", "NoDataDownloaded");
        }
    }
}
