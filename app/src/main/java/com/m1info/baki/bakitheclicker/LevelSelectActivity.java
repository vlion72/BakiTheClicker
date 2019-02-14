package com.m1info.baki.bakitheclicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class LevelSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);




        Button btnBck = (Button)findViewById(R.id.buttonBack);
        //ImageButton btn1 = (ImageButton)findViewById(R.id.imageButton1);



        btnBck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LevelSelectActivity.this, MainMenuActivity.class));
            }
        });


        /*btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(LevelSelectActivity.this, FightActivity.class);
                intent.putExtra("level", 1);
                startActivity(intent);
            }
        });*/



    }

}
