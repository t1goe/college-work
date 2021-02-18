package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class LevelMap {

    private TileObject[][] level;
    private int tileSize = 0;

    private int offsetX = 0;
    private int offsetY = 0;

    public LevelMap() {
    }

    public LevelMap(int width, int height, int tileSize) {
        this.level = new TileObject[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.level[j][i] = new TileObject();
            }
        }
        this.tileSize = tileSize;
    }

    public LevelMap(String mapLocation, int tileSize) {
        this.tileSize = tileSize;
        loadTileMap(mapLocation);
    }

    public TileObject getTile(int x, int y) {
        return this.level[x][y];
    }

    public void setTile(int x, int y, TileObject t) {
        this.level[x][y] = t;
    }

    public int getWidth() {
        return level[0].length;
    }

    public int getHeight() {
        return level.length;
    }

    public int getTileSize() {
        return this.tileSize;
    }

    public void setTileSize(int size) {
        this.tileSize = size;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public CollisionInfo collisionDetectionLine(float x1, float y1, float x2, float y2) {
        float rise = y2 - y1;
        float run = x2 - x1;

        float slope = 0;
        float yIntercept = 0;
        if (rise == 0) { //Horizontal line
            slope = 0;
            yIntercept = y1;
        } else if (run != 0) {//Normal line
            slope = rise / run;
            yIntercept = y1 - (slope * x1);
        }

        CollisionInfo xCollision = new CollisionInfo();
        xCollision.setRatio(1);
        xCollision.setState(State.EMPTY);

        if (run > 0) {//Moving right
            for (float i = x1; i < x2; i++) {//INCREMENT BY TILE SIZE INSTEAD
                if (((int) i) % this.tileSize == 0) {
                    if (level[(int) (i / this.tileSize)][(int) (((i * slope) + yIntercept) / this.tileSize)].getState() == State.BLOCK) {
                        xCollision.setRatio((i - x1) / (x2 - x1));
                        xCollision.setDirection(Direction.RIGHT);
                        xCollision.setState(State.BLOCK);
                        break;
                    }
                }
            }
        } else if (run < 0) {//Moving left
            for (float i = x1; i > x2; i--) {
                if (((int) i) % this.tileSize == 0) {
                    if (level[(int) (i / this.tileSize) - 1][(int) (((i * slope) + yIntercept) / this.tileSize)].getState() == State.BLOCK) {
                        xCollision.setRatio(Math.abs((i - x1) / (x2 - x1)));
                        xCollision.setDirection(Direction.LEFT);
                        xCollision.setState(State.BLOCK);
                        break;
                    }
                }
            }
        }

        CollisionInfo yCollision = new CollisionInfo();
        yCollision.setRatio(1);
        yCollision.setState(State.EMPTY);

        if (rise > 0) {//Moving down
            for (float i = y1; i < y2; i++) {//INCREMENT BY TILE SIZE INSTEAD
                if (((int) i) % this.tileSize == 0) {
                    if (run != 0) {//Moving angled down
                        if (level[(int) (((i - yIntercept) / this.tileSize) / slope)][(int) (i / this.tileSize)].getState() == State.BLOCK) {
                            yCollision.setRatio((i - y1 - 1) / (y2 - y1));
                            yCollision.setDirection(Direction.DOWN);
                            yCollision.setState(State.BLOCK);
                            break;
                        }
                    } else {//Moving straight down
                        if (level[(int) (x1 / this.tileSize)][(int) (i / this.tileSize)].getState() == State.BLOCK) {
                            yCollision.setRatio((i - y1 - 1) / (y2 - y1));
                            yCollision.setDirection(Direction.DOWN);
                            yCollision.setState(State.BLOCK);
                            break;
                        }
                    }
                }
            }
        } else if (rise < 0) {//Moving up
            for (float i = y1; i > y2; i--) {
                if (((int) i) % this.tileSize == 0) {
                    if (run != 0) {//Moving angled up
                        if (level[(int) (((i - yIntercept) / this.tileSize) / slope)][(int) ((i / this.tileSize)) - 1].getState() == State.BLOCK) {
                            yCollision.setRatio(Math.abs((i - y1) / (y2 - y1)));
                            yCollision.setDirection(Direction.UP);
                            yCollision.setState(State.BLOCK);
                            break;
                        }
                    } else {//Moving straight up
                        if (level[(int) (x1 / this.tileSize)][(int) (i / this.tileSize) - 1].getState() == State.BLOCK) {
                            yCollision.setRatio((i - y1) / (y2 - y1));
                            yCollision.setDirection(Direction.UP);
                            yCollision.setState(State.BLOCK);
                            break;
                        }
                    }
                }
            }
        }

        if (xCollision.getRatio() < yCollision.getRatio()) {
            return xCollision;
        } else {
            return yCollision;
        }
    }

    public CollisionInfo collisionDetection(PlayerObject p) {
        float[][] points = p.getCollisionPoints();
        CollisionInfo collisionInfo = new CollisionInfo();
        collisionInfo.setRatio(Float.MAX_VALUE);

        for (int i = 0; i < points.length; i++) {
            CollisionInfo tempCollision = collisionDetectionLine(points[i][0], points[i][1], points[i][0] + p.getVelocity().getX(), points[i][1] + p.getVelocity().getY());
            if (collisionInfo.getRatio() > tempCollision.getRatio()) {
                collisionInfo = tempCollision;
            }
        }

        return collisionInfo;
    }

    public boolean isPlayerGrounded(PlayerObject p) {
        CollisionInfo collisionLeft = collisionDetectionLine(p.getLeft(), p.getBottom(), p.getLeft(), p.getBottom() + 2);
        CollisionInfo collisionRight = collisionDetectionLine(p.getRight(), p.getBottom(), p.getRight(), p.getBottom() + 2);

        if (collisionLeft.getState() == State.BLOCK || collisionRight.getState() == State.BLOCK) {
            return true;
        } else {
            return false;
        }
    }

    public void loadTileMap(String fileLocation){
        this.tileSize = tileSize;
        try {
            //Pass the path to the file as a parameter
            File file = new File(fileLocation);

            Scanner s = new Scanner(file);

            //Get the size of the level and create the level object
            int height = 0;
            int maxWidth = 0;
            while (s.hasNextLine()) {
                maxWidth = Math.max(s.nextLine().length(), maxWidth);
                height++;
            }
            this.level = new TileObject[maxWidth][height];

            s = new Scanner(file);
            int i = 0;
            while (s.hasNextLine()) {
                String[] row = s.nextLine().split("");
                int j = 0;
                for (; j < row.length; j++) {
                    if (row[j].equals("B")) {
                        this.level[j][i] = new TileObject(State.BLOCK);
                    } else {
                        this.level[j][i] = new TileObject((State.EMPTY));
                    }
                }
                for (; j < maxWidth; j++) {
                    this.level[j][i] = new TileObject((State.EMPTY));
                }

                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
