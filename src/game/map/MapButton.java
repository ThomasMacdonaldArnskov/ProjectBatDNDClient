package game.map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by TMA on 06-12-2015.
 */
public class MapButton {

    game.map.uiContainer uiContainer;
    private Image button;
    private int tileNo;
    private int xPos = 768 + 16;
    private int yPos = 32 + 32;


    public MapButton(int tileNo) {
        try {
            button = new Image("assets/gui/tileUI/tile" + tileNo + ".png");
        } catch (SlickException slick) {
            slick.printStackTrace();
        }
        this.tileNo = tileNo;
    }

    public void display(Graphics g) {
        g.drawImage(button, xPos, yPos);
    }

    public boolean returnConfines(int x, int y) {
        return (x > xPos && x < xPos + button.getWidth() && y > yPos && y < yPos + button.getHeight());
    }

    public int getWidth() {
        return button.getWidth();
    }

    public int getHeight() {
        return button.getHeight();
    }

    public int getTileNo() {
        return tileNo;
    }

    public void setOffsetX(int xPos) {
        this.xPos = uiContainer.getContainerX() + xPos;
    }

    public void setOffsetY(int yPos) {
        this.yPos = uiContainer.getContainerY() + yPos;
    }

    public void setPos(int x, int y) {
        this.xPos = uiContainer.getContainerX() + x;
        this.yPos = uiContainer.getContainerY() + y;
    }

    public void setUiContainer(game.map.uiContainer uiContainer) {
        this.uiContainer = uiContainer;
    }
}
