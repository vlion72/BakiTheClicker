package com.m1info.baki.bakitheclicker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.widget.GridLayout.LayoutParams.*;
import static com.m1info.baki.bakitheclicker.R.drawable.greencheckmini;
import static com.m1info.baki.bakitheclicker.R.drawable.lockiconmini;

public class LevelSelectActivity extends AppCompatActivity {


    public static List<ImageButton> listButt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        int levelDone;
        /*preferences pour la page LevelSelect*/
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        Intent i = getIntent();
        levelDone=i.getIntExtra("leveldone",0);

        /* evite de remettre levelDone a 0 quand l appli restart*/
        if(levelDone != 0){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("LEVEL",levelDone);
            editor.commit();
        }

        levelDone = prefs.getInt("LEVEL", 0);

        ImageButton btn1 = findViewById(R.id.imageButton1);
        ImageButton btn2 = findViewById(R.id.imageButton2);
        ImageButton btn3 = findViewById(R.id.imageButton3);
        ImageButton btn4 = findViewById(R.id.imageButton4);
        ImageButton btn5 = findViewById(R.id.imageButton5);


        listButt = new ArrayList<>();
        listButt.add(btn1);
        listButt.add(btn2);
        listButt.add(btn3);
        listButt.add(btn4);
        listButt.add(btn5);

        int ind=0;

        for(ImageButton but:LevelSelectActivity.listButt){
            Log.d("STATE","Level done : "+levelDone+" ind : "+ind);
            if(levelDone>=ind) {
                Log.d("STATE","HERE");
                but.setEnabled(true);

                if(levelDone==ind)
                    but.setBackground(getResources().getDrawable(R.drawable.unlockiconmini));
                else
                    but.setBackground(getResources().getDrawable(R.drawable.greencheckmini));
            }
            else{
                Log.d("STATE","THERE");
                but.setEnabled(false);
                but.setBackground(getResources().getDrawable(R.drawable.lockiconmini));
            }
            ind++;
        }


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



        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                    Intent intent = new Intent(LevelSelectActivity.this, FightActivity.class);
                    intent.putExtra("level", 1);
                    startActivity(intent);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(LevelSelectActivity.this, FightActivity.class);
                intent.putExtra("level", 2);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(LevelSelectActivity.this, FightActivity.class);
                intent.putExtra("level", 3);
                startActivity(intent);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(LevelSelectActivity.this, FightActivity.class);
                intent.putExtra("level", 4);
                startActivity(intent);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(LevelSelectActivity.this, FightActivity.class);
                intent.putExtra("level", 5);
                startActivity(intent);
            }
        });






    }

}
