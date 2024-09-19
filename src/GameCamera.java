public class GameCamera {
    
    protected DungeonPanel dp;
    protected double xOffset;
    protected double yOffset;

    public GameCamera(DungeonPanel dp){
        this.dp = dp;
        this.xOffset = 0;
        this.yOffset = 0;
    }

    public void center(Player p){
        this.xOffset = p.getX() - dp.screenWidth / 2 + p.width / 2;
        this.yOffset = p.getY() - dp.screenHeight / 2 + p.height / 2;

        if (p.getX() - dp.screenWidth / 2 + p.width / 2 <= 0){
            this.xOffset = 0;
        }  
        if (p.getY() - dp.screenHeight / 2 + p.height / 2 <= 0){
            this.yOffset = 0;
        }  

        if (dp.floorM.floor[p.currentRow][p.currentCol].fourxfour){
            if (p.getX() + dp.screenWidth / 2 + p.width / 2 >= dp.screenWidth * 2){
                this.xOffset = dp.screenWidth;
            }  
            if (p.getY() + dp.screenHeight / 2 + p.height / 2 >= dp.screenHeight * 2){
                this.yOffset = dp.screenHeight;
            }  
        } else if (dp.floorM.floor[p.currentRow][p.currentCol].tall){
            if (p.getX() + dp.screenWidth / 2 + p.width / 2 >= dp.screenWidth){
                this.xOffset = 0;
            }  
            if (p.getY() + dp.screenHeight / 2 + p.height / 2 >= dp.screenHeight * 2){
                this.yOffset = dp.screenHeight;
            }  
        } else if (dp.floorM.floor[p.currentRow][p.currentCol].wide){
            if (p.getX() + dp.screenWidth / 2 + p.width / 2 >= dp.screenWidth * 2){
                this.xOffset = dp.screenWidth;
            }
            if (p.getY() + dp.screenHeight / 2 + p.height / 2 >= dp.screenHeight){
                this.yOffset = 0;
            }  
        }
    }

    public void move(double x, double y){
        xOffset += x;
        yOffset += y;
    }

    public double getXOffset(){
        return this.xOffset;
    }

    public double getYOffset(){
        return this.yOffset;
    }

    


}
