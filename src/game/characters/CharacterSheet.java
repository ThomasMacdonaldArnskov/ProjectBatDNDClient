package game.characters;

import commons.transfer.objects.FiducialTransfer;

public class CharacterSheet {
    private final HeroClass heroClass;
    private FiducialTransfer fiducial;

    public FiducialTransfer getFiducial() {
        return fiducial;
    }

    public HeroClass getHeroClass() {
        return heroClass;
    }

    public CharacterSheet(HeroClass heroClass, FiducialTransfer fiducial) {
        this.heroClass = heroClass;
        this.fiducial = fiducial;
    }
}
