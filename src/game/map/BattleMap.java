package game.map;

import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;

public class BattleMap extends BasicGame {

    /**********************************
     * VARIABLES
     **********************************/

    //PROTECTED FINAL STATICS, USED FOR CREATING MAP & UPDATING LIGHT MAP IN THE FOG OF WAR COMPONENT
    //WIDTH & HEIGHT IS THE NUMBER OF TILES IN WIDTH & HEIGHT RESPECTIVELY
    //SPRITE SIZE IS THE PIXEL SIZE OF EACH SPRITE
    //SPRITE SHEET IS THE NUMBER OF SPRITES (BOTH HEIGHT AND WIDTH) OF THE SPRITE SHEET

    public static final int WIDTH = 18;
    public static final int HEIGHT = 16;
    public static final int SPRITESIZE = 32;
    public static final int SPRITESHEET = 12;

    //private FogOfWar fogOfWar;
    private MapEditor mapEditor;
    private MapContainer mapContainer;
    int[][] map;


    /**********************************
     * CONSTRUCTOR
     **********************************/
    public BattleMap(String title) {
        super(title);
    }

    /**********************************
     * SLICK METHODS
     *********************************/

    @Override
    public void init(GameContainer gc) throws SlickException {

        this.mapContainer = new MapContainer(10, 10);
        mapEditor = new MapEditor("MapEditor", mapContainer);
        mapEditor.init(gc);
    }
    boolean ok = true;
    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        mapEditor.update(gc, i);
        //MAP TEST
        updateMap();
        map = mapContainer.getCurrentMap().getMap();

        if (ok && gc.getInput().isMouseButtonDown(0)) {
            mapContainer.subHit(gc.getInput().getMouseX(), gc.getInput().getMouseY());
            ok = false;
        }

        if (!gc.getInput().isMouseButtonDown(0)) {
            ok = true;
        }

        if (gc.getInput().isKeyPressed(Input.KEY_A)) {
            Map.saveMap(map);
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        mapEditor.render(gc, g);
        mapContainer.render(gc, g);
        mapContainer.renderMapSelector(16, 538, g);
    }

    public void updateMap() {

    }
}
