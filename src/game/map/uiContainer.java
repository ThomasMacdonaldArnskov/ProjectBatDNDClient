package game.map;

import org.newdawn.slick.*;

/**
 *
 * Created by TMA on 06-12-2015.
 */
public class uiContainer {

    int xPos;
    int yPos;

    private Image uiContainer;

    public uiContainer(int initialX, int initialY) {
        this.xPos = initialX;
        this.yPos = initialY;
        try {
            uiContainer = new Image("assets/gui/tileselectormenu.png");
        } catch (SlickException slick) {
            slick.printStackTrace();
        }

    }

    public void UIContainer(Graphics g) {
      g.drawImage(uiContainer,xPos, yPos);
    }

    public void setDragPosition(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    public boolean confinesOfContainer (int x, int y) {
        return (x > xPos && x < xPos + uiContainer.getWidth() && y > yPos && y < yPos + uiContainer.getHeight());
    }

    public int getContainerX () {
        return xPos;
    }
    public int getContainerY() {
        return yPos;
    }
}


