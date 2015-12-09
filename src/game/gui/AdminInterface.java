package game.gui;

import commons.transfer.objects.BlobTransfer;
import commons.transfer.objects.FiducialTransfer;
import game.map.BattleMap;
import game.objects.Button;
import game.objects.PlayerAdminInterfaceButton;
import game.objects.PlayerLobby;
import game.objects.PlayerStatistics;
import game.states.State;
import game.states.StateMachine;
import net.ClientChannelHandler;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import utils.GraphicsMethods;

import java.awt.*;

public class AdminInterface extends StateMachine {
    private PlayerInterface[] players = new PlayerInterface[3];
    private PlayerLobby lobby;
    private PlayerStatistics pStats;
    private Point interfacePosition;

    private Font title = GraphicsMethods.getFont(20);
    private Font regular = GraphicsMethods.getFont(12);
    private BattleMap battleMap;

    public static AdminInterface adminInterface;

    public AdminInterface(Point interfacePosition) {
        super("AdminInterface");
        this.interfacePosition = interfacePosition;
        AdminInterface.adminInterface = this;
    }

    public BattleMap getBattleMap() {
        return battleMap;
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        players[0] = new PlayerInterface(new Point(gc.getWidth() / 2 - 75, 75), 180);
        players[1] = new PlayerInterface(new Point(75, gc.getHeight() / 2), 90);
        players[2] = new PlayerInterface(new Point(gc.getWidth() / 2 - 75, gc.getHeight() - 75), 0);
        lobby = new PlayerLobby(new Point((int) interfacePosition.getX() - 205, (int) interfacePosition.getY() + 250), 270);
        pStats = new PlayerStatistics(new Point((int) interfacePosition.getX() - 135, (int) interfacePosition.getY() - 215));
        initStates();
        stateInits(STATE_INACTIVE, gc);
        for (PlayerInterface pi : players) {
            pi.init(gc);
        }
    }

    public GameContainer gcStored;

    @Override
    public void update(GameContainer gameContainer, int j) throws SlickException {
        this.gcStored = gameContainer;
        if (gameContainer.getInput().isKeyDown(Input.KEY_1)) {
            ClientChannelHandler.ping(new FiducialTransfer(24l, true,
                    new Point(gameContainer.getInput().getMouseX(),
                            gameContainer.getInput().getMouseY())));
        }
        if (gameContainer.getInput().isKeyDown(Input.KEY_2)) {
            ClientChannelHandler.ping(new FiducialTransfer(4l, true,
                    new Point(gameContainer.getInput().getMouseX(),
                            gameContainer.getInput().getMouseY())));
        }
        if (gameContainer.getInput().isKeyDown(Input.KEY_3)) {
            ClientChannelHandler.ping(new FiducialTransfer(9l, true,
                    new Point(gameContainer.getInput().getMouseX(),
                            gameContainer.getInput().getMouseY())));
        }
        updateState(gameContainer, j);
        for (int i = 0; i < players.length; i++) {
            players[i].update(gameContainer, i);
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        renderState(gameContainer, graphics);

        if (currentState != STATE_INACTIVE) {
            lobby.render(graphics);
            for (PlayerInterface pi : players) {
                pi.render(gameContainer, graphics);
            }
        }

    }

    @Override
    public void initStates() {
        setWaitingState(new State(STATE_WAITING) {
            private Button startGame = new Button(new Point((int) interfacePosition.getX() - 15,
                    (int) interfacePosition.getY() + 15), 110, 45, 270, "Start Game", () -> {
                boolean ready = false;
                for (PlayerInterface playerInterface : players) {
                    if (playerInterface.getCharacter() != null) {
                        ready = true;
                        break;
                    }
                }
                if (ready) {
                    setCurrentState(STATE_ACTIVE);
                    for (PlayerInterface playerInterface : players) {
                        if (playerInterface.getCharacter() == null) {
                            playerInterface.setCurrentState(STATE_INACTIVE);
                        } else {
                            playerInterface.setCurrentState(STATE_ACTIVE);
                        }
                    }
                }

            }, Button.standardButtonGraphics(), true);

            @Override
            public void initState(GameContainer gc) {
                startGame.setVisible(true);
                battleMap.setFoWBool(true);
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
                try {
                    battleMap.updateGame(gc, j);
                } catch (SlickException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < players.length; i++) {
                    if (players[i].getCharacter() != null) {
                        lobby.update(i, players[i].getCharacter());
                    }
                }
            }

            @Override
            public void renderState(GameContainer gc, Graphics g) {
                startGame.render(g);
                g.setFont(title);
                g.setColor(new Color(255, 255, 255));
                g.pushTransform();
                g.rotate((int) interfacePosition.getX(), (int) interfacePosition.getY(), 270);

                pStats.render(g);
                g.popTransform();
                try {
                    battleMap.getMapContainer().setXY(140, 125);
                    battleMap.renderGame(gc, g);

                } catch (SlickException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean fiducialInput(FiducialTransfer fiducial) {
                for (PlayerInterface pi : players) {
                    if (pi.fiducialInput(fiducial)) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean blobInput(BlobTransfer blob) {
                System.out.println(blob.getPosition().getX() + " " + blob.getPosition().getY());
                if (startGame.isPressed(blob.getPosition())) {
                    return true;
                }
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
        setActiveState(new State(STATE_ACTIVE) {
            @Override
            public void initState(GameContainer gc) {

            }

            @Override
            public void updateState(GameContainer gc, int i) {
                try {
                    battleMap.updateGame(gc, i);
                } catch (SlickException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void renderState(GameContainer gc, Graphics g) {
                try {
                    battleMap.renderGame(gc, g);
                } catch (SlickException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean fiducialInput(FiducialTransfer fiducial) {
                return false;
            }

            @Override
            public boolean blobInput(BlobTransfer blob) {
                return false;
            }
        });
        setInactiveState(new State(STATE_INACTIVE) {
            private Button startGame = new Button(new Point(742, 700), 270, 50, 0, "Start Game", () -> {
                stateInits(STATE_WAITING, gcStored);
            }, Button.standardButtonGraphics(), true);

            @Override
            public void initState(GameContainer gc) {
                battleMap = new BattleMap("Battlemap");
                startGame.setVisible(true);
                try {
                    battleMap.init(gc);
                } catch (SlickException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void updateState(GameContainer gc, int i) {
                try {
                    battleMap.update(gc, i);
                } catch (SlickException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void renderState(GameContainer gc, Graphics g) {
                try {
                    startGame.render(g);
                    battleMap.render(gc, g);

                } catch (SlickException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean fiducialInput(FiducialTransfer fiducial) {
                return false;
            }

            @Override
            public boolean blobInput(BlobTransfer blob) {
                if (battleMap.blobInput(blob)) return true;
                if (startGame.isPressed(blob.getPosition())) {
                    return true;
                }
                return false;
            }
        });
    }
}
