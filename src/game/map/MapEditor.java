package game.map;

import commons.transfer.objects.BlobTransfer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;

/**
 * Map editor class. Replaces the tiles in the map matrix which is displayed with the fog of war component.
 * The map editor has-a tile bank which governs if it is a single tile or a group of tiles. A grass fire algorithm
 * replaces the tiles in the map based on the input from the mouse (which tile is currently selected and where do
 * you want to place it on the map.
 * Created by TMA on 05-12-2015.
 */
public class MapEditor extends BasicGame {


    /**********************************
     * VARIABLES
     **********************************/

    private TileBank tileBank;
    private game.map.uiContainer uiContainer;

    boolean groupTileSelected;
    boolean mouseInputClicked;
    boolean mouseDown;
    MapContainer mapContainer;

    int containerX = 738;   //Initial position of the UI container
    int containerY = 7;   //Initial position of the UI container

    int selectedTileID = 0;

    private MapButton[] mapButtons;

    ArrayList<int[][]> tileStore = new ArrayList<>();

    /**********************************
     * CONSTRUCTOR
     **********************************/
    public MapEditor(String title, MapContainer mapContainer) {
        super(title);
        this.mapContainer = mapContainer;
    }

    public void initializeButtons() {

        mapButtons[0] = new MapButton(0);
        mapButtons[1] = new MapButton(12);
        mapButtons[2] = new MapButton(80);
        mapButtons[3] = new MapButton(81);
        mapButtons[4] = new MapButton(92);
        mapButtons[5] = new MapButton(93);
        mapButtons[6] = new MapButton(104);
        mapButtons[7] = new MapButton(105);
        mapButtons[8] = new MapButton(112);
        mapButtons[9] = new MapButton(113);
        mapButtons[10] = new MapButton(114);
        mapButtons[11] = new MapButton(115);
        mapButtons[12] = new MapButton(116);
        mapButtons[13] = new MapButton(117);
        mapButtons[14] = new MapButton(124);
        mapButtons[15] = new MapButton(125);
        mapButtons[16] = new MapButton(126);
        mapButtons[17] = new MapButton(127);
        mapButtons[18] = new MapButton(128);
        mapButtons[19] = new MapButton(129);
        mapButtons[20] = new MapButton(136);
        mapButtons[21] = new MapButton(137);
        mapButtons[22] = new MapButton(138);
        mapButtons[23] = new MapButton(139);
        mapButtons[24] = new MapButton(140);
        mapButtons[25] = new MapButton(141);
        mapButtons[26] = new MapButton(142);
        mapButtons[27] = new MapButton(28);
        mapButtons[28] = new MapButton(30);       //GROUP TILES FOR THE REST
        mapButtons[29] = new MapButton(24);
        mapButtons[30] = new MapButton(26);
        mapButtons[31] = new MapButton(1);
        mapButtons[32] = new MapButton(65);
        mapButtons[33] = new MapButton(86);
        mapButtons[34] = new MapButton(56);
        mapButtons[35] = new MapButton(7);
        mapButtons[36] = new MapButton(62);
        mapButtons[37] = new MapButton(10);
        mapButtons[38] = new MapButton(3);
        mapButtons[39] = new MapButton(60);
        mapButtons[40] = new MapButton(32);
        mapButtons[41] = new MapButton(89);
        mapButtons[42] = new MapButton(34);
        mapButtons[43] = new MapButton(108);
        mapButtons[44] = new MapButton(35);
        mapButtons[45] = new MapButton(47);
        mapButtons[46] = new MapButton(58);
        mapButtons[47] = new MapButton(59);
        mapButtons[48] = new MapButton(70);
        mapButtons[49] = new MapButton(71);
        mapButtons[50] = new MapButton(82);
        mapButtons[51] = new MapButton(83);
        mapButtons[52] = new MapButton(94);
        mapButtons[53] = new MapButton(95);

        for (MapButton mapButton : mapButtons) {
            mapButton.setUiContainer(uiContainer);
        }
    }


    /**********************************
     * SLICK METHODS
     **********************************/

    @Override
    public void init(GameContainer gc) throws SlickException {
        tileBank = new TileBank();
        uiContainer = new uiContainer(containerX, containerY);
        tileBank.setSelectedID(selectedTileID);
        groupTileSelected = tileBank.singleOrGroupTiles();
        mapButtons = new MapButton[54];
        initializeButtons();
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        uiContainer.UIContainer(g);
        for (MapButton mapButton : mapButtons) {
            mapButton.display(g);
        }
    }

    public boolean blobInput(BlobTransfer blob) {
        if (selectTile((int) blob.getPosition().getX(), (int) blob.getPosition().getY())) {
            return true;
        }
        if (placeTile((int) blob.getPosition().getX(), (int) blob.getPosition().getY())) {
            return true;
        }
        return false;
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        setButtonPosition();
        containerX = uiContainer.getContainerX();
        containerY = uiContainer.getContainerY();
    }


    /**********************************
     * METHODS
     **********************************/

