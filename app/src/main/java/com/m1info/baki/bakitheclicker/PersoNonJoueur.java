package com.m1info.baki.bakitheclicker;

public class PersoNonJoueur extends Personnage {

    public PersoNonJoueur(int life,int dmg, String name){

        this.enVie=true;
        this.attaque=dmg;
        this.vie=life;
        this.nom=name;

    }
}
