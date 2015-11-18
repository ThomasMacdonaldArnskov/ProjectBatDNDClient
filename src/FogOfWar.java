import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.*;

public class FogOfWar extends BasicGame {

    private static final int WIDTH = 32;
    private static final int HEIGHT = 24;

    private boolean lightingOn = true;
    //private boolean colouredLights = true;

    private SpriteSheet tiles;
    private int[][] map = new int[WIDTH][HEIGHT];

    private float[][][] lightValue = new float[WIDTH+1][HEIGHT+1][3];
    private List<LightSource> lights = new ArrayList();

    private LightSource player1Light;
    private LightSource player2Light;
    private float lightStrength = 4f;

    private float player1X = 2f;
    private float player1Y = 2f;
    private float player2X = 28f;
    private float player2Y = 2f;


    public FogOfWar() {
        super("Fog Of War");
    }


    public void init(GameContainer container) throws SlickException {
        tiles = new SpriteSheet("assets/tiles.png", 32,32);
        generateMap();
    }


    private void generateMap() {

        for (int y=0;y<HEIGHT;y++) {
            for (int x=0;x<WIDTH;x++) {
                map[x][y] = 0;

                if (Math.random() > 0.4) {
                    map[x][y] = 1 + (int) (Math.random() * 1);
                }
            }
        }

        lights.clear();

        player1Light = new LightSource(player1X,player1Y,lightStrength,Color.lightGray);
        player2Light = new LightSource(player2X,player2Y,lightStrength,Color.lightGray);

        lights.add(player1Light);
        lights.add(player2Light);

        updateLightMap();
    }

    private void updateLightMap() {

        for (int y=0;y<HEIGHT+1;y++) {
            for (int x=0;x<WIDTH+1;x++) {

                //START BY RESETTING THE VALUES TO 0
                for (int component=0;component<3;component++) {
                    lightValue[x][y][component] = 0;
                }

                //THEN ADD THE EFFECT
                for (int i=0;i<lights.size();i++) {
                    float[] effect = (lights.get(i)).getLightEffectAtLocation(x, y);
                    for (int component=0;component<3;component++) {
                        lightValue[x][y][component] += effect[component];
                    }
                }

                //SINCE THE VALUES SHOULD BE A FLOAT BETWEEN 0 AND 1, MAKE SURE THAT NO VALUE EXCEEDS 1
                for (int component=0;component<3;component++) {
                    if (lightValue[x][y][component] > 1) {
                        lightValue[x][y][component] = 1;
                    }
                }
            }
        }
    }

    public void update(GameContainer container, int delta) throws SlickException {

        if (container.getInput().isKeyPressed(Input.KEY_L)){
            lightingOn = !lightingOn;
        }
        if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
            lights.add(new LightSource(player2X,player2Y,lightStrength,Color.darkGray));
            player2X -= 0.05f;
            player2Light.setLightLocation(player2X, player2Y);
            updateLightMap();
        }
        if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
            lights.add(new LightSource(player2X,player2Y,lightStrength,Color.darkGray));
            player2X += 0.05f;
            player2Light.setLightLocation(player2X, player2Y);
            updateLightMap();
        }
        if (container.getInput().isKeyDown(Input.KEY_UP)) {
            lights.add(new LightSource(player2X,player2Y,lightStrength,Color.darkGray));
            player2Y -= 0.05f;
            player2Light.setLightLocation(player2X, player2Y);
            updateLightMap();
        }
        if (container.getInput().isKeyDown(Input.KEY_DOWN)) {
            lights.add(new LightSource(player2X,player2Y,lightStrength,Color.darkGray));
            player2Y += 0.05f;
            player2Light.setLightLocation(player2X, player2Y);
            updateLightMap();
        }
        if (container.getInput().isKeyDown(Input.KEY_A)) {
            lights.add(new LightSource(player1X,player1Y,lightStrength,Color.darkGray));
            player1X -= 0.05f;
            player1Light.setLightLocation(player1X, player1Y);
            updateLightMap();
        }
        if (container.getInput().isKeyDown(Input.KEY_D)) {
            lights.add(new LightSource(player1X,player1Y,lightStrength,Color.darkGray));
            player1X += 0.05f;
            player1Light.setLightLocation(player1X, player1Y);
            updateLightMap();
        }
        if (container.getInput().isKeyDown(Input.KEY_W)) {
            lights.add(new LightSource(player1X,player1Y,lightStrength,Color.darkGray));
            player1Y -= 0.05f;
            player1Light.setLightLocation(player1X, player1Y);
            updateLightMap();
        }
        if (container.getInput().isKeyDown(Input.KEY_S)) {
            lights.add(new LightSource(player1X,player1Y,lightStrength,Color.darkGray));
            player1Y += 0.05f;
            player1Light.setLightLocation(player1X, player1Y);
            updateLightMap();
        }
    }

    public void render(GameContainer container, Graphics g) throws SlickException {

        tiles.startUse();

        for (int y=0;y<HEIGHT;y++) {
            for (int x=0;x<WIDTH;x++) {
                int tile = map[x][y];
                Image image = tiles.getSubImage(tile % 4, tile / 4);

                if (lightingOn) {
                    image.setColor(Image.TOP_LEFT, lightValue[x][y][0], lightValue[x][y][1], lightValue[x][y][2], 1);
                    image.setColor(Image.TOP_RIGHT, lightValue[x+1][y][0], lightValue[x+1][y][1], lightValue[x+1][y][2], 1);
                    image.setColor(Image.BOTTOM_RIGHT, lightValue[x+1][y+1][0], lightValue[x+1][y+1][1], lightValue[x+1][y+1][2], 1);
                    image.setColor(Image.BOTTOM_LEFT, lightValue[x][y+1][0], lightValue[x][y+1][1], lightValue[x][y+1][2], 1);
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


    }
}
