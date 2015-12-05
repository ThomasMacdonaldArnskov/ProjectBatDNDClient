package utils;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

public class GraphicsMethods {

    private static Font font = new Font("Tahoma", Font.PLAIN, 13);

    public static TrueTypeFont getFont(int size) {
        return new TrueTypeFont(new Font(font.getName(), Font.PLAIN, size), true);
    }
}
