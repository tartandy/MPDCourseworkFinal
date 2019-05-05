//
// Name                 Andrew Hunter
// Student ID           S1512543
// Programme of Study   Computing
//

package com.s1512543.mpdcwahunte206.util;

import android.app.Application;
import android.content.Context;

public class ApplicationContext extends Application {
    private static Application sApplication;

    public static Application getApplication() {
        return sApplication;
    }

    //gets application context in a static manor
    //used for accessing application context in a static manor
    //for the local storage
    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}
