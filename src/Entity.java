import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Entity extends GameObject{
    
    public int speed;

    public BufferedImage up, up1, up2, down, down1, down2, left, left1, left2, right, right1, right2;
    public String playerDirection;

    public int hitBoxXShift, hitBoxYShift, hitBoxWidth, hitBoxHeight;

    public Rectangle topZone, bottomZone, leftZone, rightZone;

    public int zonePadding;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    @Override
    public void paint(Graphics2D g2) {
        // TODO Auto-generated method stub
        
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
    public boolean isCollidingWithTile(Rectangle zone,Tile tile) {
        // TODO Auto-generated method stub
        return false;
    }



    public void updateHitBox(){
        this.hitBox.x = (int)this.xPos + this.hitBoxXShift;
        this.hitBox.y = (int)this.yPos + this.hitBoxYShift;
        if (this.topZone != null && this.bottomZone != null && this.rightZone != null && this.leftZone != null){
            this.topZone.x = (int)this.xPos + this.hitBoxXShift + this.zonePadding;
            this.topZone.y = (int)this.yPos + this.hitBoxYShift;
            
            this.bottomZone.x = (int)this.xPos + this.hitBoxXShift + this.zonePadding;
            this.bottomZone.y = (int)this.yPos + this.hitBoxYShift + this.hitBoxHeight - this.zonePadding;
            
            this.leftZone.x = (int)this.xPos + this.hitBoxXShift;
            this.leftZone.y = (int)this.yPos + this.hitBoxYShift + this.zonePadding;
            
            this.rightZone.x = (int)this.xPos + this.hitBoxXShift + this.hitBoxWidth - this.zonePadding;
            this.rightZone.y = (int)this.yPos + this.hitBoxYShift + this.zonePadding;
        }
    }

}
