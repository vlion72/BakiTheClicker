package com.m1info.baki.bakitheclicker;

public class PersoNonJoueur extends Personnage {

    private int image;

    public PersoNonJoueur(int life,int dmg, String name,int i){

        this.enVie=true;
        this.attaque=dmg;
        this.vie=life;
        this.nom=name;
        this.image=i;

    }

    public int getImage(){
        return this.image;
    }
}
