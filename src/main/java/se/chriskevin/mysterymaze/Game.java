package se.chriskevin.mysterymaze;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import se.chriskevin.mysterymaze.environment.GameEnvironment;
import se.chriskevin.mysterymaze.geometry.Dimension;
import se.chriskevin.mysterymaze.ui.GameView;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferedImage;

import static se.chriskevin.mysterymaze.BoardGenerator.parseLevelFile;
import static se.chriskevin.mysterymaze.GameEngine.createEngine;
import static se.chriskevin.mysterymaze.environment.GameEnvironment.createEnvironment;
import static se.chriskevin.mysterymaze.ui.GameView.createView;

/**
 * @version Game.java v.1.0 2009-12-11
 * @author  Chris Sundberg
 */
public class Game extends JFrame {

    public static final Supplier<Game> createGame = () -> new Game();

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

        final GameEnvironment environment = createEnvironment.compose(parseLevelFile)
            .apply("/levels/level1.txt");

        final GameView view = createView
            .apply(new Dimension(this.getSize().width, this.getSize().height))
            .apply(environment);

        final GameEngine engine = createEngine
            .apply(view)
            .apply(environment);

        view.setEngine(engine);

        setContentPane(view);
    }

    private Supplier<BufferedImage> createBlankCursorImage =
        () -> new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

    private Function<BufferedImage, Cursor> createCustomCursor =
        image -> Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "blank cursor");

    private void hideCursor() {
        // Set the blank cursor to the JFrame.
        getContentPane().setCursor(createCustomCursor.apply(createBlankCursorImage.get()));
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            createGame
                .get()
                .setVisible(true);
        });
    }
}