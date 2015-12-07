package game.characters;

import game.Die;

import java.util.*;

public class Attributes {

    public Attributes(CharacterSheet cs) {
        int[] stats = rollStats();
        LEVEL = 3;
        PROFICIENCYBONUS = 2;
        if (cs.getRace() == Race.DWARF) SPEED = 25;
        if (cs.getRace() == Race.ELF) SPEED = 35;
        if (cs.getRace() == Race.HALFLING) SPEED = 20;
        if (cs.getRace() == Race.HUMAN) SPEED = 30;
        if (cs.getRace() == Race.DRAGONBORN) SPEED = 35;
        if (cs.getRace() == Race.GNOME) SPEED = 20;
        if (cs.getRace() == Race.HALFELF) SPEED = 30;
        if (cs.getRace() == Race.HALFORC) SPEED = 30;
        if (cs.getRace() == Race.THIEFLING) SPEED = 35;

        if (cs.getHeroClass() == HeroClass.CLERIC) {
            //Cleric: Wisdom, Strength, Constitution, Charisma, Int, Dex
            WISDOM = stats[0];
            STRENGTH = stats[1];
            CONSTITUTION = stats[2];
            CHARISMA = stats[3];
            INTELLIGENCE = stats[4];
            DEXTERITY = stats[5];
            HITPOINTS = 8 + AbilityMod(CONSTITUTION) * LEVEL + (Die.rollDie(8, LEVEL - 1));
        } else if (cs.getHeroClass() == HeroClass.FIGHTER) {
            //Fighter: Strength?Dexterity, Constitution, Dexterity?-, Wisdom, -?Strength, Charisma
            boolean dexFighter = new Random().nextInt(1) != 0;
            if (!dexFighter) {
                STRENGTH = stats[0];
                CONSTITUTION = stats[1];
                DEXTERITY = stats[2];
                WISDOM = stats[3];
                CHARISMA = stats[4];
                INTELLIGENCE = stats[5];
            } else {
                STRENGTH = stats[0];
                CONSTITUTION = stats[1];
                DEXTERITY = stats[2];
                WISDOM = stats[3];
                CHARISMA = stats[4];
                INTELLIGENCE = stats[5];
            }
            HITPOINTS = 10 + AbilityMod(CONSTITUTION) * LEVEL + (Die.rollDie(10, LEVEL - 1));
        } else if (cs.getHeroClass() == HeroClass.WIZARD) {
            //Wizard: Intelligence, Wisdom, Constitution, Dexterity, Charisma, Strength
            INTELLIGENCE = stats[0];
            WISDOM = stats[1];
            CONSTITUTION = stats[2];
            DEXTERITY = stats[3];
            CHARISMA = stats[4];
            STRENGTH = stats[5];
            HITPOINTS = 6 + AbilityMod(CONSTITUTION) * LEVEL + (Die.rollDie(6, LEVEL - 1));
        }
        INITIATIVE = Die.rollDie(20, 1) + AbilityMod(getDEXTERITY());
    }

    private int STRENGTH;
    private int DEXTERITY;
    private int CONSTITUTION;
    private int INTELLIGENCE;
    private int WISDOM;
    private int CHARISMA;

    private int PROFICIENCYBONUS;

    private int SPEED;
    private int HITPOINTS;
    private int INITIATIVE;

    private int LEVEL;

    public int getLEVEL() {
        return LEVEL;
    }

    public int getSTRENGTH() {
        return STRENGTH;
    }

    public int getDEXTERITY() {
        return DEXTERITY;
    }

    public int getCONSTITUTION() {
        return CONSTITUTION;
    }

    public int getINTELLIGENCE() {
        return INTELLIGENCE;
    }

    public int getWISDOM() {
        return WISDOM;
    }

    public int getCHARISMA() {
        return CHARISMA;
    }

    public int getPROFICIENCYBONUS() {
        return PROFICIENCYBONUS;
    }

    public int getSPEED() {
        return SPEED;
    }

    public int getHITPOINTS() {
        return HITPOINTS;
    }

    public int getINITIATIVE() {
        return INITIATIVE;
    }
    //Skills

    public static int AbilityMod(int ability) {
        int i = (int) Math.floor((ability - 10) / 2);
        return i > 0 ? i : 0;
    }

    public int[] rollStats() {
        int[] stats = new int[6];
        for (int i = 0; i < 6; i++) {
            int[] subroll = new int[4];
            for (int j = 0; j < 4; j++) {
                subroll[j] = new Random().nextInt(6) + 1;
            }

            Map<Integer, Integer> map = new HashMap<>();
            for (int n = 0; n < subroll.length; n++)
                map.put(n, subroll[n]);

            List<Map.Entry<Integer, Integer>> list =
                    new LinkedList<>(map.entrySet());

            Collections.sort(list, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));
            stats[i] = list.get(0).getValue() + list.get(1).getValue() + list.get(2).getValue();
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int n = 0; n < stats.length; n++)
            map.put(n, stats[n]);
        List<Map.Entry<Integer, Integer>> list =
                new LinkedList<>(map.entrySet());
        Collections.sort(list, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        for (int i = 0; i < stats.length; i++) {
            stats[i] = list.get(i).getValue();
        }
        return stats;
    }
}
