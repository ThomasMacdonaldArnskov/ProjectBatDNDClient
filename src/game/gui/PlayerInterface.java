package game.gui;

import commons.transfer.objects.FiducialTransfer;
import game.characters.*;
import net.ClientChannelHandler;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Color;
import utils.AreaPulse;
import utils.StateMachine;


import java.awt.*;

public class PlayerInterface extends StateMachine {

    private boolean myTurn = false;
    private int state = 0;

    private Point interfacePosition;
    private CharacterSheet character;
    private AreaPulse pulse;
    private int rotation = 0;
    private FiducialTransfer fiducial;
    private CharacterChooser chooser;

    public PlayerInterface(Point interfacePosition, int rotation) {
        super("PlayerInterface");
        this.interfacePosition = interfacePosition;
        pulse = new AreaPulse("", interfacePosition, 40);
        this.rotation = rotation;
        this.chooser = new CharacterChooser(
                new Point((int) interfacePosition.getX() + 75,
                        (int) interfacePosition.getY() - 20));
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {

    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        switch (state) {
            case STATE_WAITING:
                pulse.setColor(new Color(175, 175, 175));
                pulse.setText("Place Character\n    to join");
                pulse.setActive(true);
                waitingState();
                break;
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.rotate((int) interfacePosition.getX(), (int) interfacePosition.getY(), rotation);

        switch (state) {
            case STATE_WAITING:
                this.chooser.render(graphics);
                break;
        }
        pulse.render(graphics);
        graphics.resetTransform();
    }

    @Override
    protected void inactiveState() {

    }

    @Override
    protected void waitingState() {
        ClientChannelHandler.fiducials.stream().filter(fi ->
                pulse.isInside(fi.getPosition()) && fi.isActive()).forEach(fi -> this.fiducial = fi);

        if (this.fiducial != null &&
                (!this.fiducial.isActive() ||
                        !pulse.isInside(this.fiducial.getPosition()))) this.fiducial = null;

        if (this.fiducial != null) {
            pulse.setPulse(false);
            chooser.setActive(true);
        } else {
            pulse.setPulse(true);
            chooser.setActive(false);
        }
    }

    @Override
    protected void joinState() {

    }

    @Override
    protected void readyState() {

    }

    @Override
    protected void activeState() {

    }
}
