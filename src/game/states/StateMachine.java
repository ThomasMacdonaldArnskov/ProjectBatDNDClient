package game.states;


import commons.transfer.objects.BlobTransfer;
import commons.transfer.objects.FiducialTransfer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public abstract class StateMachine extends BasicGame {
    public static final int STATE_INACTIVE = -1;
    public static final int STATE_WAITING = 0;
    public static final int STATE_JOIN = 1;
    public static final int STATE_READY = 2;
    public static final int STATE_ACTIVE = 3;

    protected int currentState = 0;

    protected State inactiveState;
    protected State waitingState;
    protected State joinState;
    protected State readyState;
    protected State activeState;

    public int getCurrentState() {
        return currentState;
    }

    public State getInactiveState() {
        return inactiveState;
    }

    public State getWaitingState() {
        return waitingState;
    }

    public State getJoinState() {
        return joinState;
    }

    public State getReadyState() {
        return readyState;
    }

    public State getActiveState() {
        return activeState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public void setInactiveState(State inactiveState) {
        this.inactiveState = inactiveState;
    }

    public void setWaitingState(State waitingState) {
        this.waitingState = waitingState;
    }

    public void setJoinState(State joinState) {
        this.joinState = joinState;
    }

    public void setReadyState(State readyState) {
        this.readyState = readyState;
    }

    public void setActiveState(State activeState) {
        this.activeState = activeState;
    }

    public StateMachine(String title) {
        super(title);
    }

    public void initState(int state, GameContainer gc) {
        currentState = state;
        switch (currentState) {
            case STATE_INACTIVE:
                if (inactiveState != null) inactiveState.initState(gc);
                break;
            case STATE_ACTIVE:
                if (activeState != null) activeState.initState(gc);
                break;
            case STATE_WAITING:
                if (waitingState != null) waitingState.initState(gc);
                break;
            case STATE_JOIN:
                if (joinState != null) joinState.initState(gc);
                break;
            case STATE_READY:
                if (readyState != null) readyState.initState(gc);
                break;
            default:
                break;
        }
    }

    public void updateState(GameContainer gc, int i) {
        switch (currentState) {
            case STATE_INACTIVE:
                if (inactiveState != null) inactiveState.updateState(gc, i);
                break;
            case STATE_ACTIVE:
                if (activeState != null) activeState.updateState(gc, i);
                break;
            case STATE_WAITING:
                if (waitingState != null) waitingState.updateState(gc, i);
                break;
            case STATE_JOIN:
                if (joinState != null) joinState.updateState(gc, i);
                break;
            case STATE_READY:
                if (readyState != null) readyState.updateState(gc, i);
                break;
            default:
                break;
        }
    }

    public boolean fiducialInput(FiducialTransfer fiducial) {
        switch (currentState) {
            case STATE_INACTIVE:
                if (inactiveState != null) return inactiveState.fiducialInput(fiducial);
                break;
            case STATE_ACTIVE:
                if (activeState != null) activeState.fiducialInput(fiducial);
                break;
            case STATE_WAITING:
                if (waitingState != null) waitingState.fiducialInput(fiducial);
                break;
            case STATE_JOIN:
                if (joinState != null) joinState.fiducialInput(fiducial);
                break;
            case STATE_READY:
                if (readyState != null) readyState.fiducialInput(fiducial);
                break;
            default:
                break;
        }
        return false;
    }

    public boolean blobInput(BlobTransfer blobTransfer) {
        switch (currentState) {
            case STATE_INACTIVE:
                if (inactiveState != null) return inactiveState.blobInput(blobTransfer);
                break;
            case STATE_ACTIVE:
                if (activeState != null) activeState.blobInput(blobTransfer);
                break;
            case STATE_WAITING:
                if (waitingState != null) waitingState.blobInput(blobTransfer);
                break;
            case STATE_JOIN:
                if (joinState != null) joinState.blobInput(blobTransfer);
                break;
            case STATE_READY:
                if (readyState != null) readyState.blobInput(blobTransfer);
                break;
            default:
                break;
        }
        return false;
    }

    public void renderState(Graphics g) {
        switch (currentState) {
            case STATE_INACTIVE:
                if (inactiveState != null) inactiveState.renderState(g);
                break;
            case STATE_ACTIVE:
                if (activeState != null) activeState.renderState(g);
                break;
            case STATE_WAITING:
                if (waitingState != null) waitingState.renderState(g);
                break;
            case STATE_JOIN:
                if (joinState != null) joinState.renderState(g);
                break;
            case STATE_READY:
                if (readyState != null) readyState.renderState(g);
                break;
            default:
                break;
        }
    }

    public abstract void initStates();

    @Override
    public abstract void init(GameContainer gameContainer) throws SlickException;

    @Override
    public abstract void update(GameContainer gameContainer, int i) throws SlickException;

    @Override
    public abstract void render(GameContainer gameContainer, Graphics graphics) throws SlickException;
}
