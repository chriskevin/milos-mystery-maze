package se.chriskevin.mysterymaze.ui;

import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.collection.List;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.geometry.Dimension;
import se.chriskevin.mysterymaze.geometry.Point3D;
import se.chriskevin.mysterymaze.utils.AWT;

import java.awt.*;

import static se.chriskevin.mysterymaze.environment.ImageUtil.getImage;
import static se.chriskevin.mysterymaze.environment.ImageUtil.imageMapKey;
import static se.chriskevin.mysterymaze.environment.utils.GameSpriteUtil.getPlayer;
import static se.chriskevin.mysterymaze.utils.Calculation.half;
import static se.chriskevin.mysterymaze.ui.GameView.drawCollisionZone;
import static se.chriskevin.mysterymaze.ui.GameView.renderSprite;

public final class Camera {

    public static Boolean isPossibleToCenter(Dimension viewAreaSize, Dimension environmentSize, GameSprite target) {
        return AWT.Rectangle.of(
            viewAreaSize.width,
            viewAreaSize.height,
            environmentSize.width - viewAreaSize.width,
            environmentSize.height - viewAreaSize.height
        ).intersects(AWT.Rectangle.of(target.position, target.size));
    }

    public static final Function3<Dimension, Dimension, GameSprite, Point3D> targetPointCenteredToViewArea =
        (viewAreaSize, environmentSize, target) -> {
            var intersection = Dimension.half(Dimension.subtract(viewAreaSize, target.size));
            return isPossibleToCenter(viewAreaSize, environmentSize, target)
                ? Point3D.of(intersection.width, intersection.height,0L)
                : target.position;
        };

    public static final Function2<Dimension, GameSprite, Point3D> viewAreaPointFrom =
        (viewAreaSize, target) -> Point3D.of(
            target.position.x - half(viewAreaSize.width) - target.size.width,
            target.position.y - half(viewAreaSize.height) - target.size.height,
            0L
        );

    public static void update(Dimension gameViewSize, Dimension environmentSize, List<GameSprite> sprites, GameView gameView, Graphics g, GameSprite target) {
        var centeredTargetP = targetPointCenteredToViewArea.apply(gameViewSize, environmentSize, target);
        var offsetP = Point3D.subtract(centeredTargetP, target.position);
        var viewAreaPoint = viewAreaPointFrom.apply(gameViewSize, target);
        var viewArea = AWT.Rectangle.of(viewAreaPoint, gameViewSize);

        //System.out.println("GameViewSize=" + gameViewSize);

        // Sort list so that tiles list comes first
        getVisibleSprites(sprites, viewArea)
            .filter(x -> !x.equals(target))
            .forEach(x -> renderSprite(g, gameView, Point3D.add(x.position, offsetP), x));

        if (target.colliding) {
            var collisionZoneLocation = Point3D.of(target.position.x + offsetP.x, target.position.y + offsetP.y, 0L);
            drawCollisionZone(g, AWT.Rectangle.of(collisionZoneLocation, target.size));
        }

        var g2d = (Graphics2D) g;
        g2d.drawImage(getImage(imageMapKey(target.animationState, target.direction), target.images), centeredTargetP.x.intValue(), centeredTargetP.y.intValue(), gameView);
    }


    public static List<GameSprite> getVisibleSprites(List<GameSprite> sprites, Rectangle viewArea) {
        return sprites.filter(x -> x.getBounds().intersects(viewArea));
    }
}
