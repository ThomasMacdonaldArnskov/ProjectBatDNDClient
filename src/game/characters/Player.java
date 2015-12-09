package game.characters;

import game.LightSource;
import game.gui.AdminInterface;
import game.map.BattleMap;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Point;

public class Player extends BasicGame {

    int xPos;
    int yPos;
    private LightSource lightSource;
    private CharacterSheet cs;

    public Player(String title, CharacterSheet cs) {
        super(title);
        this.cs = cs;
        this.xPos = (int) cs.getFiducial().getPosition().getX();
        this.yPos = (int) cs.getFiducial().getPosition().getY();
        this.lightSource = new LightSource(xPos, yPos, 5f, Color.white);
        AdminInterface.adminInterface.getBattleMap().lightInTheDarkness(lightSource);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        Point point = AdminInterface.adminInterface.
                getBattleMap().getMapContainer().getPosition(
                (int) cs.getFiducial().getPosition().getX(),
                (int) cs.getFiducial().getPosition().getY());
        this.xPos = (int) point.getX();
        this.yPos = (int) point.getY();
        if (xPos == -1 || yPos == -1) lightSource.setEnabled(false);
        else lightSource.setEnabled(true);
        lightSource.setLightLocation(xPos, yPos);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        g.setColor(Color.white);
        g.fillRect(xPos - BattleMap.SPRITESIZE / 2, yPos - BattleMap.SPRITESIZE / 2,
                BattleMap.SPRITESIZE, BattleMap.SPRITESIZE);
    }
}
