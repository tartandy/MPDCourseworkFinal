//
// Name                 Andrew Hunter
// Student ID           S1512543
// Programme of Study   Computing
//

package com.s1512543.mpdcwahunte206.util;

import com.s1512543.mpdcwahunte206.model.QuakeItem;

import java.util.ArrayList;
import java.util.Date;

public class QuakeFilter {

    private Date oldest;
    private Date newest;

    //constructor
    public QuakeFilter(Date oldest, Date newest) {
        this.oldest = oldest;
        this.newest = newest;
    }
    //gets the most northerly quake from an arraylist
    public QuakeItem getMostNortherly(ArrayList<QuakeItem> quakeItems){
        QuakeItem current;
        current = quakeItems.get(0);
        for(QuakeItem q : quakeItems){
            if(q.getLat() > current.getLat() && betweenDateRange(q.getDate())) {
                current = q;
            }
        }
        return current;
    }

    //get the most southerly quake from an arraylist
    public QuakeItem getMostSoutherly(ArrayList<QuakeItem> quakeItems){
        QuakeItem current;
        current = quakeItems.get(0);
        for(QuakeItem q : quakeItems){
            if (q.getLat() < current.getLat()&& betweenDateRange(q.getDate())) {
                current = q;
            }
        }
        return current;
    }

    //get the most southerly quake from an arraylist
    public QuakeItem getMostEasterly(ArrayList<QuakeItem> quakeItems){
        QuakeItem current;
        current = quakeItems.get(0);
        for(QuakeItem q : quakeItems){
            if (q.getLon() > current.getLon()&& betweenDateRange(q.getDate())) {
                current = q;
            }
        }
        return current;
    }

    //get the most southerly quake from an arraylist
    public QuakeItem getMostWesterly(ArrayList<QuakeItem> quakeItems){
        QuakeItem current;
        current = quakeItems.get(0);
        for(QuakeItem q : quakeItems){
            if (q.getLon() < current.getLon()&& betweenDateRange(q.getDate())) {
                current = q;
            }
        }
        return current;
    }

    //get the most southerly quake from an arraylist
    public QuakeItem getLargestMagnitude(ArrayList<QuakeItem> quakeItems){
        QuakeItem current;
        current = quakeItems.get(0);
        for (QuakeItem q : quakeItems){
            if(q.getMagnitude() > current.getMagnitude() && betweenDateRange(q.getDate())){
                current = q;
            }
        }
        return current;
    }

    //get the most southerly quake from an arraylist
    public QuakeItem getLowestMagnitude(ArrayList<QuakeItem> quakeItems){
        QuakeItem current;
        current = quakeItems.get(0);
        for (QuakeItem q : quakeItems){
            if(q.getMagnitude() < current.getMagnitude() && betweenDateRange(q.getDate())){
                current = q;
            }
        }
        return current;
    }

    //get the most southerly quake from an arraylist
    public QuakeItem getDeepest(ArrayList<QuakeItem> quakeItems){
        QuakeItem current;
        current = quakeItems.get(0);
        for (QuakeItem q : quakeItems){
            if(q.getDepth() > current.getDepth() && betweenDateRange(q.getDate())){
                current = q;
            }
        }
        return current;
    }

    //get the most southerly quake from an arraylist
    public QuakeItem getShallowest(ArrayList<QuakeItem> quakeItems){
        QuakeItem current;
        current = quakeItems.get(0);
        for (QuakeItem q : quakeItems){
            if(q.getDepth() < current.getDepth() && betweenDateRange(q.getDate())){
                current = q;
            }
        }
        return current;
    }

    //ensures a date is between the oldest and newest allowed dates
    public boolean betweenDateRange(Date date){
        boolean dateTooOld = date.compareTo(oldest) < 0;
        boolean dateTooNew = date.compareTo(newest) > 0;
        System.out.println( "too old: " + dateTooOld + "too new: " + dateTooNew);
        return !dateTooOld && !dateTooNew;
    }
}
