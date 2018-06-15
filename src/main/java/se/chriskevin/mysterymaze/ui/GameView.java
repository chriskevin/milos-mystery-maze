package se.chriskevin.mysterymaze.ui;

import io.vavr.Function2;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import se.chriskevin.mysterymaze.GameEngine;
import se.chriskevin.mysterymaze.behavior.Behavior;
import se.chriskevin.mysterymaze.behavior.MoveBehavior;
import se.chriskevin.mysterymaze.behavior.StopBehavior;
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

import static se.chriskevin.mysterymaze.environment.ImageUtil.getImage;
import static se.chriskevin.mysterymaze.environment.ImageUtil.imageMapKey;
import static se.chriskevin.mysterymaze.environment.utils.GameSpriteUtil.getByType;
import static se.chriskevin.mysterymaze.environment.utils.GameSpriteUtil.getPlayer;
import static se.chriskevin.mysterymaze.utils.Calculation.add;

public final class GameView extends JPanel {

    // Move this
    private Map<Integer, Behavior> keyMapStop = HashMap.of(
        KeyEvent.VK_DOWN, StopBehavior.STOP_DOWN,
        KeyEvent.VK_LEFT, StopBehavior.STOP_LEFT,
        KeyEvent.VK_RIGHT, StopBehavior.STOP_RIGHT,
        KeyEvent.VK_UP, StopBehavior.STOP_UP
    );

    private Map<Integer, Behavior> keyMapMove = HashMap.of(
        KeyEvent.VK_DOWN, MoveBehavior.MOVE_DOWN,
        KeyEvent.VK_LEFT, MoveBehavior.MOVE_LEFT,
        KeyEvent.VK_RIGHT, MoveBehavior.MOVE_RIGHT,
        KeyEvent.VK_UP, MoveBehavior.MOVE_UP
    );

    private CLI cli;
    private Boolean inputEnabled;
    private Dimension dimension;
    private GameEngine engine;
    private GameEnvironment environment;

    public static final Function2<Dimension, GameEnvironment, GameView> createView =
        (dimension, environment) -> of(dimension, environment);


    public GameView(Dimension dimension, GameEnvironment environment) {
        this.environment = environment;
        this.dimension = dimension;

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(AWT.Dimension.of(dimension));
        setDoubleBuffered(true);

        inputEnabled = true;
        cli = new CLI();
    }

    public static final GameView of(Dimension dimension, GameEnvironment environment) {
        return new GameView(dimension, environment);
    }

    public static void renderSprite(Graphics g, GameView gameView, Point3D offsetP, GameSprite sprite) {
        var g2d = (Graphics2D) g;

        if (sprite.colliding) {
            drawCollisionZone(g, new Rectangle(add.apply(sprite.position.x, offsetP.x).intValue(), add.apply(sprite.position.y, offsetP.y).intValue(), sprite.size.width.intValue(), sprite.size.height.intValue()));
        }
        g2d.drawImage(getImage.apply(imageMapKey.apply(sprite.animationState, sprite.direction), sprite.images), add.apply(sprite.position.x, offsetP.x).intValue(), add.apply(sprite.position.y, offsetP.y).intValue(), gameView);
    }

    public static void drawCollisionZone(Graphics g, Rectangle bounds) {
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
        Camera.update(dimension, environment.size, environment.sprites, this, g);
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
        var enemyMsg = " Enemies: " + getByType.apply(SpriteType.ENEMY).apply(environment.sprites).size();
        var player = getPlayer.apply(environment.sprites);

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

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            final Integer key = e.getKeyCode();

            if (key.equals(0)) {
                cli.isEnabled(!cli.isEnabled());
                engine.togglePaused();
            }

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

            if (cli.isEnabled() && key.equals(KeyEvent.VK_SPACE)) {
                engine.togglePaused();
            } else {
                if (!engine.isPaused()) {
                    keyMapStop.get(e.getKeyCode())
                        .map(x -> x.execute(getPlayer.apply(environment.sprites)));
                }
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (!engine.isPaused()) {
                keyMapMove.get(e.getKeyCode())
                    .map(x -> x.execute(getPlayer.apply(environment.sprites)));
            }
        }
    }
}
