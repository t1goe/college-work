package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class LevelMap {

    private TileObject[][] level;
    private int tileSize = 0;

    private int offsetX = 0;
    private int offsetY = 0;

    private int[] checkPointLocation = new int[2];

    private ArrayList<int[]> keys = new ArrayList<>();
    private ArrayList<int[]> collectedKeys = new ArrayList<>();

    public LevelMap() {
    }

    public LevelMap(int width, int height, int tileSize) {
        this.level = new TileObject[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.level[j][i] = new TileObject();
                this.level[j][i].setCentre(new Point3f(j*tileSize, i*tileSize, 0));
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
        if (t.getState() == State.ACTIVE_CHECKPOINT) {
            checkPointLocation[0] = x;
            checkPointLocation[1] = y;
        }
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

    public int[] getCheckPointLocation() {
        return checkPointLocation;
    }

    public void setCheckPointLocation(int[] checkPointLocation) {
        this.checkPointLocation = checkPointLocation;
    }

    public ArrayList<int[]> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<int[]> keys) {
        this.keys = keys;
    }

    public ArrayList<int[]> getCollectedKeys() {
        return collectedKeys;
    }

    public void setCollectedKeys(ArrayList<int[]> collectedKeys) {
        this.collectedKeys = collectedKeys;
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

    public void loadTileMap(String fileLocation) {
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
                    switch (row[j].toUpperCase()) {
                        case "B":
                            this.level[j][i] = new TileObject(State.BLOCK);
                            break;
                        case "S":
                            this.level[j][i] = new TileObject(State.SPIKE);
                            break;
                        case "A":
                            this.level[j][i] = new TileObject(State.ACTIVE_CHECKPOINT);
                            this.checkPointLocation[0] = j;
                            this.checkPointLocation[1] = i;
                            break;
                        case "I":
                            this.level[j][i] = new TileObject(State.INACTIVE_CHECKPOINT);
                            break;
                        case "K":
                            this.level[j][i] = new TileObject(State.KEY);
                            keys.add(new int[]{j, i});
                            break;
                        case "C":
                            this.level[j][i] = new TileObject(State.KEY_COLLECTED);
                            collectedKeys.add(new int[]{j, i});
                            break;
                        case "L":
                            this.level[j][i] = new TileObject(State.LOCK);
                            break;
                        case "F":
                            this.level[j][i] = new TileObject(State.FINISH);
                            break;
                        default:
                            this.level[j][i] = new TileObject((State.EMPTY));
                            break;
                    }
                    this.level[j][i].setCentre(new Point3f(j*tileSize, i*tileSize, 0));
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

    public void playerInteraction(PlayerObject p) {
        for (int[] temp : getOccupyingTiles(p)) {
            switch (level[temp[0]][temp[1]].getState()) {
                case SPIKE:
                    p.setVelocity(new Vector3f(0, 0, p.getVelocity().getZ()));
                    Point3f tempPoint = level[checkPointLocation[0]][checkPointLocation[1]].getCentre();
                    tempPoint.setX(tempPoint.getX() + 5);//Change the offsets to put man in the center of the square
                    tempPoint.setY(tempPoint.getY() - 17);
                    p.setCentre(tempPoint);
                    break;
                case INACTIVE_CHECKPOINT:
                    changeAllByType(State.ACTIVE_CHECKPOINT, State.INACTIVE_CHECKPOINT);
                    level[temp[0]][temp[1]].setState(State.ACTIVE_CHECKPOINT);
                    checkPointLocation[0] = temp[0];
                    checkPointLocation[1] = temp[1];
                    break;
            }
        }
    }

    public void changeAllByType(State from, State to) {
        for (TileObject[] row : level) {
            for (TileObject t : row) {
                if (t.getState() == from) {
                    t.setState(to);
                }
            }
        }
    }

    public Set<int[]> getOccupyingTiles(PlayerObject p) {
        int[][] tiles = new int[6][2];

        float[][] points = p.getCollisionPoints();

        for (int i = 0; i < points.length; i++) {
            tiles[i][0] = (int) (points[i][0] / tileSize);
            tiles[i][1] = (int) (points[i][1] / tileSize);
        }

        int end = tiles.length;
        Set<int[]> set = new HashSet<>();

        for (int i = 0; i < end; i++) {
            set.add(tiles[i]);
        }

        return set;
    }
}
