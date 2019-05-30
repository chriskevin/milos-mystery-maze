package se.chriskevin.mysterymaze.ui;

import se.chriskevin.mysterymaze.geometry.Dimension;
import se.chriskevin.mysterymaze.utils.AWT;

import javax.swing.*;
import java.awt.*;

public final class ErrorView extends JPanel {

    private Dimension dimension;

    private String message;

    private ErrorView(Dimension dimension, String message) {
        this.dimension = dimension;
        this.message = message;

        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(AWT.Dimension.of(dimension));
    }

    public static ErrorView of(Dimension dimension, String message) {
        return new ErrorView(dimension, message);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawError(g, message);
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawError(Graphics g, String message) {
        g.setColor(new Color(255, 243, 205));
        g.fillRect(32, 32, 960, 100);

        g.setColor(new Color(133, 100, 4));
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString(message, 64, 64);

        g.dispose();
    };
}
