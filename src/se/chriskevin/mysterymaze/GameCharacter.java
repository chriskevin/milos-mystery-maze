package se.chriskevin.mysterymaze;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by CHSU7648 on 2016-03-04.
 */
public class GameCharacter extends GameSprite implements Actor {
    protected boolean active;
    protected boolean colliding;

    protected Point dLocation;

    protected int speed;
    protected Behavior behavior;

    public GameCharacter(Point location) {
        super(location);

        this.dLocation = new Point(0, 0);

        active = true;
        speed = 4;
        colliding = false;
    }

    public Point getDLocation() {
        return this.dLocation;
    }

    public int getSpeed() {
        return this.speed;
    }

    public boolean isActive() {
        return this.active;
    }

    public void isActive(boolean active) {
        this.active = active;
    }

    public boolean isColliding() {
        return colliding;
    }

    public void isColliding(boolean colliding) {
        this.colliding = colliding;
    }

    public void move() {
        if (colliding) {
            location.translate((int) -(dLocation.getX() * 1.5), (int) -(dLocation.getY() * 1.5));
            dLocation.setLocation(0, 0);
            colliding = false;
        } else {
            location.translate((int) dLocation.getX(), (int) dLocation.getY());
        }
    }

    public void keyPressed(KeyEvent e) {
        if (colliding)
            return;

        final int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dLocation.translate(-(speed * scale), 0);
            direction = Direction.LEFT;
            animationState = AnimationState.WALKING;
        }

        if (key == KeyEvent.VK_RIGHT) {
            System.out.println("Before: " + dLocation.toString());
            dLocation.translate(speed * scale, 0);
            System.out.println("After: " + dLocation.toString());
            direction = Direction.RIGHT;
            animationState = AnimationState.WALKING;
        }

        if (key == KeyEvent.VK_UP) {
            dLocation.translate(0, -(speed * scale));
            direction = Direction.UP;
            animationState = AnimationState.WALKING;
        }

        if (key == KeyEvent.VK_DOWN) {
            dLocation.translate(0, speed * scale);
            direction = Direction.DOWN;
            animationState = AnimationState.WALKING;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (colliding)
            return;

        final int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dLocation.setLocation(0, dLocation.getY());
            animationState = AnimationState.STOPPED;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dLocation.setLocation(0, dLocation.getY());
            animationState = AnimationState.STOPPED;
        }

        if (key == KeyEvent.VK_UP) {
            dLocation.setLocation(dLocation.getX(), 0);
            animationState = AnimationState.STOPPED;
        }

        if (key == KeyEvent.VK_DOWN) {
            dLocation.setLocation(dLocation.getX(), 0);
            animationState = AnimationState.STOPPED;
        }
    }

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
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
}
