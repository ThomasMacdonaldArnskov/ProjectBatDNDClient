package game.gui;

import commons.transfer.objects.BlobTransfer;
import commons.transfer.objects.FiducialTransfer;
import game.characters.HeroClass;
import game.objects.*;
import game.objects.Button;
import game.states.State;
import org.newdawn.slick.*;
import game.states.StateMachine;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Font;
import utils.GraphicsMethods;

import java.awt.*;

public class AdminInterface extends StateMachine {
    private PlayerInterface[] players = new PlayerInterface[3];
    private Point interfacePosition;

    private Font title = GraphicsMethods.getFont(20);
    private Font regular = GraphicsMethods.getFont(13);

    public AdminInterface(Point interfacePosition) {
        super("AdminInterface");
        this.interfacePosition = interfacePosition;


    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        players[0] = new PlayerInterface(new Point(gc.getWidth() / 2 - 75, 75), 180);
        players[1] = new PlayerInterface(new Point(75, gc.getHeight() / 2), 90);
        players[2] = new PlayerInterface(new Point(gc.getWidth() / 2 - 75, gc.getHeight() - 150), 0);
        initStates();
        initState(STATE_WAITING, gc);
        for (PlayerInterface pi : players) {
            pi.init(gc);
        }
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        for (PlayerInterface pi : players) {
            pi.update(gameContainer, i);
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.rotate((int) interfacePosition.getX(), (int) interfacePosition.getY(), 270);
        renderState(graphics);
        graphics.resetTransform();
        for (PlayerInterface pi : players) {
            pi.render(gameContainer, graphics);
        }
    }

    @Override
    public void initStates() {
        setWaitingState(new State(STATE_WAITING) {
            private Button startGame = new Button(new Point((int) interfacePosition.getX() - 55,
                    (int) interfacePosition.getY()), 110, 45, "Start Game");

            @Override
            public void initState(GameContainer gc) {
                startGame.setVisible(true);
                for (PlayerInterface pi : players) {
                    try {
                        pi.init(gc);
                    } catch (SlickException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void updateState(GameContainer gc, int i) {

            }

            @Override
            public void renderState(Graphics g) {
                startGame.render(g);
                g.setFont(title);
                g.setColor(new Color(255, 255, 255));
                g.drawString("Available Players",
                        (int) interfacePosition.getX() - g.getFont().getWidth("Available Players") / 2,
                        (int) interfacePosition.getY() - 215);
                g.setFont(regular);
                for (int i = 0; i < players.length - 1; i++) {
                    if (players[i].getChooser().getSelected() != HeroClass.CLASSLESS) {
                        g.drawString(players[i].getChooser().getSelected().toString(),
                                (int) interfacePosition.getX() -
                                        g.getFont().getWidth(players[i].getChooser().getSelected().toString()) / 2,
                                (int) interfacePosition.getY() - 180);
                    }
                }
            }

            @Override
            public void fiducialInput(FiducialTransfer fiducial) {

            }

            @Override
            public void blobInput(BlobTransfer blob) {

            }
        });
    }
}
