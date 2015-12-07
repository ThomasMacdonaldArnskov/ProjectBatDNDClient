package game.characters;

public class RaceName {
    private Race race;
    private String name;

    public RaceName(Race race, String name) {
        this.race = race;
        this.name = name;
    }

    public Race getRace() {
        return race;
    }

    public String getName() {
        return name;
    }
}
