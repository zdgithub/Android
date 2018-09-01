package com.seven.health.aboutpersonal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.seven.health.R;

/**
 * Created by HP on 2018/8/20.
 */

public class AboutsoftActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        setContentView(R.layout.personal_version);


    }
}