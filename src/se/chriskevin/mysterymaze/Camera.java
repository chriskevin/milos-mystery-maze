package se.chriskevin.mysterymaze;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by CHSU7648 on 2016-03-14.
 */
final public class Camera {
    /*
     * speed       The speed which the camera moves at.
     * targetLastX The targets latest x position.
     * targetLastY The targets latest y position.
     * target      The specified object to follow.
     * board       A reference to the board object.
     * viewArea    The area which corresponds to the levels size.
     * POV         The area which the board is visible within.
     */
    private double speed;
    private int targetLastX = 0;
    private int targetLastY = 0;

    private Sprite target;
    private Board board;
    private Rectangle viewArea;
    private Rectangle POV;

    /**
     * The constructor method which requires a reference to a board object.
     * @param board Reference to the board.
     */
    public Camera(Board board) {
        this.board = board;

        //speed = (board.tileDimension.getWidth() / 4);
        //viewArea = new Rectangle(new Point(0, 0), board.mapDimension);
        //POV = new Rectangle(new Point(0, 0), new Dimension(800, 600));
    }

    /**
     * This method locks the camera to the assigned target.
     */
    public void follow() {
        Rectangle targetBounds = target.getBounds();
        int targetX = ( (POV.width / 2) - ((int)targetBounds.getWidth() / 2) );
        int targetY = ( (POV.height / 2) - ((int)targetBounds.getHeight() / 2) );
        int relativeX = (targetX - target.getX() );
        int relativeY = (targetY - target.getY() );

        int leftBound = targetX;
        int rightBound = ( (viewArea.width - (POV.width / 2) ) );
        int topBound = targetY;
        int bottomBound = ( (viewArea.height - (POV.height / 2) ) );

        resetObjectPositions();

        // Center targets X if it is between bounds
        if (target.getX() >= leftBound && target.getX() <= rightBound) {

            if (target.getX() != targetLastX)
                targetLastX = target.getX();

            setObjectPositions(relativeX, 0);
            target.setX(targetX);

        } else if (target.getX() > rightBound) {
            setObjectPositions(-( (viewArea.width - POV.width) + 64 ), 0);
            target.setX( (targetX + (target.getX() - rightBound ) ) );
        }

        // Center targets Y if it is between bounds
        if (target.getY() >= topBound && target.getY() <= bottomBound) {

            if (target.getY() != targetLastY)
                targetLastY = target.getY();

            setObjectPositions(0, relativeY);
            target.setY(targetY);

        } else if (target.getY() > bottomBound) {
            setObjectPositions(0, -( (viewArea.height - POV.height) + 64 ) );
            target.setY( (targetY + (target.getY() - bottomBound ) ) );
        }

        updatePOV();
    }

    /**
     * This method sets all non moving objects coordinates back
     * to their initial positions.
     */
    private void resetObjectPositions() {
//        int tileSize = board.getTiles().get(0).getWidth();
//        int currentX = 0;
//        int currentY = 0;
//        int n = 0;
//
//        while (currentY <= viewArea.height) {
//            while (currentX <= viewArea.width) {
//                board.getTiles().get(n).setX(currentX);
//                board.getTiles().get(n).setY(currentY);
//                currentX += tileSize;
//                n++;
//            }
//            currentX = 0;
//            currentY += tileSize;
//        }
    }

    /**
     * This method changes all GameObject objects positions
     * relative to the POV.
     * @param x
     * @param y
     */
    public void setObjectPositions(int x, int y) {

//        // Update tiles
//        for (int i = 0; i < board.getTiles().size(); i++) {
//            Sprite tempTile = board.getTiles().get(i);
//            tempTile.setX( (tempTile.getX() + x) );
//            tempTile.setY( (tempTile.getY() + y) );
//        }
//
//        // Update players
//        for (int i = 0; i < board.players.size(); i++) {
//            MutantPC tempPlayer = board.players.get(i);
//            int targetIndex = board.players.indexOf(target);
//            if (i != targetIndex) {
//                tempPlayer.setX( (tempPlayer.getX() + x) );
//                tempPlayer.setY( (tempPlayer.getY() + y) );
//            }
//        }
//
//        // Update npcs
//        for (int i = 0; i < board.npcs.size(); i++) {
//            NPC tempNPC = (NPC) board.npcs.get(i);
//            tempNPC.setX( (tempNPC.getX() + x) );
//            tempNPC.setY( (tempNPC.getY() + y));
//        }
//
//        // Update items
//        for (int i = 0; i < board.items.size(); i++) {
//            Item tempItem = board.items.get(i);
//            tempItem.setX( (tempItem.getX() + x) );
//            tempItem.setY( (tempItem.getY() + y) );
//        }
    }

    /**
     * This method sets the target to follow.
     * @param sprite
     */
    public void setTarget(Sprite sprite) {
        target = sprite;
    }

    /**
     * This method check which tiles are inside the POV
     * and adds references to them in the Level objects
     * tempTiles list.
     */
    private void updatePOV() {
        //board.tempTiles = new ArrayList();

        // Find the start index
        for (int i = 0; i < board.getTiles().size(); i++) {
            //Sprite tempTile = board.getTiles().get(i);
            //Rectangle tileBounds = tempTile.getBounds();

            //if (tileBounds.intersects(POV))
            //    board.tempTiles.add(tempTile);
        }
    }
}
