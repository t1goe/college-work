package util;

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

    public float collisionDetectionLine(float x1, float y1, float x2, float y2) {
        float rise = y2 - y1;
        float run = x2 - x1;

        float slope = 0;
        float xIntercept = 0;
        float yIntercept = 0;
        if (rise == 0) { //Horizontal line
            slope = 0;
            yIntercept = y1;
        } else if (run != 0) {//Normal line
            slope = rise / run;
            yIntercept = y1 - (slope * x1);
        }


        float xRatio = 1;
        if (run > 0) {//Moving right
            for (float i = x1; i < x2; i++) {//INCREMENT BY TILE SIZE INSTEAD
                if (((int) i) % this.tileSize == 0) {
                    if (level[(int) (i / this.tileSize)][(int) (((i * slope) + yIntercept) / this.tileSize)].getState() == State.BLOCK) {
                        xRatio = (i - x1) / (x2 - x1);
                        break;
                    }
                }
            }
        } else if (run < 0) {//Moving left
            for (float i = x1; i > x2; i--) {
                if (((int) i) % this.tileSize == 0) {
                    if (level[(int) (i / this.tileSize) - 1][(int) (((i * slope) + yIntercept) / this.tileSize)].getState() == State.BLOCK) {
                        xRatio = Math.abs((i - x1) / (x2 - x1));
                        break;
                    }
                }
            }
        }

        float yRatio = 1;
        if (rise > 0) {//Moving down
            for (float i = y1; i < y2; i++) {//INCREMENT BY TILE SIZE INSTEAD
                if (((int) i) % this.tileSize == 0) {
                    if (run != 0) {//Moving angled up
                        if (level[(int) (((i - yIntercept) / this.tileSize) / slope)][(int) (i / this.tileSize)].getState() == State.BLOCK) {
                            yRatio = (i - y1) / (y2 - y1);
                            break;
                        }
                    } else {//Moving straight down
                        if (level[(int) (x1 / this.tileSize)][(int) (i / this.tileSize)].getState() == State.BLOCK) {
                            yRatio = (i - y1) / (y2 - y1);
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
                            yRatio = Math.abs((i - y1) / (y2 - y1));
                            break;
                        }
                    } else {//Moving straight up
                        if (level[(int) (x1 / this.tileSize)][(int) (i / this.tileSize) - 1].getState() == State.BLOCK) {
                            yRatio = (i - y1) / (y2 - y1);
                            break;
                        }
                    }
                }
            }
        }

        float ans = Math.abs(Math.min(xRatio, yRatio));
        //!TEST
//        if (ans != 1) {
//            System.out.println("xRatio: " + xRatio);
//            System.out.println("yRatio: " + yRatio);
//            System.out.println("ans: " + ans);
//            System.out.println();
//        }
        return ans;
    }

    public float collisionDetection(PlayerObject p) {
        float[][] points = p.getCollisionPoints();
        float ratio = Float.MAX_VALUE;
        for (int i = 0; i < points.length; i++){
            float tempRatio = collisionDetectionLine(points[i][0], points[i][1], points[i][0] + p.getVelocity().getX(), points[i][1] + p.getVelocity().getY());
            ratio = Math.min(ratio, tempRatio);
        }

        return ratio;

//        p.applyCurrentVelocity(r);
//
//        p.setVelocity(new Vector3f(0, 0, 0));
    }
}
