package se.chriskevin.mysterymaze;

import io.vavr.Function2;
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
    private final GameView view;
    private Boolean paused;
    private final Timer timer;

    public static final Function2<GameView, GameEnvironment, GameEngine> createEngine =
        (view, environment) -> new GameEngine(view, environment);

    public GameEngine(GameView view, GameEnvironment environment) {
        this.view = view;
        this.environment = environment;
        this.paused = false;
        this.timer = new Timer(DELAY, this);

        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!paused) {
            //environment.sprites.forEach(GameEngine::act);
            //CollisionHandler.setCollisions.apply(environment.sprites);
        }

        view.update();
    }

    public static void act(GameSprite sprite) {
        sprite.behavior.toJavaOptional().ifPresent((b) -> b.execute(sprite));
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
