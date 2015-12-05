package game;

import game.gui.AdminInterface;
import game.gui.PlayerInterface;
import net.ClientChannelHandler;
import net.NetworkClient;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BatClient extends BasicGame {

    //game.BattleMap battleMap;
    //game.Player player;
    private FogOfWar fogOfWar = new FogOfWar();
    private NetworkClient client;
    private AdminInterface adminInterface;

    public static final String HOST_IP = "127.0.0.1"; //Local Host Kappa
    public static final int PORT = 5555;

    public static int WIDTH = 1024;
    public static int HEIGHT = 768;

    public BatClient() {
        super("DnD Game");
        client = new NetworkClient(HOST_IP, PORT);
        new Thread(client).start();
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        adminInterface = new AdminInterface(new Point(gc.getWidth() - 75, gc.getHeight() / 2));
        adminInterface.init(gc);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        //player.update(gc,i);
        //fogOfWar.update(gc, i);
        adminInterface.update(gc, i);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        //fogOfWar.render(gc, g);
        //player.render(gc, g);
        adminInterface.render(gc, g);
        g.setLineWidth(10);
        g.drawLine(gc.getWidth() - 300, 0, gc.getWidth() - 300, gc.getHeight());
        if (!ClientChannelHandler.fiducials.isEmpty() && ClientChannelHandler.fiducials.get(0) != null)
            g.drawOval(
                    (int) ClientChannelHandler.fiducials.get(0).getPosition().getX() - 5,
                    (int) ClientChannelHandler.fiducials.get(0).getPosition().getY() - 5, 10, 10);
    }


    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new BatClient());
            appgc.setDisplayMode(BatClient.WIDTH, BatClient.HEIGHT, false);
            appgc.setTargetFrameRate(60);
            appgc.setShowFPS(true);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(BatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
