package se.chriskevin.mysterymaze.ui;

import se.chriskevin.mysterymaze.environment.GameSprite;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Created by CHSU7648 on 2016-03-14.
 */
public final class Camera {

    public static void update(Dimension gameViewDimension, Dimension environmentDimension, Map<String, List<GameSprite>> sprites, GameView gameView, Graphics g) {
        final Rectangle viewArea = new Rectangle(new Point(0, 0), gameViewDimension);
        final GameSprite target = sprites.get("PLAYER").get(0);
        final Graphics2D g2d = (Graphics2D) g;

        final Rectangle targetBounds = target.getBounds();

        int viewAreaWidth = (int) viewArea.getWidth();
        int viewAreaHeight = (int) viewArea.getHeight();

        int targetAreaWidth = (int) targetBounds.getWidth();
        int targetAreaHeight = (int) targetBounds.getHeight();

        final Point targetP = new Point((viewAreaWidth / 2) - (targetAreaWidth / 2), (viewAreaHeight / 2) - (targetAreaHeight / 2));
        targetP.setLocation(adjustXBoundary(targetP.getX(), viewArea, environmentDimension, target), adjustYBoundary(targetP.getY(), viewArea, environmentDimension, target));

        final Point offsetP = new Point((int) targetP.getX() - (int) target.getLocation().getX(), (int) targetP.getY() - (int) target.getLocation().getY());

        double newViewAreaX = target.getLocation().getX() - (viewArea.getX() / 2) - targetBounds.getWidth();
        double newViewAreaY = target.getLocation().getY() - (viewArea.getY() / 2) - targetBounds.getHeight();
        viewArea.setLocation((int) newViewAreaX, (int) newViewAreaY);

        sprites.get("TILE").forEach(sprite -> {
            renderSprite(sprite, g, offsetP, gameView, target, viewArea);
        });

        sprites.forEach((setName, spriteList) -> {
            if (setName != "TILE") {
                spriteList.forEach(sprite -> {
                    renderSprite(sprite, g, offsetP, gameView, target, viewArea);
                });
            }
        });

        if (target.isColliding()) {
            final Point collisionZoneLocation = new Point((int) target.getBounds().getX() + (int) offsetP.getX(), (int) target.getBounds().getY() + (int) offsetP.getY());
            drawCollisionZone(g, new Rectangle(collisionZoneLocation, target.getBounds().getSize()));
        }
        g2d.drawImage(target.getImage(), (int) targetP.getX(), (int) targetP.getY(), gameView);
    }

    public static void renderSprite(GameSprite sprite, Graphics g, Point offsetP, GameView gameView, GameSprite target, Rectangle viewArea) {
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

    public static double adjustXBoundary(double x, Rectangle viewArea, Dimension environmentDimension, GameSprite target) {
        final double halfWidth = viewArea.getWidth() / 2;
        return (target.getLocation().getX() <= halfWidth || target.getLocation().getX() >= (environmentDimension.getWidth() - halfWidth)) ? target.getLocation().getX() : x;
    }

    public static double adjustYBoundary(double y, Rectangle viewArea, Dimension environmentDimension, GameSprite target) {
        final double halfHeight = viewArea.getHeight() / 2;
        return (target.getLocation().getY() <= halfHeight || target.getLocation().getY() >= (environmentDimension.getHeight() - halfHeight)) ? target.getLocation().getY() : y;
    }

    public static void drawCollisionZone(Graphics g, Rectangle bounds) {
        final Color myColour = new Color(255, 0, 0, 128);
        g.setColor(myColour);
        g.fillRect((int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());
    }
}
