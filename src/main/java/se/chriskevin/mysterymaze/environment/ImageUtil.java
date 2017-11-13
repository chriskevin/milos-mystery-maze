package se.chriskevin.mysterymaze.environment;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;
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
import java.util.function.Function;

import static se.chriskevin.mysterymaze.geometry.Dimension.ZERO_DIMENSION;
import static se.chriskevin.mysterymaze.geometry.Dimension.dimension;

/**
 * Created by Chris Sundberg on 2017-06-18.
 */
public final class ImageUtil {

    public static final Function2<AnimationState, Direction, String> imageMapKey =
        (animationState, direction) -> (animationState + "_" + direction);

    public static final Function2<String, Map<String, Image>, Image> getImage =
        (key, images) -> images.get(key).orElse(images.get("DEFAULT")).get();

    public static final Function2<Integer, Image, Integer> multiplyImageWidth =
        (factor, image) -> factor * image.getWidth(null);

    public static final Function2<Integer, Image, Integer> multiplyImageHeight =
        (factor, image) -> factor * image.getHeight(null);

    public static final Function2<Integer, Image, Image> resize =
        (factor, image) -> image.getScaledInstance(
            multiplyImageWidth.apply(factor, image),
            multiplyImageHeight.apply(factor, image),
            BufferedImage.SCALE_SMOOTH
        );

    public static final Function<String, Try<Image>> loadImage =
        imageName -> Try.of(() -> ImageIO.read(new File(ImageUtil.class.getResource(imageName).toURI())));


    public static final Function<Map<String, String>, Map<String, Option<Image>>> convertToImages =
        imageMap -> imageMap.map((k, v) -> new Tuple2(k, loadImage.apply(v)));

    public static final Function1<Image, Long> imageWidthAsLong =
        (image) -> Long.valueOf(image.getWidth(null));

    public static final Function1<Image, Long> imageHeightAsLong =
        (image) -> Long.valueOf(image.getHeight(null));


    public static final Function3<AnimationState, Direction, Map<String, Image>, Dimension> getSizeFromImageDimensions =
        (animationState, direction, images) ->
            Option.of(getImage.apply(imageMapKey.apply(animationState, direction), images))
                .map(x -> dimension.apply(imageWidthAsLong.apply(x), imageHeightAsLong.apply(x)))
                .getOrElse(ZERO_DIMENSION);
}
