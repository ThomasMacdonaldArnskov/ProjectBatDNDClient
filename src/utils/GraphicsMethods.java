package utils;

import org.newdawn.slick.*;

import java.awt.Font;

public class GraphicsMethods {

    private static Font font = new Font("Tahoma", Font.PLAIN, 13);

    public static TrueTypeFont getFont(int size) {
        return new TrueTypeFont(new Font(font.getName(), Font.PLAIN, size), true);
    }

    public static TrueTypeFont getFont(int size, int fontType) {
        return new TrueTypeFont(new Font(font.getName(), fontType, size), true);
    }

    public static void drawStrings(String text, int x, int y, Graphics g) {

        String[] ln = text.split("/&");
        int h = g.getFont().getLineHeight();
        for (int i = 0; i < ln.length; i++) {
            g.drawString(ln[i], x - g.getFont().getWidth(ln[i]) / 2,
                    y + h * i - ((ln.length * h) / 2) - (ln.length > 1 ? h / 4 : 0));
        }
    }
}
