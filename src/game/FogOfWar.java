package game;

import net.ClientChannelHandler;
import org.lwjgl.Sys;
import org.newdawn.slick.*;

import java.util.ArrayList;
import java.util.List;

public class FogOfWar extends BasicGame {

    private boolean lightingOn = true;

    private SpriteSheet tiles;
    private int[][] map = new int[BattleMap.WIDTH][BattleMap.HEIGHT];

    private float[][][] lightValue = new float[BattleMap.WIDTH + 1][BattleMap.HEIGHT + 1][3];
    private List<LightSource> lights = new ArrayList<>();

    private LightSource fiducialLight;
    private LightSource testLight;

    private float lightStrength = 4.9f;

    private int testLightX = 700;
    private int testLightY = 500;

    public FogOfWar() {
        super("Fog Of War");
    }


    public void init(GameContainer container) throws SlickException {
        tiles = new SpriteSheet("assets/tiles.png", 32, 32);
        generateMap();
    }


    private void generateMap() {

        for (int y = 0; y < BattleMap.HEIGHT; y++) {
            for (int x = 0; x < BattleMap.WIDTH; x++) {
                map[x][y] = 0;
            }
        }

        lights.clear();

        //SO THAT IT DRAWS THE LIGHT SOURCE ON THE FIDUCIAL POSITION!
        fiducialLight = new LightSource((int) ClientChannelHandler.character.getFiducial().getPosition().getX(), (int) ClientChannelHandler.character.getFiducial().getPosition().getY(), lightStrength, Color.lightGray);
        lights.add(fiducialLight);

        testLight = new LightSource(testLightX,testLightY,lightStrength,Color.white);
        lights.add(testLight);

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
                    float[] effect = (lights.get(i)).getLightEffectAtLocation(x, y);
                    for (int component = 0; component < 3; component++) {
                        lightValue[x][y][component] += effect[component];
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

        fiducialLight.setLightLocation((int) ClientChannelHandler.character.getFiducial().getPosition().getX(),(int) ClientChannelHandler.character.getFiducial().getPosition().getY());

        if (container.getInput().isKeyPressed(Input.KEY_L)) {
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
        }
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        tiles.startUse();

        for (int y = 0; y < BattleMap.HEIGHT; y++) {
            for (int x = 0; x < BattleMap.WIDTH; x++) {
                int tile = map[x][y];
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
                image.drawEmbedded(x * 32, y * 32, 32, 32);
            }
        }
        tiles.endUse();
        if (ClientChannelHandler.character.getFiducial().isActive())
            g.drawOval((int) ClientChannelHandler.character.getFiducial().getPosition().getX(),
                    (int) ClientChannelHandler.character.getFiducial().getPosition().getY(), 30, 30);
    }

    public void setMap(int [][] map) {
        this.map = map;
    }
}
