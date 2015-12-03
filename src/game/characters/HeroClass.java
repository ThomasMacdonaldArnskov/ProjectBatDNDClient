package game.characters;

public enum HeroClass {
    FIGHTER(284), WIZARD(285), CLERIC(285), CLASSLESS(-1);
    public long id;

    HeroClass(long id) {
        this.id = id;
    }

    public static HeroClass enumFromID(long id) {
        for (HeroClass hc : values()) {
            if (hc.id == id) return hc;
        }
        return CLASSLESS;
    }

}
