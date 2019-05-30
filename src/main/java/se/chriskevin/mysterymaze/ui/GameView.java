package se.chriskevin.mysterymaze.ui;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import rx.Observable;
import rx.observables.SwingObservable;
import se.chriskevin.mysterymaze.GameEngine;
import se.chriskevin.mysterymaze.environment.GameEnvironment;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.environment.SpriteType;
import se.chriskevin.mysterymaze.geometry.Dimension;
import se.chriskevin.mysterymaze.geometry.Point3D;
import se.chriskevin.mysterymaze.utils.AWT;
import se.chriskevin.mysterymaze.utils.CLI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

import static se.chriskevin.mysterymaze.behavior.MoveBehavior.*;
import static se.chriskevin.mysterymaze.behavior.StopBehavior.*;
import static se.chriskevin.mysterymaze.environment.ImageUtil.getImage;
import static se.chriskevin.mysterymaze.environment.ImageUtil.imageMapKey;
import static se.chriskevin.mysterymaze.environment.utils.GameSpriteUtil.getByType;
import static se.chriskevin.mysterymaze.environment.utils.GameSpriteUtil.getPlayer;

public final class GameView extends JPanel {

    // TODO: Move this
    private Map<Integer, Function1<GameSprite, GameSprite>> keyMapStop = HashMap.of(
        KeyEvent.VK_DOWN, stopDown,
        KeyEvent.VK_LEFT, stopLeft,
        KeyEvent.VK_RIGHT, stopRight,
        KeyEvent.VK_UP, stopUp
    );

    private Map<Integer, Function1<GameSprite, GameSprite>> keyMapMove = HashMap.of(
        KeyEvent.VK_DOWN, moveDown,
        KeyEvent.VK_LEFT, moveLeft,
        KeyEvent.VK_RIGHT, moveRight,
        KeyEvent.VK_UP, moveUp
    );

    private CLI cli;
    private Boolean inputEnabled;
    private Dimension dimension;
    private GameEngine engine;
    private GameEnvironment environment;
    private int counter = 0;


    public GameView(Dimension dimension, GameEnvironment environment) {
        this.environment = environment;
        this.dimension = dimension;

        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(AWT.Dimension.of(dimension));
        setDoubleBuffered(true);

        inputEnabled = true;
        cli = new CLI();

        final var o = Observable
                    .fromCallable(() -> 1);

        SwingObservable
            .fromKeyEvents(this)
            .debounce(200L, TimeUnit.MILLISECONDS)
            .subscribe((keyEvent) -> {
                final Integer key = keyEvent.getKeyCode();
                final var pressed = keyEvent.paramString().split(",")[0].equals("KEY_PRESSED");

                System.out.println("key=" + key);

                if (pressed) {
                    setEnvironment(actOnPlayerAction(
                        engine,
                        keyMapMove,
                        key,
                        getEnvironment(),
                        anyCollision.apply(getEnvironment().sprites.filter(x -> x.blocking))
                    ));
                } else {
                    if (key.equals(KeyEvent.VK_SPACE)) {
                        cli.isEnabled(!cli.isEnabled());
                        engine.togglePaused();
                    }

                    actOnCliAction(cli, key);

                    if (cli.isEnabled() && key.equals(KeyEvent.VK_SPACE)) {
                        engine.togglePaused();
                    } else {
                        setEnvironment(actOnPlayerAction(
                            engine,
                            keyMapStop,
                            key,
                            getEnvironment(),
                            anyCollision.apply(getEnvironment().sprites.filter(x -> x.blocking))
                        ));
                    }
                }
            });
    }

    private GameEnvironment getEnvironment() {
        return this.environment;
    }

    private void setEnvironment(GameEnvironment environment) {
        this.environment = environment;
    }

    public static final GameView of(Dimension dimension, GameEnvironment environment) {
        return new GameView(dimension, environment);
    }

