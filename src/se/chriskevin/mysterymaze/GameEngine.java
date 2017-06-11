package se.chriskevin.mysterymaze;

import se.chriskevin.mysterymaze.behavior.MoveBehavior;
import se.chriskevin.mysterymaze.collision.CollisionHandler;
import se.chriskevin.mysterymaze.environment.GameEnvironment;
import se.chriskevin.mysterymaze.environment.GameSprite;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by CHSU7648 on 2016-03-29.
 */
public class GameEngine implements ActionListener {

    private final int DELAY = 25;

    private GameEnvironment environment;

    private se.chriskevin.mysterymaze.ui.GameView gameView;

    private boolean paused;

    private Timer timer;

    public GameEngine(se.chriskevin.mysterymaze.ui.GameView gameView, GameEnvironment environment) {
        this.gameView = gameView;
        this.environment = environment;

        paused = false;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!paused) {
            //environment.detectCollision();
            environment.getSprites().forEach((setName, spriteList) -> spriteList.forEach(GameSprite::act));
            CollisionHandler.setCollisions(environment.getSprites());
        }

        gameView.update();
    }

    public boolean isPaused() {
        return paused;
    }

    public void togglePaused() {
        paused = !paused;

        if (!paused) {
            timer.start();
        } else {
            timer.stop();
        }
    }
}
