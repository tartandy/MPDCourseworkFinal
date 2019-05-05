//
// Name                 Andrew Hunter
// Student ID           S1512543
// Programme of Study   Computing
//
package com.s1512543.mpdcwahunte206;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.s1512543.mpdcwahunte206.model.QuakeItem;
import com.s1512543.mpdcwahunte206.util.QuakeFilter;
import com.s1512543.mpdcwahunte206.util.QuakeXMLParser;
import com.s1512543.mpdcwahunte206.util.UIDesigner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;



public class MainActivity extends AppCompatActivity implements OnClickListener, DatePickerDialog.OnDateSetListener {

    private ArrayList<QuakeItem> quakeItems;
    private SimpleDateFormat dateTimeFormat;
    private LinearLayout browseLinearContainer;
    private AlertDialog dialog;
    private EditText startDateEditText;
    private EditText endDateEditText;
    private int whichEditToSet;
    private Date startDate;
    private Date endDate;
    private SimpleDateFormat dateFormat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<LinearLayout> quakeItemViews = new ArrayList<>();
        ArrayList<Integer> displayQuakeViews = new ArrayList<>();

        ScrollView scrollView = (ScrollView)findViewById(R.id.browseScroll);
       browseLinearContainer = (LinearLayout)findViewById(R.id.browseLinearContainer);

