package game;

import commons.transfer.objects.BlobTransfer;
import game.gui.AdminInterface;
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

    public static int WIDTH = 1024;
    public static int HEIGHT = 768;

    public static BatClient batClient;

    public BatClient() {
        super("DnD Game");
        client = new NetworkClient(HOST_IP, PORT);
        new Thread(client).start();
        BatClient.batClient = this;
    }

    public boolean pingBlob(BlobTransfer blob) {
        return adminInterface.blobInput(blob);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        adminInterface = new AdminInterface(new Point(gc.getWidth() - 75, gc.getHeight() / 2));
        adminInterface.init(gc);
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
        if (!ClientChannelHandler.fiducials.isEmpty() && ClientChannelHandler.fiducials.get(0) != null)
            g.drawOval(
                    (int) ClientChannelHandler.fiducials.get(0).getPosition().getX() - 5,
                    (int) ClientChannelHandler.fiducials.get(0).getPosition().getY() - 5, 10, 10);
    }


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
