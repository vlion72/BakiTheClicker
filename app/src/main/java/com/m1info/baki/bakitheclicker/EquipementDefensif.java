package com.m1info.baki.bakitheclicker;

public class EquipementDefensif extends Equipement {

    public EquipementDefensif(String n, String d, String r, int b,int i) {
        super(n, d, r, b,i);
    }

    public boolean definirBonus(){
        return false;
    }
}
