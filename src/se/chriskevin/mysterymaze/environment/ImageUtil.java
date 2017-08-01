package se.chriskevin.mysterymaze.environment;

import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Chris Sundberg on 2017-06-18.
 */
public final class ImageUtil {

    public static Image getImage(AnimationState animationState, Direction direction, Map<String, Image> images) {
        final String key = animationState + "_" + direction;
        return (images != null && images.size() > 0) ? images.get(key) : images.get("DEFAULT");
    }

    public static Image resize(int factor, BufferedImage originalImage) {
        return originalImage.getScaledInstance(factor * originalImage.getWidth(null), factor * originalImage.getHeight(null), BufferedImage.SCALE_SMOOTH);
    }

    public static Optional<Image> loadImage(String imageName, Integer scale) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(ImageUtil.class.getResource(imageName).toURI()));
            return Optional.of(resize(scale, originalImage));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static Map<String, Image> convertToImages(Map<String, String> imageMap, Integer scale) {
        return imageMap
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    (d) -> {
                        final Optional<Image> image = loadImage(d.getValue(), scale);
                        return image.isPresent() ? image.get() : null;
                    }
                ));
    }
}
