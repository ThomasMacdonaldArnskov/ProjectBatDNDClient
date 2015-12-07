package game.states;

import commons.transfer.objects.BlobTransfer;
import commons.transfer.objects.FiducialTransfer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public abstract class State {

    protected int id;

    public State(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public boolean isSame(int id) {
        return this.id == id;
    }

    public abstract void initState(GameContainer gc);

    public abstract void updateState(GameContainer gc, int i);

    public abstract void renderState(Graphics g);

    public abstract boolean fiducialInput(FiducialTransfer fiducial);

    public abstract boolean blobInput(BlobTransfer blob);
}
