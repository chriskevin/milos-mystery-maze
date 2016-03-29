package se.chriskevin.mysterymaze.ui;

import se.chriskevin.mysterymaze.environment.GameSprite;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Created by CHSU7648 on 2016-03-14.
 */
final public class Camera {

    private Dimension environmentDimension;

    private Map<String, List<GameSprite>> sprites;

    private GameSprite target;

    private Rectangle viewArea;

    public Camera(Dimension gameViewDimension, Dimension environmentDimension, Map<String, List<GameSprite>> sprites) {
        this.viewArea = new Rectangle(new Point(0, 0), gameViewDimension);
        this.environmentDimension = environmentDimension;
        this.sprites = sprites;
        this.target = sprites.get("PLAYER").get(0);
    }

    public void update(GameView gameView, Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;

        final Rectangle targetBounds = target.getBounds();

        final Point targetP = new Point(((int) viewArea.getWidth() / 2) - ((int) targetBounds.getWidth() / 2), ((int) viewArea.getHeight() / 2) - ((int) targetBounds.getHeight() / 2));
        targetP.setLocation(adjustXBoundary(targetP.getX()), adjustYBoundary(targetP.getY()));

        final Point offsetP = new Point((int) targetP.getX() - (int) target.getLocation().getX(), (int) targetP.getY() - (int) target.getLocation().getY());

        double newViewAreaX = target.getLocation().getX() - (viewArea.getX() / 2) - targetBounds.getWidth();
        double newViewAreaY = target.getLocation().getY() - (viewArea.getY() / 2) - targetBounds.getHeight();
        this.viewArea.setLocation((int) newViewAreaX, (int) newViewAreaY);

        sprites.get("TILE").forEach(sprite -> {
            renderSprite(sprite, g, offsetP, gameView);
        });

        sprites.forEach((setName, spriteList) -> {
            if (setName != "TILE") {
                spriteList.forEach(sprite -> {
                    renderSprite(sprite, g, offsetP, gameView);
                });
            }
        });

        if (target.isColliding()) {
            final Point collisionZoneLocation = new Point((int) target.getBounds().getX() + (int) offsetP.getX(), (int) target.getBounds().getY() + (int) offsetP.getY());
            drawCollisionZone(g, new Rectangle(collisionZoneLocation, target.getBounds().getSize()));
        }
        g2d.drawImage(target.getImage(), (int) targetP.getX(), (int) targetP.getY(), gameView);
    }

    private void renderSprite(GameSprite sprite, Graphics g, Point offsetP, GameView gameView) {
        final Graphics2D g2d = (Graphics2D) g;

        if (!sprite.equals(target)) {
            sprite.isVisible(sprite.getBounds().intersects(viewArea));
        }

        if (sprite.isVisible()) {
            if (sprite.isColliding()) {
                final Point collisionZoneLocation = new Point((int) sprite.getBounds().getX() + (int) offsetP.getX(), (int) sprite.getBounds().getY() + (int) offsetP.getY());
                drawCollisionZone(g, new Rectangle(collisionZoneLocation, sprite.getBounds().getSize()));
            }
            g2d.drawImage(sprite.getImage(), (int) sprite.getLocation().getX() + (int) offsetP.getX(), (int) sprite.getLocation().getY() + (int) offsetP.getY(), gameView);
        }
    }

    private double adjustXBoundary(double x) {
        final double halfWidth = viewArea.getWidth() / 2;

        if (target.getLocation().getX() <= halfWidth || target.getLocation().getX() >= (environmentDimension.getWidth() - halfWidth)) {
            return target.getLocation().getX();
        } else {
            return x;
        }
    }

    private double adjustYBoundary(double y) {
        final double halfHeight = viewArea.getHeight() / 2;

        if (target.getLocation().getY() <= halfHeight || target.getLocation().getY() >= (environmentDimension.getHeight() - halfHeight)) {
            return target.getLocation().getY();
        } else {
            return y;
        }
    }

    private void drawCollisionZone(Graphics g, Rectangle bounds) {
        final Color myColour = new Color(255, 0, 0, 128);
        g.setColor(myColour);
        g.fillRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());
    }

    /**
     * This method sets the target to follow.
     * @param target
     */
    public void setTarget(GameSprite target) {
        this.target = target;
    }
}
