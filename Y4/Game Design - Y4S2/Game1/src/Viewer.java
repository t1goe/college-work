//Thomas Igoe
//17372013

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import util.GameState;
import util.LevelMap;
import util.PlayerObject;


/*
 * Created by Abraham Campbell on 15/01/2020.
 *   Copyright (c) 2020  Abraham Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
   
   (MIT LICENSE ) e.g do what you want with this :-) 
 
 * Credits: Kelly Charles (2020)
 */
public class Viewer extends JPanel {
    private long CurrentAnimationTime = 0;

    Model gameworld = new Model();

    private HashMap<String, Image> myImages = new HashMap<>();

    private int frameWidth = 0;
    private int frameHeight = 0;

    private final boolean DEBUG_MODE = false;

    public Viewer(Model World) {
        this.gameworld = World;
        loadImages();
        // TODO Auto-generated constructor stub
    }

    public Viewer(LayoutManager layout) {
        super(layout);
        // TODO Auto-generated constructor stub
    }

    public Viewer(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        // TODO Auto-generated constructor stub
    }

    public Viewer(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        // TODO Auto-generated constructor stub
    }

    public void updateview() {
        this.repaint();
        // TODO Auto-generated method stub

    }

    public int getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(int frameWidth) {
        this.frameWidth = frameWidth;
    }

    public int getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(int frameHeight) {
        this.frameHeight = frameHeight;
    }


    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        CurrentAnimationTime++; // runs animation time step

        drawBackground(g);

        //If man is offscreen, recenter camera
        recenterCamera();

        //Update level's x/y offsets
        moveCamera();

        drawPlayer(g);

        drawLevel(g);

        drawKeyCount(g);

