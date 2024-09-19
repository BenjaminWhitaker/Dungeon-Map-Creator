import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class GameObject {
    protected double xPos, yPos;
    protected int width, height;
    public Rectangle hitBox;
    protected boolean isColliding, isCollidingTopZone, isCollidingBottomZone, isCollidingRightZone, isCollidingLeftZone;


    public GameObject(int x, int y){
        this.xPos = x;
        this.yPos = y;
        this.width = 0;
        this.height = 0;
    }

    public GameObject(){
       
    }

    public abstract void paint(Graphics2D g2);
    
    public abstract void update();

    public abstract void isColliding(GameObject gameObject);

    public abstract boolean isCollidingWithTile(Rectangle zone, Tile tile);

    public double getX(){
        return this.xPos;
    }

    public double getY(){
        return this.yPos;
    }
}
