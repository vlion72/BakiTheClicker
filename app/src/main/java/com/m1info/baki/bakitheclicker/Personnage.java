package com.m1info.baki.bakitheclicker;

public abstract class Personnage {
    /*instances*/
    protected int vie;
    protected int attaque;
    protected String nom;
    protected boolean enVie;

    protected void mourir(){
        this.enVie=false;
    }



}
