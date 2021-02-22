import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import util.GameObject;
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


    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        CurrentAnimationTime++; // runs animation time step

        //Draw background
        drawBackground(g);

        //Update level's x/y offsets
        calculateOffset(gameworld.getPlayer(), gameworld.getLevelMap());

        //Draw player
        drawPlayer(gameworld.getPlayer(), gameworld.getLevelMap(), g);

        //Draw Bullets
        // change back
        gameworld.getBullets().forEach((temp) ->
        {
            drawBullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(), g);
        });

        //Draw Enemies
        gameworld.getEnemies().forEach((temp) ->
        {
            drawEnemies((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(), g);

        });

        drawLevel(gameworld.getPlayer(), gameworld.getLevelMap(), g);
    }

    private void loadImages() {
        //All jungle assets from https://jesse-m.itch.io/jungle-pack

        //Key from https://cheekyinkling.itch.io/shikashis-fantasy-icons-pack

        //Flag is homemade

        //Spikes and lock (modified) from https://pixelfrog-store.itch.io/pixel-adventure-1

        String[] imageLocations = {
                "res/jungle/man/idle.png",
                "res/jungle/man/run.png",
                "res/jungle/man/falling.png",
                "res/jungle/man/rising.png",
                "res/jungle/man/midair.png",
                "res/jungle/background.png",
                "res/jungle/jungle_tileset.png",
                "res/jungle/title.png",
                "res/jungle/key.png",
                "res/jungle/key_transparent.png",
                "res/jungle/flag_up.png",
                "res/jungle/flag_down.png",
                "res/jungle/spikes.png",
                "res/jungle/lock.png",
                "res/jungle/lock_transparent.png"
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

    private void drawEnemies(int x, int y, int width, int height, String texture, Graphics g) {
        File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
        try {
            Image myImage = ImageIO.read(TextureToLoad);
            //The spirte is 32x32 pixel wide and 4 of them are placed together so we need to grab a different one each time
            //remember your training :-) computer science everything starts at 0 so 32 pixels gets us to 31
            int currentPositionInAnimation = ((int) (CurrentAnimationTime % 4) * 32); //slows down animation so every 10 frames we get another frame so every 100ms
            g.drawImage(myImage, x, y, x + width, y + height, currentPositionInAnimation, 0, currentPositionInAnimation + 31, 32, null);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void drawBackground(Graphics g) {
        g.drawImage(myImages.get("res/jungle/background.png"), 0, 0, 1000, 1000, 0, 20, 383, 216, null);
    }

    private void drawBullet(int x, int y, int width, int height, String texture, Graphics g) {
        File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
        try {
            Image myImage = ImageIO.read(TextureToLoad);
            //64 by 128
            g.drawImage(myImage, x, y, x + width, y + height, 0, 0, 63, 127, null);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void drawPlayer(PlayerObject p, LevelMap l, Graphics g) {

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

        //Hitbox
        g.drawRect(x + xOffset, y + yOffset, width, height);

        //Velocity lines
        for (int i = 0; i < points.length; i++) {
            g.drawLine((int) points[i][0] + xOffset, (int) points[i][1] + yOffset,
                    (int) (points[i][0] + p.getVelocity().getX()) + xOffset, (int) (points[i][1] + p.getVelocity().getY()) + yOffset);
        }

        if (grounded) {
            if (Math.abs(xVel) < 4) {//idle
                int currentPositionInAnimation = ((int) (CurrentAnimationTime % 12)) * 21;
                g.drawImage(myImages.get("res/jungle/man/idle.png"),
                        x + playerAlign + xOffset, y + yOffset,
                        x + trueWidth + playerAlign + xOffset, y + height + yOffset,
                        currentPositionInAnimation, 0,
                        currentPositionInAnimation + 21, 34,
                        null);
            } else {//running
                int currentPositionInAnimation = ((int) (CurrentAnimationTime % 8)) * 23;
                g.drawImage(myImages.get("res/jungle/man/run.png"),
                        x + playerAlign + xOffset, y + yOffset,
                        x + trueWidth + playerAlign + xOffset, y + height + yOffset,
                        currentPositionInAnimation, 0,
                        currentPositionInAnimation + 22, 34,
                        null);
            }
        } else {
            if (yVel > 5) {//falling
                g.drawImage(myImages.get("res/jungle/man/falling.png"),
                        x + playerAlign + xOffset, y + yOffset,
                        x + trueWidth + playerAlign + xOffset, y + height + yOffset,
                        0, 0,
                        21, 34,
                        null);
            } else if (yVel < -5) {//rising
                g.drawImage(myImages.get("res/jungle/man/rising.png"),
                        x + playerAlign + xOffset, y + yOffset,
                        x + trueWidth + playerAlign + xOffset, y + height + yOffset,
                        0, 0,
                        21, 34,
                        null);
            } else {//Midair
                int currentPositionInAnimation = ((int) ((CurrentAnimationTime / 4) % 2)) * 21;
                g.drawImage(myImages.get("res/jungle/man/midair.png"),
                        x + playerAlign + xOffset, y + yOffset,
                        x + trueWidth + playerAlign + xOffset, y + height + yOffset,
                        currentPositionInAnimation + 1, 0,
                        currentPositionInAnimation + 21, 34,
                        null);
            }
        }
    }

    private void drawLevel(PlayerObject p, LevelMap l, Graphics g) {
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

        switch (gameworld.getLevelMap().getTile(x, y).getState()) {
            case BLOCK:
//                g.setColor(Color.black);
//                g.fillRect((x * size) + xOffset, (y * size) + yOffset, size, size);

                g.drawImage(myImages.get("res/jungle/jungle_tileset.png"),
                        (x * size) + xOffset - 5, (y * size) + yOffset - 7,
                        (x * size) + xOffset + size + 4, (y * size) + yOffset + size + 7,
                        208, 32,
                        224, 48,
                        null);

                break;
            case SPIKE:
//                g.setColor(Color.red);
//                g.fillRect((x * size) + xOffset, (y * size) + yOffset, size, size);

                g.drawImage(myImages.get("res/jungle/spikes.png"),
                        (x * size) + xOffset, (y * size) + yOffset,
                        (x * size) + xOffset + size, (y * size) + yOffset + size,
                        0, 0,
                        42, 42,
                        null);
                break;
            case ACTIVE_CHECKPOINT:
//                g.setColor(Color.green);
//                g.fillRect((x * size) + xOffset, (y * size) + yOffset, size, size);

                g.drawImage(myImages.get("res/jungle/flag_up.png"),
                        (x * size) + xOffset, (y * size) + yOffset,
                        (x * size) + xOffset + size, (y * size) + yOffset + size,
                        0, 0,
                        26, 26,
                        null);
                break;
            case INACTIVE_CHECKPOINT:
//                g.setColor(Color.yellow);
//                g.fillRect((x * size) + xOffset, (y * size) + yOffset, size, size);

                g.drawImage(myImages.get("res/jungle/flag_down.png"),
                        (x * size) + xOffset, (y * size) + yOffset,
                        (x * size) + xOffset + size, (y * size) + yOffset + size,
                        0, 0,
                        26, 26,
                        null);
                break;
            case KEY:
//                g.setColor(Color.blue);
//                g.fillRect((x * size) + xOffset, (y * size) + yOffset, size, size);

                g.drawImage(myImages.get("res/jungle/key.png"),
                        (x * size) + xOffset + borderSize, (y * size) + yOffset + borderSize,
                        (x * size) + xOffset + size - borderSize, (y * size) + yOffset + size - borderSize,
                        0, 0,
                        26, 26,
                        null);

                break;
            case KEY_COLLECTED:
//                g.setColor(Color.lightGray);
//                g.fillRect((x * size) + xOffset, (y * size) + yOffset, size, size);
                g.drawImage(myImages.get("res/jungle/key_transparent.png"),
                        (x * size) + xOffset + borderSize, (y * size) + yOffset + borderSize,
                        (x * size) + xOffset + size - borderSize, (y * size) + yOffset + size - borderSize,
                        0, 0,
                        26, 26,
                        null);
                break;
            case LOCK:
//                g.setColor(Color.PINK);
//                g.fillRect((x * size) + xOffset, (y * size) + yOffset, size, size);
                g.drawImage(myImages.get("res/jungle/lock.png"),
                        (x * size) + xOffset, (y * size) + yOffset,
                        (x * size) + xOffset + size, (y * size) + yOffset + size,
                        0, 0,
                        32, 32,
                        null);

                break;
            case UNLOCKED:
//                g.setColor(Color.PINK);
//                g.fillRect((x * size) + xOffset, (y * size) + yOffset, size, size);
                g.drawImage(myImages.get("res/jungle/lock_transparent.png"),
                        (x * size) + xOffset, (y * size) + yOffset,
                        (x * size) + xOffset + size, (y * size) + yOffset + size,
                        0, 0,
                        32, 32,
                        null);

                break;
            case FINISH:
                g.setColor(Color.cyan);
                g.fillRect((x * size) + xOffset, (y * size) + yOffset, size, size);
            case EMPTY:
                break;
        }

        g.setColor(Color.BLACK);
//        g.drawRect(x * size, y * size, size, size);
        g.drawRect((x * size) + xOffset, (y * size) + yOffset, size, size);


    }

    private void calculateOffset(PlayerObject p, LevelMap l) {
        //TODO REWRITE THIS
        int frameWidth = 1000;
        int frameHeight = 1000;

        //Percentage of the screen to move if the player enters that part of the screen
        float moveZoneX = (float) 0.2;
        float moveZoneY = (float) 0.3;

        float playerScreenX = p.getCentre().getX() + l.getOffsetX();
        int levelWidthPx = l.getWidth() * l.getTileSize();
        int updatedOffsetX = (int) (l.getOffsetX() - p.getVelocity().getX());

        if (playerScreenX > frameWidth * (1 - moveZoneX) && p.isFacingRight()) {//Move right
            if (-updatedOffsetX < levelWidthPx - frameWidth) {
                l.setOffsetX((updatedOffsetX));
            } else {
                l.setOffsetX(-levelWidthPx + frameWidth);
            }
        } else if (playerScreenX < frameWidth * moveZoneX && !p.isFacingRight()) {//Move left
            if (updatedOffsetX < 0) {
                l.setOffsetX((updatedOffsetX));
            } else {
                l.setOffsetX(0);
            }
        }

        float playerScreenY = p.getCentre().getY() + l.getOffsetY();
        int levelHeightPx = l.getHeight() * l.getTileSize();
        int updatedOffsetY = (int) (l.getOffsetY() - p.getVelocity().getY());

        if (playerScreenY > frameHeight * (1 - moveZoneY) && p.getVelocity().getY() > 0) {//Move down
            if (updatedOffsetY < -levelHeightPx + frameHeight) {
                l.setOffsetY(-levelHeightPx + frameHeight);
            } else {
                l.setOffsetY(updatedOffsetY);
            }
        } else if (playerScreenY < frameHeight * moveZoneY && p.getVelocity().getY() < 0) {//Move up

            if ((updatedOffsetY) < 0) {
                l.setOffsetY(updatedOffsetY);
            } else {
                l.setOffsetY(0);
            }
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
