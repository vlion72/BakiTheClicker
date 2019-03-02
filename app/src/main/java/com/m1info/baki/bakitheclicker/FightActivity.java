package com.m1info.baki.bakitheclicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        Baki = new PersoJoueur();
        ennemi = new PersoNonJoueur(100,5,"mechant");
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

        myDmg.setText("Dégats : \n"+Integer.toString(Baki.getAttaque()));
        ennemiDmg.setText("Dégats ennemi :"+Integer.toString(ennemi.getAttaque()));
        ennemiName.setText(ennemi.getNom());
        stage.setText("Etage 1");
        myDmg.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        ennemiDmg.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        ennemiName.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        stage.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        //ennemiBtn.setImageResource(R.drawable.dorian);




        /* demarrage du combat */
        startFight();
        taper();


    }

    public void tuer(){

        ennemi.mourir();
        //ennemiBtn.setVisibility(View.INVISIBLE);

    }

    public void taper() {

        ennemiBtn = (ImageButton) findViewById(R.id.ennemi);

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
    public void dropEquipement(){
        double choix;
        choix = Math.random();

    }

    public void startFight(){


            Thread t = new Thread() {

                public void run() {
                    boolean etatEnnemi=ennemi.getEtat();
                    boolean etatBaki=Baki.getEtat();

                    while(etatBaki && etatEnnemi) { /*tant qu il n y a pas de mort on boucle */
                        Baki.setVie(Baki.getVie() - ennemi.getAttaque());
                        ennemi.setVie(ennemi.getVie() - Baki.getAttaque());
                        ennemiLp.setProgress(ennemi.getVie(), true);
                        myLp.setProgress(Baki.getVie(), true);

                        if(ennemi.getVie() <= 0) {
                            tuer();
                            etatEnnemi=ennemi.getEtat();
                        }
                        if(Baki.getVie() <= 0) {
                            Baki.mourir();
                            etatBaki=Baki.getEtat();
                        }
                        try {
                            sleep(1000);
                        }
                        catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    }


                }
            };
            t.start();





    }

}
