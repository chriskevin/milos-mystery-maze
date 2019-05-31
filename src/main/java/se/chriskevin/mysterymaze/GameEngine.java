package se.chriskevin.mysterymaze;

import io.vavr.Function2;
import io.vavr.collection.List;
import se.chriskevin.mysterymaze.environment.GameEnvironment;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.ui.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameEngine implements ActionListener {

    private final static Integer DELAY = 25;

    private GameEnvironment environment;
    private List<GameSprite> blockingSprites;
    private final GameView view;
    private Boolean paused;
    private final Timer timer;

    public static final Function2<GameView, GameEnvironment, GameEngine> createEngine =
            GameEngine::of;

    public GameEngine(final GameView view, final GameEnvironment environment) {
        this.view = view;
        this.environment = environment;
        this.blockingSprites = environment.sprites.filter(sprite -> sprite.blocking);
        this.paused = false;
        this.timer = new Timer(DELAY, this);

        timer.start();
    }

    public static GameEngine of(final GameView view, final GameEnvironment environment) {
        return new GameEngine(view, environment);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (!paused) {
            //environment.sprites.forEach(GameEngine::act);
            //CollisionHandler.setCollisions.apply(environment.sprites);
        }

        view.update();
    }

    public static void act(final GameSprite sprite) {
        sprite.behavior.forEach((b) -> {/* b.apply(sprite) */});
    }

    public boolean isPaused() {
        return paused;
    }

    public void setEnvironment(final GameEnvironment environment) {
        this.environment = environment;
        this.blockingSprites = environment.sprites.filter(sprite -> sprite.blocking);
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
