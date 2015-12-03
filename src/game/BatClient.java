package game;

import net.NetworkClient;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BatClient extends BasicGame {

    //game.BattleMap battleMap;
    //game.Player player;
    private FogOfWar fogOfWar = new FogOfWar();
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
        fogOfWar.init(gc);
        //player = new game.Player("game.Player");
        //player.init(gc);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        //player.update(gc,i);
        fogOfWar.update(gc, i);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        fogOfWar.render(gc, g);
        //player.render(gc, g);
    }


    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new BatClient());
            //appgc.setDisplayMode(Toolkit.getDefaultToolkit().getScreenSize().width,Toolkit.getDefaultToolkit().getScreenSize().height, true);
            appgc.setDisplayMode(BatClient.WIDTH, BatClient.HEIGHT, false);
            appgc.setTargetFrameRate(60);
            appgc.setShowFPS(false);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(BatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
