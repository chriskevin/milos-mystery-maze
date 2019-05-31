package se.chriskevin.mysterymaze;

import static se.chriskevin.mysterymaze.BoardGenerator.parseLevelFile;
import static se.chriskevin.mysterymaze.environment.GameEnvironment.createEnvironment;

import io.vavr.Function0;
import io.vavr.Function1;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import se.chriskevin.mysterymaze.environment.GameEnvironment;
import se.chriskevin.mysterymaze.ui.ErrorView;
import se.chriskevin.mysterymaze.ui.GameView;
import se.chriskevin.mysterymaze.utils.AWT;

public final class Game extends JFrame {

  private Game() {
    initUI();
  }

  public static Game of() {
    return new Game();
  }

  private void initUI() {
    setTitle("Milo's Mystery Maze");
    setResizable(true);
    setSize(1024, 768);
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setUndecorated(false);
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    hideCursor();

    createEnvironment(parseLevelFile("/levels/level1.txt")).map(viewGame).onEmpty(viewError);
  }

  private Function1<GameEnvironment, GameEnvironment> viewGame =
      (environment) -> {
        final var view = GameView.of(AWT.Dimension.of(this.getSize()), environment);

        final var engine = GameEngine.of(view, environment);
        view.setEngine(engine);

        setContentPane(view);

        return environment;
      };

  private Runnable viewError =
      () -> {
        final var view = ErrorView.of(AWT.Dimension.of(this.getSize()), "Unable to start game.");

        setContentPane(view);
      };

  private Function0<BufferedImage> blankCursorImage =
      () -> new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

  private Function1<BufferedImage, Cursor> customCursor =
      image ->
          Toolkit.getDefaultToolkit().createCustomCursor(image, AWT.Point.of(0, 0), "blank cursor");

  private void hideCursor() {
    // Set the blank cursor to the JFrame.
    getContentPane().setCursor(customCursor.apply(blankCursorImage.apply()));
  }

  public static void main(final String[] args) {
    EventQueue.invokeLater(() -> Game.of().setVisible(true));
  }
}
