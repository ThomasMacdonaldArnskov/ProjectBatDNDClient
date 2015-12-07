package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

/**
 * Map editor class. Replaces the tiles in the map matrix which is displayed with the fog of war component.
 * The map editor has-a tile bank which governs if it is a single tile or a group of tiles. A grass fire algorithm
 * replaces the tiles in the map based on the input from the mouse (which tile is currently selected and where do
 * you want to place it on the map.
 * Created by TMA on 05-12-2015.
 */
public class MapEditor extends BasicGameState {


    /**********************************
     * VARIABLES
     **********************************/

    private TileBank tileBank;
    private game.uiContainer uiContainer;

    boolean groupTileSelected;
    boolean mouseInputClicked;
    boolean mouseDown;

    int[][] map;
    int containerX = 768;   //Initial position of the UI container
    int containerY = 32;   //Initial position of the UI container
    int x;                  //x in tiles
    int y;                  //y in tiles
    int mouseX;
    int mouseY;
    int selectedTileID = 10;
    private int clickableOffsetX;
    private int clickableOffsetY;

    private Button[] buttons;

    ArrayList<int[][]> tileStore = new ArrayList<>();


    /**********************************
     * CONSTRUCTOR
     **********************************/
    public MapEditor() {
        tileBank = new TileBank();
        uiContainer = new uiContainer(containerX, containerY);
        tileBank.setSelectedID(selectedTileID);
        groupTileSelected = tileBank.singleOrGroupTiles();
        buttons = new Button[44];
        initializeButtons();
    }

