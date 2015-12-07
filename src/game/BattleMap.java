package game;

import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.*;
import java.util.Map;

public class BattleMap extends BasicGameState {

    /**********************************
     * VARIABLES
     **********************************/

    //PROTECTED FINAL STATICS, USED FOR CREATING MAP & UPDATING LIGHT MAP IN THE FOG OF WAR COMPONENT
    //WIDTH & HEIGHT IS THE NUMBER OF TILES IN WIDTH & HEIGHT RESPECTIVELY
    //SPRITE SIZE IS THE PIXEL SIZE OF EACH SPRITE
    //SPRITE SHEET IS THE NUMBER OF SPRITES (BOTH HEIGHT AND WIDTH) OF THE SPRITE SHEET

    protected static final int WIDTH = 32;
    protected static final int HEIGHT = 24;
    protected static final int SPRITESIZE = 32;
    protected static final int SPRITESHEET = 12;

    private FogOfWar fogOfWar;
    private MapEditor mapEditor;

    int[][] map;


    /**********************************
     * CONSTRUCTOR
     **********************************/
    public BattleMap(FogOfWar fogOfWar) {
        this.fogOfWar = fogOfWar;
    }


    /**********************************
     * SLICK METHODS
     *********************************/

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        fogOfWar.init(gc);
        mapEditor = new MapEditor();
        mapEditor.init(gc,sbg);
        map = new int[WIDTH][HEIGHT];
        preFabBattleMap();
        mapEditor.setMap(map);

    }

    @Override
    public void update(GameContainer gc, StateBasedGame stateBasedGame, int i) throws SlickException {
        fogOfWar.update(gc, i);
        mapEditor.update(gc,stateBasedGame,i);
        //MAP TEST
        updateMap();
        map = mapEditor.getMap();

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        fogOfWar.render(gc, g);
        mapEditor.render(gc,sbg, g);
    }

    @Override
    public int getID() {
        return 0;
    }

    /**********************************
     * METHODS
     *********************************/

    public void preFabBattleMap() {

        int[][] preFab = new int[][] {

                        {105, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 93, 113, 113, 105, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 118, 93},
                        {116, 61, 62, 1, 1, 1, 1, 1, 1, 1, 61, 62, 117, 113, 113, 116, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 13, 82, 82, 13, 117},
                        {116, 73, 74, 13, 57, 58, 1, 57, 58, 13, 73, 74, 117, 113, 113, 116, 1, 81, 1, 1, 1, 1, 1, 1, 1, 1, 1, 82, 29, 30, 82, 117},
                        {116, 85, 86, 1, 69, 70, 1, 69, 70, 1, 85, 86, 117, 113, 113, 116, 1, 1, 1, 1, 1, 1, 1, 1, 1, 81, 1, 82, 41, 42, 82, 117},
                        {116, 97, 98, 1, 1, 1, 1, 1, 1, 1, 97, 98, 117, 113, 113, 116, 1, 1, 1, 1, 129, 115, 115, 130, 1, 1, 1, 82, 53, 54, 82, 117},
                        {116, 1, 109, 110, 111, 112, 1, 109, 110, 111, 112, 1, 117, 113, 113, 116, 1, 1, 1, 1, 117, 113, 113, 116, 1, 1, 1, 13, 82, 82, 13, 117},
                        {116, 1, 121, 122, 123, 124, 1, 121, 122, 123, 124, 1, 117, 113, 113, 116, 1, 1, 81, 1, 117, 113, 113, 116, 90, 91, 92, 1, 1, 1, 1, 117},
                        {116, 1, 133, 134, 135, 136, 1, 133, 134, 135, 136, 1, 117, 113, 113, 116, 33, 34, 1, 1, 117, 113, 113, 116, 102, 103, 104, 1, 1, 1, 1, 117},
                        {116, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 117, 113, 113, 116, 45, 46, 1, 1, 117, 113, 113, 94, 115, 115, 115, 115, 115, 115, 115, 106},
                        {116, 1, 109, 110, 111, 112, 1, 109, 110, 111, 112, 1, 117, 113, 113, 94, 130, 1, 1, 1, 117, 113, 113, 113, 113, 113, 113, 113, 113, 113, 113, 113},
                        {116, 1, 121, 122, 123, 124, 1, 121, 122, 123, 124, 1, 117, 113, 113, 113, 116, 1, 1, 1, 117, 113, 113, 105, 118, 118, 118, 118, 118, 118, 118, 93},
                        {116, 1, 133, 134, 135, 136, 1, 133, 134, 135, 136, 1, 117, 113, 113, 113, 116, 1, 1, 1, 141, 118, 118, 142, 1, 1, 1, 1, 1, 25, 26, 117},
                        {116, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 117, 113, 113, 113, 116, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 37, 38, 117},
                        {94, 115, 130, 127, 127, 1, 1, 1, 127, 127, 129, 115, 106, 113, 113, 113, 116, 1, 81, 1, 11, 12, 1, 1, 1, 1, 1, 1, 1, 49, 50, 117},
                        {113, 113, 116, 25, 26, 1, 1, 1, 25, 26, 117, 113, 113, 113, 113, 113, 116, 1, 1, 1, 23, 24, 1, 1, 1, 1, 1, 1, 1, 2, 3, 117},
                        {113, 113, 116, 37, 38, 1, 1, 1, 37, 38, 117, 113, 113, 113, 113, 113, 116, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 14, 15, 117},
                        {113, 113, 116, 1, 50, 1, 1, 1, 1, 50, 117, 113, 113, 113, 113, 113, 94, 115, 115, 115, 115, 115, 115, 130, 33, 34, 1, 1, 1, 27, 28, 117},
                        {113, 113, 116, 1, 11, 12, 1, 11, 12, 1, 117, 113, 105, 118, 118, 118, 118, 93, 113, 113, 113, 113, 113, 116, 45, 46, 1, 1, 1, 39, 40, 117},
                        {113, 113, 116, 1, 23, 24, 1, 23, 24, 1, 117, 113, 116, 1, 1, 1, 1, 117, 113, 113, 113, 113, 113, 116, 140, 140, 140, 1, 1, 51, 52, 117},
                        {113, 113, 116, 140, 140, 1, 1, 1, 140, 140, 141, 118, 142, 1, 33, 34, 1, 141, 118, 118, 118, 118, 118, 118, 118, 118, 142, 1, 1, 1, 129, 106},
                        {113, 113, 113, 113, 116, 1, 1, 1, 1, 81, 1, 1, 1, 1, 45, 46, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 117, 113},
                        {113, 113, 113, 113, 116, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 81, 1, 1, 1, 1, 1, 1, 1, 1, 90, 91, 92, 117, 113},
                        {113, 113, 113, 113, 116, 81, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 102, 103, 104, 117, 113},
                        {113, 113, 113, 113, 94, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 115, 106, 113}
        };
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                map[j][i] = preFab[i][j]-1;
            }
        }
    }

    public void updateMap() {
        fogOfWar.setMap(map);

    }

    /**********************************
     * GETTERS
     *********************************/


    /**********************************
     * SETTERS
     *********************************/

}
