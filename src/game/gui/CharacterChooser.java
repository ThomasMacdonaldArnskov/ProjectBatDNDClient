package game.gui;

import game.characters.HeroClass;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import utils.AreaPulse;
import utils.TransformHitbox;

import java.awt.*;

public class CharacterChooser {
    private Image tinyFighter;
    private Image tinyWizard;
    private Image tinyCleric;

    private Point point;
    private int rotation;

    private boolean active;
    private HeroClass currentHero = HeroClass.CLASSLESS;
    private AreaPulse miniPulse;

    private TransformHitbox[] hitbox;

    public HeroClass getSelected() {
        return currentHero;
    }

    public CharacterChooser(Point point, int rotation) {
        this.point = point;
        this.rotation = rotation;
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
        setHitboxes();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setHitboxes() {
        this.hitbox = new TransformHitbox[3];
        hitbox[0] = new TransformHitbox();
        hitbox[1] = new TransformHitbox();
        hitbox[2] = new TransformHitbox();

        int width = tinyFighter.getWidth();
        int height = tinyFighter.getHeight();
        int y = (int) this.point.getY();
        int x = (int) this.point.getX();
        hitbox[0].rotateAroundPoint(rotation, new Rectangle(x, y, width, height),
                (int) this.point.getX(), (int) this.point.getY());
        hitbox[1].rotateAroundPoint(rotation, new Rectangle(x + 50, y, width, height),
                (int) this.point.getX(), (int) this.point.getY());
        hitbox[2].rotateAroundPoint(rotation, new Rectangle(x + 100, y, width, height),
                (int) this.point.getX(), (int) this.point.getY());
    }

    public boolean isInside(Point point) {
        for (int i = 0; i < hitbox.length; i++) {
            if (hitbox[i].isHit((int) point.getX(), (int) point.getY())) {
                if (i == 0) setCurrentHero(HeroClass.FIGHTER);
                else if (i == 1) setCurrentHero(HeroClass.WIZARD);
                else if (i == 2) setCurrentHero(HeroClass.CLERIC);
                return true;
            }
        }
        return false;
    }

    public void setCurrentHero(HeroClass currentHero) {
        this.currentHero = currentHero;
    }

    public void render(Graphics g) {
        if (active) {
            g.pushTransform();
            g.rotate((int) this.point.getX(), (int)  this.point.getY(), rotation);
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
            if (currentHero != HeroClass.CLASSLESS)
                miniPulse.render(g);
            g.drawImage(tinyFighter, (float) point.getX(), (float) point.getY());
            g.drawImage(tinyWizard, (float) point.getX() + 50, (float) point.getY());
            g.drawImage(tinyCleric, (float) point.getX() + 100, (float) point.getY());
            g.popTransform();
        } else {
            miniPulse.setActive(false);
        }
    }
}
