package se.chriskevin.mysterymaze;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CHSU7648 on 2016-03-12.
 */
public class Sprite {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int scale;
    protected boolean visible;
    protected boolean blocking;

    protected AnimationState animationState;
    protected Direction direction;
    protected Image image;
    protected Map<String, Image> images;

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
        this.scale = 1;

        blocking = false;
        visible = true;
        animationState = AnimationState.STOPPED;
        direction = Direction.DOWN;
    }

    protected void getImageDimensions() {
        final String key = animationState + "_" + direction;
        final Image img = (images != null && images.size() > 0) ? images.get(key) : image;
        width = img.getWidth(null);
        height = img.getHeight(null);
    }

    public boolean isBlocking() {
        return blocking;
    }

    public void isBlocking(boolean blocking) {
        this.blocking = blocking;
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

    public Image getImage() {
        final String key = animationState + "_" + direction;
        return (images != null && images.size() > 0) ? images.get(key) : image;
    }

    public Image resize(int factor, BufferedImage originalImage) {
        return originalImage.getScaledInstance(factor * originalImage.getWidth(null), factor * originalImage.getHeight(null), BufferedImage.SCALE_SMOOTH);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setScale(int factor) {
        this.scale = factor;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isVisible() {
        return visible;
    }

    public void isVisible(boolean visible) {
        this.visible = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void setAnimationState(AnimationState animationState) {
        this.animationState = animationState;
    }
}
