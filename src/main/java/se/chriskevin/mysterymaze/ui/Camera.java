package se.chriskevin.mysterymaze.ui;

import io.vavr.Function2;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.geometry.Dimension;
import se.chriskevin.mysterymaze.geometry.Point3D;

import java.awt.*;
import java.util.List;

import static java.lang.Math.addExact;
import static java.lang.Math.subtractExact;
import static java.util.stream.Collectors.toList;
import static se.chriskevin.mysterymaze.environment.ImageUtil.getImage;
import static se.chriskevin.mysterymaze.environment.ImageUtil.imageMapKey;
import static se.chriskevin.mysterymaze.environment.utils.GameSpriteUtil.getPlayer;
import static se.chriskevin.mysterymaze.utils.Calculation.half;
import static se.chriskevin.mysterymaze.ui.GameView.drawCollisionZone;
import static se.chriskevin.mysterymaze.ui.GameView.renderSprite;
import static se.chriskevin.mysterymaze.utils.Calculation.subtract;

/**
 * Created by Chris Sundberg on 2016-03-14.
 */
public final class Camera {

    public static void update(Dimension gameViewSize, Dimension environmentSize, List<GameSprite> sprites, GameView gameView, Graphics g) {
        final Dimension viewAreaSize = gameViewSize;

        final GameSprite target = getPlayer.apply(sprites);

        final Long targetX = adjustCoordinateBoundary(half.apply(viewAreaSize.width) - half.apply(viewAreaSize.width), target.position.x, viewAreaSize.width, environmentSize.width);
        final Long targetY = adjustCoordinateBoundary(half.apply(viewAreaSize.height) - half.apply(target.size.height), target.position.y, viewAreaSize.height, environmentSize.height);

        final Point3D offsetP = new Point3D(subtract.apply(targetX, target.position.x), subtract.apply(targetY, target.position.y), 0L);

        final Long viewAreaX = target.position.x - half.apply(viewAreaSize.width) - target.size.width;
        final Long viewAreaY = target.position.y - half.apply(viewAreaSize.height) - target.size.height;
        final Rectangle viewArea = new Rectangle(viewAreaX.intValue(), viewAreaY.intValue(), gameViewSize.width.intValue(), gameViewSize.height.intValue());

        // All drawing should be moved to gameview
        // Sort list so that tiles list comes first
        getVisibleSprites.apply(sprites).apply(viewArea)
            .stream()
            .filter(x -> !x.equals(target))
            .forEach(x -> renderSprite(g, gameView, offsetP, x));

        if (target.colliding) {
            final Point3D collisionZoneLocation = new Point3D(addExact(target.position.x, offsetP.x), addExact(target.position.y, offsetP.y), 0L);
            drawCollisionZone(g, new Rectangle(collisionZoneLocation.x.intValue(), collisionZoneLocation.y.intValue(), target.size.width.intValue(), target.size.height.intValue()));
        }

        final Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(getImage.apply(imageMapKey.apply(target.animationState, target.direction), target.images), targetX.intValue(), targetY.intValue(), gameView);
    }

    public static Long adjustCoordinateBoundary(Long coordinate, Long targetCoordinate, Long viewAreaMeasurement, Long environmentMeasurement) {
        return (targetCoordinate <= half.apply(viewAreaMeasurement) || targetCoordinate >= subtractExact(environmentMeasurement, half.apply(viewAreaMeasurement))) ? targetCoordinate : coordinate;
    }

    public static final Function2<List<GameSprite>, Rectangle, List<GameSprite>> getVisibleSprites =
        (sprites, viewArea) -> sprites.stream().filter(x -> x.getBounds().intersects(viewArea)).collect(toList());
}
