package com.m1info.baki.bakitheclicker;

import java.util.ArrayList;
import java.util.List;

public class Bibliotheque {
    ArrayList<EquipementOffensif> EquipementsOffensifs;
    ArrayList<EquipementDefensif> EquipementsDefensifs;

    public Bibliotheque(){
        EquipementOffensif bandages = new EquipementOffensif("Bandages","Equipement permettant d'affliger des dégats un peu supérieures à ceux de base","normale",5);
        EquipementOffensif gantsBoxe = new EquipementOffensif("Gants de boxe","Ces gants sont très pratique pour assener des coups  puissants ","rare",10);
        EquipementDefensif kimono = new EquipementDefensif("Kimono","Cette tenue est très efficace pour effectuer des mouvements de karaté","normale",5);
        EquipementDefensif gilet = new EquipementDefensif("Gilet pare balles","Meme si les muscles de Baki peuvent arreter les balles , on est jamais trop prudent","rare",10);

        EquipementsOffensifs = new ArrayList<EquipementOffensif>();
        EquipementsDefensifs = new ArrayList<EquipementDefensif>();

        EquipementsOffensifs.add(bandages);
        EquipementsOffensifs.add(gantsBoxe);
        EquipementsDefensifs.add(kimono);
        EquipementsDefensifs.add(gilet);

    }
}
