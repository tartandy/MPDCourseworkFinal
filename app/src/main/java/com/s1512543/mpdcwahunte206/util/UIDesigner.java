//
// Name                 Andrew Hunter
// Student ID           S1512543
// Programme of Study   Computing
//

package com.s1512543.mpdcwahunte206.util;

import com.s1512543.mpdcwahunte206.R;

public class UIDesigner {

    //returns the color to be set. colors are stored in the res/values/colors and can be changed easily
    public static int getColorFromMagnitude(Double magnitude){
        //Largest magnitude ever in the UK was 6.3,
        // don't see need to have conditions for higher than 5.0
        if(magnitude < -1){
            return R.color.mag1;
        } else if(magnitude < 0){
            return R.color.mag2;
        }else if(magnitude < 0.5){
            return R.color.mag3;
        }else if(magnitude < 1){
            return R.color.mag4;
        }else if(magnitude < 1.5){
            return R.color.mag5;
        }else if(magnitude < 2){
            return R.color.mag6;
        }else if(magnitude < 2.5){
            return R.color.mag7;
        }else if(magnitude < 3){
            return R.color.mag8;
        }else if(magnitude < 4){
            return R.color.mag9;
        }else if(magnitude < 5){
            return R.color.mag10;
        }else{
            return R.color.mag11;
        }
    }
}
