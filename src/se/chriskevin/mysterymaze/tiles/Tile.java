package se.chriskevin.mysterymaze.tiles;

/*
 * Tile.java v.1.0 2009-12-07 Chris Sundberg
 */

import java.awt.Rectangle;
import javax.swing.*;

public class Tile extends JLabel {
    protected int height = 32;
    protected int width  = 32;
    protected int tileX;
    protected int tileY;
  
    public Tile(String filename, int x , int y) {
        tileX = x;
        tileY = y;
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));

        setIcon(icon);
        setIconTextGap(0);
        setText(null);
        setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
    }
  
    @Override
    public Rectangle getBounds() {
        return new Rectangle(tileX, tileY, width, height);
    }
  
    public int getHeigt() {
        return height;
    }
  
    @Override
    public int getWidth() {
        return width;
    }
  
    @Override
    public int getX() {
        return tileX;
    }
  
    @Override
    public int getY() {
        return tileY;
    }
  
    public void setX(int x) {
        tileX = x;
    }
  
    public void setY(int y) {
        tileY = y;
    }
}