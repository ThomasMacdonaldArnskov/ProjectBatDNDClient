package game;

import java.util.Random;

public class Die {
    public static int rollDie(int dieSize, int amount) {
        int value = 0;
        for (int i = 0; i < amount; i++) {
            int x = new Random().nextInt(dieSize) + 1;
            value += x;
        }
        return value;
    }
}
