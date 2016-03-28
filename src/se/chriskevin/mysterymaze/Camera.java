package se.chriskevin.mysterymaze;

import java.awt.*;
import java.util.Collection;

/**
 * Created by CHSU7648 on 2016-03-14.
 */
final public class Camera {

    private Collection<GameSprite> gameSprites;

    private GameSprite target;

    private Rectangle viewArea;

    public Camera(Dimension dimension, Collection<GameSprite> gameSprites, GameSprite target) {
        this.viewArea = new Rectangle(new Point(0, 0), dimension);
        this.gameSprites = gameSprites;
        this.target = target;
    }

    public void update(Board board, Graphics g) {
        final Graphics2D g2d = (Graphics2D) g;

        final Rectangle targetBounds = target.getBounds();

        final Point targetP = new Point(((int) viewArea.getWidth() / 2) - ((int) targetBounds.getWidth() / 2), ((int) viewArea.getHeight() / 2) - ((int) targetBounds.getHeight() / 2));
        targetP.setLocation(adjustXBoundary(targetP.getX()), adjustYBoundary(targetP.getY()));

        final Point offsetP = new Point((int) targetP.getX() - (int) target.getLocation().getX(), (int) targetP.getY() - (int) target.getLocation().getY());

        double newViewAreaX = target.getLocation().getX() - (viewArea.getX() / 2) - targetBounds.getWidth();
        double newViewAreaY = target.getLocation().getY() - (viewArea.getY() / 2) - targetBounds.getHeight();
        this.viewArea.setLocation((int) newViewAreaX, (int) newViewAreaY);

        gameSprites.forEach(gameSprite -> {
            if (!gameSprite.equals(target)) {
                gameSprite.isVisible(gameSprite.getBounds().intersects(viewArea));
            }

            if (gameSprite.isVisible()) {
                if (gameSprite instanceof GameCharacter && ((GameCharacter) gameSprite).isColliding()) {
                    final Point collisionZoneLocation = new Point((int) gameSprite.getBounds().getX() + (int) offsetP.getX(), (int) gameSprite.getBounds().getY() + (int) offsetP.getY());
                    drawCollisionZone(g, new Rectangle(collisionZoneLocation, gameSprite.getBounds().getSize()));
                }
                g2d.drawImage(gameSprite.getImage(), (int) gameSprite.getLocation().getX() + (int) offsetP.getX(), (int) gameSprite.getLocation().getY() + (int) offsetP.getY(), board);
            }
        });

        if (((GameCharacter) target).isColliding()) {
            final Point collisionZoneLocation = new Point((int) target.getBounds().getX() + (int) offsetP.getX(), (int) target.getBounds().getY() + (int) offsetP.getY());
            drawCollisionZone(g, new Rectangle(collisionZoneLocation, target.getBounds().getSize()));
        }
        g2d.drawImage(target.getImage(), (int) targetP.getX(), (int) targetP.getY(), board);
    }

    private double adjustXBoundary(double x) {
        if (target.getLocation().getX() <= viewArea.getWidth() / 2) {
            return target.getLocation().getX();
        } else {
            return x;
        }
    }

    private double adjustYBoundary(double y) {
        if (target.getLocation().getY() <= viewArea.getHeight() / 2) {
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