        drawPause(g);
    }

    private void loadImages() {
        //All player assets from https://jesse-m.itch.io/jungle-pack (also grass tile/titlescreen)
        //Key from https://cheekyinkling.itch.io/shikashis-fantasy-icons-pack
        //Flag is homemade
        //Spikes and lock (modified) from https://pixelfrog-store.itch.io/pixel-adventure-1
        //Goal gem is from https://ma9ici4n.itch.io/gems

        String[] imageLocations = {
                "res/textures/man/idle.png",
                "res/textures/man/run.png",
                "res/textures/man/falling.png",
                "res/textures/man/rising.png",
                "res/textures/man/midair.png",
                "res/textures/background.png",
                "res/textures/jungle_tileset.png",
                "res/textures/title2.png",
                "res/textures/key.png",
                "res/textures/key_transparent.png",
                "res/textures/flag_up.png",
                "res/textures/flag_down.png",
                "res/textures/spikes.png",
                "res/textures/lock.png",
                "res/textures/lock_transparent.png",
                "res/textures/gem.png"
        };

        try {
            for (String s : imageLocations) {
                File TextureToLoad = new File(s);
                myImages.put(s, ImageIO.read(TextureToLoad));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawBackground(Graphics g) {
        g.drawImage(myImages.get("res/textures/background.png"), 0, 0, 2000, 1000, 0, 0, 383, 216, null);
    }

    private void drawPlayer(Graphics g) {

        PlayerObject p = gameworld.getPlayer();
        LevelMap l = gameworld.getLevelMap();

        int x = (int) p.getCentre().getX();
        int y = (int) p.getCentre().getY();
        int width = p.getWidth();
        int height = p.getHeight();

        boolean facingRight = p.isFacingRight();
        boolean grounded = p.isGrounded();
        float xVel = p.getVelocity().getX();
        float yVel = p.getVelocity().getY();
        float[][] points = p.getCollisionPoints();

        int xOffset = l.getOffsetX();
        int yOffset = l.getOffsetY();

        int trueWidth;
        int playerAlign;
        if (!facingRight) {
            trueWidth = -width;
            playerAlign = width;
        } else {
            trueWidth = width;
            playerAlign = 0;
        }

        //For testing
        if (DEBUG_MODE) {
            //Draw hitbox
            g.drawRect(x + xOffset, y + yOffset, width, height);


            //Velocity lines
            for (int i = 0; i < points.length; i++) {
                g.drawLine((int) points[i][0] + xOffset, (int) points[i][1] + yOffset,
                        (int) (points[i][0] + p.getVelocity().getX()) + xOffset, (int) (points[i][1] + p.getVelocity().getY()) + yOffset);
            }
        }

        if (grounded) {
            if (Math.abs(xVel) < 4) {//idle
                int currentPositionInAnimation = ((int) (CurrentAnimationTime % 12)) * 21;
                g.drawImage(myImages.get("res/textures/man/idle.png"),
                        x + playerAlign + xOffset, y + yOffset,
                        x + trueWidth + playerAlign + xOffset, y + height + yOffset,
                        currentPositionInAnimation, 0,
                        currentPositionInAnimation + 21, 34,
                        null);
            } else {//running
                int currentPositionInAnimation = ((int) (CurrentAnimationTime % 8)) * 23;
                g.drawImage(myImages.get("res/textures/man/run.png"),
                        x + playerAlign + xOffset, y + yOffset,
                        x + trueWidth + playerAlign + xOffset, y + height + yOffset,
                        currentPositionInAnimation, 0,
                        currentPositionInAnimation + 22, 34,
                        null);
            }
        } else {
            if (yVel > 5) {//falling
                g.drawImage(myImages.get("res/textures/man/falling.png"),
                        x + playerAlign + xOffset, y + yOffset,
                        x + trueWidth + playerAlign + xOffset, y + height + yOffset,
                        0, 0,
                        21, 34,
                        null);
            } else if (yVel < -5) {//rising
                g.drawImage(myImages.get("res/textures/man/rising.png"),
                        x + playerAlign + xOffset, y + yOffset,
                        x + trueWidth + playerAlign + xOffset, y + height + yOffset,
                        0, 0,
                        21, 34,
                        null);
            } else {//Midair
                int currentPositionInAnimation = ((int) ((CurrentAnimationTime / 4) % 2)) * 21;
                g.drawImage(myImages.get("res/textures/man/midair.png"),
                        x + playerAlign + xOffset, y + yOffset,
                        x + trueWidth + playerAlign + xOffset, y + height + yOffset,
                        currentPositionInAnimation + 1, 0,
                        currentPositionInAnimation + 21, 34,
                        null);
            }
        }
    }

    private void drawLevel(Graphics g) {
        LevelMap l = gameworld.getLevelMap();

        int mapWidth = l.getWidth();
        int mapHeight = l.getHeight();

        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                drawTile(j, i, l.getOffsetX(), l.getOffsetY(), g);
            }
        }
    }

    private void drawTile(int x, int y, int xOffset, int yOffset, Graphics g) {
        int size = gameworld.getLevelMap().getTileSize();
        int borderSize = 6;//Change the size of empty around the key

        //A bunch of offset values to use to make certain objects hover slightly
        int[] floating = new int[]{-1, 0, 1, 2, 3, 4, 5, 6, 5, 4, 3, 2, 1, 0};
        int fPos;

        switch (gameworld.getLevelMap().getTile(x, y).getState()) {
            case BLOCK:
                g.drawImage(myImages.get("res/textures/jungle_tileset.png"),
                        (x * size) + xOffset - 5, (y * size) + yOffset - 7,
                        (x * size) + xOffset + size + 4, (y * size) + yOffset + size + 7,
                        208, 32,
                        224, 48,
                        null);

                break;
            case SPIKE:
                g.drawImage(myImages.get("res/textures/spikes.png"),
                        (x * size) + xOffset, (y * size) + yOffset,
                        (x * size) + xOffset + size, (y * size) + yOffset + size,
                        0, 0,
                        42, 42,
                        null);
                break;
            case ACTIVE_CHECKPOINT:
                g.drawImage(myImages.get("res/textures/flag_up.png"),
                        (x * size) + xOffset, (y * size) + yOffset,
                        (x * size) + xOffset + size, (y * size) + yOffset + size,
                        0, 0,
                        26, 26,
                        null);
                break;
            case INACTIVE_CHECKPOINT:
                g.drawImage(myImages.get("res/textures/flag_down.png"),
                        (x * size) + xOffset, (y * size) + yOffset,
                        (x * size) + xOffset + size, (y * size) + yOffset + size,
                        0, 0,
                        26, 26,
                        null);
                break;
            case KEY:
                //Calculates an offset position for the key so it hovers slightly.
                fPos = ((int) ((CurrentAnimationTime + 3) / 2 % floating.length));

                g.drawImage(myImages.get("res/textures/key.png"),
                        (x * size) + xOffset + borderSize, (y * size) + yOffset + borderSize + floating[fPos],
                        (x * size) + xOffset + size - borderSize, (y * size) + yOffset + size - borderSize + floating[fPos],
                        0, 0,
                        26, 26,
                        null);

                break;
            case KEY_COLLECTED:
                //Calculates an offset position for the key so it hovers slightly.
                fPos = ((int) ((CurrentAnimationTime + 4) / 2 % floating.length));

                g.drawImage(myImages.get("res/textures/key_transparent.png"),
                        (x * size) + xOffset + borderSize, (y * size) + yOffset + borderSize + floating[fPos],
                        (x * size) + xOffset + size - borderSize, (y * size) + yOffset + size - borderSize + floating[fPos],
                        0, 0,
                        26, 26,
                        null);
                break;
            case LOCK:
                g.drawImage(myImages.get("res/textures/lock.png"),
                        (x * size) + xOffset, (y * size) + yOffset,
                        (x * size) + xOffset + size, (y * size) + yOffset + size,
                        0, 0,
                        32, 32,
                        null);

                break;
            case UNLOCKED:
                g.drawImage(myImages.get("res/textures/lock_transparent.png"),
                        (x * size) + xOffset, (y * size) + yOffset,
                        (x * size) + xOffset + size, (y * size) + yOffset + size,
                        0, 0,
                        32, 32,
                        null);

                break;
            case FINISH:
                int horizontalSpace = 1;
                int verticalSpace = 5;

                //Calculates an offset position for the gem so it hovers slightly.
                fPos = ((int) ((CurrentAnimationTime / 2) % floating.length));

                g.drawImage(myImages.get("res/textures/gem.png"),
                        (x * size) + xOffset + horizontalSpace, (y * size) + yOffset + verticalSpace + floating[fPos],
                        (x * size) + xOffset + size - horizontalSpace, (y * size) + yOffset + size - verticalSpace + floating[fPos],
                        0, 0,
                        30, 22,
                        null);
            case EMPTY:
                break;
            default:
                //Sample code used for testing untextured blocks
                g.setColor(Color.black);
                g.fillRect((x * size) + xOffset, (y * size) + yOffset, size, size);
        }

        //Draw grid
        if (DEBUG_MODE) {
            g.setColor(Color.BLACK);
            g.drawRect((x * size) + xOffset, (y * size) + yOffset, size, size);
//        optimisedDrawRect((x * size) + xOffset, (y * size) + yOffset, size, size, g);
        }


    }

    private boolean recenterCamera() {
        PlayerObject p = gameworld.getPlayer();
        LevelMap l = gameworld.getLevelMap();

        //If off camera, recenter.
        int x = (int) p.getCentre().getX();
        int y = (int) p.getCentre().getY();
        int width = p.getWidth();
        int height = p.getHeight();

        int xOffset = l.getOffsetX();
        int yOffset = l.getOffsetY();

        int trueWidth;
        int playerAlign;
        if (!p.isFacingRight()) {
            trueWidth = -width;
            playerAlign = width;
        } else {
            trueWidth = width;
            playerAlign = 0;
        }

        if (!isInFrame(
                x + playerAlign + xOffset,
                y + yOffset,
                x + trueWidth + playerAlign + xOffset,
                y + height + yOffset)) {


            //Recalc x/y offsets
            int updatedXOffset = x - (frameWidth / 2);
            int updatedYOffset = y - (frameHeight / 2);

            //Ensure that the x/y values max out so it does not show OOB area
            updatedXOffset = Math.max(updatedXOffset, 0);
            updatedXOffset = Math.min(updatedXOffset, l.getWidth() * l.getTileSize() - frameWidth);

            updatedYOffset = Math.max(updatedYOffset, 0);
            updatedYOffset = Math.min(updatedYOffset, l.getHeight() * l.getTileSize() - frameHeight);


            l.setOffsetX(-updatedXOffset);
            l.setOffsetY(-updatedYOffset);

            return true;
        }

        return false;
    }

    private void moveCamera() {

        //Percentage of the screen to move if the player enters that part of the screen
        //MAX 0.5 (ie screen follows the player)
        float moveZoneX = (float) 0.4;
        float moveZoneY = (float) 0.4;

        PlayerObject p = gameworld.getPlayer();
        LevelMap l = gameworld.getLevelMap();

        float playerScreenX = p.getCentre().getX() + l.getOffsetX();
        int levelWidthPx = l.getWidth() * l.getTileSize();

        if (playerScreenX > frameWidth * (1 - moveZoneX)) {//Too far right
            int newOffsetX = (int) (l.getOffsetX() - (playerScreenX - frameWidth * (1 - moveZoneX)));
            l.setOffsetX(Math.max(newOffsetX, frameWidth - levelWidthPx));
        }

        if (playerScreenX < frameWidth * moveZoneX) {//Too far left
            int newOffsetX = (int) (l.getOffsetX() - (playerScreenX - frameWidth * moveZoneX));
            l.setOffsetX(Math.min(newOffsetX, 0));
        }

        float playerScreenY = p.getCentre().getY() + l.getOffsetY();
        int levelHeightPx = l.getHeight() * l.getTileSize();

        if (playerScreenY < frameHeight * moveZoneY) {//Too far up
            int newOffsetY = (int) (l.getOffsetY() - (playerScreenY - frameHeight * moveZoneY));
            l.setOffsetY(Math.min(newOffsetY, 0));
        }

        if (playerScreenY > frameHeight * (1 - moveZoneY)) {//Too far down
            int newOffsetY = (int) (l.getOffsetY() - (playerScreenY - frameHeight * (1 - moveZoneY)));
            l.setOffsetY(Math.max(newOffsetY, frameHeight - levelHeightPx));
        }
    }

    private boolean isInFrame(int dx1, int dy1, int dx2, int dy2) {
        return ((0 <= dx1 && dx1 <= frameWidth) || (0 <= dx2 && dx2 <= frameWidth)) && //If either side is within frame and
                ((0 <= dy1 && dy1 <= frameHeight) || (0 <= dy2 && dy2 <= frameHeight));// If top or bottom is within frame
    }

    private void drawKeyCount(Graphics g) {
        int collectedKeyCount = gameworld.getLevelMap().getCollectedKeys().size();
        int totalKeyCount = gameworld.getLevelMap().getKeys().size() + collectedKeyCount;

        if (totalKeyCount == 0) {
            return;
        }
        g.setColor(new Color(24, 44, 59));
        g.fillRect(20, 10, 300, 60);

        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.setColor(Color.white);
        String keyString =
                "Keys collected: " + collectedKeyCount + " / " + totalKeyCount;
        g.drawString(keyString, 30, 50);
    }

    private void drawPause(Graphics g) {
        if (gameworld.getGameState() == GameState.PAUSE) {
            g.setColor(new Color(24, 44, 59));
            g.fillRect(450, 450, 125, 45);

            g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g.setColor(Color.white);
            g.drawString("Paused", 462, 483);
        }
    }
}