    public static final void renderSprite(Graphics g, GameView gameView, Point3D spriteOffset, GameSprite sprite) {
        var g2d = (Graphics2D) g;

        if (sprite.colliding) {
            drawCollisionZone(g, AWT.Rectangle.of(spriteOffset, sprite.size));
        }

        g2d.drawImage(
            getImage(imageMapKey(sprite.animationState, sprite.direction), sprite.images),
            spriteOffset.x.intValue(),
            spriteOffset.y.intValue(),
            gameView
        );
    }

    public static final void drawCollisionZone(Graphics g, Rectangle bounds) {
        var myColour = new Color(255, 0, 0, 128);
        g.setColor(myColour);  
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public void enableInput(boolean enabled) {
        this.inputEnabled = enabled;
    }

    public void setEngine(GameEngine engine) {
        this.engine = engine;
    }

    public void update() {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        counter = (counter < environment.sprites.size()) ? counter + 1 : 0;
        // var target = environment.sprites.get(counter);
        var target = getPlayer(environment.sprites);
        Camera.update(dimension, environment.size, environment.sprites, this, g, target);
        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {
        var myColour = new Color(0, 0, 0, 128);
        g.setColor(myColour);
        g.fillRect(0, 0, 1024, 100);

        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        var ingameMsg = !engine.isPaused() ? "Playing " : "Paused ";
        var levelMsg = " Level 1";
        var enemyMsg = " Enemies: " + getByType(SpriteType.ENEMY, environment.sprites).size();
        var player = getPlayer(environment.sprites);

        var coordsMsg =
            new StringBuilder()
                .append(" X: ")
                .append(player.position.x)
                .append(" Y: ")
                .append(player.position.y)
                .append(" Colliding: ")
                .append(player.colliding)
                .toString();

        var debugMsg =
            new StringBuilder()
                .append(ingameMsg)
                .append(levelMsg)
                .append(enemyMsg)
                .append(coordsMsg)
                .toString();

        g.drawString(debugMsg, 32, 32);
        drawCli(g);
        g.dispose();
    }

    private void drawCli(Graphics g) {
        g.drawString("Console: " + cli.getCurrentCommand(), 32, 64);
    }

    public static void actOnCliAction(CLI cli, Integer key) {
        if (cli.isEnabled()) {
            if (key >= 65 && key <= 90 || key.equals(KeyEvent.VK_SPACE)) {
                var currentCommand = cli.getCurrentCommand() + (char) key.intValue();
                cli.setCurrentCommand(currentCommand);
            } else if (key.equals(KeyEvent.VK_BACK_SPACE)) {
                var currentCommand = cli.getCurrentCommand();
                cli.setCurrentCommand(currentCommand.substring(0, currentCommand.length() - 1));
            } else if (key.equals(KeyEvent.VK_ENTER)) {
                cli.run();
            }
        }
    }

    public static GameEnvironment actOnPlayerAction(
            GameEngine engine,
            Map<Integer, Function1<GameSprite, GameSprite>> keyMap,
            Integer key,
            GameEnvironment environment,
            Function1<GameSprite, Boolean> checkCollision
    ) {
        if (!engine.isPaused() && keyMap.get(key).isDefined()) {
            var player = getPlayer(environment.sprites);
            var updatedPlayer = keyMap.get(key)
                    .map(b -> b.apply(player))
                    .getOrNull();

            return (checkCollision.apply(updatedPlayer)) ?
                environment
                : GameEnvironment.of(
                    environment.size,
                    environment.sprites.replace(player, updatedPlayer)
                );
        } else {
            return environment;
        }
    }

    public static final Function2<GameSprite, GameSprite, Boolean> isColliding =
        (target, blocker) ->
            AWT.Rectangle.of(blocker.position, blocker.size)
                .intersects(AWT.Rectangle.of(target.position, target.size));

    public static final Function2<List<GameSprite>, GameSprite, Boolean> anyCollision =
        (blockingSprites, sprite) ->
            blockingSprites
                .remove(sprite)
                .toJavaStream()
                .anyMatch((blocker) -> isColliding.apply(sprite, blocker));
}
