package com.mantoto.property.myapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by Mr.liu
 * On 2016/7/13
 * At 18:30
 * My Application
 */
public class MantotoApplication extends Application {
    private static Context mConText;
    @Override
    public void onCreate() {
        super.onCreate();
        mConText = getApplicationContext();
    }

    public static Context getConText(){
        return mConText;
    }
}
