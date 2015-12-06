package game.objects;

import game.characters.CharacterSheet;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import utils.GraphicsMethods;

import java.awt.*;

public class PlayerStatistics {
    private CharacterSheet cs;
    private Point point;
    private Font title = GraphicsMethods.getFont(20);

    public PlayerStatistics(Point point) {
        this.point = point;
    }

    public void update(CharacterSheet cs) {
        this.cs = cs;
    }

    public void render(Graphics g) {
        if (cs != null) {
            g.setFont(title);
            g.drawString(cs.getName(), (int) point.getX() -
                    g.getFont().getWidth(cs.getName()) / 2, ((int) point.getY()));

            String left = "STR: " + cs.getAttributes().getSTRENGTH()
                    + "/&DEX: " + cs.getAttributes().getDEXTERITY() +
                    "/&CON: " + +cs.getAttributes().getCONSTITUTION();
            GraphicsMethods.drawStrings(left, (int) point.getX(), (int) point.getY(), g);
        }
    }
}
