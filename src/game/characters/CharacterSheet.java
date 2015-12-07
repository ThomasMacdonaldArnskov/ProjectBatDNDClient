package game.characters;

import commons.transfer.objects.FiducialTransfer;

public class CharacterSheet {

    private FiducialTransfer fiducial;

    private final String name;
    private final Race race;
    private final HeroClass heroClass;
    private Attributes attributes;

    @Override
    public String toString() {
        return name + "/&" + race + "/&" + heroClass;
    }

    public CharacterSheet(String name, Race race, HeroClass heroClass, FiducialTransfer fiducial) {
        this.heroClass = heroClass;
        this.fiducial = fiducial;
        this.name = name;
        this.race = race;
        this.attributes = new Attributes(this);
    }

    public CharacterSheet(RaceName raceName, HeroClass heroClass, FiducialTransfer fiducial) {
        this.heroClass = heroClass;
        this.fiducial = fiducial;
        this.name = raceName.getName();
        this.race = raceName.getRace();
        this.attributes = new Attributes(this);
    }

    public FiducialTransfer getFiducial() {
        return fiducial;
    }

    public HeroClass getHeroClass() {
        return heroClass;
    }

    public String getName() {
        return name;
    }

    public Race getRace() {
        return race;
    }

    public Attributes getAttributes() {
        return attributes;
    }
}
