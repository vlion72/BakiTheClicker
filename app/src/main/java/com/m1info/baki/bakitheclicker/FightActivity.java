package com.m1info.baki.bakitheclicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FightActivity extends AppCompatActivity {
    private ProgressBar myLp;
    private ProgressBar ennemiLp;
    private ImageButton ennemiBtn;
    private PersoNonJoueur ennemi;
    private PersoJoueur Baki;
    private TextView myDmg;
    private TextView stage;
    private TextView ennemiDmg;
    private TextView ennemiName;
    private Thread t;
    private Thread tap;
    private Bibliotheque biblio;
    private ImageView equip;
    private Equipement stuff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        Baki = new PersoJoueur();
        ennemi = new PersoNonJoueur(100,5,"mechant");
        biblio = new Bibliotheque();
        /* barres de vie*/
        myLp = (ProgressBar) findViewById(R.id.lifePoints);
        ennemiLp = (ProgressBar) findViewById(R.id.ennemiLP);
        myLp.setProgress(myLp.getMax());
        ennemiLp.setProgress(ennemiLp.getMax());
        myLp.setMax(Baki.getVie());
        ennemiLp.setMax(ennemi.getVie());

        /* textviews */
        myDmg = (TextView) findViewById(R.id.myDmgText);
        stage = (TextView) findViewById(R.id.etageTexte);
        ennemiDmg = (TextView) findViewById(R.id.ennemiDmgTexte);
        ennemiName = (TextView) findViewById(R.id.ennemiNomTexte);
        /*equipement*/
        equip= (ImageView) findViewById(R.id.equipement);
        equip.setVisibility(View.INVISIBLE);
        equip.setOnTouchListener(touchListenerEquipement);

        myDmg.setText("Dégats : \n"+Integer.toString(Baki.getAttaque()));
        ennemiDmg.setText("Dégats ennemi :"+Integer.toString(ennemi.getAttaque()));
        ennemiName.setText(ennemi.getNom());
        stage.setText("Etage 1");
        myDmg.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        ennemiDmg.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        ennemiName.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        stage.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        ennemiBtn = (ImageButton) findViewById(R.id.ennemi);
        //ennemiBtn.setImageResource(R.drawable.dorian);

        /*creation de la bibliothèque */
        biblio=new Bibliotheque();



        /* demarrage du combat */
        t = new Thread() {
            public void run() {
                startFight();

            }
        };
        tap = new Thread() {
            public void run() {
                taper();
            }
        };


        t.start();
        tap.start();





    }

    public void tuer(){

        ennemi.mourir();
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                ennemiBtn.setVisibility(View.GONE);

            }
        });

        dropEquipementOffensif();

    }

    public void taper() {



        ennemiBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (Baki.getEtat()) {
                    ennemi.setVie(ennemiLp.getProgress() - ennemi.getAttaque());
                    ennemiLp.setProgress(ennemi.getVie(), true);

                }
            }

        });

    }
    public void dropEquipementOffensif(){
        double choix;
        choix = Math.random();
        if(choix>0.7){
            stuff=biblio.EquipementsOffensifs.get(1);
        }else{
            stuff=biblio.EquipementsOffensifs.get(0);
        }
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                //equip.setImageResource(stuff.getImage());
                equip.setVisibility(View.VISIBLE);

            }
        });
    }



    public void startFight(){


        while(Baki.getEtat() && ennemi.getEtat()) { /*tant qu il n y a pas de mort on boucle */
            Baki.setVie(Baki.getVie() - ennemi.getAttaque());
            ennemi.setVie(ennemi.getVie() - Baki.getAttaque());
            ennemiLp.setProgress(ennemi.getVie(), true);
            myLp.setProgress(Baki.getVie(), true);

            if(ennemi.getVie() <= 0) {
                tuer();
            }
            if(Baki.getVie() <= 0) {
                Baki.mourir();


            }
            try {
                t.sleep(1000);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }

        }



    }

    public View.OnTouchListener touchListenerEquipement = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    v.setX(event.getX() + v.getX());
                    v.setY(event.getY() + v.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    v.setX(event.getX() + v.getX());
                    v.setY(event.getY() + v.getY());
                    break;
                case MotionEvent.ACTION_UP:
                    v.setX(event.getX() + v.getX());
                    v.setY(event.getY() + v.getY());
                    break;
            }
            return true;
        }
    };








}
