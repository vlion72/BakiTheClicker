package com.m1info.baki.bakitheclicker;

import java.util.ArrayList;

public class PersoJoueur extends Personnage {
    /*liste contenant le nom des equipements de Baki */
    public ArrayList<String> bakipement;

    public PersoJoueur(int a,int v, ArrayList<String>l){

        this.attaque=a;
        this.nom="Baki";
        this.enVie=true;
        this.vie=v;
        this.bakipement=l;

    }
    public void setAttaque(int a){
        this.attaque=a;
    }


}
