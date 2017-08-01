package se.chriskevin.mysterymaze;

import se.chriskevin.mysterymaze.collision.CollisionHandler;
import se.chriskevin.mysterymaze.environment.GameEnvironment;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.ui.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Chris Sundberg on 2016-03-29.
 */
public class GameEngine implements ActionListener {

    private final static Integer DELAY = 25;

    private final GameEnvironment environment;
    private final GameView gameView;
    private Boolean paused;
    private final Timer timer;

    public GameEngine(GameView gameView, GameEnvironment environment) {
        this.gameView = gameView;
        this.environment = environment;
        this.paused = false;
        this.timer = new Timer(DELAY, this);

        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!paused) {
            //environment.sprites.forEach(GameEngine::act);
            //CollisionHandler.setCollisions(environment.sprites);
        }

        gameView.update();
    }

    public static void act(GameSprite sprite) {
        sprite.behavior.ifPresent((b) -> b.execute(sprite));
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
