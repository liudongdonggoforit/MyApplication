package com.mantoto.property.myapplication.activities;

import android.os.Bundle;

import com.mantoto.property.myapplication.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setImmerseLayout(findViewById(R.id.weather));
    }
}
