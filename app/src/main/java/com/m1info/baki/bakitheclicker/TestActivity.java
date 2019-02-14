package com.m1info.baki.bakitheclicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        GridLayout grid = (GridLayout) findViewById(R.id.grid);

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
        }


    }
}
