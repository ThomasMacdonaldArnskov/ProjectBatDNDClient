package game;

import game.map.BattleMap;
import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.List;

public class FogOfWar extends BasicGame {

    private boolean lightingOn = true;

    private SpriteSheet tiles;

    private float[][][] lightValue = new float[BattleMap.WIDTH + 1][BattleMap.HEIGHT + 1][3];
    private List<LightSource> lights = new ArrayList<>();

    private BattleMap bm;
    private int xPos;
    private int yPos;

    public FogOfWar(BattleMap bm) {
        super("Fog Of War");
        this.bm = bm;
    }


    public void init(GameContainer container) throws SlickException {
        tiles = new SpriteSheet("assets/tiles.png", 32, 32);
        generateMap();
    }


    public void addLightSource(LightSource ls) {
        if (!lights.contains(ls)) {
            lights.add(ls);
        }
    }

    public void removeLightSource(LightSource ls) {
        if (lights.contains(ls)) lights.remove(ls);
    }

    private void generateMap() {
        lights.clear();
        updateLightMap();
    }

    private void updateLightMap() {
        for (int y = 0; y < BattleMap.HEIGHT + 1; y++) {
            for (int x = 0; x < BattleMap.WIDTH + 1; x++) {

                //START BY RESETTING THE VALUES TO 0
                for (int component = 0; component < 3; component++) {
                    lightValue[x][y][component] = 0;
                }

                //THEN ADD THE EFFECT
                for (int i = 0; i < lights.size(); i++) {
                    if ((lights.get(i)).isEnabled()) {
                        float[] effect = (lights.get(i)).getLightEffectAtLocation(x, y);
                        for (int component = 0; component < 3; component++) {
                            lightValue[x][y][component] += effect[component];
                        }
                    }
                }

                //SINCE THE VALUES SHOULD BE A FLOAT BETWEEN 0 AND 1, MAKE SURE THAT NO VALUE EXCEEDS 1
                for (int component = 0; component < 3; component++) {
                    if (lightValue[x][y][component] > 1) {
                        lightValue[x][y][component] = 1;
                    }
                }
            }
        }
    }

    public void update(GameContainer container, int delta) throws SlickException {
        /*if (container.getInput().isKeyPressed(Input.KEY_L)) {
            lightingOn = !lightingOn;
        }
        if (container.getInput().isKeyDown(Input.KEY_A)) {
            //lights.add(new LightSource(player1X, player1Y, lightStrength, Color.darkGray));
            testLightX -= 1;
            testLight.setLightLocation(testLightX, testLightY);
            updateLightMap();
        }
        if (container.getInput().isKeyDown(Input.KEY_D)) {
            //lights.add(new LightSource(player1X, player1Y, lightStrength, Color.darkGray));
            testLightX += 1;
            testLight.setLightLocation(testLightX, testLightY);
            updateLightMap();
        }
        if (container.getInput().isKeyDown(Input.KEY_W)) {
            //lights.add(new LightSource(player1X, player1Y, lightStrength, Color.darkGray));
            testLightY -= 1;
            testLight.setLightLocation(testLightX, testLightY);
            updateLightMap();
        }
        if (container.getInput().isKeyDown(Input.KEY_S)) {
            //lights.add(new LightSource(player1X, player1Y, lightStrength, Color.darkGray));
            testLightY += 1;
            testLight.setLightLocation(testLightX, testLightY);
            updateLightMap();
        }*/
        //fiducialLight.setLightLocation((int) ClientChannelHandler.character.getFiducial().getPosition().getX(),(int) ClientChannelHandler.character.getFiducial().getPosition().getY());
        this.xPos = this.bm.getMapContainer().getxPos();
        this.yPos = this.bm.getMapContainer().getyPos();
        updateLightMap();

    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        tiles.startUse();

        for (int y = 0; y < BattleMap.HEIGHT; y++) {
            for (int x = 0; x < BattleMap.WIDTH; x++) {
                int tile = bm.getMapContainer().getCurrentMap().getMap()[x][y];
                Image image = tiles.getSubImage(tile % BattleMap.SPRITESHEET, tile / BattleMap.SPRITESHEET);

                if (lightingOn) {
                    image.setColor(Image.TOP_LEFT, lightValue[x][y][0], lightValue[x][y][1], lightValue[x][y][2], 1);
                    image.setColor(Image.TOP_RIGHT, lightValue[x + 1][y][0], lightValue[x + 1][y][1], lightValue[x + 1][y][2], 1);
                    image.setColor(Image.BOTTOM_RIGHT, lightValue[x + 1][y + 1][0], lightValue[x + 1][y + 1][1], lightValue[x + 1][y + 1][2], 1);
                    image.setColor(Image.BOTTOM_LEFT, lightValue[x][y + 1][0], lightValue[x][y + 1][1], lightValue[x][y + 1][2], 1);
                } else {
                    float light = 1;
                    image.setColor(Image.TOP_LEFT, light, light, light, 1);
                    image.setColor(Image.TOP_RIGHT, light, light, light, 1);
                    image.setColor(Image.BOTTOM_RIGHT, light, light, light, 1);
                    image.setColor(Image.BOTTOM_LEFT, light, light, light, 1);
                }
                image.drawEmbedded(xPos + x * BattleMap.SPRITESIZE, yPos + y * BattleMap.SPRITESIZE,
                        BattleMap.SPRITESIZE, BattleMap.SPRITESIZE);
            }
        }
        tiles.endUse();
    }
}
