package game.objects;

import org.newdawn.slick.Graphics;

import java.awt.*;

import org.newdawn.slick.Color;
import utils.GraphicsMethods;
import utils.TransformHitbox;

public class Button {
    protected Point position;
    protected int width;
    protected int height;
    protected String text;
    protected ButtonAction action;
    protected ButtonGraphics graphics;
    protected boolean visible;
    protected TransformHitbox hitbox;
    protected int degrees;

    public Button(Point position, int width, int height, String text) {
        this(position, width, height, 0, text, () -> {
        }, standardButtonGraphics(), true);
    }

    public Button(Point position, int width, int height, String text, int degrees, boolean visible) {
        this(position, width, height, degrees, text, () -> {
        }, standardButtonGraphics(), visible);
    }

    public Button(Point position, int width, int height, int degrees, String text,
                  ButtonAction action, ButtonGraphics graphics, boolean visible) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.text = text;
        this.action = action;
        this.visible = visible;
        this.graphics = graphics;
        this.hitbox = new TransformHitbox();
        this.hitbox.rotateCenter(degrees,
                new Rectangle((int) position.getX(), (int) position.getY(), width, height));
        this.degrees = degrees;
    }

    public boolean isPressed(Point point) {
        if (hitbox.isHit((int) point.getX(), (int) point.getY())) {
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
        this.hitbox.rotateCenter(degrees,
                new Rectangle((int) position.getX(), (int) position.getY(), width, height));
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

    public int getDegrees() {
        return degrees;
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
                g.pushTransform();
                g.rotate((int) button.position.getX() + button.getWidth() / 2,
                        (int) button.position.getY() + button.getHeight() / 2, button.getDegrees());
                g.setLineWidth(2);
                g.setColor(new Color(255, 255, 255, 255));
                g.drawRect((int) button.getPosition().getX(), (int) button.getPosition().getY(),
                        button.getWidth(), button.getHeight());
                g.fillRect((int) button.getPosition().getX() + 3, (int) button.getPosition().getY() + 3,
                        button.getWidth() - 6, button.getHeight() - 6);
                g.setColor(new Color(0, 0, 0, 255));
                GraphicsMethods.drawStringsCentered(button.getText(),
                        (int) button.getPosition().getX() + button.getWidth() / 2,
                        (int) button.getPosition().getY() + button.getHeight() / 2, g);
                g.popTransform();

            }
        };
    }

    public static boolean basicRectHitTest(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}
