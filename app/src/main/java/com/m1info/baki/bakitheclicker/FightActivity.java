package com.m1info.baki.bakitheclicker;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
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
    private TabLayout inventaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        Baki = new PersoJoueur();
        ennemi = new PersoNonJoueur(100,5,"Oliva",R.drawable.oliva);
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
        equip.setOnClickListener(touchListenerEquipement);

        myDmg.setText("Dégats : \n"+Integer.toString(Baki.getAttaque()));
        ennemiDmg.setText("Dégats ennemi :"+Integer.toString(ennemi.getAttaque()));
        ennemiName.setText(ennemi.getNom());
        stage.setText("Etage 1");
        myDmg.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        ennemiDmg.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        ennemiName.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        stage.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        ennemiBtn = (ImageButton) findViewById(R.id.ennemi);
        ennemiBtn.setBackgroundResource(ennemi.getImage());

        /*creation de la bibliothèque */
        biblio=new Bibliotheque();

        /*inventaire*/
        inventaire= (TabLayout) findViewById(R.id.inventaire);
        inventaire.setSelected(false);

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
                equip.setImageResource(stuff.getImage());
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
    /*affiche les caractéristiques d un equipement */
    public View.OnClickListener touchListenerEquipement = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            findViewById(R.id.fightView).post(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FightActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle(stuff.getNom());
                    if(stuff.definirBonus()) {/* si c'est un equipement offensif*/
                        builder.setMessage(stuff.getDescription() + "\n" +"Dégats :"+stuff.getBonus() + "\n" + "Raretée :"+stuff.getRareté());
                    }
                    else{/*si c'est un équipement défensif*/
                        builder.setMessage(stuff.getDescription() + "\n" +"Vie :"+stuff.getBonus() + "\n" + "Raretée :"+stuff.getRareté());
                    }
                    builder.setIcon(stuff.getImage());
                    builder.setPositiveButton("Confirmer",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    inventaire.setOnTabSelectedListener(tablistener);
                                }
                            });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            equip.setVisibility(View.GONE);
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    };




    public TabLayout.OnTabSelectedListener tablistener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            Log.d("clicktab","click on tab detected");
            tab.setIcon(stuff.getImage());
            Baki.raiseAttaque(stuff.getBonus());
            equip.setVisibility(View.GONE);
        }
        public void onTabUnselected(TabLayout.Tab tab) {
            Log.d("unselect","unselect detected");
            tab.setIcon(null);

        }
        public void onTabReselected(TabLayout.Tab tab) {
            Log.d("reselected","reselect detected");

        }
    };



}
