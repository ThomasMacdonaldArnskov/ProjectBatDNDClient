package game.gui;

import commons.transfer.objects.BlobTransfer;
import commons.transfer.objects.FiducialTransfer;
import game.characters.*;
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
    private PlayerLobby lobby;
    private PlayerStatistics pStats;
    private Point interfacePosition;

    private Font title = GraphicsMethods.getFont(20);
    private Font regular = GraphicsMethods.getFont(12);

    public AdminInterface(Point interfacePosition) {
        super("AdminInterface");
        this.interfacePosition = interfacePosition;
    }


    @Override
    public void init(GameContainer gc) throws SlickException {
        players[0] = new PlayerInterface(new Point(gc.getWidth() / 2 - 75, 150), 180);
        players[1] = new PlayerInterface(new Point(75, gc.getHeight() / 2), 90);
        players[2] = new PlayerInterface(new Point(gc.getWidth() / 2 - 75, gc.getHeight() - 150), 0);
        lobby = new PlayerLobby(new Point((int) interfacePosition.getX() - 250, (int) interfacePosition.getY() - 215), 270);
        pStats = new PlayerStatistics(new Point((int) interfacePosition.getX() - 50, (int) interfacePosition.getY() - 215));
        initStates();
        initState(STATE_WAITING, gc);
        for (PlayerInterface pi : players) {
            pi.init(gc);
        }
    }

    @Override
    public void update(GameContainer gameContainer, int j) throws SlickException {
        updateState(gameContainer, j);
        for (int i = 0; i < players.length; i++) {
            players[i].update(gameContainer, i);
        }

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        renderState(graphics);
        lobby.render(graphics);
        graphics.rotate((int) interfacePosition.getX(), (int) interfacePosition.getY(), 270);

        graphics.resetTransform();
        for (PlayerInterface pi : players) {
            pi.render(gameContainer, graphics);
        }
    }

    @Override
    public void initStates() {
        setWaitingState(new State(STATE_WAITING) {
            private Button startGame = new Button(new Point((int) interfacePosition.getX() - 55,
                    (int) interfacePosition.getY() + 15), 110, 45, "Start Game");

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
            public void updateState(GameContainer gc, int j) {
                for (int i = 0; i < players.length; i++) {
                    if (players[i].getCharacter() != null) {
                        lobby.update(i, players[i].getCharacter());
                    }
                }
            }

            @Override
            public void renderState(Graphics g) {
                startGame.render(g);
                g.setFont(title);
                g.setColor(new Color(255, 255, 255));
                pStats.render(g);
            }

            @Override
            public boolean fiducialInput(FiducialTransfer fiducial) {
                return false;
            }

            @Override
            public boolean blobInput(BlobTransfer blob) {
                for (PlayerInterface pi : players) {
                    if (pi.blobInput(blob)) {
                        return true;
                    }
                }
                for (PlayerAdminInterfaceButton paib : lobby.getPlayers()) {
                    if (paib != null && paib.isPressed(blob.getPosition())) {
                        pStats.update(players[paib.getPlayerNumber()].getCharacter());
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
