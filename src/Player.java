import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Player extends Entity {
    DungeonPanel dp;
    KeyHandler key;
    int playerScale = 2;
    int currentRow;
    int currentCol;

    public Player(DungeonPanel dp, KeyHandler key) {
        this.dp = dp;
        this.key = key;

        // this.hitBoxXShift = 32;
        // this.hitBoxYShift = 18;
        // this.hitBoxWidth = 63;
        // this.hitBoxHeight = 105;
        // this.zonePadding = 6;

        // this.hitBoxXShift = 16 * playerScale;
        // this.hitBoxYShift = 9 * playerScale;
        // this.hitBoxWidth = 32 * playerScale;
        // this.hitBoxHeight = 52 * playerScale;
        // this.zonePadding = 3 * playerScale;

        this.hitBoxXShift = 8 * playerScale * dp.scale;
        this.hitBoxYShift = 4 * playerScale * dp.scale;
        this.hitBoxWidth = 16 * playerScale * dp.scale;
        this.hitBoxHeight = 26 * playerScale * dp.scale;
        this.zonePadding = 2 * playerScale * dp.scale;

        this.hitBox = new Rectangle((int) xPos + hitBoxXShift, (int) yPos + hitBoxYShift, this.hitBoxWidth, this.hitBoxHeight);
        this.width = this.hitBoxWidth;
        this.height = this.hitBoxHeight;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        this.xPos = dp.screenWidth / 2;
        this.yPos = dp.screenHeight / 2;
        this.speed = 2;
        this.playerDirection = "down";
        this.topZone = new Rectangle((int) xPos + hitBoxXShift + this.zonePadding, (int) yPos + hitBoxYShift,
                this.hitBoxWidth - (2 * this.zonePadding), this.zonePadding);
        this.bottomZone = new Rectangle((int) xPos + hitBoxXShift + this.zonePadding,
                (int) yPos + this.hitBoxHeight + hitBoxYShift - this.zonePadding,
                this.hitBoxWidth - (2 * this.zonePadding), this.zonePadding);
        this.leftZone = new Rectangle((int) xPos + hitBoxXShift, (int) yPos + hitBoxYShift + this.zonePadding,
                this.zonePadding, this.hitBoxHeight - (2 * this.zonePadding));
        this.rightZone = new Rectangle((int) xPos + hitBoxXShift + this.hitBoxWidth - this.zonePadding,
                (int) yPos + hitBoxYShift + this.zonePadding, this.zonePadding,
                this.hitBoxHeight - (2 * this.zonePadding));
    }

    public void getPlayerImage() {

        try {
            this.up = ImageIO.read(getClass().getResourceAsStream("/MainCharImages/back.png"));
            this.up1 = ImageIO.read(getClass().getResourceAsStream("/MainCharImages/back1.png"));
            this.up2 = ImageIO.read(getClass().getResourceAsStream("/MainCharImages/back2.png"));
            this.down = ImageIO.read(getClass().getResourceAsStream("/MainCharImages/front.png"));
            this.down1 = ImageIO.read(getClass().getResourceAsStream("/MainCharImages/front1.png"));
            this.down2 = ImageIO.read(getClass().getResourceAsStream("/MainCharImages/front2.png"));
            this.left = ImageIO.read(getClass().getResourceAsStream("/MainCharImages/leftside.png"));
            this.left1 = ImageIO.read(getClass().getResourceAsStream("/MainCharImages/leftside1.png"));
            this.left2 = ImageIO.read(getClass().getResourceAsStream("/MainCharImages/leftside2.png"));
            this.right = ImageIO.read(getClass().getResourceAsStream("/MainCharImages/rightside.png"));
            this.right1 = ImageIO.read(getClass().getResourceAsStream("/MainCharImages/rightside1.png"));
            this.right2 = ImageIO.read(getClass().getResourceAsStream("/MainCharImages/rightside2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

        //OLD VERSION
        // for (Tile tile : dp.tileM.tileCollection) {
        //     if (this.isCollidingWithTile(topZone, tile)) {
        //         this.isCollidingTopZone = true;
        //         break;
        //     } else {
        //         this.isCollidingTopZone = false;
        //     }
        // }

        for (int i = 0; i < dp.tileM.tileCollection.size(); i++) {
            Tile tile = dp.tileM.tileCollection.get(i);
            if (this.isCollidingWithTile(topZone, tile)) {
                this.isCollidingTopZone = true;
                break;
            } else {
                this.isCollidingTopZone = false;
            }
        }

        for (int i = 0; i < dp.tileM.tileCollection.size(); i++) {
            Tile tile = dp.tileM.tileCollection.get(i);
            if (this.isCollidingWithTile(bottomZone, tile)) {
                this.isCollidingBottomZone = true;
                break;
            } else {
                this.isCollidingBottomZone = false;
            }
        }

        for (int i = 0; i < dp.tileM.tileCollection.size(); i++) {
            Tile tile = dp.tileM.tileCollection.get(i);
            if (this.isCollidingWithTile(leftZone, tile)) {
                this.isCollidingLeftZone = true;
                break;
            } else {
                this.isCollidingLeftZone = false;
            }
        }

        for (int i = 0; i < dp.tileM.tileCollection.size(); i++) {
            Tile tile = dp.tileM.tileCollection.get(i);
            if (this.isCollidingWithTile(rightZone, tile)) {
                this.isCollidingRightZone = true;
                break;
            } else {
                this.isCollidingRightZone = false;
            }
        }

        if (key.upPressed == true) {
            this.playerDirection = "up";
            if (!this.isCollidingTopZone) {
                yPos -= speed;
            }
        }
        if (key.downPressed == true) {
            this.playerDirection = "down";
            if (!this.isCollidingBottomZone) {
                yPos += speed;
            }
        }
        if (key.leftPressed == true) {
            this.playerDirection = "left";
            if (!this.isCollidingLeftZone) {
                xPos -= speed;
            }
        }
        if (key.rightPressed == true) {
            this.playerDirection = "right";
            if (!this.isCollidingRightZone) {
                xPos += speed;
            }
        }

        this.updateHitBox();
        //this.adjustHitBox(dp.camera);

        if (key.areAnyKeysPressed()) {
            spriteCounter++;
            if (spriteCounter > 20) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    @Override
    public void paint(Graphics2D g2) {
        BufferedImage image = null;

        if (playerDirection.equals("up")) {
            if (key.areAnyKeysPressed()) {
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
            } else {
                image = up;
            }
        } else if (playerDirection.equals("down")) {
            if (key.areAnyKeysPressed()) {
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
            } else {
                image = down;
            }
        } else if (playerDirection.equals("left")) {
            if (key.areAnyKeysPressed()) {
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
            } else {
                image = left;
            }
        } else if (playerDirection.equals("right")) {
            if (key.areAnyKeysPressed()) {
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
            } else {
                image = right;
            }
        }

        g2.drawImage(image, (int) (this.xPos - dp.camera.getXOffset()), (int) (this.yPos - dp.camera.getYOffset()), dp.tileSize * playerScale, dp.tileSize * playerScale,null);

        // for testing to see hitbox
        if (this.isCollidingTopZone) {
            g2.setColor(Color.red);
        } else {
            g2.setColor(Color.green);
        }
        // g2.draw(this.topZone);

        if (this.isCollidingBottomZone) {
            g2.setColor(Color.red);
        } else {
            g2.setColor(Color.green);
        }
        // g2.draw(this.bottomZone);

        if (this.isCollidingLeftZone) {
            g2.setColor(Color.red);
        } else {
            g2.setColor(Color.green);
        }
        // g2.draw(this.leftZone);

        if (this.isCollidingRightZone) {
            g2.setColor(Color.red);
        } else {
            g2.setColor(Color.green);
        }
        // g2.draw(this.rightZone);

        g2.drawRect((int)(this.hitBox.x - dp.camera.xOffset), (int)(this.hitBox.y - dp.camera.yOffset), this.hitBox.width, this.hitBox.height);

    }

    @Override
    public void isColliding(GameObject gameObject) {
        if (!this.hitBox.intersects(gameObject.hitBox) && !this.hitBox.contains(gameObject.hitBox)) {
            return;
        }

        if (gameObject instanceof Door) {
        // cant see the room being entered so it dosent know where to place player
        // update map needs to look at what room the player is now in and then place them in the proper cords
        // going to be very dificult, maybe make new method to check where they are, has to look at room shape 
        // also has to look which direction player enterend new room to know which wall to put player
        // the cords that I started arent valid cause it dosent know what walking into
            if (this.hitBox.contains(gameObject.hitBox)) {
                if (((Door) gameObject).direction.equals("Top")) {
                    this.xPos = dp.screenWidth / 2 - this.width;
                    this.yPos = dp.screenHeight - dp.tileSize - this.height * 1.5;
                    this.currentRow--;
                    dp.tileM.updateMap();
                } else if (((Door) gameObject).direction.equals("Right")) {
                    this.xPos = 0 + dp.tileSize;
                    this.yPos = dp.screenHeight / 2 - (this.height * 1.25 / 2);
                    this.currentCol++;
                    dp.tileM.updateMap();
                } else if (((Door) gameObject).direction.equals("Bottom")) {
                    this.xPos = dp.screenWidth / 2 - this.width;
                    this.yPos = 0 + this.height - this.height * .25;
                    this.currentRow++;
                    dp.tileM.updateMap();
                } else if (((Door) gameObject).direction.equals("Left")) {
                    this.xPos = dp.screenWidth - dp.tileSize - this.width * 2;
                    this.yPos = dp.screenHeight / 2 - (this.height * 1.25 / 2);
                    this.currentCol--;
                    dp.tileM.updateMap();
                } else if (dp.floorM.floor[this.currentRow][this.currentCol].layoutLocation[0][0] != null && dp.floorM.floor[this.currentRow][this.currentCol].layoutLocation[0][0].equals(dp.floorM.roomGroups.get(new LocationNode(this.currentRow, this.currentCol, dp.floorM).findIndexOfGroup()).get(0))){
                    if (((Door) gameObject).direction.equals("TopLeft")) {
                        this.xPos = dp.screenWidth / 2 - this.width;
                        this.yPos = dp.screenHeight - dp.tileSize - this.height * 1.5;
                        this.currentRow--;
                        dp.tileM.updateMap();
                    } else if (((Door) gameObject).direction.equals("TopRight")) {
                        this.xPos = dp.screenWidth / 2 - this.width + dp.screenWidth;
                        this.yPos = dp.screenHeight - dp.tileSize - this.height * 1.5;
                        this.currentRow--;
                        this.currentCol++;
                        dp.tileM.updateMap();
                    } else if (((Door) gameObject).direction.equals("LeftTop")) {
                        this.xPos = dp.screenWidth - dp.tileSize - this.width * 2;
                        this.yPos = dp.screenHeight / 2 - (this.height * 1.25 / 2);
                        this.currentCol--;
                        dp.tileM.updateMap();
                    } else if (((Door) gameObject).direction.equals("MiddleRightTop")) {
                        this.xPos = dp.screenWidth / 2 - this.width + dp.screenWidth;
                        this.yPos = dp.screenHeight - dp.tileSize - this.height * 1.5;
                        this.currentCol++;
                        dp.tileM.updateMap();
                    } else if (((Door) gameObject).direction.equals("MiddleLeftTop")) {
                        this.xPos = dp.screenWidth - dp.tileSize - this.width * 2;
                        this.yPos = dp.screenHeight / 2 - (this.height * 1.25 / 2);
                        this.currentCol--;
                        dp.tileM.updateMap();
                    } else if (((Door) gameObject).direction.equals("RightTop")) {
                        this.currentCol += 2;
                        dp.tileM.updateMap();
                    } else if (((Door) gameObject).direction.equals("MiddleBottomLeft")) {
                        this.currentRow++;
                        dp.tileM.updateMap();
                    } else if (((Door) gameObject).direction.equals("MiddleBottomRight")) {
                        this.currentRow++;
                        this.currentCol++;
                        dp.tileM.updateMap();
                    } else if (((Door) gameObject).direction.equals("MiddleTopLeft")) {
                        this.currentRow--;
                        dp.tileM.updateMap();
                    } else if (((Door) gameObject).direction.equals("MiddleTopRight")) {
                        this.currentRow--;
                        this.currentCol++;
                        dp.tileM.updateMap();
                    } else if (((Door) gameObject).direction.equals("LeftBottom")) {
                        this.currentRow++;
                        this.currentCol--;
                        dp.tileM.updateMap();
                    } else if (((Door) gameObject).direction.equals("RightBottom")) {
                        this.currentRow++;
                        this.currentCol += 2;
                        dp.tileM.updateMap();
                    } else if (((Door) gameObject).direction.equals("MiddleLeftBottom")) {
                        this.currentRow++;
                        dp.tileM.updateMap();
                    } else if (((Door) gameObject).direction.equals("MiddleRightBottom")) {
                        this.currentRow++;
                        this.currentCol++;
                        dp.tileM.updateMap();
                    } else if (((Door) gameObject).direction.equals("BottomLeft")) {
                        this.currentRow += 2;
                        dp.tileM.updateMap();
                    } else if (((Door) gameObject).direction.equals("BottomRight")) {
                        this.currentRow += 2;
                        this.currentCol ++;
                        dp.tileM.updateMap();
                    }
                }
        
            }
        }

    }

    @Override
    public boolean isCollidingWithTile(Rectangle zone, Tile tile) {
        Rectangle tempHitBox = this.hitBox;
        if (zone.equals(this.topZone)) {
            tempHitBox = this.topZone;
        } else if (zone.equals(this.bottomZone)) {
            tempHitBox = this.bottomZone;
        } else if (zone.equals(this.leftZone)) {
            tempHitBox = this.leftZone;
        } else if (zone.equals(this.rightZone)) {
            tempHitBox = this.rightZone;
        }

        if (tempHitBox.intersects(tile.hitBox)) {
            if (tile.collision) {
                return true;
            } else {
                return false;
            }
            // dp.gameObjects.remove(gameObject);
            // need to remember

        }

        return false;
    }
}
