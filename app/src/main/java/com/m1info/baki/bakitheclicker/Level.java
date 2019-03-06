package com.m1info.baki.bakitheclicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {

    private int nbPersonnages;
    private List<PersoNonJoueur> listePNJ;


    public Level(int ind){

        int random = new Random().nextInt(2) + 3;
        this.nbPersonnages = ((ind%5)+1)*random;


        int attaque;
        int vie;

        this.listePNJ = new ArrayList<>();

        for(int i=0; i<this.nbPersonnages; i++)
        {

            random = new Random().nextInt(10);
            attaque = 5*(1+(10-random)/10)*((ind%5)+1);
            vie= 100*(1+(10-random)/10)*((ind%5)+1);
            PersoNonJoueur ennemi = new PersoNonJoueur(vie, attaque, "mechant");

            this.listePNJ.add(ennemi);
        }
    }

    public List<PersoNonJoueur> getListePNJ(){
        return listePNJ;
    }

    public int getNbPersonnages(){
        return nbPersonnages;
    }

}
