package game.characters;

import java.util.Random;

public class NameRaceGenerator {
    public static RaceName getRaceAndName(HeroClass heroClass) {
        if (heroClass == HeroClass.FIGHTER) {
            int race = new Random().nextInt(fighers.length);
            int name = new Random().nextInt(fighers[race].length);

            switch (race) {
                case 0:
                    return new RaceName(Race.DRAGONBORN, fighers[race][name]);
                case 1:
                    return new RaceName(Race.HALFORC, fighers[race][name]);
                case 2:
                    return new RaceName(Race.HUMAN, fighers[race][name]);
            }
        } else if (heroClass == HeroClass.WIZARD) {
            int race = new Random().nextInt(wizards.length);
            int name = new Random().nextInt(wizards[race].length);

            switch (race) {
                case 0:
                    return new RaceName(Race.ELF, wizards[race][name]);
                case 1:                    return new RaceName(Race.GNOME, wizards[race][name]);
                case 2:
                    return new RaceName(Race.THIEFLING, wizards[race][name]);
            }
        } else if (heroClass == HeroClass.CLERIC) {
            int race = new Random().nextInt(clerics.length);
            int name = new Random().nextInt(clerics[race].length);

            switch (race) {
                case 0:
                    return new RaceName(Race.DWARF, clerics[race][name]);
                case 1:
                    return new RaceName(Race.HALFELF, clerics[race][name]);
                case 2:
                    return new RaceName(Race.HALFLING, clerics[race][name]);
            }
        }
        return new RaceName(Race.DWARF, "PlaceHolder Bob");
    }

    public static String[][] fighers = {
            //DragonBorn
            {"Arjhan", "Balasar", "Bharash", "Donaar",
                    "Ghesh", "Heskan", "Kriv", "Medrash",
                    "Mehen", "Nadarr", "Akra", "Biri",
                    "Daar", "Farideh", "Harann", "Havilar",
                    "Jheri", "Kava", "Korinn", "Misha"},
            //HalfOrc
            {"Dench", "Feng", "Gell", "Henk",
                    "Holg", "Imsh", "Keth", "Krusk",
                    "Mhurren", "Ront", "Baggi", "Emen",
                    "Engong", "Kansif", "Myev", "Neega",
                    "Ovak", "Ownka", "Shautha", "Vola"},
            //Human
            {"Bor", "Darvin", "Dorn", "Fodel",
                    "Geth", "Lander", "Marcon", "Pieron",
                    "Romero", "Salazar", "Arveene", "Balama",
                    "Betha", "Dona", "Faila", "Kara",
                    "Katernin", "Mara", "Natali", "Tana"}};
    public static String[][] wizards = {
            //elf
            {"Adran", "Aelar", "Aramil", "Arannis",
                    "Aust", "Beiro", "Berrian", "Carric",
                    "Enialis", "Erdan", "Adria", "Althaea",
                    "Astrianna", "Andraste", "Antinua", "Bethrynna",
                    "Birel", "Caelynn", "Drusilia", "Enna"},
            //gnome
            {"Alston", "Alvyn", "Boddynock", "Brocc",
                    "Burgell", "Dimble", "Eldon", "Erky",
                    "Fonkin", "Frug", "Bimpnottin", "Breena",
                    "Caramip", "Carlin", "Donella", "Duvamil",
                    "Ella", "Ellybell", "Ellywick", "Lilli"},
            //theifling
            {"Akmenos", "Amnon", "Barakas", "Damakos",
                    "Ekemon", "Iados", "Kairon", "Leucis",
                    "Melech", "Mordai", "Akta", "Anakis",
                    "Bryseis", "Criella", "Damaia", "Ea",
                    "Kalista", "Lerissa", "Makaria", "Nemeia"}};
    public static String[][] clerics = {
            //Dwarf
            {"Adrik", "Alberich", "Baern", "Barendd",
                    "Brottor", "Bruenor", "Dain", "Darrak",
                    "Delg", "Eberk", "Amber", "Artin",
                    "Audhild", "Bardryn", "Dagnal", "Diesa",
                    "Eldeth", "Falkrunn", "Finellen", "Gunnloda"},
            //HalfElf
            {"Alfred", "Boramil", "Eradrin", "Franz",
                    "Gatrin", "Hiladron", "Igor", "Jordan",
                    "Kirinath", "Larin", "Allysia", "Bella",
                    "Caradynn", "Dara", "Errani", "Falaria",
                    "Hilda", "Illyith", "Kirith", "Lana"},
            //Halfling
            {"Alton", "Ander", "Cade", "Corrin",
                    "Eldon", "Errich", "Finnan", "Garret",
                    "Lindal", "Lyle", "Andry", "Bree",
                    "Callie", "Cora", "Euphemia", "Killian",
                    "Kithri", "Lavinia", "Lidda", "Merla"}};
}
