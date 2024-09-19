import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DungeonPanel extends JPanel implements Runnable {

    // Screen Settings
    final int originalTileSize = 32;
    final int scale = 2;

    final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 18;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    

    // Time Vars
    Thread gameThread;
    int FPS = 60;

    // Lists
    List<GameObject> gameObjects;

    // Inititating
    KeyHandler key = new KeyHandler();
    public FloorManager floorM = new FloorManager(this);
    public TileManager tileM = new TileManager(this);
    public Player player = new Player(this, key);
    public Door door;
    public GameCamera camera = new GameCamera(this);

    public DungeonPanel() {
        startGameThread();
        Dimension screen = new Dimension(screenWidth, screenHeight);
        setPreferredSize(screen);
        setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        gameObjects = new ArrayList<GameObject>();
        gameObjectsSetup();
        

    }

    public void gameObjectsSetup(){
        player.currentRow = floorM.floor.length / 2;
        player.currentCol = floorM.floor.length / 2;
        gameObjects.add(player);
        
        for (GameObject gameObject: floorM.floor[player.currentRow][player.currentCol].objectsInRoom){
            gameObjects.add(gameObject);
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            
            if (delta >= 1) {
                tick();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println(drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //for (Tile tile : tileM.tileCollection) {
        //  tile.paint(g2);
        //}
        for (int i = 0; i < tileM.tileCollection.size(); i++) {
            if (floorM.floor[player.currentRow][player.currentCol].regular){
                tileM.tileCollection.get(i).paint(g2);
            } else if (withinRange(tileM.tileCollection.get(i).getX(), tileM.tileCollection.get(i).getY())){
                tileM.tileCollection.get(i).paint(g2);
            }
        }
        for (int i = 0; i < floorM.floor[player.currentRow][player.currentCol].objectsInRoom.size(); i++) {
            GameObject object = floorM.floor[player.currentRow][player.currentCol].objectsInRoom.get(i);
            if (floorM.floor[player.currentRow][player.currentCol].regular){
                object.paint(g2);
            } else if (withinRange(object.getX(), object.getY())){
                object.paint(g2);
            }
        }
        player.paint(g2);
        floorM.paint(g2);
        g2.dispose();

    }

    public void tick() {
        player.update();
        //for (GameObject gameObject : gameObjects) {
        //    player.isColliding(gameObject);
        //}
        for (int i = 0; i < floorM.floor[player.currentRow][player.currentCol].objectsInRoom.size(); i++) {
            player.isColliding(floorM.floor[player.currentRow][player.currentCol].objectsInRoom.get(i));
        }

        if (!floorM.floor[player.currentRow][player.currentCol].regular){
            camera.center(player);
        } else {
            camera.xOffset = 0;
            camera.yOffset = 0;
        }

    }

    public void delay(int n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean withinRange(double xPos, double yPos){

        if (camera.xOffset == this.screenWidth || camera.yOffset == this.screenHeight || camera.xOffset == 0 || camera.yOffset == 0){
            if (camera.xOffset == this.screenWidth && camera.yOffset == this.screenHeight){
                if (xPos > player.getX() - screenWidth - tileSize && xPos < player.getX() + screenWidth / 2 + tileSize && yPos > player.getY() - screenHeight - tileSize && yPos < player.getY() + screenHeight / 2 + tileSize){
                    System.out.println(1);
                    return true;
                } else {
                    return false;
                }
            } else if (camera.xOffset == 0 && camera.yOffset == 0){
                if (xPos > player.getX() - screenWidth - tileSize && xPos < player.getX() + screenWidth + tileSize && yPos > player.getY() - screenHeight - tileSize && yPos < player.getY() + screenHeight + tileSize){
                    System.out.println(2);
                    return true;
                } else {
                    return false;
                }
            } else if (camera.xOffset == this.screenWidth && camera.yOffset == 0){
                if (xPos > player.getX() - screenWidth - tileSize && xPos < player.getX() + screenWidth / 2 + tileSize && yPos > player.getY() - screenHeight - tileSize && yPos < player.getY() + screenHeight  + tileSize){
                    System.out.println(3);
                    return true;
                } else {
                    return false;
                }
            } else if (camera.xOffset == 0 && camera.yOffset == this.screenHeight){
                if (xPos > player.getX() - screenWidth - tileSize && xPos < player.getX() + screenWidth + tileSize && yPos > player.getY() - screenHeight - tileSize && yPos < player.getY() + screenHeight / 2 + tileSize){
                    System.out.println(4);
                    return true;
                } else {
                    return false;
                }
            } else if (camera.xOffset == 0 && (camera.yOffset != this.screenHeight && camera.yOffset != 0)){
                if (xPos > player.getX() - screenWidth - tileSize && xPos < player.getX() + screenWidth + tileSize && yPos > player.getY() - screenHeight / 2 - tileSize && yPos < player.getY() + screenHeight / 2  + tileSize){
                    System.out.println(5);
                    return true;
                } else {
                    return false;
                }
            } else if (camera.xOffset == this.screenWidth && (camera.yOffset != this.screenHeight && camera.yOffset != 0)){
                if (xPos > player.getX() - screenWidth - tileSize && xPos < player.getX() + screenWidth / 2 + tileSize && yPos > player.getY() - screenHeight / 2 - tileSize && yPos < player.getY() + screenHeight / 2  + tileSize){
                    System.out.println(6);
                    return true;
                } else {
                    return false;
                }
            } else if ((camera.xOffset != this.screenWidth && camera.xOffset != 0) && camera.yOffset == 0){
                if (xPos > player.getX() - screenWidth / 2 - tileSize && xPos < player.getX() + screenWidth / 2 + tileSize && yPos > player.getY() - screenHeight - tileSize && yPos < player.getY() + screenHeight + tileSize){
                    System.out.println(7);
                    return true;
                } else {
                    return false;
                }
            } else if ((camera.xOffset != this.screenWidth && camera.xOffset != 0) && camera.yOffset == this.screenHeight){
                if (xPos > player.getX() - screenWidth / 2 - tileSize && xPos < player.getX() + screenWidth / 2 + tileSize && yPos > player.getY() - screenHeight - tileSize && yPos < player.getY() + screenHeight / 2 + tileSize){
                    System.out.println(8);
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if (xPos > player.getX() - screenWidth / 2 - tileSize && xPos < player.getX() + screenWidth / 2 + tileSize && yPos > player.getY() - screenHeight / 2 - tileSize && yPos < player.getY() + screenHeight / 2 + tileSize){
                return true;
            } else {
                return false;
            }
        }
        return false;
        
    }
}