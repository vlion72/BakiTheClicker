package com.m1info.baki.bakitheclicker;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class FightActivity extends AppCompatActivity {
    /* vie */
    private ProgressBar myLp;
    private ProgressBar ennemiLp;
    private int vie;

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

    /* level */
    private int nLevel;
    private Level lvl;

    private int actualEnnemi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        //interdit la rotation penant le fight
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        /* on charge la sauvegarde */
        int attaque;
        ArrayList<String> equipement =new ArrayList<String>();
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        attaque=prefs.getInt("ATTAQUE",5);
        this.vie=prefs.getInt("VIE",200);
        equipement.add(prefs.getString("EQUIPEMENTO1","null"));
        equipement.add(prefs.getString("EQUIPEMENTO2","null"));
        equipement.add(prefs.getString("EQUIPEMENTD1","null"));
        equipement.add(prefs.getString("EQUIPEMENTD2","null"));

        /* recuperation du level */
        nLevel=0;
        Intent intent = getIntent();
        nLevel=intent.getIntExtra("level",0);

        lvl=new Level(nLevel);

        /*initialisation Baki + ennemi */
        actualEnnemi=0;
        Baki = new PersoJoueur(attaque,vie,equipement);
        ennemi = lvl.getListePNJ().get(actualEnnemi);
        biblio = new Bibliotheque();

        /* barres de vie*/
        myLp = (ProgressBar) findViewById(R.id.lifePoints);
        ennemiLp = (ProgressBar) findViewById(R.id.ennemiLP);
        myLp.setProgress(myLp.getMax());
        ennemiLp.setProgress(ennemiLp.getMax());
        myLp.setMax(Baki.getVie());
        ennemiLp.setMax(ennemi.getVie());

        myLp.getProgressDrawable().setColorFilter(
                Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);

        /* textviews */
        myDmg = (TextView) findViewById(R.id.myDmgText);
        stage = (TextView) findViewById(R.id.etageTexte);
        ennemiDmg = (TextView) findViewById(R.id.ennemiDmgTexte);
        ennemiName = (TextView) findViewById(R.id.ennemiNomTexte);
        /*equipement*/
        equip= (ImageView) findViewById(R.id.equipement);
        equip.setVisibility(View.INVISIBLE);
        equip.setOnClickListener(touchListenerEquipement);


        myDmg.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        ennemiDmg.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        ennemiName.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        stage.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        ennemiBtn = (ImageButton) findViewById(R.id.ennemi);

        bakimage = (ImageView) findViewById(R.id.Bakimage);
        bakimage.setOnLongClickListener(bakilistener);

        /*inventaire*/
        io1= (ImageButton) findViewById(R.id.InventaireOffens1);
        io2 =(ImageButton) findViewById(R.id.InventaireOffens2);
        id1=(ImageButton) findViewById(R.id.InventaireDef1);
        id2=(ImageButton) findViewById(R.id.InventaireDef2);

        for(int i=0;i<4;i++){
            for(int j=0;j<biblio.longueur;j++){
                if(Baki.bakipement.get(i)==biblio.EquipementsDefensifs.get(j).getNom()){
                     if(i==2){
                        id1.setBackgroundResource(biblio.EquipementsDefensifs.get(j).getImage());
                    }if(i==3){
                        id2.setBackgroundResource(biblio.EquipementsDefensifs.get(j).getImage());
                    }
                }else if(Baki.bakipement.get(i)==biblio.EquipementsOffensifs.get(j).getNom()){
                    if(i==0){
                        io1.setBackgroundResource(biblio.EquipementsOffensifs.get(j).getImage());
                    }if(i==1){
                        io2.setBackgroundResource(biblio.EquipementsOffensifs.get(j).getImage());

                    }
                }
            }
        }






        /* demarrage du combat */
        t = new Thread() {
            public void run() {
                startFight();

            }
        };

        taper();
        t.start();






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
                    ennemiBtn.startAnimation(AnimationUtils.loadAnimation(FightActivity.this, R.xml.ennemi_animation));
                }
            }

        });

    }
    public void dropEquipementOffensif(){
        double choix;
        choix = Math.random();
        if(choix>0.9){
            stuff=biblio.EquipementsOffensifs.get(3);
        }else if(choix>0.7 && choix<=0.9){
            stuff=biblio.EquipementsOffensifs.get(2);
        }else if(choix>0.4 && choix<=0.7){
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
        if(choix>0.9){
            stuff=biblio.EquipementsDefensifs.get(3);
        }else if(choix>0.7 && choix<=0.9){
            stuff=biblio.EquipementsDefensifs.get(2);
        }else if(choix>0.4 && choix<=0.7){
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
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                Baki.setAttaque(prefs.getInt("ATTAQUE",5));
                Baki.setVie(prefs.getInt("VIE",200));





                Baki.bakipement.remove(0);
                Baki.bakipement.add(0,prefs.getString("EQUIPEMENTO1","null"));
                Baki.bakipement.remove(1);
                Baki.bakipement.add(1,prefs.getString("EQUIPEMENTO2","null"));
                Baki.bakipement.remove(2);
                Baki.bakipement.add(2,prefs.getString("EQUIPEMENTD1","null"));
                Baki.bakipement.remove(3);
                Baki.bakipement.add(3,prefs.getString("EQUIPEMENTD2","null"));

                myDmg.setText("Dégats : \n"+Integer.toString(Baki.getAttaque()));
                ennemiDmg.setText("Dégats ennemi :"+Integer.toString(ennemi.getAttaque()));
                ennemiName.setText(ennemi.getNom());
                stage.setText("Niveau "+Integer.toString(nLevel));
                ennemiBtn.setBackgroundResource(ennemi.getImage());
                ennemiBtn.setVisibility(View.VISIBLE);

            }
        });

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
                    /* quand tu ne veux pas l equipement */
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            equip.setVisibility(View.GONE);
                            Baki.setVie(vie);// remet la vie de Baki a la valeur de debut de partie
                            if(actualEnnemi<lvl.getNbPersonnages()-1){
                                actualEnnemi+=1;
                                ennemi=lvl.getListePNJ().get(actualEnnemi);

                                SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putInt("ATTAQUE",Baki.getAttaque());
                                editor.putInt("VIE",Baki.getVie());
                                editor.putInt("LEVEL",nLevel);
                                editor.commit();

                                /* demarrage du combat */
                                t = new Thread() {
                                    public void run() {
                                        startFight();

                                    }
                                };
                                t.start();

                            }else{
                                validerNiveau();
                            }

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
            if(stuff.definirBonus()){
                Baki.raiseAttaque(stuff.getBonus());
                Baki.setVie(vie); // remet la vie de Baki a la valeur de debut de partie
            }else{
                Baki.setVie(vie+stuff.getBonus());
            }
            SharedPreferences prefs = getPreferences(MODE_PRIVATE);
            if(v==io1){
                /* enleve le bonus precedent */
                for(int i=0;i<biblio.longueur;i++){
                    if(Baki.bakipement.get(0)==biblio.EquipementsOffensifs.get(i).getNom()){
                        Baki.raiseAttaque(-(biblio.EquipementsOffensifs.get(i).getBonus()));
                    }
                }
                Baki.bakipement.remove(0);
                Baki.bakipement.add(0,stuff.getNom());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("EQUIPEMENTO1",Baki.bakipement.get(0));
                editor.commit();
            }else if(v==io2){
                /* enleve le bonus precedent */
                for(int i=0;i<biblio.longueur;i++){
                    if(Baki.bakipement.get(1)==biblio.EquipementsOffensifs.get(i).getNom()){
                        Baki.raiseAttaque(-(biblio.EquipementsOffensifs.get(i).getBonus()));
                    }
                }
                Baki.bakipement.remove(1);
                Baki.bakipement.add(1,stuff.getNom());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("EQUIPEMENTO2",Baki.bakipement.get(1));
                editor.commit();
            }else if(v==id1){
                /* enleve le bonus precedent */
                for(int i=0;i<biblio.longueur;i++){
                    if(Baki.bakipement.get(2)==biblio.EquipementsDefensifs.get(i).getNom()){
                        Baki.setVie(Baki.getVie()-(biblio.EquipementsDefensifs.get(i).getBonus()));
                    }
                }
                Baki.bakipement.remove(2);
                Baki.bakipement.add(2,stuff.getNom());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("EQUIPEMENTD1",Baki.bakipement.get(2));
                editor.commit();
            }else if(v==id2){
                /* enleve le bonus precedent */
                for(int i=0;i<biblio.longueur;i++){
                    if(Baki.bakipement.get(3)==biblio.EquipementsDefensifs.get(i).getNom()){
                        Baki.setVie(Baki.getVie()-(biblio.EquipementsDefensifs.get(i).getBonus()));
                    }
                }
                Baki.bakipement.remove(3);
                Baki.bakipement.add(3,stuff.getNom());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("EQUIPEMENTD2",Baki.bakipement.get(3));
                editor.commit();
            }
            equip.setVisibility(View.GONE);
            /*on met tous les listener a nul une fois le click effectué*/
            io1.setOnClickListener(null);
            io2.setOnClickListener(null);
            id1.setOnClickListener(null);
            id2.setOnClickListener(null);
            if(actualEnnemi<lvl.getNbPersonnages()-1){
                actualEnnemi+=1;
                ennemi=lvl.getListePNJ().get(actualEnnemi);


                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("ATTAQUE",Baki.getAttaque());
                editor.putInt("VIE",Baki.getVie());
                editor.putInt("LEVEL",nLevel);
                editor.commit();



                /* demarrage du combat */
                t = new Thread() {
                    public void run() {
                        startFight();

                    }
                };

                t.start();

            }else{
                validerNiveau();
            }
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
                    builder.setMessage("Total Attaque :"+Baki.getAttaque()+"\n"+"Total vie :"+vie);
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
                editor.putString("EQUIPEMENTO1",Baki.bakipement.get(0));
                editor.putString("EQUIPEMENTO2",Baki.bakipement.get(1));
                editor.putString("EQUIPEMENTD1",Baki.bakipement.get(2));
                editor.putString("EQUIPEMENTD2",Baki.bakipement.get(3));
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
                        Intent i =new Intent(FightActivity.this, LevelSelectActivity.class);
                        i.putExtra("leveldone",nLevel);
                        startActivity(i);
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

}
