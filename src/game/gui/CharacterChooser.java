package game.gui;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;

import java.awt.*;

public class CharacterChooser {
    private Image tinyFighter;
    private Image tinyWizard;
    private Image tinyCleric;

    private Point point;


    public CharacterChooser(Point point) {
        this.point = point;
        try {
            tinyFighter = new Image("assets/img/fighter_icon_small.png");
            tinyWizard = new Image("assets/img/wizard_icon_small.png");
            tinyCleric = new Image("assets/img/cleric_icon_small.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void render(Graphics g) {
        g.drawImage(tinyFighter, (float) point.getX(), (float) point.getY());
        g.drawImage(tinyWizard, (float) point.getX() + 50, (float) point.getY());
        g.drawImage(tinyCleric, (float) point.getX() + 100, (float) point.getY());
    }
}
