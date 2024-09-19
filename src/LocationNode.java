
public class LocationNode {
    int row, col;
    FloorManager floorM;

    public LocationNode(int row, int col, FloorManager floorM) {
        this.floorM = floorM;
        if (row >= 0 && row < floorM.floor.length && col >= 0 && col < floorM.floor.length) {
            this.row = row;
            this.col = col;
        } else {
            throw new IndexOutOfBoundsException("The value put in nodes are not within range of map array");
        }
    }

    public int getCol() {
        return this.col;
    }

    public int getRow() {
        return this.row;
    }

    public boolean getLeft() {
        if (this.col - 1 >= 0) {
            if (floorM.floor[row][col - 1] == null) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean getDown() {
        if (this.row + 1 < floorM.floor.length) {
            if (floorM.floor[row + 1][col] == null) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean getRight() {
        if (this.col + 1 < floorM.floor.length) {
            if (floorM.floor[row][col + 1] == null) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean getUp() {
        if (this.row - 1 >= 0) {
            if (floorM.floor[row - 1][col] == null) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean getUpLeft() {
        if (this.row - 1 >= 0 && this.col - 1 >= 0) {
            if (floorM.floor[row - 1][col - 1] == null) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean getUpRight() {
        if (this.row - 1 >= 0 && this.col + 1 < floorM.floor.length) {
            if (floorM.floor[row - 1][col + 1] == null) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean getDownLeft() {
        if (this.row + 1 < floorM.floor.length && this.col - 1 >= 0) {
            if (floorM.floor[row + 1][col - 1] == null) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean getDownRight() {
        if (this.row + 1 < floorM.floor.length && this.col + 1 < floorM.floor.length) {
            if (floorM.floor[row + 1][col + 1] == null) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public LocationNode getUpLocationNode(){
        return new LocationNode(this.row - 1, this.col, this.floorM);
    }

    public LocationNode getRightLocationNode(){
        return new LocationNode(this.row, this.col + 1, this.floorM);
    }

    public LocationNode getDownLocationNode(){
        return new LocationNode(this.row + 1, this.col, this.floorM);
    }

    public LocationNode getLeftLocationNode(){
        return new LocationNode(this.row, this.col - 1, this.floorM);
    }

    public boolean getUpInRange(){
        if (this.row - 1 >= 0){
            return true;
        } else {
            return false;
        }
    }

    public boolean getRightInRange(){
        if (this.col + 1 < this.floorM.floor.length){
            return true;
        } else {
            return false;
        }
    }

    public boolean getDownInRange(){
        if (this.row + 1 < this.floorM.floor.length){
            return true;
        } else {
            return false;
        }
    }

    public boolean getLeftInRange(){
        if (this.col - 1 >= 0){
            return true;
        } else {
            return false;
        }
    }
    

    public int getNeighbors() {
        int neighborCount = 0;

        if (!this.getUp()) {
            neighborCount++;
        }
        if (!this.getDown()) {
            neighborCount++;
        }
        if (!this.getLeft()) {
            neighborCount++;
        }
        if (!this.getRight()) {
            neighborCount++;
        }

        return neighborCount;
    }

    public int findIndexOfGroup() {

        for (int index = 0; index < floorM.roomGroups.size(); index++) {

            for (int j = 0; j < floorM.roomGroups.get(index).size(); j++) {

                if (floorM.roomGroups.get(index).get(j).getRow() == this.getRow() && floorM.roomGroups.get(index).get(j).getCol() == this.getCol()) {
                    return index;
                }
            }
        }
        return -1;
    }

    public boolean equals(LocationNode node){

            if (this.getRow() == node.getRow() && this.getCol() == node.getCol()){
                return true;
            } else {
                return false;
            }
        
    }

    public boolean inSameGroup(LocationNode node){
       if (this.findIndexOfGroup() == node.findIndexOfGroup()){
           return true;
       } else {
           return false;
       }
    }

}
