package se.chriskevin.mysterymaze.environment;

import se.chriskevin.mysterymaze.animation.AnimationState;
import se.chriskevin.mysterymaze.animation.Direction;
import se.chriskevin.mysterymaze.behavior.Actor;
import se.chriskevin.mysterymaze.behavior.Behavior;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CHSU7648 on 2016-03-12.
 */
public class GameSprite implements GameObject, Actor {

    protected Behavior behavior;

    protected boolean colliding;

    protected Point location;
    protected Dimension size;

    protected int scale;
    protected boolean visible;
    protected boolean blocking;

    protected AnimationState animationState;
    protected Direction direction;
    protected Image image;
    protected Map<String, Image> images;

    protected Point dLocation;

    protected int speed;

    public GameSprite(Point location) {
        this.location = location;
        this.dLocation = new Point(0, 0);
        this.size = new Dimension(0, 0);
        this.scale = 1;

        animationState = AnimationState.STOPPED;
        blocking = false;
        colliding = false;
        direction = Direction.DOWN;
        speed = 4;
        visible = true;
    }

    @Override
    public void act() {
        if (behavior != null) {
            behavior.execute(this);
        }
    }

    @Override
    public void act(Behavior behavior) {
        if (behavior != null) {
            behavior.execute(this);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(location, size);
    }

    public Direction getDirection() {
        return this.direction;
    }

    public Point getDLocation() {
        return this.dLocation;
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

    public int getSpeed() {
        return this.speed * scale;
    }

    public boolean isBlocking() {
        return blocking;
    }

    public void isBlocking(boolean blocking) {
        this.blocking = blocking;
    }

    public boolean isColliding() {
        return colliding;
    }

    public void isColliding(boolean colliding) {
        this.colliding = colliding;
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

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
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
            BufferedImage originalImage = ImageIO.read(new File(getClass().getResource(imageName).toURI()));
            return resize(this.scale, originalImage);
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

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
