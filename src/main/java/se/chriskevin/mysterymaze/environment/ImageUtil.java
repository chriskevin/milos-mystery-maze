package se.chriskevin.mysterymaze.environment;

import io.vavr.Function2;
import io.vavr.Tuple2;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import io.vavr.control.Try;
import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.geometry.Dimension;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import static se.chriskevin.mysterymaze.geometry.Dimension.ZERO_DIMENSION;

public final class ImageUtil {

    public static final String imageMapKey(AnimationState animationState, Direction direction) {
        return (animationState + "_" + direction);
    }

    public static final Image getImage(String key, Map<String, Image> images) {
        return images.get(key).getOrElse(/*images.get("DEFAULT").get()*/ new BufferedImage(1, 1, 1));
    }

    public static final Long multiplyImageWidth(Long factor, Image image) {
        return factor * image.getWidth(null);
    }

    public static final Long multiplyImageHeight(Long factor, Image image) {
        return factor * image.getHeight(null);
    }

    public static final Image resize(Long factor, Image image) {
        return image.getScaledInstance(
                multiplyImageWidth(factor, image).intValue(),
                multiplyImageHeight(factor, image).intValue(),
                BufferedImage.SCALE_SMOOTH
        );
    }

    public static final Option<BufferedImage> loadImage(String imageName) {
        return Try.of(() -> ImageIO.read(new File(ImageUtil.class.getResource(imageName).toURI())))
                .map(Option::of)
                .getOrElse(Option::none);
    }


    public static final Map<String, Option<Image>> convertToImages(Map<String, String> imageMap) {
        return imageMap.map((k, v) -> new Tuple2(k, loadImage(v)));
    }

    public static final Dimension imageDimension(Image image) {
        return Dimension.of(
            Long.valueOf(image.getWidth(null)),
            Long.valueOf(image.getHeight(null))
        );
    }

    public static final Dimension getSizeFromImageDimensions(AnimationState animationState, Direction direction, Map<String, Image> images) {
        return Option.of(getImage(imageMapKey(animationState, direction), images))
                .map(ImageUtil::imageDimension)
                .getOrElse(ZERO_DIMENSION);
    }
}
