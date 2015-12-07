package game.gui;

import commons.transfer.objects.BlobTransfer;
import commons.transfer.objects.FiducialTransfer;
import game.characters.CharacterSheet;
import game.characters.HeroClass;
import game.characters.NameRaceGenerator;
import game.objects.Button;
import game.states.State;
import game.states.StateMachine;
import net.ClientChannelHandler;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import utils.AreaPulse;
import utils.GraphicsMethods;

import java.awt.*;

public class PlayerInterface extends StateMachine {

    private boolean myTurn = false;

    private Point interfacePosition;
    private CharacterSheet character;

    private int rotation = 0;
    private FiducialTransfer fiducial;
    private CharacterChooser chooser;
    private Font font = GraphicsMethods.getFont(14);

    public PlayerInterface(Point interfacePosition, int rotation) {
        super("PlayerInterface");
        this.interfacePosition = interfacePosition;

        this.rotation = rotation;
        this.chooser = new CharacterChooser(
                new Point((int) interfacePosition.getX() + (rotation > 90 ? -50 : +50),
                        (int) interfacePosition.getY() + (rotation >= 90 ? +50 : -50)), rotation);
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
        renderState(graphics);
    }

    @Override
    public void initStates() {
        setWaitingState(new State(STATE_WAITING) {
            private AreaPulse pulse;
            private Button instalock = new Button(
                    new Point((int) interfacePosition.getX() + (rotation != 90 ? (rotation > 90 ? -200 : +52) : -85),
                            (int) interfacePosition.getY() + (rotation != 90 ? (rotation > 90 ? -25 : -5) : 110)),
                    145, 26, rotation, "Lock Choice", () -> {
                if (chooser != null && chooser.getSelected() != HeroClass.CLASSLESS)
                    character = new CharacterSheet(
                            NameRaceGenerator.getRaceAndName(chooser.getSelected()),
                            chooser.getSelected(), fiducial);
            }, Button.standardButtonGraphics(), true);

            @Override
            public void initState(GameContainer gc) {
                pulse = new AreaPulse("", interfacePosition, 40, rotation);
                pulse.setColor(new Color(175, 175, 175));
                pulse.setText("Place/&Character");
                pulse.setActive(true);
                instalock.setVisible(false);
            }

            @Override
            public void updateState(GameContainer gc, int i) {
                ClientChannelHandler.fiducials.stream().filter(fi ->
                        pulse.isInside(fi.getPosition()) && fi.isActive()).forEach(fi -> fiducial = fi);

                if (fiducial != null &&
                        (!fiducial.isActive() ||
                                !pulse.isInside(fiducial.getPosition()))) fiducial = null;

                if (character == null) {
                    if (fiducial != null) {
                        pulse.setPulse(false);
                        chooser.setActive(true);
                        //chooser.setCurrentHero(HeroClass.CLASSLESS);
                        if (chooser.getSelected() != HeroClass.CLASSLESS) {
                            instalock.setVisible(true);
                        } else {
                            instalock.setVisible(false);
                        }
                    } else {
                        pulse.setPulse(true);
                        pulse.setText("Place/&Character");
                        chooser.setActive(false);
                        instalock.setVisible(false);
                    }
                } else {
                    pulse.setActive(true);
                    pulse.setPulse(false);
                    pulse.setText("Hero Locked");
                    instalock.setVisible(false);
                    chooser.setActive(false);
                    instalock.setVisible(false);
                }
            }

            @Override
            public void renderState(Graphics g) {
                pulse.render(g);
                chooser.render(g);
                g.setFont(font);
                g.setAntiAlias(false);
                instalock.render(g);
            }

            @Override
            public boolean fiducialInput(FiducialTransfer fi) {
                if (pulse.isInside(fi.getPosition())) {
                    fiducial = fi;
                    return true;
                }
                return false;
            }

            @Override
            public boolean blobInput(BlobTransfer blob) {
                if (chooser.getSelected() != null && chooser.getSelected() != HeroClass.CLASSLESS) {
                    if (instalock.isPressed(blob.getPosition())) {
                        return true;
                    }
                }
                if (chooser.isActive()) {
                    if (chooser.isInside(blob.getPosition())) {
                        return true;
                    }
                }
                return false;
            }
        });
        setActiveState(new State(STATE_ACTIVE) {
            @Override
            public void initState(GameContainer gc) {

            }

            @Override
            public void updateState(GameContainer gc, int i) {

            }

            @Override
            public void renderState(Graphics g) {

            }

            @Override
            public boolean fiducialInput(FiducialTransfer fi) {
                if (fiducial.isSame(fi.getId())) {
                    return true;
                }
                return false;
            }

            @Override
            public boolean blobInput(BlobTransfer blob) {
                return false;
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
