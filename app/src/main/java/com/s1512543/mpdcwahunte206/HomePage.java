//
// Name                 Andrew Hunter
// Student ID           S1512543
// Programme of Study   Computing
//

package com.s1512543.mpdcwahunte206;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.s1512543.mpdcwahunte206.util.ApplicationContext;
import com.s1512543.mpdcwahunte206.util.DownloadTask;
import com.s1512543.mpdcwahunte206.util.FileHandler;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

    Button homeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //show layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        //set onclicklistener for button
        homeButton = (Button)findViewById(R.id.homeButton);
        homeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //if the home button pressed then start the main activity
        if(v.getId() == R.id.homeButton){
            startBrowse();
        }
    }

    private void startBrowse() {
        //checks internet available, downloads from internet if yes, otherwise
        //tries to load from local storage
        if(DownloadTask.checkInternetAvailable(this)){
            new DownloadTask(this).execute();
        } else {
            loadData();
        }
    }

    private void loadData() {
        //if the file exists load from storage, otherwise exit the application
        if(FileHandler.fileExists()){
            //read from file
            final String data = FileHandler.readFile();
            Toast.makeText(getApplicationContext(),"Internet unavailable, loading most recent local version...", Toast.LENGTH_LONG).show();
            //start browse activity and pass in data
            Intent intent = new Intent(HomePage.this, MainActivity.class);
            intent.putExtra("data", data);
            startActivity(intent);

        } else{
            //exit application
            Toast.makeText(getApplicationContext(),"Internet unavailable & no offline version available. Exiting.", Toast.LENGTH_LONG).show();
            finish();

        }
    }

}
