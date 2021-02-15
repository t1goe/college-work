package util;

public class LevelMap{

    private TileObject[][] level;
    private int tileSize = 0;

    private int offsetX = 0;
    private int offsetY = 0;

    public LevelMap() {}

    public LevelMap(int width, int height, int tileSize) {
        this.level = new TileObject[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.level[j][i] = new TileObject();
            }
        }

        this.tileSize = tileSize;
    }

    public TileObject getTile(int x, int y){
        return this.level[x][y];
    }

    public void setTile(int x, int y, TileObject t){
        this.level[x][y] = t;
    }

    public int getWidth(){
        return level[0].length;
    }

    public int getHeight(){
        return level.length;
    }

    public int getTileSize(){
        return this.tileSize;
    }

    public void setTileSize(int size){
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

    public void horizontalCollision(float x1, float y1, float x2, float y2){

    }

    public void collisionDetection(float x1, float y1, float x2, float y2){
        float rise = y2 - y1;
        float run = x2 - x1;

        float slope = 0;
        float c = 0;
        if(rise == 0){ //Horizontal line
            slope = 0;
            c = Float.parseFloat(null);
        }else if(run == 0){ //Vertical line
            slope = Float.parseFloat(null);
            c = x1;
        }else{//Normal line
            slope = rise / run;
            c = y1 - (slope * x1);
        }

        float gridPositionX = x1 / this.tileSize;
        float gridPositionY = y1 / this.tileSize;



    }
}
