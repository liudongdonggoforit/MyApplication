package com.mantoto.property.myapplication;

import android.app.Application;
import android.content.Context;

import com.mantoto.property.myapplication.utils.LogU;

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
        LogU.isDebug = true;
    }

    public static Context getConText(){
        return mConText;
    }
}
