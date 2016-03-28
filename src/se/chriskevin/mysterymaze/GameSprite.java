package se.chriskevin.mysterymaze;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CHSU7648 on 2016-03-12.
 */
public class GameSprite implements GameObject {

    protected Point location;
    protected Dimension size;

    protected int scale;
    protected boolean visible;
    protected boolean blocking;

    protected AnimationState animationState;
    protected Direction direction;
    protected Image image;
    protected Map<String, Image> images;

    public GameSprite(Point location) {
        this.location = location;
        this.size = new Dimension(0, 0);
        this.scale = 1;

        blocking = false;
        visible = true;
        animationState = AnimationState.STOPPED;
        direction = Direction.DOWN;
    }

    public Rectangle getBounds() {
        return new Rectangle(location, size);
    }

    public Direction getDirection() {
        return this.direction;
    }

    public Image getImage() {
        final String key = animationState + "_" + direction;
        return (images != null && images.size() > 0) ? images.get(key) : image;
    }

    public Point getLocation() {
        return this.location;
    }

    public Dimension getSize() {
        return this.size;
    }

    public boolean isBlocking() {
        return blocking;
    }

    public void isBlocking(boolean blocking) {
        this.blocking = blocking;
    }

    public boolean isVisible() {
        return visible;
    }

    public void isVisible(boolean visible) {
        this.visible = visible;
    }

    public Image resize(int factor, BufferedImage originalImage) {
        return originalImage.getScaledInstance(factor * originalImage.getWidth(null), factor * originalImage.getHeight(null), BufferedImage.SCALE_SMOOTH);
    }

    public void setAnimationState(AnimationState animationState) {
        this.animationState = animationState;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setImage(String imageFilename) {
        image = loadImage(imageFilename);
        getImageDimensions();
    }

    public void setImages(Map<String, String> imageMap) {
        images = new HashMap<>();
        for (Map.Entry<String, String> entry: imageMap.entrySet()) {
            final Image image = loadImage(entry.getValue());
            images.put(entry.getKey(), image);
        }
        getImageDimensions();
    }

    private Image loadImage(String imageName) {
        try {
            //ImageIcon ii = new ImageIcon(getClass().getResource(imageName));
            BufferedImage originalImage = ImageIO.read(new File(getClass().getResource(imageName).toURI()));
            return resize(this.scale, originalImage);
            //return ii.getImage();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load image: " + imageName);
        }
    }

    protected void getImageDimensions() {
        final String key = animationState + "_" + direction;
        final Image img = (images != null && images.size() > 0) ? images.get(key) : image;
        size.setSize(img.getWidth(null), img.getHeight(null));
    }

    public void setScale(int factor) {
        this.scale = factor;
    }
}
