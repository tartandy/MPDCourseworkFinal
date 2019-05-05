//
// Name                 Andrew Hunter
// Student ID           S1512543
// Programme of Study   Computing
//

package com.s1512543.mpdcwahunte206.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class QuakeItem {

    private String location;
    private double magnitude;
    private int depth;
    private Date date;
    private double lat;
    private double lon;
    private String link;
    private SimpleDateFormat dateFormat;

    //constructors
    public QuakeItem(){
         dateFormat = new SimpleDateFormat("dd/MM/yy 'at' HH:mm:ss", Locale.UK);
    }
    public QuakeItem(String input){
        String[] temp = input.split(";;");
        location = temp[0];
        magnitude = Double.parseDouble(temp[1]);
        depth = Integer.parseInt(temp[2]);
        dateFormat = new SimpleDateFormat("dd/MM/yy 'at' HH:mm:ss", Locale.UK);
        try {
            date = dateFormat.parse(temp[3]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        lat = Double.parseDouble(temp[4]);
        lon = Double.parseDouble(temp[5]);
        link = temp[6];
    }

    //getters and setters
    public String getLocation() {
        return location;
    }
    public double getMagnitude() {
        return magnitude;
    }
    public int getDepth() {
        return depth;
    }
    public Date getDate() {
        return date;
    }
    public String getFormattedDate(){ return dateFormat.format(date); }
    public double getLat() {
        return lat;
    }
    public double getLon() {
        return lon;
    }
    public String getLink() {
        return link;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }
    public void setDepth(int depth) {
        this.depth = depth;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }
    public void setLink(String link) {
        this.link = link;
    }

    //object toString
    @Override
    public String toString() {
        String output = location + ";;";
        output += magnitude + ";;";
        output += depth + ";;";
        output += dateFormat.format(date) + ";;";
        output += lat + ";;";
        output += lon + ";;";
        output += link;
        return output;
    }


}
