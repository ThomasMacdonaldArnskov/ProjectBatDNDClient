package game;

import com.sun.istack.internal.NotNull;
import commons.transfer.objects.BlobTransfer;
import commons.transfer.objects.FiducialTransfer;
import game.gui.AdminInterface;
import net.ClientChannelHandler;
import net.NetworkClient;
import org.newdawn.slick.*;
<<<<<<< HEAD
import org.newdawn.slick.state.StateBasedGame;
=======
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
>>>>>>> df2a8af86e3b70ce5cf4b1aa3a5225e49601cfe4

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BatClient extends StateBasedGame {

    game.BattleMap battleMap;
    private FogOfWar fogOfWar = new game.FogOfWar();

    private NetworkClient client;
    private AdminInterface adminInterface;

    public static final String HOST_IP = "127.0.0.1"; //Local Host Kappa
    public static final int PORT = 5555;

    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    public static BatClient batClient;

    public BatClient() {
        super("DnD Game");

        BatClient.batClient = this;
    }

    public boolean pingBlob(BlobTransfer blob) {
        return adminInterface.blobInput(blob);
    }

    public boolean pingFiducial(@NotNull FiducialTransfer fiducial) {
        return adminInterface.fiducialInput(fiducial);
    }

    @Override
<<<<<<< HEAD
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        this.addState(new BattleMap(fogOfWar));
        this.addState(new MapEditor());
    }

=======
    public void init(GameContainer gc) throws SlickException {
        adminInterface = new AdminInterface(new Point(gc.getWidth() - 75, gc.getHeight() / 2));
        adminInterface.init(gc);
        client = new NetworkClient(HOST_IP, PORT);
        new Thread(client).start();
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        adminInterface.update(gc, i);
        if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            try {
                client.getChannelHandler().channelRead(null, new BlobTransfer(30L, true, new Point(gc.getInput().getMouseX(), gc.getInput().getMouseY())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        adminInterface.render(gc, g);
        g.setLineWidth(10);
        g.setColor(Color.white);
        g.drawLine(gc.getWidth() - 300, 0, gc.getWidth() - 300, gc.getHeight());
        if (!ClientChannelHandler.fiducials.isEmpty())
            ClientChannelHandler.fiducials.stream().filter(fi -> fi != null && fi.isActive()).forEach(fi -> g.drawOval(
                    (int) fi.getPosition().getX() - 5,
                    (int) fi.getPosition().getY() - 5, 10, 10));

    }


>>>>>>> df2a8af86e3b70ce5cf4b1aa3a5225e49601cfe4
    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new BatClient());
            appgc.setDisplayMode(BatClient.WIDTH, BatClient.HEIGHT, false);
            appgc.setTargetFrameRate(60);
            appgc.setShowFPS(true);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(BatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