       //setup the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Browse");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar();
        }
        //toolbar buttons
        ImageButton filter = (ImageButton)findViewById(R.id.filterButton);
        filter.setOnClickListener(this);


        //load and parse data
        String rawData = getIntent().getStringExtra("data");
        quakeItems = QuakeXMLParser.parse(rawData);

        //setupParams for display
        dateTimeFormat = new SimpleDateFormat("dd/MM/yy 'at' HH:mm:ss", Locale.UK);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);

        //initialLoad
        renderQuakeItemViews(createQuakeItemViews(quakeItems));
    }


    private void renderQuakeItemViews(ArrayList<LinearLayout> quakeItemViews) {
        //remove all views then rebuild from ArrayList
        browseLinearContainer.removeAllViews();
        for(LinearLayout l : quakeItemViews){
            //adds all views to the scrollview
            browseLinearContainer.addView(l);
        }
    }

    @Override
    public void onClick(View v) {
        //if view is a quakeItem
        if(v.getTag() != null){
            //start quakeitemactivity and pass in quake as string
            Intent intent = new Intent(this, QuakeItemActivity.class);
            intent.putExtra("quakeitem", this.quakeItems.get(Integer.parseInt(v.getTag().toString())).toString());
            startActivity(intent);
        }
        //show the filter dialog if filter button pressed
        if(v.getId() == R.id.filterButton){
            createDialog();
        }
        //if filter submitted then filter the page
        if(v.getId() == R.id.createFilterButton){
            //start date and end date are swappable, code will work if either is older than the other
            if(isDateValid(startDate) && isDateValid(endDate)){
                if(startDate.compareTo(endDate) > 0){
                    //start date ahead
                    filterItems(startDate, endDate);
                } else{
                    //end date ahead
                    filterItems(endDate, startDate);
                }
                //hide the filter dialog
                dialog.hide();
            } else {
                Toast.makeText(getApplicationContext(),"Dates must be between now and 100 days ago", Toast.LENGTH_SHORT).show();
            }
        }
        //clear filter button shown
        if(v.getId() == R.id.cancelButton){
            System.out.println("cancelFilter");
            //renders all views and hides dialog
            renderQuakeItemViews(createQuakeItemViews(quakeItems));
            dialog.hide();
        }
        //creates a date picker dialog when date edit text clicked
        if(v.getId() == R.id.startDate || v.getId() == R.id.endDate){
            //which edit to set is used to tell which edit text was set
            whichEditToSet = v.getId();
            DatePickerDialog dialog = new DatePickerDialog(this, this,
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }

    }

    private void filterItems(Date newest, Date oldest) {
        QuakeFilter quakeFilter = new QuakeFilter(oldest, newest);
        ArrayList<QuakeItem> filteredQuakeItems = new ArrayList<>();
        //gets the filtered results and adds each to a new arraylist
        filteredQuakeItems.add(quakeFilter.getMostNortherly(quakeItems));
        filteredQuakeItems.add(quakeFilter.getMostSoutherly(quakeItems));
        filteredQuakeItems.add(quakeFilter.getMostEasterly(quakeItems));
        filteredQuakeItems.add(quakeFilter.getMostWesterly(quakeItems));
        filteredQuakeItems.add(quakeFilter.getLargestMagnitude(quakeItems));
        filteredQuakeItems.add(quakeFilter.getLowestMagnitude(quakeItems));
        filteredQuakeItems.add(quakeFilter.getDeepest(quakeItems));
        filteredQuakeItems.add( quakeFilter.getShallowest(quakeItems));
        //clears current scrollview
        browseLinearContainer.removeAllViews();

        //shows filtered results
        ArrayList<LinearLayout> filterLayouts = createQuakeItemViews(filteredQuakeItems);
        renderFilteredQuakeItemViews(filterLayouts);

    }

    private void renderFilteredQuakeItemViews(ArrayList<LinearLayout> filterLayouts) {
        //clears all views in scrollview
        browseLinearContainer.removeAllViews();
        //ensures 8 results are passed in
        if (filterLayouts.size() != 8) throw new AssertionError();
        //adds all views and headers to the scrollview
        browseLinearContainer.addView(createHeaderView("Most northerly"));
        browseLinearContainer.addView(filterLayouts.get(0));
        browseLinearContainer.addView(createHeaderView("Most Southerly"));
        browseLinearContainer.addView(filterLayouts.get(1));
        browseLinearContainer.addView(createHeaderView("Most Easterly"));
        browseLinearContainer.addView(filterLayouts.get(2));
        browseLinearContainer.addView(createHeaderView("Most Westerly"));
        browseLinearContainer.addView(filterLayouts.get(3));
        browseLinearContainer.addView(createHeaderView("Highest Magnitude"));
        browseLinearContainer.addView(filterLayouts.get(4));
        browseLinearContainer.addView(createHeaderView("Lowest Magnitude"));
        browseLinearContainer.addView(filterLayouts.get(5));
        browseLinearContainer.addView(createHeaderView("Deepest"));
        browseLinearContainer.addView(filterLayouts.get(6));
        browseLinearContainer.addView(createHeaderView("Shallowest"));
        browseLinearContainer.addView(filterLayouts.get(7));
    }

    private RelativeLayout createHeaderView(String headerText){
        //creates header for the filtered results
        RelativeLayout header = (RelativeLayout)View.inflate(this, R.layout.browse_filter_header, null);
        TextView tv = (TextView)header.getChildAt(0);
        tv.setText(headerText);
        return header;
    }

    private ArrayList<LinearLayout> createQuakeItemViews(ArrayList<QuakeItem> quakeItems){
        //creates and populates the views
        ArrayList<LinearLayout> quakeItemViews = new ArrayList<>();

        for(QuakeItem quake  :quakeItems){
            quakeItemViews.add((LinearLayout) View.inflate(this, R.layout.template_list_item, null));
            //set onClickListener and give ID
            quakeItemViews.get(quakeItemViews.size()-1).setOnClickListener(this);
            quakeItemViews.get(quakeItemViews.size()-1).setTag(this.quakeItems.indexOf(quake));
            //get title layout
            LinearLayout topHalf = (LinearLayout) quakeItemViews.get(quakeItemViews.size()-1).getChildAt(1);
            //location TextView
            TextView locationTV = (TextView)topHalf.getChildAt(0);
            locationTV.setText(quake.getLocation());
            //magnitude TextView
            RelativeLayout topRight = (RelativeLayout)topHalf.getChildAt(1);
            TextView magnitudeTV = (TextView)topRight.getChildAt(0);
            magnitudeTV.setText(String.valueOf(quake.getMagnitude()));
            magnitudeTV.setTextColor(ContextCompat.getColor(this, UIDesigner.getColorFromMagnitude(quake.getMagnitude())));
            //date/time TextView
            LinearLayout bottomHalf =(LinearLayout) quakeItemViews.get(quakeItemViews.size()-1).getChildAt(2);
            TextView dateTV = (TextView)bottomHalf.getChildAt(0);
            dateTV.setText(dateTimeFormat.format(quake.getDate()));
        }
        return quakeItemViews;
    }

    private void createDialog() {
        //creates the filter dialog and sets button onClickListeners
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") View dialogView = getLayoutInflater().inflate(R.layout.filter_dialog, null);
        Button createFilterButton = (Button)dialogView.findViewById(R.id.createFilterButton);
        Button cancelButton = (Button)dialogView.findViewById(R.id.cancelButton);
        startDateEditText = (EditText)dialogView.findViewById(R.id.startDate);
        endDateEditText = (EditText)dialogView.findViewById(R.id.endDate);
        //hides keyboard in edit texts
        startDateEditText.setInputType(InputType.TYPE_NULL);
        endDateEditText.setInputType(InputType.TYPE_NULL);
        createFilterButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        startDateEditText.setOnClickListener(this);
        endDateEditText.setOnClickListener(this);

        //show the dialog
        dialogBuilder.setView(dialogView);
        dialog = dialogBuilder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //code for the back button in the toolbar
        //returns to the parent activity
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
        //Java month for some reason starts at 0 when everything else starts at 1
        monthOfYear++;
        //if the start date was pressed set the date there
        if(whichEditToSet == R.id.startDate){
            String output = dayOfMonth + "/" + monthOfYear + "/" + year;
            startDateEditText.setText(output);
            try {
                startDate = dateFormat.parse(output);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            whichEditToSet = 0;
        }
        //if the end date was pressed set the date there
        if(whichEditToSet == R.id.endDate){
            String output = dayOfMonth + "/" + monthOfYear + "/" + year;
            endDateEditText.setText(output);
            whichEditToSet = 0;
            try {
                endDate = dateFormat.parse(output);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isDateValid(Date date){
        //checks date between 100 days ago and now
        //date is rounded down to avoid same day counting as a false negative
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);
        Date currentDate = new Date();
        cal.add(Calendar.DAY_OF_YEAR, -100);
        Date hundredDaysAgo = cal.getTime();

        //checks the date not null and then returns the result
        if(date == null){
            return false;
        } else if(hundredDaysAgo.compareTo(date) > 0){
            return false;
        } else return date.compareTo(currentDate) <= 0;
    }
}