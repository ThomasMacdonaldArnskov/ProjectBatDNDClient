package game.characters;

import commons.transfer.objects.FiducialTransfer;

public class Character {
    private final HeroClass heroClass;
    private FiducialTransfer fiducial;

    public FiducialTransfer getFiducial() {
        return fiducial;
    }

    public HeroClass getHeroClass() {
        return heroClass;
    }

    public Character(HeroClass heroClass, FiducialTransfer fiducial) {
        this.heroClass = heroClass;
        this.fiducial = fiducial;
    }

    public static Character generateCharacterFromFiducial(FiducialTransfer fiducial) {
        return new Character(HeroClass.enumFromID(fiducial.getId()), fiducial);
    }
}
