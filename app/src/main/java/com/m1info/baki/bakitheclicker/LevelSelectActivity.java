package com.m1info.baki.bakitheclicker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.Toast;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.widget.GridLayout.LayoutParams.*;

public class LevelSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);


        //test
        /*GridLayout grid = (GridLayout) findViewById(R.id.grid);

        for (int i = 1; i <= 20; i++) {
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(4), GridLayout.spec(5));
            Button btn = new Button(this);
            btn.setId(i);
            final int id_ = btn.getId();
            //btn.setText("button " + id_);
            //btn.setBackgroundColor(Color.rgb(70, 80, 90));
            grid.addView(btn, params);

            Button btn1 = ((Button) findViewById(id_));
            btn1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"Button clicked index = " + id_, Toast.LENGTH_SHORT).show();
                }
            });
        }*/



        Button btnBck = (Button)findViewById(R.id.buttonBack);
        btnBck.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LevelSelectActivity.this, MainMenuActivity.class));
            }
        });


        ImageButton btn1 = findViewById(R.id.imageButton1);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(LevelSelectActivity.this, FightActivity.class);
                intent.putExtra("level", 1);
                startActivity(intent);
            }
        });

        ImageButton btn2 = findViewById(R.id.imageButton2);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(LevelSelectActivity.this, FightActivity.class);
                intent.putExtra("level", 2);
                startActivity(intent);
            }
        });

        ImageButton btn3 = findViewById(R.id.imageButton3);
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(LevelSelectActivity.this, FightActivity.class);
                intent.putExtra("level", 3);
                startActivity(intent);
            }
        });

        ImageButton btn4 = findViewById(R.id.imageButton4);
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(LevelSelectActivity.this, FightActivity.class);
                intent.putExtra("level", 4);
                startActivity(intent);
            }
        });

        ImageButton btn5 = findViewById(R.id.imageButton5);
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(LevelSelectActivity.this, FightActivity.class);
                intent.putExtra("level", 5);
                startActivity(intent);
            }
        });



    }

}
