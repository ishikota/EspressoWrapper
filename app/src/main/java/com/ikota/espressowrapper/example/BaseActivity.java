package com.ikota.espressowrapper.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by kota on 2015/11/14.
 */
public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AndroidApplication)getApplication())
                .getObjectGraph()
                .inject(getApplication());
    }

}
