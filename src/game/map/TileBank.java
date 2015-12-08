package game.map;

/**
 * The Tile Bank. Handles the parts of the sprite sheet that is grouped objects and which parts are not.
 * That way we don't need to hardcode it anywhere else. Furthermore it stores which id corresponds to which matrix size
 * so that we can grass fire to our hearts content.
 * Created by TMA on 05-12-2015.
 */
public class TileBank {

    /**********************************
     * VARIABLES
     **********************************/

    private int selectedID;


    /**********************************
     * CONSTRUCTOR
     **********************************/

    public TileBank() {
    }


    /**********************************
     * METHODS
     **********************************/


    /**
     * Checks if it is a single tile selected or a group of tiles.
     * @return : boolean : true for group, false for single tile.
     */
    public boolean singleOrGroupTiles() {

        boolean multiTileSelected;

        if (       selectedID == 1 || selectedID == 10 || selectedID == 32 || selectedID == 56
                || selectedID == 24 || selectedID == 26 || selectedID == 28 || selectedID == 30
                || selectedID == 7 || selectedID == 62 || selectedID == 65 || selectedID == 86 || selectedID == 89
                || selectedID == 60 || selectedID == 3 || selectedID == 108 || selectedID == 34
           )
            multiTileSelected = true;
         else
            multiTileSelected = false;

        return multiTileSelected;
    }

    /**
     * Returns an empty array with the dimensions of the group so that it can be filled with the correct tiles
     * in the group
     * @return : int[][] : The dimensions of the array corresponds to the tile group size
     */
    public int[][] returnedMultiArr() {

        int[][] returnedTileGroup;

        //2X2 TILES
        if (selectedID == 1 || selectedID == 10 || selectedID == 32 || selectedID == 56)
            returnedTileGroup = new int[2][2];

        //2X3 TILES
        else if (selectedID == 24 || selectedID == 26 || selectedID == 28 || selectedID == 30)
            returnedTileGroup = new int[2][3];

        //2X4 TILES
        else if (selectedID == 60)
            returnedTileGroup = new int [2][4];

        //3X2 TILES
        else if (selectedID == 7 || selectedID == 62 || selectedID == 65 || selectedID == 86 || selectedID == 89)
            returnedTileGroup = new int[3][2];

        //4X2 TILES
        else if (selectedID == 3)
            returnedTileGroup = new int[4][2];

        else if (selectedID == 34)
            returnedTileGroup = new int[1][2];

        //4X3 TILES
        else
            returnedTileGroup = new int [4][3];

        return returnedTileGroup;
    }


    /**********************************
     * GETTERS
     **********************************/


    /**********************************
     * SETTERS
     **********************************/

    public void setSelectedID(int id) {
        this.selectedID = id;
    }
}
