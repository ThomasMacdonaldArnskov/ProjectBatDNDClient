package game;

import com.sun.istack.internal.NotNull;
import commons.transfer.objects.BlobTransfer;
import commons.transfer.objects.FiducialTransfer;
import game.gui.AdminInterface;
import game.map.BattleMap;
import net.ClientChannelHandler;
import net.NetworkClient;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BatClient extends BasicGame {

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
        //battleMap.render(gc, g);
        if (!ClientChannelHandler.fiducials.isEmpty())
            ClientChannelHandler.fiducials.stream().filter(fi -> fi != null && fi.isActive()).forEach(fi -> g.drawOval(
                    (int) fi.getPosition().getX() - 5,
                    (int) fi.getPosition().getY() - 5, 10, 10));

    }

    public static void main(String[] args) {
        try {
            AppGameContainer appgc;
            appgc = new AppGameContainer(new BatClient());
            appgc.setDisplayMode(BatClient.WIDTH, BatClient.HEIGHT, true);
            appgc.setTargetFrameRate(60);
            appgc.setShowFPS(false);
            appgc.start();
        } catch (SlickException ex) {
            Logger.getLogger(BatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
