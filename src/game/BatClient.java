package game;

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
    private PlayerInterface[] players = new PlayerInterface[3];
    private NetworkClient client;

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
        //battleMap = new game.BattleMap("Battle Map");
        //battleMap.init(gc);
        //fogOfWar = new game.FogOfWar("game.FogOfWar");
        // fogOfWar.init(gc);
        players[0] = new PlayerInterface(new Point(gc.getWidth() / 2 - 75, 75), 180);
        players[1] = new PlayerInterface(new Point(75, gc.getHeight() / 2), 90);
        players[2] = new PlayerInterface(new Point(gc.getWidth() / 2 - 75, gc.getHeight() - 150), 0);

        for (PlayerInterface pi : players) {
            pi.init(gc);
        }
        //player = new game.Player("game.Player");
        //player.init(gc);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        //player.update(gc,i);
        //fogOfWar.update(gc, i);
        for (PlayerInterface pi : players) {
            pi.update(gc, i);
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        //fogOfWar.render(gc, g);
        //player.render(gc, g);
        for (PlayerInterface pi : players) {
            pi.render(gc, g);
        }
        g.setLineWidth(10);
        g.drawLine(gc.getWidth() - 300, 0, gc.getWidth() - 300, gc.getHeight());
        if (ClientChannelHandler.fiducials.get(0) != null)
            g.drawOval(
                    (int) ClientChannelHandler.fiducials.get(0).getPosition().getX() - 5,
                    (int) ClientChannelHandler.fiducials.get(0).getPosition().getY() - 5, 10, 10);
    }


    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new BatClient());
            //appgc.setDisplayMode(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height, true);
            appgc.setDisplayMode(BatClient.WIDTH, BatClient.HEIGHT, false);
            appgc.setTargetFrameRate(60);
            appgc.setShowFPS(true);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(BatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
