package game.gui;

import game.characters.HeroClass;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import utils.AreaPulse;

import java.awt.*;

public class CharacterChooser {
    private Image tinyFighter;
    private Image tinyWizard;
    private Image tinyCleric;

    private Point point;

    private boolean active;
    private HeroClass currentHero = HeroClass.CLERIC;
    private AreaPulse miniPulse;

    public HeroClass getSelected() {
        return currentHero;
    }

    public CharacterChooser(Point point) {
        this.point = point;
        this.active = false;
        try {
            tinyFighter = new Image("assets/img/fighter_icon_small.png");
            tinyWizard = new Image("assets/img/wizard_icon_small.png");
            tinyCleric = new Image("assets/img/cleric_icon_small.png");
            miniPulse = new AreaPulse("", point, (tinyFighter.getWidth() - 10) / 2);
            miniPulse.setColor(Color.white);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isInside(Point point) {
        for (int i = 0; i < 3; i++) {
            float disX = (float) ((this.point.getX() + (50 * i)) - point.getX());
            float disY = (float) (this.point.getY() - point.getY());
            if (Math.sqrt((disX * disX) + (disY * disY)) < tinyFighter.getWidth() / 2) {
                return true;
            }
        }

        return false;
    }

    public void render(Graphics g) {
        if (active) {
            miniPulse.setActive(true);

            g.setColor(new Color(255, 255, 255, 100));

            if (currentHero == HeroClass.FIGHTER)
                miniPulse.setCenterPosition(
                        new Point((int) (point.getX()) + tinyFighter.getWidth() / 2,
                                (int) (point.getY()) + tinyFighter.getHeight() / 2));
            if (currentHero == HeroClass.WIZARD)
                miniPulse.setCenterPosition(
                        new Point((int) (point.getX() + 50) + tinyWizard.getWidth() / 2,
                                (int) (point.getY()) + tinyWizard.getHeight() / 2));
            if (currentHero == HeroClass.CLERIC)
                miniPulse.setCenterPosition(
                        new Point((int) (point.getX() + 100) + tinyFighter.getWidth() / 2,
                                (int) (point.getY()) + tinyFighter.getHeight() / 2));
            miniPulse.render(g);
            g.drawImage(tinyFighter, (float) point.getX(), (float) point.getY());
            g.drawImage(tinyWizard, (float) point.getX() + 50, (float) point.getY());
            g.drawImage(tinyCleric, (float) point.getX() + 100, (float) point.getY());
        } else {
            miniPulse.setActive(false);
        }
    }
}
