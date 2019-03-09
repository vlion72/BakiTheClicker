package com.m1info.baki.bakitheclicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.Locale;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class SettingsActivity extends AppCompatActivity {

    Locale myLocale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        Button btnENG = (Button)findViewById(R.id.buttonENG);
        btnENG.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Change le language
                setLocale("ENG");
                startActivity(new Intent(SettingsActivity.this, MainMenuActivity.class));
            }
        });


        Button btnFR = (Button)findViewById(R.id.buttonFR);
        btnFR.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Change le language
                setLocale("FR");
                startActivity(new Intent(SettingsActivity.this, MainMenuActivity.class));

            }
        });


        Button btnBck = (Button)findViewById(R.id.buttonBack);
        btnBck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, MainMenuActivity.class));
            }
        });

    }


    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

}
