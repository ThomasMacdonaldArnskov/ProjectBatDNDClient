package utils;

import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;

import java.awt.*;
import java.awt.Font;
import java.awt.geom.AffineTransform;

public class GraphicsMethods {

    private static Font font = new Font("Tahoma", Font.PLAIN, 13);

    public static TrueTypeFont getFont(int size) {
        return new TrueTypeFont(new Font(font.getName(), Font.PLAIN, size), true);
    }

    public static TrueTypeFont getFont(int size, int fontType) {
        return new TrueTypeFont(new Font(font.getName(), fontType, size), true);
    }

    public static void drawStringsCentered(String text, int x, int y, Graphics g) {

        String[] ln = text.split("/&");
        int h = g.getFont().getLineHeight();
        for (int i = 0; i < ln.length; i++) {
            g.drawString(ln[i], x - g.getFont().getWidth(ln[i]) / 2,
                    y + h * i - ((ln.length * h) / 2) - (ln.length > 1 ? h / 4 : 0));
        }
    }

    public static void drawStrings(String text, int x, int y, Graphics g) {

        String[] ln = text.split("/&");
        int h = g.getFont().getLineHeight();
        for (int i = 0; i < ln.length; i++) {
            g.drawString(ln[i], x, y + h * i);
        }
    }

    public static Rectangle rotate(int rotation, Rectangle rectangle) {
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotation), rectangle.getX() + rectangle.getWidth() / 2,
                rectangle.getY() + rectangle.getHeight() / 2);
        return (Rectangle) transform.createTransformedShape(rectangle);
    }
}
