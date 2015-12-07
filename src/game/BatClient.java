package game;

import net.NetworkClient;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BatClient extends StateBasedGame {

    game.BattleMap battleMap;
    private FogOfWar fogOfWar = new game.FogOfWar();

    private NetworkClient client;

    public static final String HOST_IP = "127.0.0.1"; //Local Host Kappa
    public static final int PORT = 5555;

    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    public BatClient() {
        super("DnD Game");
        client = new NetworkClient(HOST_IP, PORT);
        new Thread(client).start();
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new BattleMap(fogOfWar));
        this.addState(new MapEditor());
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
