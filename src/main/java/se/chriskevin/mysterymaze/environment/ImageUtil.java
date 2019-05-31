package se.chriskevin.mysterymaze.environment;

import static se.chriskevin.mysterymaze.geometry.Dimension.ZERO_DIMENSION;

import io.vavr.Tuple2;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import io.vavr.control.Try;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.geometry.Dimension;

public final class ImageUtil {

  public static String imageMapKey(final AnimationState animationState, final Direction direction) {
    return (animationState + "_" + direction);
  }

  public static Image getImage(final String key, final Map<String, Image> images) {
    return images.get(key).getOrElse(/*images.get("DEFAULT").get()*/ new BufferedImage(1, 1, 1));
  }

  public static Long multiplyImageWidth(final Long factor, final Image image) {
    return factor * image.getWidth(null);
  }

  public static Long multiplyImageHeight(final Long factor, final Image image) {
    return factor * image.getHeight(null);
  }

  public static Image resize(final Long factor, final Image image) {
    return image.getScaledInstance(
        multiplyImageWidth(factor, image).intValue(),
        multiplyImageHeight(factor, image).intValue(),
        BufferedImage.SCALE_SMOOTH);
  }

  public static Option<BufferedImage> loadImage(final String imageName) {
    return Try.of(() -> ImageIO.read(new File(ImageUtil.class.getResource(imageName).toURI())))
        .map(Option::of)
        .getOrElse(Option::none);
  }

  public static Map<String, Option<Image>> convertToImages(final Map<String, String> imageMap) {
    return imageMap.map((k, v) -> new Tuple2(k, loadImage(v)));
  }

  public static Dimension imageDimension(final Image image) {
    return Dimension.of((long) image.getWidth(null), (long) image.getHeight(null));
  }

  public static Dimension getSizeFromImageDimensions(
      final AnimationState animationState,
      final Direction direction,
      final Map<String, Image> images) {
    return Option.of(getImage(imageMapKey(animationState, direction), images))
        .map(ImageUtil::imageDimension)
        .getOrElse(ZERO_DIMENSION);
  }
}
