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
        boolean nonMovable = AdminInterface.adminInterface.getBattleMap().getMapContainer().getTileBool(
                (int) cs.getFiducial().getPosition().getX(),
                (int) cs.getFiducial().getPosition().getY());

        if ((int) point.getX() == -1 || (int) point.getY() == -1) lightSource.setEnabled(false);
        else lightSource.setEnabled(true);

        int tileID = AdminInterface.adminInterface.getBattleMap().getMapContainer().getTileID(
                (int) cs.getFiducial().getPosition().getX(),
                (int) cs.getFiducial().getPosition().getY());
        if (!nonMovable) {
            if (AdminInterface.adminInterface.getBattleMap().getMapContainer().canStartGame()) {
                if (tileID == 81) {
                    AdminInterface.adminInterface.getBattleMap().getMapContainer().nextMap();
                }
                if (tileID == 65 || tileID == 66 || tileID == 67 ||
                        tileID == 77 || tileID == 78 || tileID == 79) {
                    AdminInterface.adminInterface.getBattleMap().getMapContainer().nextMap();
                }
                if (tileID == 106) {
                    AdminInterface.adminInterface.setWinLoose(AdminInterface.LOST);
                    return;
                }
                if (tileID == 107) {
                    AdminInterface.adminInterface.setWinLoose(AdminInterface.WON);
                    return;
                }
            }

            this.xPos = (int) point.getX();
            this.yPos = (int) point.getY();
            lightSource.setLightLocation(
                    xPos - (lightSource.get_lightStrength() * BattleMap.SPRITESIZE) + BattleMap.SPRITESIZE / 2,
                    yPos - (lightSource.get_lightStrength() * BattleMap.SPRITESIZE) + BattleMap.SPRITESIZE / 2);
        }


    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        g.setColor(Color.white);
        g.fillRect(xPos - BattleMap.SPRITESIZE / 2, yPos - BattleMap.SPRITESIZE / 2,
                BattleMap.SPRITESIZE, BattleMap.SPRITESIZE);
    }
}
