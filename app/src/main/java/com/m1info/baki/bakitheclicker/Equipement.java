package com.m1info.baki.bakitheclicker;

public abstract class Equipement {
    protected String nom;
    protected String description;
    protected int bonus;
    protected String rareté;

    Equipement(String n, String d, String r, int b){

        this.nom=n;
        this.description=d;
        this.rareté=r;
        this.bonus=b;

    }
    /*getters */

    public String getNom(){
        return this.nom;
    }
    public String getDescription(){
        return this.description;
    }
    public String getRareté(){
        return this.rareté;
    }
    public int getBonus(){
        return this.bonus;
    }

    /* setters */
    public void setNom(String n){
        this.nom=n;
    }
    public void setDescription(String d){
        this.description= d;
    }
    public void setRareté(String r){
        this.rareté=r;
    }
    public void setBonus(int b){
        this.bonus=b;
    }

    /*****************/
    public boolean definirBonus(){
        return true;
    }
}