    public void initializeButtons() {

        //THE WONDERFUL WORLD OF MONKEY CODING! SINCE EACH TILE IS AT AN ARBITRARY POSITION IN THE SPRITE SHEET
        //AND NOT NECESSARILY OF THE SAME SIZE THERE ISN'T REALLY A SMART WAY OF DOING THIS UNFORTUNATELY!
        buttons[0] = new Button(0);
        buttons[1] = new Button(12);
        buttons[2] = new Button(80);
        buttons[3] = new Button(81);
        buttons[4] = new Button(92);
        buttons[5] = new Button(93);
        buttons[6] = new Button(104);
        buttons[7] = new Button(105);
        buttons[8] = new Button(112);
        buttons[9] = new Button(113);
        buttons[10] = new Button(114);
        buttons[11] = new Button(115);
        buttons[12] = new Button(116);
        buttons[13] = new Button(117);
        buttons[14] = new Button(124);
        buttons[15] = new Button(125);
        buttons[16] = new Button(126);
        buttons[17] = new Button(127);
        buttons[18] = new Button(128);
        buttons[19] = new Button(129);
        buttons[20] = new Button(136);
        buttons[21] = new Button(137);
        buttons[22] = new Button(138);
        buttons[23] = new Button(139);
        buttons[24] = new Button(140);
        buttons[25] = new Button(141);
        buttons[26] = new Button(142);
        buttons[27] = new Button(28);
        buttons[28] = new Button(30);       //GROUP TILES FOR THE REST
        buttons[29] = new Button(24);
        buttons[30] = new Button(26);
        buttons[31] = new Button(1);
        buttons[32] = new Button(65);
        buttons[33] = new Button(86);
        buttons[34] = new Button(56);
        buttons[35] = new Button(7);
        buttons[36] = new Button(62);
        buttons[37] = new Button(10);
        buttons[38] = new Button(3);
        buttons[39] = new Button(60);
        buttons[40] = new Button(32);
        buttons[41] = new Button(89);
        buttons[42] = new Button(34);
        buttons[43] = new Button(108);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setUiContainer(uiContainer);
        }
    }


    /**********************************
     * SLICK METHODS
     **********************************/


    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        uiContainer.UIContainer(g);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].display(g);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        setButtonPosition();
        mouseX = gc.getInput().getMouseX();
        mouseY = gc.getInput().getMouseY();
        x = mouseX / BattleMap.SPRITESIZE;
        y = mouseY / BattleMap.SPRITESIZE;
        containerX = uiContainer.getContainerX();
        containerY = uiContainer.getContainerY();
        mouseInputClicked = gc.getInput().isMousePressed(0);
        mouseDown = gc.getInput().isMouseButtonDown(0);

        selectTile();


        if (uiContainer.confinesOfContainer(mouseX, mouseY))
            uiDrag();
        else {
            placeTile();
        }

    }


    /**********************************
     * METHODS
     **********************************/

    /**
     * Places tiles on the map. If it is a single tile selected then it is immediately placed.
     * If it is a group of tiles (doesn't make sense to just place one part of a single group,
     * and it is too tedious for the user to do so) is places the tiles with a grass fire algorithm
     * as long as the position where will be within the scope of the map matrix.
     *
     * @param gc : The slick2D GameContainer
     */
    public void placeTile() {


        //IF IT IS A SINGLE TILE, NO WORRIES JUST PLACE THAT SUCKER ON THE MAP
        if (mouseInputClicked && !groupTileSelected) {
            map[x][y] = selectedTileID;
        }

        //IF IT IS A GROUP HOWEVER,
        if (mouseInputClicked && groupTileSelected) {
            tileSelector(tileBank.returnedMultiArr());
            //CHECK IF THE SELECTED TILE GROUP IS PLACED WITHIN THE GRID OF THE MAP
            if (y < map[0].length - tileStore.get(0).length + 1 && x < map.length - tileStore.get(0)[0].length + 1) {
                for (int i = 0; i < tileStore.get(0).length; i++) {
                    for (int j = 0; j < tileStore.get(0)[0].length; j++) {
                        map[j + x][i + y] = tileStore.get(0)[i][j];
                    }
                }
            }
        }
    }

    /**
     * Stores the selected tile group in an array list based on a simple grass fire algorithm
     *
     * @param tile : The returned tile matrix from the tile bank.
     */
    public void tileSelector(int[][] tile) {
        tileStore.clear();
        int[][] tmpTileStore = new int[tile[0].length][tile.length];
        for (int i = 0; i < tmpTileStore.length; i++) {
            for (int j = 0; j < tmpTileStore[0].length; j++) {
                tmpTileStore[i][j] = selectedTileID + j + i * 12;
            }
        }
        tileStore.add(tmpTileStore);
    }

    public void uiDrag() {

        if (uiContainer.confinesOfContainer(mouseX, mouseY)) {
            if (mouseInputClicked) {
                clickableOffsetX = mouseX - containerX;
                clickableOffsetY = mouseY - containerY;
            }
            if (mouseDown) {
                uiContainer.setDragPosition(mouseX - clickableOffsetX, mouseY - clickableOffsetY);
            }
        }
    }

    public void setButtonPosition() {

        int startXinContainer = 16;
        int startYinContainer = 32;

        for (int i = 0; i < 6; i++) {
            buttons[i].setOffsetX(startXinContainer + i * 31);
            buttons[i].setOffsetY(startYinContainer);
        }
        for (int i = 0; i < 6; i++) {
            buttons[i + 6].setOffsetX(startXinContainer + i * 31);
            buttons[i + 6].setOffsetY(startYinContainer + 29);
        }
        for (int i = 0; i < 6; i++) {
            buttons[i + 12].setOffsetX(startXinContainer + i * 31);
            buttons[i + 12].setOffsetY(startYinContainer + 29 * 2);
        }
        for (int i = 0; i < 6; i++) {
            buttons[i + 18].setOffsetX(startXinContainer + i * 31);
            buttons[i + 18].setOffsetY(startYinContainer + 29 * 3);
        }
        for (int i = 0; i < 3; i++) {
            buttons[i + 24].setOffsetX(startXinContainer + i * 31);
            buttons[i + 24].setOffsetY(startYinContainer + 29 * 4);
        }
        for (int i = 0; i < 4; i++) {
            buttons[i + 27].setOffsetX(startXinContainer + i * 46);
            buttons[i + 27].setOffsetY(startYinContainer + 163);
        }
        buttons[31].setOffsetX(startXinContainer);
        buttons[31].setOffsetY(startYinContainer + 163 + 69);
        for (int i = 0; i < 2; i++) {
            buttons[i + 32].setOffsetX(startXinContainer + 46 + i * 69);
            buttons[i + 32].setOffsetY(startYinContainer + 163 + 69);
        }
        buttons[34].setOffsetX(startXinContainer);
        buttons[34].setOffsetY(startYinContainer + 278);
        for (int i = 0; i < 2; i++) {
            buttons[i + 35].setOffsetX(startXinContainer + 46 + i * 69);
            buttons[i + 35].setOffsetY(startYinContainer + 278);
        }
        buttons[37].setOffsetX(startXinContainer);
        buttons[37].setOffsetY(startYinContainer + 325);
        buttons[38].setOffsetX(startXinContainer + 46);
        buttons[38].setOffsetY(startYinContainer + 325);
        buttons[39].setOffsetX(startXinContainer + 138);
        buttons[39].setOffsetY(startYinContainer + 325);
        buttons[40].setOffsetX(startXinContainer);
        buttons[40].setOffsetY(startYinContainer + 372);
        buttons[41].setOffsetX(startXinContainer + 46);
        buttons[41].setOffsetY(startYinContainer + 372);
        buttons[42].setOffsetX(startXinContainer + 114);
        buttons[42].setOffsetY(startYinContainer + 372);
        buttons[43].setOffsetX(startXinContainer);
        buttons[43].setOffsetY(startYinContainer + 420);
    }

    public void selectTile() {
        for (int j = 0; j < buttons.length; j++) {
            if (buttons[j].returnConfines(mouseX,mouseY) && mouseInputClicked) {
                groupTileSelected = tileBank.singleOrGroupTiles();
                selectedTileID = buttons[j].getTileNo();
                tileBank.setSelectedID(selectedTileID);
                groupTileSelected = tileBank.singleOrGroupTiles();
                break;
            }
        }
    }


    /**********************************
     * GETTERS
     **********************************/
    public int[][] getMap() {
        return map;
    }

    /**********************************
     * SETTERS
     **********************************/
    public void setMap(int[][] map) {
        this.map = map;
    }
}
