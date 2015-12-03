package game;

import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.tiled.TiledMap;

import java.awt.*;

public class BattleMap extends BasicGame {

    Image img, white, black;
    Image[][] map;
    TiledMap battleMap;


    public BattleMap(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {

        white = new Image("assets/white.png");
        black = new Image("assets/black.png");

        //map = new Image[][]{ {white}, {white}};

        map = new Image[32][24];

        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 24; j++) {
                map[i][j] = new Image("assets/white.png");
            }
        }

        img = new Image("assets/1024.png");
        battleMap = new TiledMap("assets/dungeonmap.tmx");

    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        //battleMap.render(0,0);
        Input input = gc.getInput();
        int mouseX = input.getMouseX();
        int mouseY = input.getMouseY();
        if (mouseX > 0 && mouseX < 32 && mouseY > 0 && mouseY < 32) {
            map[0][0] = black;
        }
        Color alphaComponent;
        alphaComponent = new Color(0, 0, 0, 128);

        g.setColor(alphaComponent);

        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 24; j++) {
                g.drawImage(map[i][j], i * 32, j * 32);
            }
        }

    }
}
