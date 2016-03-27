package se.chriskevin.mysterymaze;

import java.awt.event.KeyEvent;

/**
 * Created by CHSU7648 on 2016-03-04.
 */
public class Character extends Sprite implements Actor {
    protected boolean active;
    protected boolean colliding;
    protected int dx;
    protected int dy;
    protected int speed;
    protected Behavior behavior;

    public Character(int x, int y) {
        super(x, y);

        active = true;
        speed = 4;
        colliding = false;
    }

    public int getDx() {
        return this.dx;
    }

    public int getDy() {
        return this.dy;
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
            x += -dx*1.5;
            y += -dy*1.5;
            dx = 0;
            dy = 0;
            colliding = false;
        } else {
            x += dx;
            y += dy;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (colliding)
            return;

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SHIFT) {

        }

        if (key == KeyEvent.VK_LEFT) {
            dx = -speed * scale;
            direction = Direction.LEFT;
            animationState = AnimationState.WALKING;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = speed * scale;
            direction = Direction.RIGHT;
            animationState = AnimationState.WALKING;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -speed * scale;
            direction = Direction.UP;
            animationState = AnimationState.WALKING;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = speed * scale;
            direction = Direction.DOWN;
            animationState = AnimationState.WALKING;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (colliding)
            return;

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
            animationState = AnimationState.STOPPED;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
            animationState = AnimationState.STOPPED;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
            animationState = AnimationState.STOPPED;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
            animationState = AnimationState.STOPPED;
        }
    }

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    @Override
    public void act() {
        if (this.behavior != null) {
            this.behavior.execute(this);
        }
    }

    @Override
    public void act(Behavior behavior) {
        if (behavior != null) {
            behavior.execute(this);
        }
    }
}
