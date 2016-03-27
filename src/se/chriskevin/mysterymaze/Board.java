package se.chriskevin.mysterymaze;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.*;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Dimension dimension;

    private int zoomFactor = 2;

    private final int DELAY = 25;

    private boolean paused;

    private Camera camera;
    private Timer timer;
    private Character hero;
    private Collection<Character> enemies;
    private Collection<Sprite> tiles;

    private BoardGenerator generator;

    private CLI cli;

    public Board(Dimension dimension) {
        this.dimension = dimension;
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(dimension);
        setDoubleBuffered(true);

        cli = new CLI();

        paused = false;

        generator = new BoardGenerator();
        final Collection<String> mapData = generator.parseLevelFile("/levels/level1.txt");
        createLevel(mapData);

        Collection<Sprite> sprites = new ArrayList<>();
        sprites.add(hero);
        sprites.addAll(tiles);
        sprites.addAll(enemies);

        camera = new Camera(dimension, sprites, hero);

        timer = new Timer(DELAY, this);
        timer.start();
    }

    public Collection<Sprite> getTiles() {
        return this.tiles;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        camera.update(this, g);
        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Color myColour = new Color(0, 0, 0, 128);
        g.setColor(myColour);
        g.fillRect(0, 0, 1024, 100);

        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        final String ingameMsg = !paused ? "Playing " : "Paused ";
        final String levelMsg = " Level 1";
        final String enemyMsg = " Enemies: " + enemies.size();
        final String coordsMsg = " X: "+ hero.getX() + " Y: " + hero.getY() + " Colliding: " + hero.isColliding();
        g.drawString(ingameMsg + levelMsg + enemyMsg + coordsMsg, 32, 32);
        drawCli(g);
        g.dispose();
    }

    private void drawCli(Graphics g) {
        g.drawString("Console: " + cli.getCurrentCommand(), 32, 64);
    }

    private void detectCollision() {

        // Tile checks
        for (Sprite tile : tiles) {
            if (tile.isBlocking()) {

                // Hero
                if (hero.getBounds().intersects(tile.getBounds())) {
                    hero.isColliding(true);
                    hero.move();
                }

                // Enemies
                for (Character enemy : enemies) {
                    if (enemy.getBounds().intersects(tile.getBounds())) {
                        enemy.isColliding(true);
                    }
                }
            }
        }

        // Enemy checks
        for (Character enemyA : enemies) {
            if (enemyA.getBounds().intersects(hero.getBounds())) {
                hero.isColliding(true);
                enemyA.isColliding(true);
            }

            for (Character enemyB : enemies) {
                if (enemyB.getBounds().intersects(enemyA.getBounds())){
                    enemyA.isColliding(true);
                    enemyB.isColliding(true);
                }
            }
        }

        // Hero is on exit
        //if (heroBounds.intersects(exitBounds)) {
        //    nextLevel();
        //}
    }

    private void act() {
        for (Character enemy : enemies) {
            enemy.act();
        }
    }

    private void nextLevel() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!paused) {
            hero.move();
            detectCollision();
            act();
        }

        repaint();
    }

    private void togglePaused() {
        paused = !paused;

        if (!paused) {
            timer.start();
        } else {
            timer.stop();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == 0) {
                cli.isEnabled(!cli.isEnabled());
                togglePaused();
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
                togglePaused();
            } else {
                if (!paused) {
                    hero.keyReleased(e);
                }
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (!paused) {
                hero.keyPressed(e);
            }
        }
    }

    private void createLevel(Collection<String> mapData) {
        enemies = new ArrayList<>();
        tiles = new ArrayList<>();
        int currX  = 0;
        int currY  = 0;

        // Parse map data
        for (String row: mapData) {
            System.out.println(row);

            // Parse row
            for (int j = 0; j < row.length(); j += 3) {
                char type = row.charAt(j);
                char typeId = row.charAt(j + 1);

                // Select proper action
                switch (type) {
                    case 'e':
                    case 'f':
                    case 'w':
                        createTile(currX, currY, "/images/tiles/" + type + typeId + ".gif", false);
                        break;
                    case 'h':
                        createTile(currX, currY, "/images/tiles/f1.gif", false);
                        hero = CharacterFactory.createHero(currX, currY - 14, zoomFactor);
                        break;
                    case 'm':
                        createTile(currX, currY, "/images/tiles/f1.gif", false);
                        int enemyNo = row.charAt(j + 1) - '0';
                        createEnemy(enemyNo, currX, currY);
                        break;
                    case 't':
                        createTile(currX, currY, "/images/tiles/" + type + typeId + ".gif", true);
                        break;
                    default:
                        currX -= 32 * zoomFactor;
                        break;
                }

                currX += 32 * zoomFactor;

            }
            currX = 0;
            currY += 32 * zoomFactor;
        }
    }

    private void createEnemy(int enemyNo, int currX, int currY) {
        switch (enemyNo) {
            case 1:
                enemies.add(CharacterFactory.createBat(currX, currY, zoomFactor));
                break;
            case 2:
                enemies.add(CharacterFactory.createRat(currX, currY, zoomFactor));
                break;
            case 3:
                enemies.add(CharacterFactory.createZombie(currX, currY - (14 * zoomFactor), zoomFactor));
                break;
        }
    }

    private void createTile(int x, int y, String imageFilename, boolean blocking) {
        final Sprite sprite = new Sprite(x, y);
        sprite.setScale(zoomFactor);
        if (blocking) {
            sprite.isBlocking(blocking);
        }
        sprite.setImage(imageFilename);
        tiles.add(sprite);
    }


}