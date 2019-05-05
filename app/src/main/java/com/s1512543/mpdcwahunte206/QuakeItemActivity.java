//
// Name                 Andrew Hunter
// Student ID           S1512543
// Programme of Study   Computing
//

package com.s1512543.mpdcwahunte206;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.s1512543.mpdcwahunte206.model.QuakeItem;
import com.s1512543.mpdcwahunte206.util.UIDesigner;

public class QuakeItemActivity extends AppCompatActivity implements View.OnClickListener {

    private QuakeItem quake;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //loads the view on screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quake_item_page_port);
        //get quake from intent
        quake = new QuakeItem(getIntent().getStringExtra("quakeitem"));
        //adds the information to the page
        setupInformation();
    }

    private void setupInformation(){
        //generates the information on the page, is called on page load and again on orientation
        //change since the activity clears the data on screen when this occurs
        TextView location = findViewById(R.id.location);
        location.setText(quake.getLocation());
        TextView magnitude = findViewById(R.id.magnitude);
        //sets the appropriate colour for the magnitude
        magnitude.setTextColor(ContextCompat.getColor(this, UIDesigner.getColorFromMagnitude(quake.getMagnitude())));
        magnitude.setText(String.valueOf(quake.getMagnitude()));
        TextView date = findViewById(R.id.date);
        date.setText(quake.getFormattedDate());
        TextView depth = findViewById(R.id.depth);
        String temp = quake.getDepth() + " km";
        depth.setText(temp);
        TextView lat = findViewById(R.id.latitude);
        temp = quake.getLat() + "°";
        lat.setText(temp);
        TextView lon = findViewById(R.id.longitude);
        temp = quake.getLon() + "°";
        lon.setText(temp);
        Button link = findViewById(R.id.linkButton);
        link.setOnClickListener(this);

        //setup the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Details");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //hides the filter button since it isn't needed
        ImageButton filterButton = (ImageButton)findViewById(R.id.filterButton);
        filterButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        //if the link button is pressed then load external link
        if (v.getId() == R.id.linkButton) {
            startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(quake.getLink())));
        }
    }

    //if back button pressed then navigate to parent
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }

    //if orientation is changed then change the layout on page
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //landscape mode
            setContentView(R.layout.quake_item_page_land);
            setupInformation();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            //portrait mode
            setContentView(R.layout.quake_item_page_port);
            setupInformation();
        }
    }
}