/*
 * 
 * 
 *              VIEWER HMD into the world                                                             
                                                                                
                                      .                                         
                                         .                                      
                                             .  ..                              
                               .........~++++.. .  .                            
                 .   . ....,++??+++?+??+++?++?7ZZ7..   .                        
         .   . . .+?+???++++???D7I????Z8Z8N8MD7I?=+O$..                         
      .. ........ZOZZ$7ZZNZZDNODDOMMMMND8$$77I??I?+?+=O .     .                 
      .. ...7$OZZ?788DDNDDDDD8ZZ7$$$7I7III7??I?????+++=+~.                      
       ...8OZII?III7II77777I$I7II???7I??+?I?I?+?+IDNN8??++=...                  
     ....OOIIIII????II?I??II?I????I?????=?+Z88O77ZZO8888OO?++,......            
      ..OZI7III??II??I??I?7ODM8NN8O8OZO8DDDDDDDDD8DDDDDDDDNNNOZ= ......   ..    
     ..OZI?II7I?????+????+IIO8O8DDDDD8DNMMNNNNNDDNNDDDNDDNNNNNNDD$,.........    
      ,ZII77II?III??????DO8DDD8DNNNNNDDMDDDDDNNDDDNNNDNNNNDNNNNDDNDD+.......   .
      7Z??II7??II??I??IOMDDNMNNNNNDDDDDMDDDDNDDNNNNNDNNNNDNNDMNNNNNDDD,......   
 .  ..IZ??IIIII777?I?8NNNNNNNNNDDDDDDDDNDDDDDNNMMMDNDMMNNDNNDMNNNNNNDDDD.....   
      .$???I7IIIIIIINNNNNNNNNNNDDNDDDDDD8DDDDNM888888888DNNNNNNDNNNNNNDDO.....  
       $+??IIII?II?NNNNNMMMMMDN8DNNNDDDDZDDNN?D88I==INNDDDNNDNMNNMNNNNND8:..... 
   ....$+??III??I+NNNNNMMM88D88D88888DDDZDDMND88==+=NNNNMDDNNNNNNMMNNNNND8......
.......8=+????III8NNNNMMMDD8I=~+ONN8D8NDODNMN8DNDNNNNNNNM8DNNNNNNMNNNNDDD8..... 
. ......O=??IIIIIMNNNMMMDDD?+=?ONNNN888NMDDM88MNNNNNNNNNMDDNNNMNNNMMNDNND8......
........,+++???IINNNNNMMDDMDNMNDNMNNM8ONMDDM88NNNNNN+==ND8NNNDMNMNNNNNDDD8......
......,,,:++??I?ONNNNNMDDDMNNNNNNNNMM88NMDDNN88MNDN==~MD8DNNNNNMNMNNNDND8O......
....,,,,:::+??IIONNNNNNNDDMNNNNNO+?MN88DN8DDD888DNMMM888DNDNNNNMMMNNDDDD8,.... .
...,,,,::::~+?+?NNNNNNNMD8DNNN++++MNO8D88NNMODD8O88888DDDDDDNNMMMNNNDDD8........
..,,,,:::~~~=+??MNNNNNNNND88MNMMMD888NNNNNNNMODDDDDDDDND8DDDNNNNNNDDD8,.........
..,,,,:::~~~=++?NMNNNNNNND8888888O8DNNNNNNMMMNDDDDDDNMMNDDDOO+~~::,,,.......... 
..,,,:::~~~~==+?NNNDDNDNDDNDDDDDDDDNNND88OOZZ$8DDMNDZNZDZ7I?++~::,,,............
..,,,::::~~~~==7DDNNDDD8DDDDDDDD8DD888OOOZZ$$$7777OOZZZ$7I?++=~~:,,,.........   
..,,,,::::~~~~=+8NNNNNDDDMMMNNNNNDOOOOZZZ$$$77777777777II?++==~::,,,......  . ..
...,,,,::::~~~~=I8DNNN8DDNZOM$ZDOOZZZZ$$$7777IIIIIIIII???++==~~::,,........  .  
....,,,,:::::~~~~+=++?I$$ZZOZZZZZ$$$$$777IIII?????????+++==~~:::,,,...... ..    
.....,,,,:::::~~~~~==+?II777$$$$77777IIII????+++++++=====~~~:::,,,........      
......,,,,,:::::~~~~==++??IIIIIIIII?????++++=======~~~~~~:::,,,,,,.......       
.......,,,,,,,::::~~~~==+++???????+++++=====~~~~~~::::::::,,,,,..........       
.........,,,,,,,,::::~~~======+======~~~~~~:::::::::,,,,,,,,............        
  .........,.,,,,,,,,::::~~~~~~~~~~:::::::::,,,,,,,,,,,...............          
   ..........,..,,,,,,,,,,::::::::::,,,,,,,,,.,....................             
     .................,,,,,,,,,,,,,,,,.......................                   
       .................................................                        
           ....................................                                 
               ....................   .                                         
                                                                                
                                                                                
                                                                 GlassGiant.com
                                                                 */
