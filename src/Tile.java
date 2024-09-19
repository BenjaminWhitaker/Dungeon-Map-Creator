import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile extends GameObject {

    public BufferedImage image;
    public boolean collision = false;
    public int imageType;
    DungeonPanel dp;

    public Tile(double x, double y, int imageType, DungeonPanel dp) {
        this.imageType = imageType;
        this.dp = dp;
        this.xPos = x;
        this.yPos = y;
        this.width = dp.tileSize;
        this.height = dp.tileSize;
        this.hitBox = new Rectangle((int) this.xPos, (int) this.yPos, this.width, this.height);
        this.collision = false;
        getTileImage();
    }

    public void getTileImage() {
        try {

            // only for scanning not actually used
            if (imageType == 0) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1.png"));
            } else if (imageType == 1) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1.png"));
            } else if (imageType == 2) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1.png"));
            } else if (imageType == 3) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1.png"));
            } else if (imageType == 4) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1.png"));
            } else if (imageType == 5) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1.png"));
            } else if (imageType == 6) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1.png"));
            } else if (imageType == 7) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1.png"));
            } else if (imageType == 8) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1.png"));
            } else if (imageType == 9) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1.png"));
                // start actually being used

                // START DIRT FLOOR
            }  else if (imageType == 10) {

                if (Math.random() >= .07){
                    this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1.png"));
                } else {
                    int randNum = (int) (Math.random() * 13);

                    if (randNum == 0){
                        this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1Type1.png"));
                        
                    } else if(randNum == 1){
                        this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1Type2.png"));

                    } else if(randNum == 2){
                        this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1Type3.png"));

                    } else if(randNum == 3){
                        this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1Type4.png"));

                    } else if(randNum == 4){
                        this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1Type5.png"));

                    } else if(randNum == 5){
                        this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1Type6.png"));

                    } else if(randNum == 6){
                        this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1Type7.png"));

                    } else if(randNum == 7){
                        this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1Type8.png"));

                    } else if(randNum == 8){
                        this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1Type9.png"));

                    } else if(randNum == 9){
                        this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1Type10.png"));

                    } else if(randNum == 10){
                        this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1Type11.png"));

                    } else if(randNum == 11){
                        this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1Type12.png"));

                    } else if(randNum == 12){
                        this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt1Type13.png"));

                    }

                }


            }  else if (imageType == 11) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt2.png"));
            }  else if (imageType == 12) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt3.png"));
            }  else if (imageType == 13) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt4.png"));
            }  else if (imageType == 14) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt5.png"));
            }  else if (imageType == 15) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt6.png"));
            }  else if (imageType == 16) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt7.png"));
            }  else if (imageType == 17) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt8.png"));
            }  else if (imageType == 18) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt9.png"));

                // START STONE WALLS
            } else if (imageType == 19) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/StoneWall1.png"));
                this.collision = true;
            } else if (imageType == 20) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/StoneWall2.png"));
                this.collision = true;
            } else if (imageType == 21) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/StoneWall3.png"));
                this.collision = true;
            } else if (imageType == 22) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/StoneWall4.png"));
                this.collision = true;
            } else if (imageType == 23) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/StoneWall5.png"));
                this.collision = true;
            } else if (imageType == 24) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/StoneWall6.png"));
                this.collision = true;
            } else if (imageType == 25) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/StoneWall7.png"));
                this.collision = true;
            } else if (imageType == 26) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/StoneWall8.png"));
                this.collision = true;

                // START BLOCK
            } else if (imageType == 27){
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Block.png"));
                this.collision = true;

                // START VOID
            } else if (imageType == 28){
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/void1.png"));
                this.collision = true;
            } else if (imageType == 29){
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/void2.png"));
                this.collision = true;
            } else if (imageType == 30){
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/void3.png"));
                this.collision = true;
            } else if (imageType == 31){
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/void4.png"));
                this.collision = true;
            } else if (imageType == 32){
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/void5.png"));
                this.collision = true;
            } else if (imageType == 33){
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/void6.png"));
                this.collision = true;
            } else if (imageType == 34){
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/void7.png"));
                this.collision = true;
            } else if (imageType == 35){
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/void8.png"));
                this.collision = true;
            } else if (imageType == 36){
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/void9.png"));
                this.collision = true;

                // START STONE WALL INSIDE CORNER
            } else if (imageType == 37) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/StoneWall9.png"));
                this.collision = true;
            } else if (imageType == 38) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/StoneWall10.png"));
                this.collision = true;
            } else if (imageType == 39) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/StoneWall11.png"));
                this.collision = true;
            } else if (imageType == 40) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/StoneWall12.png"));
                this.collision = true;

                // START DIRT INSIDE CORNER
            } else if (imageType == 41) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt10.png"));
            } else if (imageType == 42) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt11.png"));
            } else if (imageType == 43) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt12.png"));
            } else if (imageType == 44) {
                this.image = ImageIO.read(getClass().getResourceAsStream("/tiles/Dirt13.png"));
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    @Override
    public void paint(Graphics2D g2) {
        g2.drawImage(this.image, (int) (xPos - dp.camera.getXOffset()), (int) (yPos - dp.camera.getYOffset()), this.width, this.height, null);
        //test
        g2.setColor(Color.red);
        /*
        g2.drawString("xPos: " + this.xPos, (int)this.xPos + 3, (int) this.yPos + 20);
        g2.drawString("yPos: " + this.yPos, (int)this.xPos + 3, (int) this.yPos + 30);
        g2.drawString("hitX: " + this.hitBox.x , (int)this.xPos + 3, (int) this.yPos + 40);
        g2.drawString("hitY: " + this.hitBox.y, (int)this.xPos + 3, (int) this.yPos + 50);
        g2.drawString("" + this.collision, (int)this.xPos + 3, (int) this.yPos + 60);
        */
        //g2.draw(this.hitBox);

    }

    @Override
    public void update() {

    }

    @Override
    public void isColliding(GameObject gameObject) {
        
    }

    @Override
    public boolean isCollidingWithTile(Rectangle zone, Tile tile) {
        return false;
    }
}
