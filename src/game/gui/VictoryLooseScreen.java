package game.gui;

import game.objects.Button;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.awt.*;

public class VictoryLooseScreen {
    Image lost;
    Image win;
    Button replay;

    public VictoryLooseScreen() {
        try {
            lost = new Image("assets/Lost.png");
            win = new Image("assets/Victory.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
        replay = new Button(new Point(400, 655), 184, 45, "Replay?");
    }

    public void render(boolean won, Graphics g) {
        if (won) {
            g.drawImage(win, 0, 0);
        } else {
            g.drawImage(lost, 0, 0);
        }
        replay.render(g);
    }

    public boolean replay(int x, int y) {
        return replay.isPressed(new Point(x, y));
    }
}
