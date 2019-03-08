package com.m1info.baki.bakitheclicker;

public abstract class Personnage {
    /*instances*/
    protected int vie;
    protected int attaque;
    protected String nom;
    protected boolean enVie;

    public void mourir(){
        this.enVie=false;
    }

    /* ensemble des méthodes d acces aux variables */
    public int getVie(){
        return this.vie;
    }

    public int getAttaque(){
        return this.attaque;
    }
    public String getNom(){
        return this.nom;
    }

    public boolean getEtat(){
        return this.enVie;
    }

    /* setters */

    public void setVie(int v){
        this.vie= v;
    }

    public void raiseAttaque(int a){
        this.attaque+=a;
    }


}
