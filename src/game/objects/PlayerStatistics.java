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
    private Font subtitle = GraphicsMethods.getFont(15);

    private Font small = GraphicsMethods.getFont(12);
    private Font smallbold = GraphicsMethods.getFont(12);

    public PlayerStatistics(Point point) {
        this.point = point;
    }

    public void update(CharacterSheet cs) {
        this.cs = cs;
    }

    public void render(Graphics g) {
        if (cs != null) {
            g.drawRect((int) point.getX() - 5, ((int) point.getY()), 205, 228);
            g.setFont(title);
            g.drawString(cs.getName(), (int) point.getX(), ((int) point.getY()));
            int nameW = g.getFont().getWidth(cs.getName());
            int h = g.getFont().getLineHeight() + 5;

            g.setFont(subtitle);
            g.drawString(cs.getRace() + " " + cs.getHeroClass(),
                    (int) point.getX(), (int) (point.getY() + (h * 0.8)));

            g.setFont(small);
            g.drawString("lvl: " + cs.getAttributes().getLEVEL() + " hp: " + cs.getAttributes().getHITPOINTS(),
                    (int) (point.getX() + nameW * 1.1),
                    ((int) point.getY() + h / 3));

            g.drawLine((int) point.getX() - 5, (int) point.getY() + 95,
                    (int) point.getX() + 200, (int) point.getY() + 95);

            String left = "STR: " + cs.getAttributes().getSTRENGTH()
                    + "/&DEX: " + cs.getAttributes().getDEXTERITY() +
                    "/&CON: " + +cs.getAttributes().getCONSTITUTION();

            GraphicsMethods.drawStrings(left, (int) point.getX() + 25,
                    (int) (point.getY() + h * 1.4), g);

            String right = "INT: " + cs.getAttributes().getINTELLIGENCE()
                    + "/&WIS: " + cs.getAttributes().getWISDOM() +
                    "/&CHA: " + +cs.getAttributes().getCHARISMA();

            GraphicsMethods.drawStrings(right, (int) point.getX() + 110,
                    (int) (point.getY() + h * 1.4), g);

        }
    }
}
