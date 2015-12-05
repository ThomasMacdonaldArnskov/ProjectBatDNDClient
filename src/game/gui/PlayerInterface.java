package game.gui;

import commons.transfer.objects.BlobTransfer;
import commons.transfer.objects.FiducialTransfer;
import game.characters.*;
import game.states.State;
import net.ClientChannelHandler;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Color;
import utils.AreaPulse;
import game.states.StateMachine;


import java.awt.*;

public class PlayerInterface extends StateMachine {

    private boolean myTurn = false;

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
        initStates();
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        initState(STATE_WAITING, gameContainer);
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        updateState(gc, i);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.rotate((int) interfacePosition.getX(), (int) interfacePosition.getY(), rotation);
        renderState(graphics);
        graphics.resetTransform();
    }

    @Override
    public void initStates() {
        setWaitingState(new State(STATE_WAITING) {
            @Override
            public void initState(GameContainer gc) {
                pulse.setColor(new Color(175, 175, 175));
                pulse.setText("Place/&Character");
                pulse.setActive(true);
            }

            @Override
            public void updateState(GameContainer gc, int i) {
                ClientChannelHandler.fiducials.stream().filter(fi ->
                        pulse.isInside(fi.getPosition()) && fi.isActive()).forEach(fi -> fiducial = fi);

                if (fiducial != null &&
                        (!fiducial.isActive() ||
                                !pulse.isInside(fiducial.getPosition()))) fiducial = null;

                if (fiducial != null) {
                    pulse.setPulse(false);
                    chooser.setActive(true);
                    chooser.setCurrentHero(HeroClass.CLERIC);
                    character = new CharacterSheet(
                            NameRaceGenerator.getRaceAndName(chooser.getSelected()),
                            chooser.getSelected(), new Attributes(), fiducial);
                } else {
                    pulse.setPulse(true);
                    chooser.setActive(false);
                }
            }

            @Override
            public void renderState(Graphics g) {
                pulse.render(g);
                chooser.render(g);
            }

            @Override
            public void fiducialInput(FiducialTransfer fiducial) {

            }

            @Override
            public void blobInput(BlobTransfer blob) {
                if (chooser.isActive()) {
                    chooser.isInside(blob.getPosition()); //maybe do the if statement
                }
            }
        });
    }

    public CharacterSheet getCharacter() {
        return character;
    }

    public CharacterChooser getChooser() {
        return chooser;
    }
}
