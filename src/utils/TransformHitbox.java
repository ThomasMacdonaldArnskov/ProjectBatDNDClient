package utils;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class TransformHitbox {

    private Shape hitbox;

    public boolean isHit(int x, int y) {
        if (hitbox != null)
            if (hitbox.contains(x, y)) return true;
        return false;
    }

    public void setHitbox(Shape shape) {
        hitbox = shape;
    }

    public void rotateCenter(int rotation, Rectangle rectangle) {
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotation),
                rectangle.getX() + rectangle.getWidth() / 2,
                rectangle.getY() + rectangle.getHeight() / 2);
        hitbox = transform.createTransformedShape(rectangle);
    }

    public void rotateAroundPoint(int rotation, Rectangle rectangle, int pointX, int pointY) {
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(rotation), pointX, pointY);
        hitbox = transform.createTransformedShape(rectangle);
    }
}
