package game;

import org.newdawn.slick.*;

public class Player extends BasicGame {

    int xPos;
    int yPos;
    private LightSource lightSource;

    public Player(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {

        org.newdawn.slick.Input input = gc.getInput();
        xPos = input.getMouseX();
        yPos = input.getMouseY();
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        g.setColor(Color.white);
        g.fillRect(xPos, yPos, 32, 32);
    }
}
