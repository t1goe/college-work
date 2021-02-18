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
import java.util.Iterator;

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

    public Viewer(Model World) {
        this.gameworld = World;
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
        File TextureToLoad = new File("res/jungle/background.png");  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
        try {
            Image myImage = ImageIO.read(TextureToLoad);
            g.drawImage(myImage, 0, 0, 1000, 1000, 0, 20, 383, 216, null);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

//        int xOffset = l.getOffsetX();
//        int yOffset = l.getOffsetY();

        int xOffset = 100;
        int yOffset = 100;

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

        try {
            if (grounded) {
                if (Math.abs(xVel) < 4) {//idle
                    File TextureToLoad = new File("res/jungle/man/idle.png");
                    Image myImage = ImageIO.read(TextureToLoad);
                    int currentPositionInAnimation = ((int) (CurrentAnimationTime % 12)) * 21;
                    g.drawImage(myImage,
                            x + playerAlign + xOffset, y + yOffset,
                            x + trueWidth + playerAlign + xOffset, y + height + yOffset,
                            currentPositionInAnimation, 0,
                            currentPositionInAnimation + 21, 34,
                            null);
                } else {//running
                    File TextureToLoad = new File("res/jungle/man/run.png");
                    Image myImage = ImageIO.read(TextureToLoad);
                    int currentPositionInAnimation = ((int) (CurrentAnimationTime % 8)) * 23;
                    g.drawImage(myImage,
                            x + playerAlign + xOffset, y + yOffset,
                            x + trueWidth + playerAlign + xOffset, y + height + yOffset,
                            currentPositionInAnimation, 0,
                            currentPositionInAnimation + 22, 34,
                            null);
                }
            } else {
                if (yVel > 5) {//falling
                    File TextureToLoad = new File("res/jungle/man/falling.png");
                    Image myImage = ImageIO.read(TextureToLoad);
                    g.drawImage(myImage,
                            x + playerAlign + xOffset, y + yOffset,
                            x + trueWidth + playerAlign + xOffset, y + height + yOffset,
                            0, 0,
                            21, 34,
                            null);
                } else if (yVel < -5) {//rising
                    File TextureToLoad = new File("res/jungle/man/rising.png");
                    Image myImage = ImageIO.read(TextureToLoad);
                    g.drawImage(myImage,
                            x + playerAlign + xOffset, y + yOffset,
                            x + trueWidth + playerAlign + xOffset, y + height + yOffset,
                            0, 0,
                            21, 34,
                            null);
                } else {//Midair
                    File TextureToLoad = new File("res/jungle/man/midair.png");
                    Image myImage = ImageIO.read(TextureToLoad);
                    int currentPositionInAnimation = ((int) ((CurrentAnimationTime / 4) % 2)) * 21;
                    g.drawImage(myImage,
                            x + playerAlign + xOffset, y + yOffset,
                            x + trueWidth + playerAlign + xOffset, y + height + yOffset,
                            currentPositionInAnimation + 1, 0,
                            currentPositionInAnimation + 21, 34,
                            null);
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer));
        //Lighnting Png from https://opengameart.org/content/animated-spaceships  its 32x32 thats why I know to increament by 32 each time
        // Bullets from https://opengameart.org/forumtopic/tatermands-art
        // background image from https://www.needpix.com/photo/download/677346/space-stars-nebula-background-galaxy-universe-free-pictures-free-photos-free-images

    }

    private void drawLevel(PlayerObject p, LevelMap l, Graphics g) {
        int mapWidth = l.getWidth();
        int mapHeight = l.getHeight();

        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                drawTile(j, i, 100, 100, g);
            }
        }
    }

    private void drawTile(int x, int y, int xOffset, int yOffset, Graphics g) {
        int size = gameworld.getLevelMap().getTileSize();
        switch (gameworld.getLevelMap().getTile(x, y).getState()) {
            case BLOCK:
                g.setColor(Color.black);
                g.fillRect((x * size) + xOffset, (y * size) + yOffset, size, size);
                break;
            case SPIKE:
                g.setColor(Color.red);
//                g.fillRect(x * size, y * size, size, size);
                g.fillRect((x * size) + xOffset, (y * size) + yOffset, size, size);

                break;
            case ACTIVE_CHECKPOINT:
                g.setColor(Color.green);
//                g.fillRect(x * size, y * size, size, size);
                g.fillRect((x * size) + xOffset, (y * size) + yOffset, size, size);
                break;
            case INACTIVE_CHECKPOINT:
                g.setColor(Color.yellow);
//                g.fillRect(x * size, y * size, size, size);
                g.fillRect((x * size) + xOffset, (y * size) + yOffset, size, size);
                break;
            case EMPTY:
                break;
        }

        g.setColor(Color.BLACK);
//        g.drawRect(x * size, y * size, size, size);
        g.drawRect((x * size) + xOffset, (y * size) + yOffset, size, size);


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