    /**
     * Places tiles on the map. If it is a single tile selected then it is immediately placed.
     * If it is a group of tiles (doesn't make sense to just place one part of a single group,
     * and it is too tedious for the user to do so) is places the tiles with a grass fire algorithm
     * as long as the position where will be within the scope of the map matrix.
     */
    public boolean placeTile(int x, int y) {
        //IF IT IS A SINGLE TILE, NO WORRIES JUST PLACE THAT SUCKER ON THE MAP
        int x1 = (int) mapContainer.getMapPos(x, y).getX();
        int y1 = (int) mapContainer.getMapPos(x, y).getY();
        if (x1 > -1 && y1 > -1)
            if (!groupTileSelected) {
                mapContainer.getCurrentMap().getMap()[x1][y1] = selectedTileID;
            } else {
                tileSelector(tileBank.returnedMultiArr());
                //CHECK IF THE SELECTED TILE GROUP IS PLACED WITHIN THE GRID OF THE MAP
                if (y1 < mapContainer.getCurrentMap().getMap()[0].length - tileStore.get(0).length + 1
                        && x1 < mapContainer.getCurrentMap().getMap().length - tileStore.get(0)[0].length + 1) {
                    for (int i = 0; i < tileStore.get(0).length; i++) {
                        for (int j = 0; j < tileStore.get(0)[0].length; j++) {
                            mapContainer.getCurrentMap().getMap()[j + x1][i + y1] = tileStore.get(0)[i][j];
                        }
                    }
                    return true;
                }
            }
        return false;
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

    public void setButtonPosition() {

        int startXinContainer = 16;
        int startYinContainer = 32;

        for (int i = 0; i < 6; i++) {
            mapButtons[i].setOffsetX(startXinContainer + i * 31);
            mapButtons[i].setOffsetY(startYinContainer);
        }
        for (int i = 0; i < 6; i++) {
            mapButtons[i + 6].setOffsetX(startXinContainer + i * 31);
            mapButtons[i + 6].setOffsetY(startYinContainer + 29);
        }
        for (int i = 0; i < 6; i++) {
            mapButtons[i + 12].setOffsetX(startXinContainer + i * 31);
            mapButtons[i + 12].setOffsetY(startYinContainer + 29 * 2);
        }
        for (int i = 0; i < 6; i++) {
            mapButtons[i + 18].setOffsetX(startXinContainer + i * 31);
            mapButtons[i + 18].setOffsetY(startYinContainer + 29 * 3);
        }
        for (int i = 0; i < 3; i++) {
            mapButtons[i + 24].setOffsetX(startXinContainer + i * 31);
            mapButtons[i + 24].setOffsetY(startYinContainer + 29 * 4);
        }
        for (int i = 0; i < 4; i++) {
            mapButtons[i + 27].setOffsetX(startXinContainer + i * 46);
            mapButtons[i + 27].setOffsetY(startYinContainer + 163);
        }
        mapButtons[31].setOffsetX(startXinContainer);
        mapButtons[31].setOffsetY(startYinContainer + 163 + 69);
        for (int i = 0; i < 2; i++) {
            mapButtons[i + 32].setOffsetX(startXinContainer + 46 + i * 69);
            mapButtons[i + 32].setOffsetY(startYinContainer + 163 + 69);
        }
        mapButtons[34].setOffsetX(startXinContainer);
        mapButtons[34].setOffsetY(startYinContainer + 278);
        for (int i = 0; i < 2; i++) {
            mapButtons[i + 35].setOffsetX(startXinContainer + 46 + i * 69);
            mapButtons[i + 35].setOffsetY(startYinContainer + 278);
        }

        mapButtons[37].setOffsetX(startXinContainer);
        mapButtons[37].setOffsetY(startYinContainer + 325);

        mapButtons[38].setOffsetX(startXinContainer + 46);
        mapButtons[38].setOffsetY(startYinContainer + 325);

        mapButtons[39].setOffsetX(startXinContainer + 138);
        mapButtons[39].setOffsetY(startYinContainer + 325);

        mapButtons[40].setOffsetX(startXinContainer);
        mapButtons[40].setOffsetY(startYinContainer + 372);

        mapButtons[41].setOffsetX(startXinContainer + 46);
        mapButtons[41].setOffsetY(startYinContainer + 372);

        mapButtons[42].setOffsetX(startXinContainer + 114);
        mapButtons[42].setOffsetY(startYinContainer + 372);

        mapButtons[43].setOffsetX(startXinContainer);
        mapButtons[43].setOffsetY(startYinContainer + 420);

        for (int i = 0; i < 6; i++) {
            mapButtons[i + 44].setOffsetX(startXinContainer + i * 31);
            mapButtons[i + 44].setOffsetY(startYinContainer + 504);
        }
        for (int i = 0; i < 4; i++) {
            mapButtons[i + 50].setOffsetX(startXinContainer + i * 31);
            mapButtons[i + 50].setOffsetY(startYinContainer + 533);
        }
    }

    public boolean selectTile(int x, int y) {
        for (MapButton mapButton : mapButtons) {
            if (mapButton.returnConfines(x, y)) {
                groupTileSelected = tileBank.singleOrGroupTiles();
                selectedTileID = mapButton.getTileNo();
                tileBank.setSelectedID(selectedTileID);
                groupTileSelected = tileBank.singleOrGroupTiles();
                return true;
            }
        }
        return false;
    }
}
