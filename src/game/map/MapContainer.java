package game.map;

import game.gui.AdminInterface;
import game.objects.Button;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import utils.AreaPulse;
import utils.GraphicsMethods;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class MapContainer {
    private SpriteSheet tiles;
    private int xPos, yPos;
    private ArrayList<Map> maps;
    private int currentMap;
    private Font font = GraphicsMethods.getFont(72);
    private Button up;
    private Button down;
    private Button saveMap;
    private int folderpos = 0;
    private AreaPulse startGame;
    private boolean gameSTART = false;

    public void setXY(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public MapContainer(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        currentMap = 0;
        maps = Map.loadMaps();
        if (maps.isEmpty()) {
            maps.add(new Map());
        }
        try {
            tiles = new SpriteSheet("assets/tiles2.png", 32, 32);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        up = new Button(new java.awt.Point(xPos, yPos), 45, 45, "UP");
        down = new Button(new java.awt.Point(xPos, yPos), 45, 45, "DOWN");
        saveMap = new Button(new java.awt.Point(xPos, yPos), 45, 45, "SAVE");
        startGame = new AreaPulse("Place Hero To Start", new java.awt.Point(412, 243), 30);
    }


    public Point getMapPos(int mouseX, int mouseY) {
        int x = (mouseX - xPos) / BattleMap.SPRITESIZE < BattleMap.WIDTH ?
                ((mouseX - xPos) / BattleMap.SPRITESIZE) : -1;
        int y = (mouseY - yPos) / BattleMap.SPRITESIZE < BattleMap.HEIGHT ?
                ((mouseY - yPos) / BattleMap.SPRITESIZE) : -1;

        return new Point(x, y);
    }

    public boolean getTileBool(int mouseX, int mouseY) {
        int x = (mouseX - xPos) / BattleMap.SPRITESIZE;
        int y = (mouseY - yPos) / BattleMap.SPRITESIZE;
        return !(x > 0 && x < BattleMap.WIDTH && y > 0 && y < BattleMap.HEIGHT) ||
                (getCurrentMap().getMap()[x][y] == 112) ||
                (getCurrentMap().getMap()[x][y] == 92) ||
                (getCurrentMap().getMap()[x][y] == 93) ||
                (getCurrentMap().getMap()[x][y] == 104) ||
                (getCurrentMap().getMap()[x][y] == 105) ||
                (getCurrentMap().getMap()[x][y] == 113) ||
                (getCurrentMap().getMap()[x][y] == 114) ||
                (getCurrentMap().getMap()[x][y] == 115) ||
                (getCurrentMap().getMap()[x][y] == 116) ||
                (getCurrentMap().getMap()[x][y] == 117) ||
                (getCurrentMap().getMap()[x][y] == 124) ||
                (getCurrentMap().getMap()[x][y] == 125) ||
                (getCurrentMap().getMap()[x][y] == 126) ||
                (getCurrentMap().getMap()[x][y] == 127) ||
                (getCurrentMap().getMap()[x][y] == 128) ||
                (getCurrentMap().getMap()[x][y] == 129) ||
                (getCurrentMap().getMap()[x][y] == 136) ||
                (getCurrentMap().getMap()[x][y] == 137) ||
                (getCurrentMap().getMap()[x][y] == 138) ||
                (getCurrentMap().getMap()[x][y] == 139) ||
                (getCurrentMap().getMap()[x][y] == 140) ||
                (getCurrentMap().getMap()[x][y] == 141) ||
                (getCurrentMap().getMap()[x][y] == 142) ||
                (getCurrentMap().getMap()[x][y] == 108);
    }

    public int getTileID(int mouseX, int mouseY) {

        int x = (mouseX - xPos) / BattleMap.SPRITESIZE < BattleMap.WIDTH ?
                ((mouseX - xPos) / BattleMap.SPRITESIZE) : -1;
        int y = (mouseY - yPos) / BattleMap.SPRITESIZE < BattleMap.HEIGHT ?
                ((mouseY - yPos) / BattleMap.SPRITESIZE) : -1;
        if (x >= 0 && y >= 0)
            return getCurrentMap().getMap()[x][y];
        else return -1;
    }

    public void nextMap() {
        currentMap++;
    }

    public void replay() {
        currentMap = getRandom(new int[]{
                0, 2
        });
        if (currentMap == 0) {
            startGame.setCenterPosition(new java.awt.Point(412, 243));
        }
        if (currentMap == 2) {
            startGame.setCenterPosition(new java.awt.Point(325, 479));
        }
        gameSTART = false;
    }

    public static int getRandom(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public Point getPosition(int mouseX, int mouseY) {
        int x1 = (mouseX - xPos) / BattleMap.SPRITESIZE < BattleMap.WIDTH ?
                ((mouseX - xPos) / BattleMap.SPRITESIZE) : -1;
        int y1 = (mouseY - yPos) / BattleMap.SPRITESIZE < BattleMap.HEIGHT ?
                ((mouseY - yPos) / BattleMap.SPRITESIZE) : -1;

        if (x1 == -1 || y1 == -1) return new Point(-1, -1);
        int x = xPos + x1 * BattleMap.SPRITESIZE + BattleMap.SPRITESIZE / 2;
        int y = yPos + y1 * BattleMap.SPRITESIZE + BattleMap.SPRITESIZE / 2;

        return new Point(x, y);
    }

    public void addMap(Map map) {
        maps.add(map);
    }

    public int getxPos() {
        return xPos;
    }

    public Map getCurrentMap() {
        return maps.get(currentMap);
    }

    public int getyPos() {
        return yPos;
    }

    public ArrayList<Map> getMaps() {
        return maps;
    }

    public void renderMap(int size, int[][] map, int x, int y) throws SlickException {
        tiles.startUse();
        for (int i = 0; i < BattleMap.HEIGHT; i++) {
            for (int j = 0; j < BattleMap.WIDTH; j++) {
                int tile = map[j][i];
                Image image = tiles.getSubImage(tile % BattleMap.SPRITESHEET, tile / BattleMap.SPRITESHEET);
                image.drawEmbedded(x + j * size, y + i * size, size, size);
            }
        }
        tiles.endUse();

    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        if (!maps.isEmpty() && maps.get(currentMap) != null) {
            tiles.startUse();
            for (int i = 0; i < BattleMap.HEIGHT; i++) {
                for (int j = 0; j < BattleMap.WIDTH; j++) {
                    int tile = getCurrentMap().getMap()[j][i];
                    Image image = tiles.getSubImage(tile % BattleMap.SPRITESHEET, tile / BattleMap.SPRITESHEET);
                    image.drawEmbedded(xPos + j * BattleMap.SPRITESIZE, yPos + i * BattleMap.SPRITESIZE, BattleMap.SPRITESIZE, BattleMap.SPRITESIZE);
                }
            }
            tiles.endUse();
        }
        g.resetTransform();
    }

    public void renderStarter(Graphics g) throws SlickException {
        if (!gameSTART) {
            startGame.setActive(true);
            startGame.setColor(new Color(255, 255, 255, 255));
            startGame.render(g);

        }
    }

    public boolean canStartGame() {
        return gameSTART;
    }

    public AreaPulse getStarter() {
        return startGame;
    }

    public void setStartGame(boolean bo) {
        gameSTART = bo;
    }

    int size;

    int x1, y1;
    int currentI;

    public void renderMapSelector(int x1, int y1, Graphics g) throws SlickException {
        this.x1 = up.getWidth() + x1 + 15;
        this.y1 = y1;
        if (!maps.isEmpty()) {
            size = 7;
            up.setPosition(new java.awt.Point(x1, y1));
            down.setPosition(new java.awt.Point(x1, y1 + 10 + up.getHeight()));
            saveMap.setPosition(new java.awt.Point(x1, (y1 + 20 + up.getHeight() + down.getHeight())));
            int x = up.getWidth() + x1 + 15;
            int y = y1;

            up.render(g);
            down.render(g);
            saveMap.render(g);

            g.setLineWidth(3);
            g.setColor(Color.gray);
            g.setFont(font);

            for (int i = folderpos; i < 4 + folderpos; i++) {
                g.drawRect(x + (size * BattleMap.WIDTH + 20) * (i - folderpos) - 5, y - 5,
                        (size * BattleMap.WIDTH + 13), (size * BattleMap.HEIGHT + 13));
                if (i < 3 + folderpos) {
                    if (maps.size() > i && maps.get(i) != null)
                        renderMap(size, maps.get(i).getMap(),
                                x + ((size * BattleMap.WIDTH + 20) * (i - folderpos)), y);
                    else {
                        g.drawString("+",
                                x + ((size * BattleMap.WIDTH + 20) * (i - folderpos)) +
                                        (size * BattleMap.WIDTH) / 2 - g.getFont().getWidth("+") / 2,
                                y + (size * BattleMap.HEIGHT) / 2 - g.getFont().getHeight("+") / 2);
                        currentI = i + 1;
                        break;
                    }
                } else {
                    g.drawString("+", x + ((size * BattleMap.WIDTH + 20) * (i - folderpos)) + (size * BattleMap.WIDTH) / 2 - g.getFont().getWidth("+") / 2,
                            y + (size * BattleMap.HEIGHT) / 2 - g.getFont().getHeight("+") / 2);
                    currentI = i + 1;
                }
            }

        }
    }

    public boolean subHit(int x, int y) {
        if (up.isPressed(new java.awt.Point(x, y))) {
            folderpos += 3;
            return true;
        }
        if (down.isPressed(new java.awt.Point(x, y))) {
            if (folderpos - 3 >= 0)
                folderpos -= 3;
            else folderpos = 0;
            return true;
        }
        if (saveMap.isPressed(new java.awt.Point(x, y))) {
            save();
            return true;
        }
        for (int i = folderpos; i < currentI; i++) {
            if (Button.basicRectHitTest(x1 + (size * BattleMap.WIDTH + 20) * (i - folderpos) - 5, y1 - 5,
                    (size * BattleMap.WIDTH + 13), (size * BattleMap.HEIGHT + 13), x, y)) {
                if (i < 3 + folderpos)
                    if (maps.size() > i && maps.get(i) != null) {
                        currentMap = i;
                    } else {
                        maps.add(new Map());
                        currentMap = maps.size() - 1;
                    }
                else {
                    maps.add(new Map());
                    currentMap = maps.size() - 1;
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    public void save() {
        for (Map map : maps) {
            if (map.isSaved()) {
                map.saveMap();
            } else {
                Map.saveMap(map.getMap());
            }
        }
        maps = Map.loadMaps();
    }
}
