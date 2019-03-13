package com.m1info.baki.bakitheclicker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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


        final SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        int lvldone=i.getIntExtra("leveldone",0);
        final int leveldone=prefs.getInt("LEVEL",0);
        if(lvldone!=leveldone) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("LEVEL", lvldone);
            editor.commit();
        }



        if(langue!=null){

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("LANGUE",langue);
            editor.commit();
        }

        setLocale(prefs.getString("LANGUE","FR"));


        Button btn = (Button)findViewById(R.id.buttonSelectLvl);
        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, LevelSelectActivity.class));
            }
        });

        Button btnConti = (Button)findViewById(R.id.buttonConti);
        btnConti.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(MainMenuActivity.this, FightActivity.class);
                if(prefs.getInt("LEVEL",0)!=5) {
                    intent.putExtra("level", prefs.getInt("LEVEL", 0) + 1);
                }else{
                    intent.putExtra("level", prefs.getInt("LEVEL", 0));
                }
                    startActivity(intent);
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
        Locale.setDefault(myLocale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        //Intent intent = new Intent(MainMenuActivity.this, MainMenuActivity.class);
        //startActivity(intent);
    }


}
