package se.chriskevin.mysterymaze.ui;

import se.chriskevin.mysterymaze.GameEngine;
import se.chriskevin.mysterymaze.behavior.Behavior;
import se.chriskevin.mysterymaze.behavior.MoveBehavior;
import se.chriskevin.mysterymaze.behavior.StopBehavior;
import se.chriskevin.mysterymaze.environment.GameEnvironment;
import se.chriskevin.mysterymaze.environment.GameSprite;
import se.chriskevin.mysterymaze.utils.CLI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CHSU7648 on 2016-03-29.
 */
public class GameView extends JPanel {

    // Move this
    private Map<Integer, Behavior> keyMapStop;
    {
        keyMapStop = new HashMap<>();
        keyMapStop.put(KeyEvent.VK_DOWN, StopBehavior.STOP_DOWN);
        keyMapStop.put(KeyEvent.VK_LEFT, StopBehavior.STOP_LEFT);
        keyMapStop.put(KeyEvent.VK_RIGHT, StopBehavior.STOP_RIGHT);
        keyMapStop.put(KeyEvent.VK_UP, StopBehavior.STOP_UP);
    }
    private Map<Integer, Behavior> keyMapMove;
    {
        keyMapMove = new HashMap<>();
        keyMapMove.put(KeyEvent.VK_DOWN, MoveBehavior.MOVE_DOWN);
        keyMapMove.put(KeyEvent.VK_LEFT, MoveBehavior.MOVE_LEFT);
        keyMapMove.put(KeyEvent.VK_RIGHT, MoveBehavior.MOVE_RIGHT);
        keyMapMove.put(KeyEvent.VK_UP, MoveBehavior.MOVE_UP);
    }

    private CLI cli;

    private boolean inputEnabled;

    private Dimension dimension;

    private GameEngine engine;

    private GameEnvironment environment;

    public GameView(Dimension dimension, GameEnvironment environment) {
        this.environment = environment;
        this.dimension = dimension;

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(dimension);
        setDoubleBuffered(true);

        inputEnabled = true;
        cli = new CLI();
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
        Camera.update(dimension, environment.getDimension(), environment.getSprites(), this, g);
        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {
        final Color myColour = new Color(0, 0, 0, 128);
        g.setColor(myColour);
        g.fillRect(0, 0, 1024, 100);

        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        final String ingameMsg = !engine.isPaused() ? "Playing " : "Paused ";
        final String levelMsg = " Level 1";
        final String enemyMsg = " Enemies: " + environment.getSprites().get("ENEMY").size();
        final GameSprite player = environment.getSprites().get("PLAYER").get(0);
        final String coordsMsg = " X: "+ player.getLocation().getX() + " Y: " + player.getLocation().getY() + " Colliding: " + player.isColliding();
        g.drawString(ingameMsg + levelMsg + enemyMsg + coordsMsg, 32, 32);
        drawCli(g);
        g.dispose();
    }

    private void drawCli(Graphics g) {
        g.drawString("Console: " + cli.getCurrentCommand(), 32, 64);
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            final int key = e.getKeyCode();

            if (key == 0) {
                cli.isEnabled(!cli.isEnabled());
                engine.togglePaused();
            }

            if (cli.isEnabled()) {
                if (key >= 65 && key <= 90 || key == KeyEvent.VK_SPACE) {
                    String currentCommand = cli.getCurrentCommand() + (char) key;
                    cli.setCurrentCommand(currentCommand);
                } else if (key == KeyEvent.VK_BACK_SPACE) {
                    String currentCommand = cli.getCurrentCommand();
                    cli.setCurrentCommand(currentCommand.substring(0, currentCommand.length() -1));
                } else if (key == KeyEvent.VK_ENTER) {
                    cli.run();
                }
            }

            if (cli.isEnabled() && key == KeyEvent.VK_SPACE) {
                engine.togglePaused();
            } else {
                if (!engine.isPaused()) {
                    environment.getSprites().get("PLAYER").get(0).act(keyMapStop.get(e.getKeyCode()));
                }
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (!engine.isPaused()) {
                environment.getSprites().get("PLAYER").get(0).act(keyMapMove.get(e.getKeyCode()));
            }
        }
    }
}
