//
// Name                 Andrew Hunter
// Student ID           S1512543
// Programme of Study   Computing
//

package com.s1512543.mpdcwahunte206.util;

import com.s1512543.mpdcwahunte206.model.QuakeItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class QuakeXMLParser {

    //parses the XML data and returns arraylist of Quake Items
    public static ArrayList<QuakeItem> parse(String source){

        ArrayList<QuakeItem> quakeItems = new ArrayList<>();

        try{
            //remove prefix tags as they give errors with the pull parser
            source = source.replace("geo:lat", "lat");
            source = source.replace("geo:long", "lon");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.UK);
            //setup the pull parser
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(source));
            int eventType = xpp.getEventType();
            //loop for every event in the XML stream
            while(eventType != XmlPullParser.END_DOCUMENT){
                if(eventType == XmlPullParser.START_TAG){
                    switch(xpp.getName().toLowerCase()){
                        //if a new item then create new Quake
                        case "item":
                            quakeItems.add(new QuakeItem());
                            break;
                            //if description then get all details possible
                        case "description":
                            xpp.next();
                            String[] temp;
                            temp = xpp.getText().split(" ; ");
                            //ignores description at top of page
                            if(quakeItems.size() == 0) break;
                            //get and save the date
                            temp[0] = temp[0].substring(18);
                            quakeItems.get(quakeItems.size()-1).setDate(simpleDateFormat.parse(temp[0]));
                            //get and save the location
                            temp[1] = temp[1].substring(10);
                            quakeItems.get(quakeItems.size()-1).setLocation(temp[1]);
                            //get and save the depth
                            temp[3] = temp[3].replaceAll("\\D", "");
                            quakeItems.get(quakeItems.size()-1).setDepth(Integer.parseInt(temp[3]));
                            //get and save the magnitude
                            temp[4] = temp[4].substring(11).replaceAll(" ", "");
                            quakeItems.get(quakeItems.size()-1).setMagnitude(Double.parseDouble(temp[4]));
                            break;
                            //if lat then get and save the latitude
                        case "lat":
                            xpp.next();
                            quakeItems.get(quakeItems.size()-1).setLat(Double.parseDouble(xpp.getText()));
                            break;
                            //if lon then get and save the longitude
                        case "lon":
                            xpp.next();
                            quakeItems.get(quakeItems.size()-1).setLon(Double.parseDouble(xpp.getText()));
                            break;
                            //if lin k then get and save the link
                        case "link":
                            //ignore link at top of page
                            if(quakeItems.size() == 0) break;
                            xpp.next();
                            quakeItems.get(quakeItems.size()-1).setLink(xpp.getText());
                            break;
                    }
                }
                //get the next event
                eventType = xpp.next();
            }
        }catch(XmlPullParserException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return quakeItems;
    }
}
