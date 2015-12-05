package game.objects;

import game.characters.CharacterSheet;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import utils.AreaPulse;
import utils.GraphicsMethods;
import org.newdawn.slick.Image;

import java.awt.*;

public class PlayerAdminInterfaceButton extends Button {

    private CharacterSheet characterSheet;
    private Image classIcon;
    private int playerNumber;
    private Font name = GraphicsMethods.getFont(15, java.awt.Font.BOLD);
    private Font raceclass = GraphicsMethods.getFont(10, java.awt.Font.ITALIC);

    public PlayerAdminInterfaceButton(int playerNumber, CharacterSheet characterSheet, Point position, ButtonAction action) {
        super(position, 200, 55, characterSheet.toString(), action, (button, g) -> {
        }, true);
        this.characterSheet = characterSheet;
        this.playerNumber = playerNumber;
        try {
            switch (this.characterSheet.getHeroClass()) {
                case FIGHTER:
                    classIcon = new Image("assets/img/fighter_icon_small.png");
                    break;
                case WIZARD:
                    classIcon = new Image("assets/img/wizard_icon_small.png");
                    break;
                case CLERIC:
                    classIcon = new Image("assets/img/cleric_icon_small.png");
                    break;
                default:
                    classIcon = new Image("assets/img/fighter_icon_small.png");
                    break;
            }
        } catch (SlickException e) {
            e.printStackTrace();
            return;
        }

        setGraphics((button, g) -> {
            g.setLineWidth(2);
            g.setColor(new Color(255, 255, 255, 255));
            g.drawRect((int) button.getPosition().getX(), (int) button.getPosition().getY(),
                    button.getWidth(), button.getHeight());
            g.fillRect((int) button.getPosition().getX() + 3, (int) button.getPosition().getY() + 3,
                    button.getWidth() - 6, button.getHeight() - 6);
            g.setColor(new Color(0, 0, 0, 255));
            g.setFont(name);
            g.drawString(characterSheet.getName().toUpperCase(),
                    (int) button.getPosition().getX() + classIcon.getWidth() + 10,
                    (int) button.getPosition().getY() + 4);
            int h = g.getFont().getLineHeight();
            g.setFont(raceclass);
            g.drawString(characterSheet.getRace().toString() + " " + characterSheet.getHeroClass().toString(),
                    (int) button.getPosition().getX() + classIcon.getWidth() + 10,
                    (int) button.getPosition().getY() + 4 + h);
            int hx = g.getFont().getLineHeight();
            g.drawString("Lvl: " + characterSheet.getAttributes().getLEVEL(),
                    (int) button.getPosition().getX() + classIcon.getWidth() + 10,
                    (int) button.getPosition().getY() + 4 + h + hx);
            g.drawImage(classIcon, (int) button.getPosition().getX() + 5,
                    (int) button.getPosition().getY() + button.getHeight() / 2 - classIcon.getHeight() / 2);
        });
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public boolean isPressed(Point point) {
        if (point.getX() >= this.position.getX() && point.getX() <= this.position.getX() + width &&
                point.getY() >= this.position.getY() && point.getY() <= this.position.getY() + height) {
            action.action();
            return true;
        } else {
            return false;
        }
    }
}
