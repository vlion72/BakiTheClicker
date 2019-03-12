package com.m1info.baki.bakitheclicker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import java.util.Locale;

public class MainMenuActivity extends AppCompatActivity {
    Locale myLocale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        String langue;
        Intent i =getIntent();
        langue = i.getStringExtra("LANGUE");

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        if(langue!=null){

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("LANGUE",langue);
            editor.commit();
        }

        setLocale(prefs.getString("LANGUE","ENG"));

        Button btn = (Button)findViewById(R.id.buttonSelectLvl);
        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, LevelSelectActivity.class));
            }
        });



        Button btnSet = (Button)findViewById(R.id.buttonSettings);
        btnSet.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, SettingsActivity.class));
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
