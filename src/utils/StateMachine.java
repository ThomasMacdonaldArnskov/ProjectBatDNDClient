package utils;


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

    public StateMachine(String title) {
        super(title);
    }

    public void changeState(int state) {
        currentState = state;
        switch (currentState) {
            case STATE_INACTIVE:
                inactiveState();
                break;
            case STATE_ACTIVE:
                activeState();
                break;
            case STATE_WAITING:
                waitingState();
                break;
            case STATE_JOIN:
                joinState();
                break;
            case STATE_READY:
                readyState();
                break;
            default:
                break;
        }
    }

    @Override
    public abstract void init(GameContainer gameContainer) throws SlickException;

    @Override
    public abstract void update(GameContainer gameContainer, int i) throws SlickException;

    @Override
    public abstract void render(GameContainer gameContainer, Graphics graphics) throws SlickException;

    protected abstract void inactiveState();

    protected abstract void waitingState();

    protected abstract void joinState();

    protected abstract void readyState();

    protected abstract void activeState();

}
