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

import static se.chriskevin.mysterymaze.geometry.Dimension.ZERO_DIMENSION;

public final class ImageUtil {

    public static final Function2<AnimationState, Direction, String> imageMapKey =
        (animationState, direction) -> (animationState + "_" + direction);

    public static final Function2<String, Map<String, Image>, Image> getImage =
        (key, images) -> images.get(key).orElse(images.get("DEFAULT")).get();

    public static final Function2<Long, Image, Long> multiplyImageWidth =
        (factor, image) -> factor * image.getWidth(null);

    public static final Function2<Long, Image, Long> multiplyImageHeight =
        (factor, image) -> factor * image.getHeight(null);

    public static final Function2<Long, Image, Image> resize =
        (factor, image) -> image.getScaledInstance(
            multiplyImageWidth.apply(factor, image).intValue(),
            multiplyImageHeight.apply(factor, image).intValue(),
            BufferedImage.SCALE_SMOOTH
        );

    public static final Function1<String, Option<BufferedImage>> loadImage =
        imageName -> Try.of(() -> ImageIO.read(new File(ImageUtil.class.getResource(imageName).toURI())))
            .map(Option::of)
            .getOrElse(Option::none);


    public static final Function1<Map<String, String>, Map<String, Option<Image>>> convertToImages =
        imageMap -> imageMap
                .map((k, v) -> new Tuple2(k, loadImage.apply(v)));

    public static final Function1<Image,Dimension> imageDimension =
        (image) -> Dimension.of(
            Long.valueOf(image.getWidth(null)),
            Long.valueOf(image.getHeight(null))
        );


    public static final Function3<AnimationState, Direction, Map<String, Image>, Dimension> getSizeFromImageDimensions =
        (animationState, direction, images) ->
            Option.of(getImage.apply(imageMapKey.apply(animationState, direction), images))
                .map(imageDimension)
                .getOrElse(ZERO_DIMENSION);
}
