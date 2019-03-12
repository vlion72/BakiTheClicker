package com.m1info.baki.bakitheclicker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Level {

    private int nbPersonnages;
    private List<PersoNonJoueur> listePNJ;
    private List<R.drawable> listeImg;
    Bibliotheque biblio = new Bibliotheque();


    public Level(int ind){

        int random = new Random().nextInt(2) + 2;
        this.nbPersonnages = random;
        Map<String, Integer> persos = biblio.persos;
        List<String> persosNames = biblio.persosNames;

        int attaque;
        int vie;

        this.listePNJ = new ArrayList<>();

        for(int i=0; i<this.nbPersonnages; i++)
        {
            random = new Random().nextInt(10)+ind;
            attaque = 5*(1+(random/10))*((ind%5)+1);
            vie= 100*(1+(random/10))*((ind%5)+1);
            PersoNonJoueur ennemi = new PersoNonJoueur(vie, attaque, persosNames.get(i),persos.get(persosNames.get(i)));

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
