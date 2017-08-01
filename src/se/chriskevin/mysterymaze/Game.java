package se.chriskevin.mysterymaze;

import se.chriskevin.mysterymaze.environment.GameEnvironment;
import se.chriskevin.mysterymaze.geometry.Dimension;
import se.chriskevin.mysterymaze.ui.GameView;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @version Game.java v.1.0 2009-12-11
 * @author  Chris Sundberg
 */
public class Game extends JFrame {

    private GameEngine engine;
    private GameEnvironment environment;
    private GameView gameView;

    public Game() {
        initUI();
    }

    private void initUI() {
        setTitle("Milo's Mystery Maze");
        setResizable(true);
        setSize(1024, 768);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        hideCursor();

        environment = new GameEnvironment();
        gameView = new GameView(new Dimension(this.getSize().width, this.getSize().height), environment);
        engine = new GameEngine(gameView, environment);
        gameView.setEngine(engine);

        setContentPane(gameView);
    }

    private void hideCursor() {
        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        // Set the blank cursor to the JFrame.
        getContentPane().setCursor(blankCursor);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Game g = new Game();
            g.setVisible(true);
        });
    }
}