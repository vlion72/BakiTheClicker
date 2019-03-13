package com.m1info.baki.bakitheclicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bibliotheque {
    public ArrayList<EquipementOffensif> EquipementsOffensifs;
    public ArrayList<EquipementDefensif> EquipementsDefensifs;
    public int longueur;

    Map<String, Integer> persos;
    List<String> persosNames;

    public Bibliotheque(){
        EquipementOffensif bandages = new EquipementOffensif("Bandages","Equipement permettant d'affliger des dégats un peu supérieures à ceux de base. ","Normale",5,R.drawable.bandages);
        EquipementOffensif gantsBoxe = new EquipementOffensif("Gants de boxe","Ces gants sont très pratique pour assener des coups  puissants. ","Rare",10,R.drawable.gantsboxe);
        EquipementOffensif kunai = new EquipementOffensif("Kunaï","Le Kunaï est un équipement efficace autant en combat rapproché qu'à distance. ","Légendaire",20,R.drawable.kunai);
        EquipementOffensif katana = new EquipementOffensif("Katana","Ce Katana renferme un esprit puissant capable de décimer n'importe quel ennemi. ","Mythique",40,R.drawable.katana);
        EquipementDefensif kimono = new EquipementDefensif("Kimono","Cette tenue est très efficace pour effectuer des mouvements de karaté. ","Normale",5,R.drawable.kimono);
        EquipementDefensif gilet = new EquipementDefensif("Gilet pare balles","Meme si les muscles de Baki peuvent arreter les balles , on est jamais trop prudent. ","Rare",10,R.drawable.gilet);
        EquipementDefensif masque = new EquipementDefensif("Masque Ancestrale","Masque ancien ayant appartenu à un puissant guerrier. ","Légendaire",20,R.drawable.masque);
        EquipementDefensif armure = new EquipementDefensif("Armure de samouraï","Ne vous laissez pas surprendre par sa légereté car sa robustesse est son vrai atout. Elle aurait appartenu à un samouraï que l'on surnomait L'immortel. ","Mythique",40,R.drawable.armuresamourai);

        EquipementsOffensifs = new ArrayList<EquipementOffensif>();
        EquipementsDefensifs = new ArrayList<EquipementDefensif>();

        EquipementsOffensifs.add(bandages);
        EquipementsOffensifs.add(gantsBoxe);
        EquipementsOffensifs.add(kunai);
        EquipementsOffensifs.add(katana);

        EquipementsDefensifs.add(kimono);
        EquipementsDefensifs.add(gilet);
        EquipementsDefensifs.add(masque);
        EquipementsDefensifs.add(armure);

        persos =  new HashMap<>();
        persosNames = new ArrayList<>();

        //persos.put("Dorian",R.drawable.p_dorian);
        persos.put("Hanayama",R.drawable.p_hanayama);
        persos.put("Katsumi",R.drawable.p_karateguy);
        persos.put("Motobe",R.drawable.p_katanaguy);
        persos.put("Gouki",R.drawable.p_oldguy);
        persos.put("Oliva",R.drawable.p_oliva);
        persos.put("Dorian",R.drawable.p_dorian);

        //persosNames.add("Dorian");
        persosNames.add("Hanayama");
        persosNames.add("Katsumi");
        persosNames.add("Motobe");
        persosNames.add("Gouki");
        persosNames.add("Oliva");
        persosNames.add("Dorian");


        longueur=4;
    }

}
