package se.chriskevin.mysterymaze.ui;

import io.vavr.Function2;
import io.vavr.collection.List;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.geometry.Dimension;
import se.chriskevin.mysterymaze.geometry.Point3D;
import se.chriskevin.mysterymaze.utils.AWT;

import java.awt.*;

import static java.lang.Math.addExact;
import static java.lang.Math.subtractExact;
import static se.chriskevin.mysterymaze.environment.ImageUtil.getImage;
import static se.chriskevin.mysterymaze.environment.ImageUtil.imageMapKey;
import static se.chriskevin.mysterymaze.environment.utils.GameSpriteUtil.getPlayer;
import static se.chriskevin.mysterymaze.utils.Calculation.half;
import static se.chriskevin.mysterymaze.ui.GameView.drawCollisionZone;
import static se.chriskevin.mysterymaze.ui.GameView.renderSprite;
import static se.chriskevin.mysterymaze.utils.Calculation.subtract;

public final class Camera {

    public static void update(Dimension gameViewSize, Dimension environmentSize, List<GameSprite> sprites, GameView gameView, Graphics g) {
        var viewAreaSize = gameViewSize;

        var target = getPlayer.apply(sprites);

        var targetX = adjustCoordinateBoundary(half.apply(viewAreaSize.width) - half.apply(viewAreaSize.width), target.position.x, viewAreaSize.width, environmentSize.width);
        var targetY = adjustCoordinateBoundary(half.apply(viewAreaSize.height) - half.apply(target.size.height), target.position.y, viewAreaSize.height, environmentSize.height);

        var offsetP = Point3D.of(subtract.apply(targetX, target.position.x), subtract.apply(targetY, target.position.y), 0L);

        var viewAreaPoint = Point3D.of(
            target.position.x - half.apply(viewAreaSize.width) - target.size.width,
            target.position.y - half.apply(viewAreaSize.height) - target.size.height,
            0L
        );
        var viewArea = AWT.Rectangle.of(viewAreaPoint, gameViewSize);

        // All drawing should be moved to gameview
        // Sort list so that tiles list comes first
        getVisibleSprites.apply(sprites, viewArea)
            .filter(x -> !x.equals(target))
            .forEach(x -> renderSprite(g, gameView, offsetP, x));

        if (target.colliding) {
            var collisionZoneLocation = Point3D.of(addExact(target.position.x, offsetP.x), addExact(target.position.y, offsetP.y), 0L);
            drawCollisionZone(g, AWT.Rectangle.of(collisionZoneLocation, target.size));
        }

        var g2d = (Graphics2D) g;
        g2d.drawImage(getImage.apply(imageMapKey.apply(target.animationState, target.direction), target.images), targetX.intValue(), targetY.intValue(), gameView);
    }

    public static Long adjustCoordinateBoundary(Long coordinate, Long targetCoordinate, Long viewAreaMeasurement, Long environmentMeasurement) {
        return (targetCoordinate <= half.apply(viewAreaMeasurement) || targetCoordinate >= subtractExact(environmentMeasurement, half.apply(viewAreaMeasurement))) ? targetCoordinate : coordinate;
    }

    public static final Function2<List<GameSprite>, Rectangle, List<GameSprite>> getVisibleSprites =
        (sprites, viewArea) -> sprites.filter(x -> x.getBounds().intersects(viewArea));
}
