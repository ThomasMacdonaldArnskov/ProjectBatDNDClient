package game.objects;

import game.Player;
import game.characters.CharacterSheet;
import game.gui.PlayerInterface;
import org.newdawn.slick.*;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import utils.GraphicsMethods;

import java.awt.*;

public class PlayerLobby {
    private PlayerAdminInterfaceButton[] players = new PlayerAdminInterfaceButton[3];
    private Point point;
    private Font title = GraphicsMethods.getFont(20);

    public PlayerLobby(Point point) {
        this.point = point;
    }

    public void update(int playerNumber, CharacterSheet cs) {
        if (players[playerNumber] == null) {
            PlayerAdminInterfaceButton pib = new PlayerAdminInterfaceButton(playerNumber, cs, point, () -> {
            });
            players[playerNumber] = pib;
        }
    }

    public void render(Graphics g) {
        g.setFont(title);
        g.drawString("Players", (int) point.getX() - g.getFont().getWidth("Players") / 2, ((int) point.getY()));
        for (int i = 0; i < players.length; i++) {
            Point p = new Point((int) point.getX() - 100,
                    ((int) point.getY() + (int) (g.getFont().getLineHeight() * 1.5)) + (i * 60));
            if (players[i] == null) {
                g.setColor(new org.newdawn.slick.Color(255, 255, 255, 255));
                g.drawRect((int) p.getX(), (int) p.getY(), 200, 55);
                g.drawString("Player " + i,
                        (int) point.getX() - g.getFont().getWidth("Players") / 2,
                        ((int) p.getY() + g.getFont().getLineHeight() / 2));
            } else {
                players[i].setPosition(p);
                players[i].render(g);
            }
        }
    }
}
