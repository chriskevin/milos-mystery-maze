package se.chriskevin.mysterymaze;

import java.awt.*;
import java.util.Collection;

/**
 * Created by CHSU7648 on 2016-03-14.
 */
final public class Camera {

    private Collection<Sprite> sprites;

    private Sprite target;

    private Rectangle viewArea;

    public Camera(Dimension dimension, Collection<Sprite> sprites, Sprite target) {
        this.viewArea = new Rectangle(new Point(0, 0), dimension);
        this.sprites = sprites;
        this.target = target;
    }

    public void update(Board board, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Rectangle targetBounds = target.getBounds();

        int targetX = ((int) viewArea.getWidth() / 2) - ((int) targetBounds.getWidth() / 2);
        int targetY = ((int) viewArea.getHeight() / 2) - ((int) targetBounds.getHeight() / 2);

        targetX = adjustXBoundary(targetX);
        targetY = adjustYBoundary(targetY);

        int offsetX = (targetX - target.getX());
        int offsetY = (targetY - target.getY());

        int newViewAreaX = target.getX() - ((int) viewArea.getX() / 2) - (int) targetBounds.getWidth();
        int newViewAreaY = target.getY() - ((int) viewArea.getY() / 2) - (int) targetBounds.getHeight();
        this.viewArea.setLocation(newViewAreaX, newViewAreaY);

        for (Sprite sprite : sprites) {
            if (!sprite.equals(target)) {
                sprite.isVisible(sprite.getBounds().intersects(viewArea));
            }

            if (sprite.isVisible()) {
                if (sprite instanceof Character && ((Character) sprite).isColliding()) {
                    drawCollisionZone(g, new Rectangle(new Point((int) sprite.getBounds().getX() + offsetX, (int) sprite.getBounds().getY() + offsetY), sprite.getBounds().getSize()));
                }
                g2d.drawImage(sprite.getImage(), sprite.getX() + offsetX, sprite.getY() + offsetY, board);
            }
        }

        if (((Character) target).isColliding()) {
            drawCollisionZone(g, new Rectangle(new Point((int) target.getBounds().getX() + offsetX, (int) target.getBounds().getY() + offsetY), target.getBounds().getSize()));
        }
        g2d.drawImage(target.getImage(), targetX, targetY, board);
    }

    private int adjustXBoundary(int x) {
        if (target.getX() <= viewArea.getWidth() / 2) {
            return target.getX();
        } else {
            return x;
        }
    }

    private int adjustYBoundary(int y) {
        if (target.getY() <= viewArea.getHeight() / 2) {
            return target.getY();
        } else {
            return y;
        }
    }

    private void drawCollisionZone(Graphics g, Rectangle bounds) {
        Color myColour = new Color(255, 0, 0, 128);
        g.setColor(myColour);
        g.fillRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());
    }

    /**
     * This method sets the target to follow.
     * @param target
     */
    public void setTarget(Sprite target) {
        this.target = target;
    }
}
