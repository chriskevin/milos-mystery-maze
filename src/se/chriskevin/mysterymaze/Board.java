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
    private GameCharacter hero;
    private Collection<GameCharacter> enemies;
    private Collection<GameSprite> tiles;

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

        Collection<GameSprite> gameSprites = new ArrayList<>();
        gameSprites.add(hero);
        gameSprites.addAll(tiles);
        gameSprites.addAll(enemies);

        camera = new Camera(dimension, gameSprites, hero);

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        camera.update(this, g);
        doDrawing(g);
        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {
        final Color myColour = new Color(0, 0, 0, 128);
        g.setColor(myColour);
        g.fillRect(0, 0, 1024, 100);

        g.setColor(Color.WHITE);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        final String ingameMsg = !paused ? "Playing " : "Paused ";
        final String levelMsg = " Level 1";
        final String enemyMsg = " Enemies: " + enemies.size();
        final String coordsMsg = " X: "+ hero.getLocation().getX() + " Y: " + hero.getLocation().getY() + " Colliding: " + hero.isColliding();
        g.drawString(ingameMsg + levelMsg + enemyMsg + coordsMsg, 32, 32);
        drawCli(g);
        g.dispose();
    }

    private void drawCli(Graphics g) {
        g.drawString("Console: " + cli.getCurrentCommand(), 32, 64);
    }

    private void detectCollision() {

        // Tile checks
        tiles.forEach(tile -> {
            if (tile.isBlocking()) {

                // Hero
                if (hero.getBounds().intersects(tile.getBounds())) {
                    hero.isColliding(true);
                    hero.move();
                }

                // Enemies
                enemies.forEach(enemy -> {
                    if (enemy.getBounds().intersects(tile.getBounds())) {
                        enemy.isColliding(true);
                    }
                });
            }
        });

        // Enemy checks
        enemies.forEach(enemyA -> {
            if (enemyA.getBounds().intersects(hero.getBounds())) {
                hero.isColliding(true);
                enemyA.isColliding(true);
            }

            enemies.forEach(enemyB -> {
                if (enemyB.getBounds().intersects(enemyA.getBounds())){
                    enemyA.isColliding(true);
                    enemyB.isColliding(true);
                }
            });
        });

        // Hero is on exit
        //if (heroBounds.intersects(exitBounds)) {
        //    nextLevel();
        //}
    }

    private void act() {
        enemies.forEach(enemy -> enemy.act());
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
            final int key = e.getKeyCode();

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
        final int TILE_WIDTH = 32;

        enemies = new ArrayList<>();
        tiles = new ArrayList<>();
        final Point currentLocation = new Point(0, 0);

        // Parse map data
        mapData.forEach(row -> {
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
                        createTile(currentLocation, "/images/tiles/" + type + typeId + ".gif", false);
                        break;
                    case 'h':
                        createTile(currentLocation, "/images/tiles/f1.gif", false);
                        hero = GameCharacterFactory.createHero(new Point((int) currentLocation.getX(), (int) currentLocation.getY() - 14), zoomFactor);
                        break;
                    case 'm':
                        createTile(currentLocation, "/images/tiles/f1.gif", false);
                        int enemyNo = row.charAt(j + 1) - '0';
                        createEnemy(enemyNo, currentLocation);
                        break;
                    case 't':
                        createTile(currentLocation, "/images/tiles/" + type + typeId + ".gif", true);
                        break;
                    default:
                        currentLocation.translate(-(TILE_WIDTH * zoomFactor), 0);
                        break;
                }

                currentLocation.translate((TILE_WIDTH * zoomFactor), 0);

            }
            currentLocation.setLocation(0, currentLocation.getY());
            currentLocation.translate(0, (TILE_WIDTH * zoomFactor));
        });
    }

    private void createEnemy(int enemyNo, Point currentLocation) {
        switch (enemyNo) {
            case 1:
                enemies.add(GameCharacterFactory.createBat(new Point(currentLocation), zoomFactor));
                break;
            case 2:
                enemies.add(GameCharacterFactory.createRat(new Point(currentLocation), zoomFactor));
                break;
            case 3:
                enemies.add(GameCharacterFactory.createZombie(new Point((int) currentLocation.getX(), (int) currentLocation.getY() - (14 * zoomFactor)), zoomFactor));
                break;
        }
    }

    private void createTile(Point currentLocation, String imageFilename, boolean blocking) {
        final GameSprite gameSprite = new GameSprite(new Point(currentLocation));
        gameSprite.setScale(zoomFactor);

        if (blocking) {
            gameSprite.isBlocking(blocking);
        }

        gameSprite.setImage(imageFilename);
        tiles.add(gameSprite);
    }
}