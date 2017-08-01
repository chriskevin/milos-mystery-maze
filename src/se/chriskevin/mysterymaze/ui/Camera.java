package se.chriskevin.mysterymaze.ui;

import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.environment.SpriteType;
import se.chriskevin.mysterymaze.geometry.Dimension;
import se.chriskevin.mysterymaze.geometry.Point3D;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static se.chriskevin.mysterymaze.Calculation.half;
import static se.chriskevin.mysterymaze.environment.ImageUtil.getImage;
import static se.chriskevin.mysterymaze.ui.GameView.drawCollisionZone;
import static se.chriskevin.mysterymaze.ui.GameView.renderSprite;

/**
 * Created by Chris Sundberg on 2016-03-14.
 */
public final class Camera {

    public static void update(Dimension<Integer> gameViewSize, Dimension<Integer> environmentSize, List<GameSprite> sprites, GameView gameView, Graphics g) {
        final Dimension<Integer> viewAreaSize = gameViewSize;

        final GameSprite target = sprites.stream().filter(x -> SpriteType.PLAYER.equals(x.type)).collect(toList()).get(0);

        final Integer targetX = adjustCoordinateBoundary(half(viewAreaSize.width) - half(viewAreaSize.width), target.position.x, viewAreaSize.width, environmentSize.width);
        final Integer targetY = adjustCoordinateBoundary(half(viewAreaSize.height) - half(target.size.height), target.position.y, viewAreaSize.height, environmentSize.height);

        final Point3D offsetP = new Point3D(targetX - target.position.x, targetY - target.position.y, 0);

        final Integer viewAreaX = target.position.x - half(viewAreaSize.width) - target.size.width;
        final Integer viewAreaY = target.position.y - half(viewAreaSize.height) - target.size.height;
        final Rectangle viewArea = new Rectangle(viewAreaX, viewAreaY, gameViewSize.width, gameViewSize.height);

        // All drawing should be moved to gameview
        // Sort list so that tiles list comes first
        getVisibleSprites(sprites, target, viewArea)
            .forEach(x -> renderSprite(g, gameView, offsetP, x));

        if (target.colliding) {
            final Point3D collisionZoneLocation = new Point3D(target.position.x + offsetP.x, target.position.y + offsetP.y, 0);
            drawCollisionZone(g, new Rectangle(collisionZoneLocation.x, collisionZoneLocation.y, target.size.width, target.size.height));
        }

        final Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(getImage(target.animationState, target.direction, target.images), targetX, targetY, gameView);
    }

    public static Integer adjustCoordinateBoundary(Integer coordinate, Integer targetCoordinate, Integer viewAreaMeasurement, Integer environmentMeasurement) {
        return (targetCoordinate <= half(viewAreaMeasurement) || targetCoordinate >= (environmentMeasurement - half(viewAreaMeasurement))) ? targetCoordinate : coordinate;
    }

    public static List<GameSprite> getVisibleSprites(List<GameSprite> sprites, GameSprite target, Rectangle viewArea) {
        return sprites
                .stream()
                .filter(x -> !x.equals(target) && x.getBounds().intersects(viewArea))
                .collect(toList());
    }
}
