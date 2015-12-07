package game.objects;

import game.characters.CharacterSheet;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import utils.GraphicsMethods;

import java.awt.*;

public class PlayerLobby {
    private PlayerAdminInterfaceButton[] players = new PlayerAdminInterfaceButton[3];
    private Point point;
    private Font title = GraphicsMethods.getFont(20);
    private int rotation;

    public PlayerLobby(Point point, int rotation) {
        this.point = point;
        this.rotation = rotation;
    }

    public void update(int playerNumber, CharacterSheet cs) {
        if (players[playerNumber] == null) {
            PlayerAdminInterfaceButton pib = new PlayerAdminInterfaceButton(playerNumber, cs, point, rotation, () -> {
            });
            players[playerNumber] = pib;
        }
    }

    public PlayerAdminInterfaceButton[] getPlayers() {
        return players;
    }

    public boolean someoneReady() {
        for (PlayerAdminInterfaceButton paib : players) {
            if (paib.getPlayerNumber() > -1) {
                return true;
            }
        }
        return false;
    }

    public void render(Graphics g) {
        g.pushTransform();
        g.rotate((int) point.getX(), (int) point.getY(), 270);
        g.setFont(title);
        g.drawString("Players", (int) point.getX() - g.getFont().getWidth("Players") / 2, ((int) point.getY()));
        for (int i = 0; i < players.length; i++) {
            g.setFont(title);
            Point p = new Point((int) point.getX() - 100,
                    ((int) point.getY() + (int) (g.getFont().getLineHeight() * 1.5)) + (i * 60));
            if (players[i] == null) {
                g.setColor(new org.newdawn.slick.Color(255, 255, 255, 255));
                g.drawRect((int) p.getX(), (int) p.getY(), 200, 55);
                g.drawString("Player " + i,
                        (int) point.getX() - g.getFont().getWidth("Player " + i) / 2,
                        ((int) p.getY() + g.getFont().getLineHeight() / 2));
            } else {
                players[i].setPosition(p);
                players[i].hitbox.rotateAroundPoint(rotation,
                        new Rectangle((int) players[i].getPosition().getX(), (int) players[i].getPosition().getY(),
                                players[i].getWidth(), players[i].getHeight()), (int) point.getX(), (int) point.getY());
                players[i].render(g);
            }
        }
        g.popTransform();
    }
}
