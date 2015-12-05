package game.objects;

import org.newdawn.slick.Graphics;

import java.awt.*;

import org.newdawn.slick.Color;
import utils.GraphicsMethods;

public class Button {
    protected Point position;
    protected int width;
    protected int height;
    protected String text;
    protected ButtonAction action;
    protected ButtonGraphics graphics;
    protected boolean visible;

    public Button(Point position, int width, int height, String text) {
        this(position, width, height, text, () -> {
        }, standardButtonGraphics(), true);
    }

    public Button(Point position, int width, int height, String text, boolean visible) {
        this(position, width, height, text, () -> {
        }, standardButtonGraphics(), visible);
    }

    public Button(Point position, int width, int height, String text,
                  ButtonAction action, ButtonGraphics graphics, boolean visible) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.text = text;
        this.action = action;
        this.visible = visible;
        this.graphics = graphics;
    }

    public boolean isPressed(Point point) {
        if (point.getX() >= this.position.getX() && point.getX() <= this.position.getX() + width &&
                point.getY() >= this.position.getY() && point.getY() <= this.position.getY() + height) {
            action.action();
            return true;
        } else {
            return false;
        }
    }

    public void render(Graphics g) {
        if (visible && action != null) {
            graphics.render(this, g);
        }
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ButtonAction getAction() {
        return action;
    }

    public void setAction(ButtonAction action) {
        this.action = action;
    }

    public ButtonGraphics getGraphics() {
        return graphics;
    }

    public void setGraphics(ButtonGraphics graphics) {
        this.graphics = graphics;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public static ButtonGraphics standardButtonGraphics() {
        return (button, g) -> {
            if (button.isVisible()) {

                g.setLineWidth(2);
                g.setColor(new Color(255, 255, 255, 255));
                g.drawRect((int) button.getPosition().getX(), (int) button.getPosition().getY(),
                        button.getWidth(), button.getHeight());
                g.fillRect((int) button.getPosition().getX() + 3, (int) button.getPosition().getY() + 3,
                        button.getWidth() - 6, button.getHeight() - 6);
                g.setColor(new Color(0, 0, 0, 255));
                GraphicsMethods.drawStrings(button.getText(),
                        (int) button.getPosition().getX() + button.getWidth() / 2,
                        (int) button.getPosition().getY() + button.getHeight() / 2, g);

            }
        };
    }
}
