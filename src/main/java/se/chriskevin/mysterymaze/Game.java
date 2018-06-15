package se.chriskevin.mysterymaze;

import io.vavr.Function0;
import io.vavr.Function1;
import se.chriskevin.mysterymaze.geometry.Dimension;
import se.chriskevin.mysterymaze.ui.ErrorView;
import se.chriskevin.mysterymaze.ui.GameView;
import se.chriskevin.mysterymaze.utils.AWT;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferedImage;

import static se.chriskevin.mysterymaze.BoardGenerator.parseLevelFile;
import static se.chriskevin.mysterymaze.environment.GameEnvironment.createEnvironment;

public final class Game extends JFrame {

    private Game() {
        initUI();
    }

    public static final Game of() {
        return new Game();
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

        var environment = createEnvironment.compose(parseLevelFile).apply("/levels/level1.txt");

        if (environment.isEmpty()) {
            var errorView = ErrorView.of(Dimension.of(
                Long.valueOf(this.getSize().width),
                Long.valueOf(this.getSize().height)
            ), "Unable to start game.");

            setContentPane(errorView);
        } else {
            /*var view = GameView.of(Dimension.of(
                Long.valueOf(this.getSize().width),
                Long.valueOf(this.getSize().height)
            ), environment);*/

            //var engine = GameEngine.of(view, environment);

            // view.setEngine(engine);

            //setContentPane(view);
        }
    }

    private Function0<BufferedImage> blankCursorImage =
        () -> new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

    private Function1<BufferedImage, Cursor> customCursor =
        image -> Toolkit.getDefaultToolkit().createCustomCursor(image, AWT.Point.of(0, 0), "blank cursor");

    private void hideCursor() {
        // Set the blank cursor to the JFrame.
        getContentPane().setCursor(customCursor.apply(blankCursorImage.apply()));
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Game.of().setVisible(true);
        });
    }
}