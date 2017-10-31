package se.chriskevin.mysterymaze.environment;

import io.vavr.Function2;
import io.vavr.control.Try;
import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Chris Sundberg on 2017-06-18.
 */
public final class ImageUtil {

    public static final Function2<AnimationState, Direction, String> imageMapKey =
        (animationState, direction) -> (animationState + "_" + direction);

    public static final Function2<String, Map<String, Image>, Image> getImage =
        (key, images) -> Optional.ofNullable(images.get(key)).orElse(images.get("DEFAULT"));

    public static final Function2<Integer, BufferedImage, Image> resize =
        (factor, image) -> image.getScaledInstance(
            (factor * image.getWidth(null)),
            (factor * image.getHeight(null)),
            BufferedImage.SCALE_SMOOTH
        );

    public static final Function<String, Try<Image>> loadImage =
        imageName -> Try.of(() -> ImageIO.read(new File(ImageUtil.class.getResource(imageName).toURI())));


    public static final Function<Map<String, String>, Map<String, Optional<Image>>> convertToImages =
        imageMap -> imageMap.entrySet()
            .stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                (d) -> loadImage.apply(d.getValue()).toJavaOptional()
            ));
}
