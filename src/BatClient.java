import org.newdawn.slick.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BatClient extends BasicGame {

    //BattleMap battleMap;
    //Player player;
    FogOfWar fogOfWar = new FogOfWar();

    public BatClient(String gamename) {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        //battleMap = new BattleMap("Battle Map");
        //battleMap.init(gc);
        //fogOfWar = new FogOfWar("FogOfWar");
        fogOfWar.init(gc);
        //player = new Player("Player");
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
            appgc = new AppGameContainer(new BatClient("Simple Slick Game"));
            appgc.setDisplayMode(1024, 768, false); //THIS SHOULD BE THE RESOLUTION OF OUR CURRENT PROJECTOR TOSHIBA TDP-T90
            appgc.setTargetFrameRate(60);
            appgc.setShowFPS(false);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(BatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
