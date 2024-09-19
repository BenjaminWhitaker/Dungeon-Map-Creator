import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Door extends Entity {

    private BufferedImage topDoorOpen, topDoorClosed, rightDoorOpen, rightDoorClosed, bottomDoorOpen, bottomDoorClosed, leftDoorOpen, leftDoorClosed;
    public String direction;
    private DungeonPanel dp;
    private int doorWidth;
    private int doorHeight;
    public boolean open;
    private int padding;


    public Door(String direction, DungeonPanel dp){
        if (direction != "Top" && direction != "Right" && direction != "Bottom" && direction != "Left" && direction != "TopLeft" && direction != "TopRight" && direction != "RightTop" && direction != "RightBottom" && direction != "BottomLeft" && direction != "BottomRight" && direction != "LeftTop" && direction != "LeftBottom" && direction != "MiddleBottomLeft" && direction != "MiddleBottomRight" && direction != "MiddleTopLeft" && direction != "MiddleTopRight" && direction != "MiddleRightTop" && direction != "MiddleRightBottom" && direction != "MiddleLeftTop" && direction != "MiddleLeftBottom"){
            throw new Error("Door string is not valid: " + direction);
        }
        this.direction = direction;
        this.dp = dp;
        //this.doorWidth = 125;
        //this.doorHeight = 70;
        this.doorWidth = 62 * dp.scale;
        this.doorHeight = 35 * dp.scale;
        this.padding = 3;
        this.open = false;
        getDoorImage();
        if (this.direction == "Top"){
            this.hitBox = new Rectangle(dp.screenWidth / 2 - (this.doorWidth / 3) / 2, doorHeight - this.padding, this.doorWidth / 3, this.padding);
        } else if (this.direction == "Right"){
            this.hitBox = new Rectangle(dp.screenWidth - this.doorHeight, dp.screenHeight / 2 - (this.doorWidth / 3) / 2, this.padding, this.doorWidth / 3);
        } else if (this.direction == "Bottom"){
            this.hitBox = new Rectangle(dp.screenWidth / 2 - (this.doorWidth / 3) / 2, dp.screenHeight - this.doorHeight, this.doorWidth / 3, this.padding);
        } else if (this.direction == "Left"){
            this.hitBox = new Rectangle(this.doorHeight - this.padding, dp.screenHeight / 2 - (this.doorWidth / 3) / 2, this.padding, this.doorWidth / 3);
            // NEW STUFF
        } else if (this.direction == "TopLeft"){
            this.hitBox = new Rectangle(dp.screenWidth / 2 - (this.doorWidth / 3) / 2, doorHeight - this.padding, this.doorWidth / 3, this.padding);
        } else if (this.direction == "TopRight"){
            this.hitBox = new Rectangle(((dp.screenWidth * 2) / 2) + (dp.screenWidth / 2) - (this.doorWidth / 3) / 2, doorHeight - this.padding, this.doorWidth / 3, this.padding);

        } else if (this.direction == "RightTop"){
            this.hitBox = new Rectangle(dp.screenWidth * 2 - this.doorHeight, dp.screenHeight / 2 - (this.doorWidth / 3) / 2, this.padding, this.doorWidth / 3);
        } else if (this.direction == "RightBottom"){
            this.hitBox = new Rectangle(dp.screenWidth * 2 - this.doorHeight, dp.screenHeight / 2 - (this.doorWidth / 3) / 2 + dp.screenHeight, this.padding, this.doorWidth / 3);

        } else if (this.direction == "BottomLeft"){
            this.hitBox = new Rectangle(dp.screenWidth / 2 - (this.doorWidth / 3) / 2, dp.screenHeight * 2 - this.doorHeight, this.doorWidth / 3, this.padding);
        } else if (this.direction == "BottomRight"){
            this.hitBox = new Rectangle(((dp.screenWidth * 2) / 2) + (dp.screenWidth / 2) - (this.doorWidth / 3) / 2, dp.screenHeight * 2- this.doorHeight, this.doorWidth / 3, this.padding);

        } else if (this.direction == "LeftTop"){
            this.hitBox = new Rectangle(this.doorHeight - this.padding, dp.screenHeight / 2 - (this.doorWidth / 3) / 2, this.padding, this.doorWidth / 3);
        } else if (this.direction == "LeftBottom"){
            this.hitBox = new Rectangle(this.doorHeight - this.padding, dp.screenHeight / 2 - (this.doorWidth / 3) / 2 + dp.screenHeight, this.padding, this.doorWidth / 3);

        } else if (this.direction == "MiddleBottomLeft"){
            this.hitBox = new Rectangle(dp.screenWidth / 2 - (this.doorWidth / 3) / 2, dp.screenHeight - this.doorHeight, this.doorWidth / 3, this.padding);
        } else if (this.direction == "MiddleBottomRight"){
            this.hitBox = new Rectangle(dp.screenWidth / 2 - (this.doorWidth / 3) / 2 + dp.screenWidth, dp.screenHeight - this.doorHeight, this.doorWidth / 3, this.padding);

        } else if (this.direction == "MiddleTopLeft"){
            this.hitBox = new Rectangle(dp.screenWidth / 2 - (this.doorWidth / 3) / 2, doorHeight - this.padding + dp.screenHeight, this.doorWidth / 3, this.padding);
        } else if (this.direction == "MiddleTopRight"){
            this.hitBox = new Rectangle(((dp.screenWidth * 2) / 2) + (dp.screenWidth / 2) - (this.doorWidth / 3) / 2, doorHeight - this.padding + dp.screenHeight, this.doorWidth / 3, this.padding);

        } else if (this.direction == "MiddleRightTop"){
            this.hitBox = new Rectangle(dp.screenWidth - this.doorHeight, dp.screenHeight / 2 - (this.doorWidth / 3) / 2, this.padding, this.doorWidth / 3);
        } else if (this.direction == "MiddleRightBottom"){
            this.hitBox = new Rectangle(dp.screenWidth - this.doorHeight, dp.screenHeight / 2 - (this.doorWidth / 3) / 2 + dp.screenHeight, this.padding, this.doorWidth / 3);

        } else if (this.direction == "MiddleLeftTop"){
            this.hitBox = new Rectangle(this.doorHeight - this.padding + dp.screenWidth, dp.screenHeight / 2 - (this.doorWidth / 3) / 2, this.padding, this.doorWidth / 3);
        } else if (this.direction == "MiddleLeftBottom"){
            this.hitBox = new Rectangle(this.doorHeight - this.padding + dp.screenWidth, dp.screenHeight / 2 - (this.doorWidth / 3) / 2 + dp.screenHeight, this.padding, this.doorWidth / 3);

        }

        this.xPos = this.hitBox.x;
        this.yPos = this.hitBox.y;
        

        // Need to add middle left top
        // Need to add middle left bottom

        
    }

    public void getDoorImage() {
        try {
            this.topDoorOpen = ImageIO.read(getClass().getResourceAsStream("/objectImages/door1.png"));
            this.topDoorClosed = ImageIO.read(getClass().getResourceAsStream("/objectImages/door5.png"));
            this.rightDoorOpen = ImageIO.read(getClass().getResourceAsStream("/objectImages/door2.png"));
            this.rightDoorClosed = ImageIO.read(getClass().getResourceAsStream("/objectImages/door6.png"));
            this.bottomDoorOpen = ImageIO.read(getClass().getResourceAsStream("/objectImages/door3.png"));
            this.bottomDoorClosed = ImageIO.read(getClass().getResourceAsStream("/objectImages/door7.png"));
            this.leftDoorOpen = ImageIO.read(getClass().getResourceAsStream("/objectImages/door4.png"));
            this.leftDoorClosed = ImageIO.read(getClass().getResourceAsStream("/objectImages/door8.png"));
        

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics2D g2) {
        if (this.direction == "Top" && this.open){
            g2.drawImage(topDoorOpen, dp.screenWidth / 2 - this.doorWidth / 2 - (int) dp.camera.getXOffset(), 0 - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);
        } else if (this.direction == "Top" && !this.open){
            g2.drawImage(topDoorClosed, dp.screenWidth / 2 - this.doorWidth / 2 - (int) dp.camera.getXOffset(), 0 - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);
        } else if (this.direction == "Bottom" && this.open){
            g2.drawImage(bottomDoorOpen, dp.screenWidth / 2 - this.doorWidth / 2 - (int) dp.camera.getXOffset(), dp.screenHeight - this.doorHeight - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);
        } else if (this.direction == "Bottom" && !this.open){
            g2.drawImage(bottomDoorClosed, dp.screenWidth / 2 - this.doorWidth / 2 - (int) dp.camera.getXOffset(), dp.screenHeight - this.doorHeight - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);
        } else if (this.direction == "Left" && this.open){
            g2.drawImage(leftDoorOpen, 0 - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);
        } else if (this.direction == "Left" && !this.open){
            g2.drawImage(leftDoorClosed, 0 - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);
        } else if (this.direction == "Right" && this.open){
            g2.drawImage(rightDoorOpen, dp.screenWidth - this.doorHeight - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);
        } else if (this.direction == "Right" && !this.open){
            g2.drawImage(rightDoorClosed, dp.screenWidth - this.doorHeight - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);
            // NEW STUFF
        } else if (this.direction == "TopLeft" && this.open){
            g2.drawImage(topDoorOpen, dp.screenWidth / 2 - this.doorWidth / 2 - (int) dp.camera.getXOffset(), 0 - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);
        } else if (this.direction == "TopLeft" && !this.open){
            g2.drawImage(topDoorClosed, dp.screenWidth / 2 - this.doorWidth / 2 - (int) dp.camera.getXOffset(), 0 - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);

        } else if (this.direction == "TopRight" && this.open){
            g2.drawImage(topDoorOpen, ((dp.screenWidth * 2) / 2) + (dp.screenWidth / 2) - this.doorWidth / 2 - (int) dp.camera.getXOffset(), 0 - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);
        } else if (this.direction == "TopRight" && !this.open){
            g2.drawImage(topDoorClosed, ((dp.screenWidth * 2) / 2) + (dp.screenWidth / 2) - this.doorWidth / 2 - (int) dp.camera.getXOffset(), 0 - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);

        } else if (this.direction == "RightTop" && this.open){
            g2.drawImage(rightDoorOpen, dp.screenWidth * 2 - this.doorHeight - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);
        } else if (this.direction == "RightTop" && !this.open){
            g2.drawImage(rightDoorClosed, dp.screenWidth * 2 - this.doorHeight - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);

        } else if (this.direction == "RightBottom" && this.open){
            g2.drawImage(rightDoorOpen, dp.screenWidth * 2 - this.doorHeight - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 + dp.screenHeight - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);
        } else if (this.direction == "RightBottom" && !this.open){
            g2.drawImage(rightDoorClosed, dp.screenWidth * 2 - this.doorHeight - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 + dp.screenHeight - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);

        } else if (this.direction == "BottomLeft" && this.open){
            g2.drawImage(bottomDoorOpen, dp.screenWidth / 2 - this.doorWidth / 2 - (int) dp.camera.getXOffset(), dp.screenHeight * 2 - this.doorHeight - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);
        } else if (this.direction == "BottomLeft" && !this.open){
            g2.drawImage(bottomDoorClosed, dp.screenWidth / 2 - this.doorWidth / 2 - (int) dp.camera.getXOffset(), dp.screenHeight * 2 - this.doorHeight - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);

        } else if (this.direction == "BottomRight" && this.open){
            g2.drawImage(bottomDoorOpen,((dp.screenWidth * 2) / 2) + (dp.screenWidth / 2) - this.doorWidth / 2 - (int) dp.camera.getXOffset(), dp.screenHeight * 2 - this.doorHeight - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);
        } else if (this.direction == "BottomRight" && !this.open){
            g2.drawImage(bottomDoorClosed,((dp.screenWidth * 2) / 2) + (dp.screenWidth / 2) - this.doorWidth / 2 - (int) dp.camera.getXOffset(), dp.screenHeight * 2 - this.doorHeight - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);

        } else if (this.direction == "LeftTop" && this.open){
            g2.drawImage(leftDoorOpen, 0 - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);
        } else if (this.direction == "LeftTop" && !this.open){
            g2.drawImage(leftDoorClosed, 0 - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);

        } else if (this.direction == "LeftBottom" && this.open){
            g2.drawImage(leftDoorOpen, 0 - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 + dp.screenHeight - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);
        } else if (this.direction == "LeftBottom" && !this.open){
            g2.drawImage(leftDoorClosed, 0 - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 + dp.screenHeight - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);

        } else if (this.direction == "MiddleBottomLeft" && this.open){
            g2.drawImage(bottomDoorOpen, dp.screenWidth / 2 - this.doorWidth / 2 - (int) dp.camera.getXOffset(), dp.screenHeight - this.doorHeight - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);
        } else if (this.direction == "MiddleBottomLeft" && !this.open){
            g2.drawImage(bottomDoorClosed, dp.screenWidth / 2 - this.doorWidth / 2 - (int) dp.camera.getXOffset(), dp.screenHeight - this.doorHeight - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);

        } else if (this.direction == "MiddleBottomRight" && this.open){
            g2.drawImage(bottomDoorOpen, dp.screenWidth / 2 - this.doorWidth / 2 + dp.screenWidth - (int) dp.camera.getXOffset(), dp.screenHeight - this.doorHeight - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);
        } else if (this.direction == "MiddleBottomRight" && !this.open){
            g2.drawImage(bottomDoorClosed, dp.screenWidth / 2 - this.doorWidth / 2 + dp.screenWidth - (int) dp.camera.getXOffset(), dp.screenHeight - this.doorHeight - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);

        } else if (this.direction == "MiddleTopLeft" && this.open){
            g2.drawImage(topDoorOpen, dp.screenWidth / 2 - this.doorWidth / 2 - (int) dp.camera.getXOffset(), 0 - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);
        } else if (this.direction == "MiddleTopLeft" && !this.open){
            g2.drawImage(topDoorClosed, dp.screenWidth / 2 - this.doorWidth / 2 - (int) dp.camera.getXOffset(), 0 - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);

        } else if (this.direction == "MiddleTopRight" && this.open){
            g2.drawImage(topDoorOpen, ((dp.screenWidth * 2) / 2) + (dp.screenWidth / 2) - this.doorWidth / 2 - (int) dp.camera.getXOffset(), dp.screenHeight - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);
        } else if (this.direction == "MiddleTopRight" && !this.open){
            g2.drawImage(topDoorClosed, ((dp.screenWidth * 2) / 2) + (dp.screenWidth / 2) - this.doorWidth / 2 - (int) dp.camera.getXOffset(), dp.screenHeight - (int) dp.camera.getYOffset(), this.doorWidth, this.doorHeight, null);

        } else if (this.direction == "MiddleRightTop" && this.open){
            g2.drawImage(rightDoorOpen, dp.screenWidth - this.doorHeight - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);
        } else if (this.direction == "MiddleRightTop" && !this.open){
            g2.drawImage(rightDoorClosed, dp.screenWidth - this.doorHeight - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);

        } else if (this.direction == "MiddleRightBottom" && this.open){
            g2.drawImage(rightDoorOpen, dp.screenWidth - this.doorHeight - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 + dp.screenHeight - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);
        } else if (this.direction == "MiddleRightBottom" && !this.open){
            g2.drawImage(rightDoorClosed, dp.screenWidth - this.doorHeight - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 + dp.screenHeight - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);

        } else if (this.direction == "MiddleLeftTop" && this.open){
            g2.drawImage(leftDoorOpen, dp.screenWidth - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);
        } else if (this.direction == "MiddleLeftTop" && !this.open){
            g2.drawImage(leftDoorClosed, dp.screenWidth - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);

        } else if (this.direction == "MiddleLeftBottom" && this.open){
            g2.drawImage(leftDoorOpen, dp.screenWidth - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 + dp.screenHeight - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);
        } else if (this.direction == "MiddleLeftBottom" && !this.open){
            g2.drawImage(leftDoorClosed, dp.screenWidth - (int) dp.camera.getXOffset(), dp.screenHeight / 2 - this.doorWidth / 2 + dp.screenHeight - (int) dp.camera.getYOffset(), this.doorHeight, this.doorWidth, null);

        }

        g2.setColor(Color.red);
        g2.drawRect((int)(this.hitBox.x - dp.camera.xOffset), (int)(this.hitBox.y - dp.camera.yOffset), this.hitBox.width, this.hitBox.height);

    }

    @Override
    public void update() {
        // TODO Auto-generated method stub        
    }

    @Override
    public void isColliding(GameObject gameObject) {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean isCollidingWithTile(Rectangle zone, Tile tile) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
