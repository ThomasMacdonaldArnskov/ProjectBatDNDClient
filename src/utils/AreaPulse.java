package utils;

import org.newdawn.slick.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;

public class AreaPulse {

    private Point centerPosition;
    private int radius;
    private int pulseRadius;
    private boolean pulseOut;

    private boolean active;
    private boolean pulse;
    private String text;
    private Color color;
    private Font font = GraphicsMethods.getFont(13);

    public AreaPulse(String text, Point centerPosition, int radius) {
        this.text = text;
        this.radius = radius;
        this.centerPosition = centerPosition;
        this.active = false;
        this.pulseRadius = radius * 2;
        this.pulseOut = true;
        this.pulse = true;
        this.color = new Color(0, 0, 0);
    }

    public void setPulse(boolean pulse) {
        this.pulse = pulse;
    }

    public void render(Graphics g) {
        if (pulse && pulseOut && pulseRadius++ > radius * 3.5) {
            pulseOut = false;
        }
        if (pulse && !pulseOut && pulseRadius-- < radius * 2) {
            pulseOut = true;
        }

        if (active) {
            g.setAntiAlias(true);
            g.setLineWidth(10);
            g.setFont(font);

            if (pulse) {
                g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 50));
                g.drawOval((int) centerPosition.getX() - (pulseRadius - 20) / 2,
                        (int) centerPosition.getY() - (pulseRadius - 20) / 2,
                        pulseRadius - 20, pulseRadius - 20);
                g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 100));
                g.drawOval((int) centerPosition.getX() - pulseRadius / 2,
                        (int) centerPosition.getY() - pulseRadius / 2,
                        pulseRadius, pulseRadius);
            }

            g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 200));
            g.setLineWidth(6);
            g.drawOval(
                    (int) centerPosition.getX() - radius,
                    (int) centerPosition.getY() - radius,
                    radius * 2, radius * 2);

            g.setColor(Color.white);
            GraphicsMethods.drawStringsCentered(text,
                    (int) centerPosition.getX(),
                    (int) centerPosition.getY(), g);
        }
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isInside(int x, int y) {
        float disX = this.centerPosition.x - x;
        float disY = this.centerPosition.y - y;
        return Math.sqrt((disX * disX) + (disY * disY)) < radius;
    }

    public boolean isInside(Point point) {
        float disX = (float) (this.centerPosition.getX() - point.getX());
        float disY = (float) (this.centerPosition.getY() - point.getY());
        return Math.sqrt((disX * disX) + (disY * disY)) < radius;
    }

    public Point getCenterPosition() {
        return centerPosition;
    }

    public void setCenterPosition(Point centerPosition) {
        this.centerPosition = centerPosition;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

}
