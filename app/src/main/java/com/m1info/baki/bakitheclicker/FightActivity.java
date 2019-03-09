package com.m1info.baki.bakitheclicker;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
    /* vie */
    private ProgressBar myLp;
    private ProgressBar ennemiLp;
    /*personnages*/
    private ImageButton ennemiBtn;
    private ImageView bakimage;
    private PersoNonJoueur ennemi;
    private PersoJoueur Baki;

    /*textviews*/
    private TextView myDmg;
    private TextView stage;
    private TextView ennemiDmg;
    private TextView ennemiName;
    /*Threads */
    private Thread t;
    private Thread tap;
    /*equipement*/
    private Bibliotheque biblio;
    private ImageView equip;
    private Equipement stuff;
    /*inventaire correspond à l inventaire pour les objets offesifs tandis que inventaireDef les objets défensifs*/
    private ImageButton io1;
    private ImageButton io2;
    private ImageButton id1;
    private ImageButton id2;

    private int nLevel;



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
        bakimage = (ImageView) findViewById(R.id.Bakimage);
        bakimage.setOnLongClickListener(bakilistener);
        /*creation de la bibliothèque */
        biblio=new Bibliotheque();

        /*inventaire*/
        io1= (ImageButton) findViewById(R.id.InventaireOffens1);
        io2 =(ImageButton) findViewById(R.id.InventaireOffens2);
        id1=(ImageButton) findViewById(R.id.InventaireDef1);
        id2=(ImageButton) findViewById(R.id.InventaireDef2);

        /* recuperation du level */
        nLevel=0;
        Intent intent = getIntent();
        intent.getIntExtra("level",nLevel);

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
        double choixOD;
        choixOD = Math.random();
        if(choixOD>=0.5){
            dropEquipementOffensif();
        }else{
            dropEquipementDefensif();
        }

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

    public void dropEquipementDefensif(){
        double choix;
        choix = Math.random();
        if(choix>0.7){
            stuff=biblio.EquipementsDefensifs.get(1);
        }else{
            stuff=biblio.EquipementsDefensifs.get(0);
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
                findViewById(R.id.fightView).post(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FightActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle("YOU LOOSE");
                        builder.setIcon(R.drawable.bakimain);
                        builder.setPositiveButton("Menu Principal",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        startActivity(new Intent(FightActivity.this, MainMenuActivity.class));

                                    }
                                });
                        builder.setNegativeButton("Recommencer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Intent intent = new Intent(FightActivity.this, FightActivity.class);
                                intent.putExtra("level", nLevel);
                                startActivity(intent);
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
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
                                    if(stuff.definirBonus()){
                                        io1.setOnClickListener(inventaireListener);
                                        io2.setOnClickListener(inventaireListener);
                                    }else{
                                        id1.setOnClickListener(inventaireListener);
                                        id2.setOnClickListener(inventaireListener);
                                    }

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




    public View.OnClickListener inventaireListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setBackgroundResource(stuff.getImage());
            Baki.raiseAttaque(stuff.getBonus());
            equip.setVisibility(View.GONE);
            /*on met tous les listener a nul une fois le click effectué*/
            io1.setOnClickListener(null);
            io2.setOnClickListener(null);
            id1.setOnClickListener(null);
            id2.setOnClickListener(null);
            validerNiveau();
        }

    };


    public View.OnLongClickListener bakilistener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            findViewById(R.id.fightView).post(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FightActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle(Baki.getNom());
                    builder.setMessage("Total Attaque :"+Baki.getAttaque()+"\n"+"Total vie :"+Baki.getVie());
                    builder.setIcon(R.drawable.bakimain);
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
          return true;
        }

    };

    public void validerNiveau(){
        findViewById(R.id.fightView).post(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("ATTAQUE",Baki.getAttaque());
                editor.putInt("VIE",Baki.getVie());
                editor.putInt("LEVEL",nLevel);
                editor.putInt("EQUIPMENTO1",io1.getBackground().hashCode());
                editor.putInt("EQUIPMENTO2",io2.getBackground().hashCode());
                editor.putInt("EQUIPMENTD1",id1.getBackground().hashCode());
                editor.putInt("EQUIPMENTD2",id2.getBackground().hashCode());
                editor.commit();

                /*pop up */
                AlertDialog.Builder builder = new AlertDialog.Builder(FightActivity.this);
                builder.setCancelable(true);
                builder.setTitle("YOU WIN");
                builder.setMessage("Vous accedez desormais au niveau suivant ");
                builder.setIcon(R.drawable.bakimain);
                builder.setPositiveButton("Niveau suivant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent intent = new Intent(FightActivity.this, FightActivity.class);
                        intent.putExtra("level", nLevel+1);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Selection des niveaux", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        startActivity(new Intent(FightActivity.this, LevelSelectActivity.class));
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

}
