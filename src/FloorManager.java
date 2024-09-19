import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class FloorManager {

    List<LocationNode> queue;
    DungeonPanel dp;
    Room[][] floor;
    int amountOfRooms;
    final int mapWidth = 150;
    final int mapHeight = 150;
    int jump;
    int padding = 20;
    int roomCount = 0;
    List<List<LocationNode>> roomGroups;
    double bigRoomChance;

    public FloorManager(DungeonPanel dp) {
        this.dp = dp;
        queue = new ArrayList<LocationNode>();
        amountOfRooms = 11 + (int) (Math.random() * 5);
        roomGroups = new ArrayList<List<LocationNode>>();
        floor = new Room[11][11];
        bigRoomChance = .4;

        floor[floor.length / 2][floor.length / 2] = new StartingRoom();

        queue.add(new LocationNode(floor.length / 2, floor.length / 2, this));
        roomCount++;
        jump = mapWidth / floor.length;
        while (roomCount < amountOfRooms) {
            createFloor();
        }
        // ADDS DP 
        for (int index = 0; index < queue.size(); index++) {
            floor[queue.get(index).getRow()][queue.get(index).getCol()].addInfo(floor.length);
        }
        for (int i = 0; i < roomGroups.size(); i++) {
            floor[roomGroups.get(i).get(0).getRow()][roomGroups.get(i).get(0).getCol()].determineLayout(roomGroups.get(i));
        }
        for (int i = 0; i < roomGroups.size(); i++) {
            floor[roomGroups.get(i).get(0).getRow()][roomGroups.get(i).get(0).getCol()].createBigRoom();
        }
        for (int i = 0; i < roomGroups.size(); i++) {
            floor[roomGroups.get(i).get(0).getRow()][roomGroups.get(i).get(0).getCol()].determineAdvancedLayout(this);
        }
        addDoors();

    }

public void addDoors() {
        for (LocationNode location : queue) {
                    if (floor[location.getRow()][location.getCol()].regular) {
                            if (!location.getUp()) { 
                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("Top", dp));
                            }
                            if (!location.getRight()) {
                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("Right", dp));
                            }
                            if (!location.getDown()) {
                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("Bottom", dp));
                            }
                            if (!location.getLeft()) {
                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("Left", dp));
                            }

                    } else if (floor[location.getRow()][location.getCol()].fourxfour) {

                        if (floor[location.getRow()][location.getCol()].layoutLocation[0][0] != null && floor[location.getRow()][location.getCol()].layoutLocation[0][0].equals(location)) {
                                    System.out.println("this should be woprking!");
                                    // XO
                                    // OO
                                    if (!location.getUp() && !location.inSameGroup(location.getUpLocationNode())) {
                                            floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("TopLeft", dp));
                                    }
                                    if (!location.getRight() && !location.inSameGroup(location.getRightLocationNode())) {
                                            floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleRightTop", dp));
                                    }
                                    if (!location.getDown() && !location.inSameGroup(location.getDownLocationNode())) {
                                            floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleBottomLeft", dp));
                                    }
                                    if (!location.getLeft() && !location.inSameGroup(location.getLeftLocationNode())) {
                                            floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("LeftTop", dp));
                                    }
                                    // OX
                                    // OO
                                    if (floor[location.getRow()][location.getCol()].layoutLocation[0][1] != null) {
                                            LocationNode temp = new LocationNode(location.getRow(), location.getCol() + 1, this);

                                            if (!temp.getUp() && temp.getUpInRange() && !location.inSameGroup(temp.getUpLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("TopRight", dp));
                                            }
                                            if (!temp.getRight() && temp.getRightInRange() && !location.inSameGroup(temp.getRightLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("RightTop", dp));
                                            }
                                            if (!temp.getDown() && temp.getDownInRange() && !location.inSameGroup(temp.getDownLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleBottomRight", dp));
                                            }
                                            if (!temp.getLeft() && temp.getLeftInRange() && !location.inSameGroup(temp.getLeftLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleLeftTop", dp));
                                            }

                                    }
                                    // OO
                                    // XO
                                    if (floor[location.getRow()][location.getCol()].layoutLocation[1][0] != null) {
                                            LocationNode temp = new LocationNode(location.getRow() + 1, location.getCol(), this);
                                            if (!temp.getUp() && temp.getUpInRange() && !location.inSameGroup(temp.getUpLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleTopLeft", dp));
                                            }
                                            if (!temp.getRight() && temp.getRightInRange() && !location.inSameGroup(temp.getRightLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleRightBottom", dp));
                                            }
                                            if (!temp.getDown() && temp.getDownInRange() && !location.inSameGroup(temp.getDownLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("BottomLeft", dp));
                                            }
                                            if (!temp.getLeft() && temp.getLeftInRange() && !location.inSameGroup(temp.getLeftLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("LeftBottom", dp));
                                            }

                                    }
                                    // OO
                                    // OX
                                    if (floor[location.getRow()][location.getCol()].layoutLocation[1][1] != null) {
                                            LocationNode temp = new LocationNode(location.getRow() + 1,
                                                            location.getCol() + 1, this);
                                            if (!temp.getUp() && temp.getUpInRange()
                                                            && !location.inSameGroup(temp.getUpLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("MiddleTopRight", dp));
                                            }
                                            if (!temp.getRight() && temp.getRightInRange()
                                                            && !location.inSameGroup(temp.getRightLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("RightBottom", dp));
                                            }
                                            if (!temp.getDown() && temp.getDownInRange()
                                                            && !location.inSameGroup(temp.getDownLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("BottomRight", dp));
                                            }
                                            if (!temp.getLeft() && temp.getLeftInRange()
                                                            && !location.inSameGroup(temp.getLeftLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("MiddleLeftBottom", dp));
                                            }
                                    }

                                    // CUTOFF
                                    // CUTOFF
                                    // CUTOFF
                                    // CUTOFF
                        } else if (floor[location.getRow()][location.getCol()].layoutLocation[0][1] != null && floor[location.getRow()][location.getCol()].layoutLocation[0][1].equals(location)) {
                                    System.out.println("this should be woprking!");
                                    // OX
                                    // OO
                                    if (!location.getUp() && !location.inSameGroup(location.getUpLocationNode())) {
                                            floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("TopRight", dp)); 
                                    }
                                    if (!location.getRight() && !location.inSameGroup(location.getRightLocationNode())) {
                                            floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("RightTop", dp)); 
                                    }
                                    if (!location.getDown() && !location.inSameGroup(location.getDownLocationNode())) {
                                            floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleBottomRight", dp)); 
                                    }
                                    if (!location.getLeft() && !location.inSameGroup(location.getLeftLocationNode())) {
                                            floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleLeftTop", dp));
                                    }
                                    // XO
                                    // OO
                                    if (floor[location.getRow()][location.getCol()].layoutLocation[0][0] != null) {
                                            LocationNode temp = new LocationNode(location.getRow(), location.getCol() - 1, this);
                                            if (!temp.getUp() && temp.getUpInRange()
                                                            && !location.inSameGroup(temp.getUpLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("TopLeft", dp));
                                            }
                                            if (!temp.getRight() && temp.getRightInRange()
                                                            && !location.inSameGroup(temp.getRightLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("MiddleRightTop", dp));
                                            }
                                            if (!temp.getDown() && temp.getDownInRange()
                                                            && !location.inSameGroup(temp.getDownLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("MiddleBottomLeft", dp));
                                            }
                                            if (!temp.getLeft() && temp.getLeftInRange()
                                                            && !location.inSameGroup(temp.getLeftLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("LeftTop", dp));
                                            }

                                    }
                                    // OO
                                    // XO
                                    if (floor[location.getRow()][location.getCol()].layoutLocation[1][0] != null) {
                                            LocationNode temp = new LocationNode(location.getRow() + 1, location.getCol() - 1, this);
                                            if (!temp.getUp() && temp.getUpInRange()
                                                            && !location.inSameGroup(temp.getUpLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("MiddleTopLeft", dp));
                                            }
                                            if (!temp.getRight() && temp.getRightInRange()
                                                            && !location.inSameGroup(temp.getRightLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("MiddleRightBottom", dp));
                                            }
                                            if (!temp.getDown() && temp.getDownInRange()
                                                            && !location.inSameGroup(temp.getDownLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("BottomLeft", dp));
                                            }
                                            if (!temp.getLeft() && temp.getLeftInRange()
                                                            && !location.inSameGroup(temp.getLeftLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("LeftBottom", dp));
                                            }

                                    }
                                    // OO
                                    // OX
                                    if (floor[location.getRow()][location.getCol()].layoutLocation[1][1] != null) {
                                            LocationNode temp = new LocationNode(location.getRow() + 1, location.getCol(), this);
                                            if (!temp.getUp() && temp.getUpInRange()
                                                            && !location.inSameGroup(temp.getUpLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("MiddleTopRight", dp));
                                            }
                                            if (!temp.getRight() && temp.getRightInRange()
                                                            && !location.inSameGroup(temp.getRightLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("RightBottom", dp));
                                            }
                                            if (!temp.getDown() && temp.getDownInRange()
                                                            && !location.inSameGroup(temp.getDownLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("BottomRight", dp));
                                            }
                                            if (!temp.getLeft() && temp.getLeftInRange()
                                                            && !location.inSameGroup(temp.getLeftLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("MiddleLeftBottom", dp));
                                            }

                                    }
                                    // CUTOFF
                                    // CUTOFF
                                    // CUTOFF
                                    // CUTOFF
                        } else if (floor[location.getRow()][location.getCol()].layoutLocation[1][0] != null && floor[location.getRow()][location.getCol()].layoutLocation[1][0].equals(location)) {
                                    System.out.println("this should be working!");
                                    // OO
                                    // XO
                                    if (!location.getUp() && !location.inSameGroup(location.getUpLocationNode())) {
                                            floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleTopLeft", dp));
                                    }
                                    if (!location.getRight()
                                                    && !location.inSameGroup(location.getRightLocationNode())) {
                                            floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleRightBottom", dp));
                                    }
                                    if (!location.getDown() && !location.inSameGroup(location.getDownLocationNode())) {
                                            floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("BottomLeft", dp));
                                    }
                                    if (!location.getLeft() && !location.inSameGroup(location.getLeftLocationNode())) {
                                            floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("LeftBottom", dp));
                                    }
                                    // OX
                                    // OO
                                    if (floor[location.getRow()][location.getCol()].layoutLocation[0][1] != null) {
                                            LocationNode temp = new LocationNode(location.getRow() - 1,location.getCol() + 1, this);

                                            if (!temp.getUp() && temp.getUpInRange()
                                                            && !location.inSameGroup(temp.getUpLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("TopRight", dp));
                                            }
                                            if (!temp.getRight() && temp.getRightInRange()
                                                            && !location.inSameGroup(temp.getRightLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("RightTop", dp));
                                            }
                                            if (!temp.getDown() && temp.getDownInRange()
                                                            && !location.inSameGroup(temp.getDownLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("MiddleBottomRight", dp));
                                            }
                                            if (!temp.getLeft() && temp.getLeftInRange()
                                                            && !location.inSameGroup(temp.getLeftLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("MiddleLeftTop", dp));
                                            }

                                    }
                                    // XO
                                    // OO
                                    if (floor[location.getRow()][location.getCol()].layoutLocation[0][0] != null) {
                                            LocationNode temp = new LocationNode(location.getRow() - 1, location.getCol(),
                                                            this);
                                            if (!temp.getUp() && temp.getUpInRange()
                                                            && !location.inSameGroup(temp.getUpLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("TopLeft", dp));
                                            }
                                            if (!temp.getRight() && temp.getRightInRange()
                                                            && !location.inSameGroup(temp.getRightLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("MiddleRightTop", dp));
                                            }
                                            if (!temp.getDown() && temp.getDownInRange()
                                                            && !location.inSameGroup(temp.getDownLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("MiddleBottomLeft", dp));
                                            }
                                            if (!temp.getLeft() && temp.getLeftInRange()
                                                            && !location.inSameGroup(temp.getLeftLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("LeftTop", dp));
                                            }

                                    }
                                    // OO
                                    // OX
                                    if (floor[location.getRow()][location.getCol()].layoutLocation[1][1] != null) {
                                            LocationNode temp = new LocationNode(location.getRow(), location.getCol() + 1, this);
                                            if (!temp.getUp() && temp.getUpInRange()
                                                            && !location.inSameGroup(temp.getUpLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("MiddleTopRight", dp));
                                            }
                                            if (!temp.getRight() && temp.getRightInRange()
                                                            && !location.inSameGroup(temp.getRightLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("RightBottom", dp));
                                            }
                                            if (!temp.getDown() && temp.getDownInRange()
                                                            && !location.inSameGroup(temp.getDownLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("BottomRight", dp));
                                            }
                                            if (!temp.getLeft() && temp.getLeftInRange()
                                                            && !location.inSameGroup(temp.getLeftLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("MiddleLeftBottom", dp));
                                            }
                                    }

                                    // CUTOFF
                                    // CUTOFF
                                    // CUTOFF
                                    // CUTOFF

                        } else if (floor[location.getRow()][location.getCol()].layoutLocation[1][1] != null && floor[location.getRow()][location.getCol()].layoutLocation[1][1].equals(location)) {
                                    System.out.println("this should be woprking!");
                                    // OO
                                    // OX
                                    if (!location.getUp() && !location.inSameGroup(location.getUpLocationNode())) {
                                            floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleTopRight", dp));
                                    }
                                    if (!location.getRight() && !location.inSameGroup(location.getRightLocationNode())) {
                                            floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("RightBottom", dp));
                                    }
                                    if (!location.getDown() && !location.inSameGroup(location.getDownLocationNode())) {
                                            floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("BottomRight", dp));
                                    }
                                    if (!location.getLeft() && !location.inSameGroup(location.getLeftLocationNode())) {
                                            floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleLeftBottom", dp));
                                    }
                                    // OX
                                    // OO
                                    if (floor[location.getRow()][location.getCol()].layoutLocation[0][1] != null) {
                                            LocationNode temp = new LocationNode(location.getRow() - 1, location.getCol(), this);

                                            if (!temp.getUp() && temp.getUpInRange() && !location.inSameGroup(temp.getUpLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("TopRight", dp));
                                            }
                                            if (!temp.getRight() && temp.getRightInRange() && !location.inSameGroup(temp.getRightLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("RightTop", dp));
                                            }
                                            if (!temp.getDown() && temp.getDownInRange() && !location.inSameGroup(temp.getDownLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleBottomRight", dp));
                                            }
                                            if (!temp.getLeft() && temp.getLeftInRange() && !location.inSameGroup(temp.getLeftLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleLeftTop", dp));
                                            }

                                    }
                                    // OO
                                    // XO
                                    if (floor[location.getRow()][location.getCol()].layoutLocation[1][0] != null) {
                                            LocationNode temp = new LocationNode(location.getRow(), location.getCol() - 1, this);
                                            if (!temp.getUp() && temp.getUpInRange()
                                                            && !location.inSameGroup(temp.getUpLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("MiddleTopLeft", dp));
                                            }
                                            if (!temp.getRight() && temp.getRightInRange()
                                                            && !location.inSameGroup(temp.getRightLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("MiddleRightBottom", dp));
                                            }
                                            if (!temp.getDown() && temp.getDownInRange()
                                                            && !location.inSameGroup(temp.getDownLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("BottomLeft", dp));
                                            }
                                            if (!temp.getLeft() && temp.getLeftInRange()
                                                            && !location.inSameGroup(temp.getLeftLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom
                                                                    .add(new Door("LeftBottom", dp));
                                            }

                                    }
                                    // XO
                                    // OO
                                    if (floor[location.getRow()][location.getCol()].layoutLocation[0][0] != null) {
                                            LocationNode temp = new LocationNode(location.getRow() - 1 , location.getCol() - 1, this);
                                            if (!temp.getUp() && temp.getUpInRange() && !location.inSameGroup(temp.getUpLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("TopLeft", dp));
                                            }
                                            if (!temp.getRight() && temp.getRightInRange() && !location.inSameGroup(temp.getRightLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleRightTop", dp));
                                            }
                                            if (!temp.getDown() && temp.getDownInRange() && !location.inSameGroup(temp.getDownLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleBottomLeft", dp));
                                            }
                                            if (!temp.getLeft() && temp.getLeftInRange() && !location.inSameGroup(temp.getLeftLocationNode())) {
                                                    floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("LeftTop", dp));
                                            }

                                    }

                            } 
                        

                } else if (floor[location.getRow()][location.getCol()].wide){
                        System.out.println("GELOOOOOOOOOOOOOOOOOOO");

                        if (floor[location.getRow()][location.getCol()].layoutLocation[0][0] != null && floor[location.getRow()][location.getCol()].layoutLocation[0][0].equals(location)) {
                                System.out.println("can this please work!");
                                // XO
                                // OO
                                if (!location.getUp() && !location.inSameGroup(location.getUpLocationNode())) {
                                        floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("TopLeft", dp));
                                }
                                if (!location.getRight() && !location.inSameGroup(location.getRightLocationNode())) {
                                        floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleRightTop", dp));
                                }
                                if (!location.getDown() && !location.inSameGroup(location.getDownLocationNode())) {
                                        floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleBottomLeft", dp));
                                }
                                if (!location.getLeft() && !location.inSameGroup(location.getLeftLocationNode())) {
                                        floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("LeftTop", dp));
                                }
                                // OX
                                // OO
                                if (floor[location.getRow()][location.getCol()].layoutLocation[0][1] != null) {
                                        LocationNode temp = new LocationNode(location.getRow(), location.getCol() + 1, this);

                                        if (!temp.getUp() && temp.getUpInRange() && !location.inSameGroup(temp.getUpLocationNode())) {
                                                floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("TopRight", dp));
                                        }
                                        if (!temp.getRight() && temp.getRightInRange() && !location.inSameGroup(temp.getRightLocationNode())) {
                                                floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("RightTop", dp));
                                        }
                                        if (!temp.getDown() && temp.getDownInRange() && !location.inSameGroup(temp.getDownLocationNode())) {
                                                floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleBottomRight", dp));
                                        }
                                        if (!temp.getLeft() && temp.getLeftInRange() && !location.inSameGroup(temp.getLeftLocationNode())) {
                                                floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleLeftTop", dp));
                                        }

                                }

                        } else if (floor[location.getRow()][location.getCol()].layoutLocation[0][1] != null && floor[location.getRow()][location.getCol()].layoutLocation[0][1].equals(location)) {
                                System.out.println("please work!");
                                // OX
                                // OO
                                if (!location.getUp() && !location.inSameGroup(location.getUpLocationNode())) {
                                        floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("TopRight", dp));
                                }
                                if (!location.getRight() && !location.inSameGroup(location.getRightLocationNode())) {
                                        floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("RightTop", dp));
                                }
                                if (!location.getDown() && !location.inSameGroup(location.getDownLocationNode())) {
                                        floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleBottomRight", dp));
                                }
                                if (!location.getLeft() && !location.inSameGroup(location.getLeftLocationNode())) {
                                        floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleLeftTop", dp));
                                }
                                // XO
                                // OO
                                if (floor[location.getRow()][location.getCol()].layoutLocation[0][0] != null) {
                                        LocationNode temp = new LocationNode(location.getRow(), location.getCol() - 1, this);

                                        if (!temp.getUp() && temp.getUpInRange() && !location.inSameGroup(temp.getUpLocationNode())) {
                                                floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("TopLeft", dp));
                                        }
                                        if (!temp.getRight() && temp.getRightInRange() && !location.inSameGroup(temp.getRightLocationNode())) {
                                                floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleRightTop", dp));
                                        }
                                        if (!temp.getDown() && temp.getDownInRange() && !location.inSameGroup(temp.getDownLocationNode())) {
                                                floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleBottomLeft", dp));
                                        }
                                        if (!temp.getLeft() && temp.getLeftInRange() && !location.inSameGroup(temp.getLeftLocationNode())) {
                                                floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("LeftTop", dp));
                                        }

                                }


                        }



                } else if (floor[location.getRow()][location.getCol()].tall){
                                System.out.println("RIGHT LETS GO LOLOLOLOLOLOLOLOLOL");
                        if (floor[location.getRow()][location.getCol()].layoutLocation[0][0] != null && floor[location.getRow()][location.getCol()].layoutLocation[0][0].equals(location)) {
                                // XO
                                // OO
                                if (!location.getUp() && !location.inSameGroup(location.getUpLocationNode())) {
                                        floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("TopLeft", dp));
                                }
                                if (!location.getRight() && !location.inSameGroup(location.getRightLocationNode())) {
                                        floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleRightTop", dp));
                                }
                                if (!location.getDown() && !location.inSameGroup(location.getDownLocationNode())) {
                                        floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleBottomLeft", dp));
                                }
                                if (!location.getLeft() && !location.inSameGroup(location.getLeftLocationNode())) {
                                        floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("LeftTop", dp));
                                }
                                // OO
                                // XO
                                if (floor[location.getRow()][location.getCol()].layoutLocation[1][0] != null) {
                                        LocationNode temp = new LocationNode(location.getRow() + 1, location.getCol(), this);

                                        if (!temp.getUp() && temp.getUpInRange() && !location.inSameGroup(temp.getUpLocationNode())) {
                                                floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleTopRight", dp));
                                        }
                                        if (!temp.getRight() && temp.getRightInRange() && !location.inSameGroup(temp.getRightLocationNode())) {
                                                floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleRightBottom", dp));
                                        }
                                        if (!temp.getDown() && temp.getDownInRange() && !location.inSameGroup(temp.getDownLocationNode())) {
                                                floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("BottomLeft", dp));
                                        }
                                        if (!temp.getLeft() && temp.getLeftInRange() && !location.inSameGroup(temp.getLeftLocationNode())) {
                                                floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("LeftBottom", dp));
                                        }

                                }

                        } else if (floor[location.getRow()][location.getCol()].layoutLocation[1][0] != null && floor[location.getRow()][location.getCol()].layoutLocation[1][0].equals(location)) {
                                // OO
                                // XO
                                if (!location.getUp() && !location.inSameGroup(location.getUpLocationNode())) {
                                        floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleTopLeft", dp));
                                }
                                if (!location.getRight() && !location.inSameGroup(location.getRightLocationNode())) {
                                        floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleRightBottom", dp));
                                }
                                if (!location.getDown() && !location.inSameGroup(location.getDownLocationNode())) {
                                        floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("BottomLeft", dp));
                                }
                                if (!location.getLeft() && !location.inSameGroup(location.getLeftLocationNode())) {
                                        floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("LeftBottom", dp));
                                }
                                // XO
                                // OO
                                if (floor[location.getRow()][location.getCol()].layoutLocation[0][0] != null) {
                                        LocationNode temp = new LocationNode(location.getRow() - 1, location.getCol(), this);

                                        if (!temp.getUp() && temp.getUpInRange() && !location.inSameGroup(temp.getUpLocationNode())) {
                                                floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("TopLeft", dp));
                                        }
                                        if (!temp.getRight() && temp.getRightInRange() && !location.inSameGroup(temp.getRightLocationNode())) {
                                                floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleRightTop", dp));
                                        }
                                        if (!temp.getDown() && temp.getDownInRange() && !location.inSameGroup(temp.getDownLocationNode())) {
                                                floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("MiddleBottomLeft", dp));
                                        }
                                        if (!temp.getLeft() && temp.getLeftInRange() && !location.inSameGroup(temp.getLeftLocationNode())) {
                                                floor[location.getRow()][location.getCol()].objectsInRoom.add(new Door("LeftTop", dp));
                                        }

                                }


                        }




                }
        }
}

    

    public void createFloor() {

        // for (LocationNode locationNode : queue) {
        for (int k = 0; k < queue.size(); k++) {
            LocationNode locationNode = queue.get(k);

            // 1 = up
            // 2 = right
            // 3 = down
            // 4 = left
            for (int index = 1; index < 4; index++) {

                int a, b, c, d;
                int randomGen = (int) (Math.random() * 4) + 1;

                if (randomGen == 1) {
                    a = 1;
                    b = 2;
                    c = 3;
                    d = 4;
                } else if (randomGen == 2) {
                    a = 4;
                    b = 3;
                    c = 2;
                    d = 1;
                } else if (randomGen == 3) {
                    a = 3;
                    b = 4;
                    c = 1;
                    d = 2;
                } else if (randomGen == 4) {
                    a = 2;
                    b = 3;
                    c = 4;
                    d = 1;
                } else {
                    a = 0;
                    b = 0;
                    c = 0;
                    d = 0;
                    System.out.println("Issue with randomizer in create floor");
                }

                if (index == a) {

                    if (locationNode.getUp()) {
                        LocationNode tempNode = new LocationNode(locationNode.getRow() - 1, locationNode.getCol(),
                                this);
                        if (tempNode.getNeighbors() <= 1) {
                            if (roomCount < amountOfRooms) {
                                if (Math.random() <= .5) {

                                    if (Math.random() <= bigRoomChance) {

                                        int random = (int) (Math.random() * 3) + 1;

                                        if (random == 1) {
                                            // 2x2

                                            // Building Left
                                            if (tempNode.getLeft() && tempNode.getUpLeft() && tempNode.getUp()
                                                    && Math.random() >= .5) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow() - 1][tempNode.getCol()] = new Room(true, false);
                                                floor[tempNode.getRow() - 1][tempNode.getCol() - 1] = new Room(true,
                                                        false);
                                                floor[tempNode.getRow()][tempNode.getCol() - 1] = new Room(true, false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                        this));
                                                queue.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol() - 1,
                                                        this));
                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                        this));
                                                group.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol() - 1,
                                                        this));
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                                // Building Right
                                            } else if (tempNode.getRight() && tempNode.getUpRight() && tempNode.getUp()
                                                    && Math.random() >= .5) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow() - 1][tempNode.getCol()] = new Room(true, false);
                                                floor[tempNode.getRow() - 1][tempNode.getCol() + 1] = new Room(true,
                                                        false);
                                                floor[tempNode.getRow()][tempNode.getCol() + 1] = new Room(true, false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                        this));
                                                queue.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol() + 1,
                                                        this));
                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                        this));
                                                group.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol() + 1,
                                                        this));
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                            }

                                        } else if (random == 2) {
                                            // 2x1

                                            // Building Straight
                                            if (tempNode.getUp() && Math.random() <= .33) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow() - 1][tempNode.getCol()] = new Room(true, false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                                // Build Left
                                            } else if (tempNode.getLeft() && Math.random() <= .33) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow()][tempNode.getCol() - 1] = new Room(true, false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                                // Build Right
                                            } else if (tempNode.getRight() && Math.random() <= .33) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow()][tempNode.getCol() + 1] = new Room(true, false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                            }

                                        } else if (random == 3) {
                                            // L shape

                                            // Building Right
                                            if (tempNode.getUp() && tempNode.getUpRight() && tempNode.getRight()
                                                    && Math.random() >= .5) {

                                                // Version One
                                                if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow() - 1][tempNode.getCol()] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow() - 1][tempNode.getCol() + 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() - 1,
                                                            tempNode.getCol() + 1, this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() - 1,
                                                            tempNode.getCol() + 1, this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                    // Version Two
                                                } else if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow() - 1][tempNode.getCol()] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow()][tempNode.getCol() + 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                            this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                            this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                    // Version Three
                                                } else if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow() - 1][tempNode.getCol() + 1] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow()][tempNode.getCol() + 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() - 1,
                                                            tempNode.getCol() + 1, this));
                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                            this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() - 1,
                                                            tempNode.getCol() + 1, this));
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                            this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                }

                                                // Building Left
                                            } else if (tempNode.getUp() && tempNode.getUpLeft() && tempNode.getLeft()
                                                    && Math.random() >= .5) {
                                                // Version One
                                                if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow() - 1][tempNode.getCol() - 1] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow()][tempNode.getCol() - 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() - 1,
                                                            tempNode.getCol() - 1, this));
                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                            this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() - 1,
                                                            tempNode.getCol() - 1, this));
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                            this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                    // Version Two
                                                } else if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow() - 1][tempNode.getCol()] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow()][tempNode.getCol() - 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                            this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                            this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                    // Version Three
                                                } else if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow() - 1][tempNode.getCol()] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow() - 1][tempNode.getCol() - 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() - 1,
                                                            tempNode.getCol() - 1, this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() - 1,
                                                            tempNode.getCol() - 1, this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                }

                                            }
                                        }

                                    } else {
                                        floor[locationNode.getRow() - 1][locationNode.getCol()] = new Room(false,
                                                false);
                                        queue.add(new LocationNode(locationNode.getRow() - 1, locationNode.getCol(),
                                                this));
                                        roomCount++;
                                    }
                                } else {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }

                } else if (index == b) {

                    if (locationNode.getRight()) {
                        LocationNode tempNode = new LocationNode(locationNode.getRow(), locationNode.getCol() + 1,
                                this);
                        if (tempNode.getNeighbors() <= 1) {
                            if (roomCount < amountOfRooms) {
                                if (Math.random() <= .5) {

                                    if (Math.random() <= bigRoomChance) {

                                        int random = (int) (Math.random() * 3) + 1;

                                        if (random == 1) {
                                            // 2x2

                                            // Building Up
                                            if (tempNode.getUp() && tempNode.getUpRight() && tempNode.getRight()
                                                    && Math.random() >= .5) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow() - 1][tempNode.getCol()] = new Room(true, false);
                                                floor[tempNode.getRow() - 1][tempNode.getCol() + 1] = new Room(true,
                                                        false);
                                                floor[tempNode.getRow()][tempNode.getCol() + 1] = new Room(true, false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                        this));
                                                queue.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol() + 1,
                                                        this));
                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                        this));
                                                group.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol() + 1,
                                                        this));
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                                // Building Down
                                            } else if (tempNode.getDown() && tempNode.getRight()
                                                    && tempNode.getDownRight() && Math.random() >= .5) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow()][tempNode.getCol() + 1] = new Room(true, false);
                                                floor[tempNode.getRow() + 1][tempNode.getCol()] = new Room(true, false);
                                                floor[tempNode.getRow() + 1][tempNode.getCol() + 1] = new Room(true,
                                                        false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                        this));
                                                queue.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                        this));
                                                queue.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol() + 1,
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                        this));
                                                group.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                        this));
                                                group.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol() + 1,
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                            }

                                        } else if (random == 2) {
                                            // 2x1

                                            // Building Straight
                                            if (tempNode.getRight() && Math.random() <= .33) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow()][tempNode.getCol() + 1] = new Room(true, false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                                // Build Up
                                            } else if (tempNode.getUp() && Math.random() <= .33) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow() - 1][tempNode.getCol()] = new Room(true, false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                                // Build Down
                                            } else if (tempNode.getDown() && Math.random() <= .33) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow() + 1][tempNode.getCol()] = new Room(true, false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                            }

                                        } else if (random == 3) {
                                            // L shape

                                            // Building Up
                                            if (tempNode.getUp() && tempNode.getUpRight() && tempNode.getRight()
                                                    && Math.random() >= .5) {

                                                // Version One
                                                if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow() - 1][tempNode.getCol()] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow() - 1][tempNode.getCol() + 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() - 1,
                                                            tempNode.getCol() + 1, this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() - 1,
                                                            tempNode.getCol() + 1, this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                    // Version Two
                                                } else if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow() - 1][tempNode.getCol()] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow()][tempNode.getCol() + 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                            this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                            this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                    // Version Three
                                                } else if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow() - 1][tempNode.getCol() + 1] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow()][tempNode.getCol() + 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() - 1,
                                                            tempNode.getCol() + 1, this));
                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                            this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() - 1,
                                                            tempNode.getCol() + 1, this));
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                            this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                }

                                                // Building Down
                                            } else if (tempNode.getRight() && tempNode.getDown()
                                                    && tempNode.getDownRight() && Math.random() >= .5) {
                                                // Version One
                                                if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow()][tempNode.getCol() + 1] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow() + 1][tempNode.getCol()] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                            this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                            this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                    // Version Two
                                                } else if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow() + 1][tempNode.getCol()] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow() + 1][tempNode.getCol() + 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() + 1,
                                                            tempNode.getCol() + 1, this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() + 1,
                                                            tempNode.getCol() + 1, this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                    // Version Three
                                                } else if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow()][tempNode.getCol() + 1] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow() + 1][tempNode.getCol() + 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() + 1,
                                                            tempNode.getCol() + 1, this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() + 1,
                                                            tempNode.getCol() + 1, this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                }

                                            }
                                        }

                                    } else {
                                        floor[locationNode.getRow()][locationNode.getCol() + 1] = new Room(false,
                                                false);
                                        queue.add(new LocationNode(locationNode.getRow(), locationNode.getCol() + 1,
                                                this));
                                        roomCount++;
                                    }
                                } else {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }

                } else if (index == c) {

                    if (locationNode.getDown()) {
                        LocationNode tempNode = new LocationNode(locationNode.getRow() + 1, locationNode.getCol(),
                                this);
                        if (tempNode.getNeighbors() <= 1) {
                            if (roomCount < amountOfRooms) {
                                if (Math.random() <= .5) {

                                    if (Math.random() <= bigRoomChance) {

                                        int random = (int) (Math.random() * 3) + 1;

                                        if (random == 1) {
                                            // 2x2

                                            // Building Left
                                            if (tempNode.getLeft() && tempNode.getDownLeft() && tempNode.getDown()
                                                    && Math.random() >= .5) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow() + 1][tempNode.getCol()] = new Room(true, false);
                                                floor[tempNode.getRow() + 1][tempNode.getCol() - 1] = new Room(true,
                                                        false);
                                                floor[tempNode.getRow()][tempNode.getCol() - 1] = new Room(true, false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                        this));
                                                queue.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol() - 1,
                                                        this));
                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                        this));
                                                group.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol() - 1,
                                                        this));
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                                // Building Right
                                            } else if (tempNode.getRight() && tempNode.getDownRight()
                                                    && tempNode.getDown() && Math.random() >= .5) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow() + 1][tempNode.getCol()] = new Room(true, false);
                                                floor[tempNode.getRow() + 1][tempNode.getCol() + 1] = new Room(true,
                                                        false);
                                                floor[tempNode.getRow()][tempNode.getCol() + 1] = new Room(true, false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                        this));
                                                queue.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol() + 1,
                                                        this));
                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                        this));
                                                group.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol() + 1,
                                                        this));
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                            }

                                        } else if (random == 2) {
                                            // 2x1

                                            // Building Straight
                                            if (tempNode.getDown() && Math.random() <= .33) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow() + 1][tempNode.getCol()] = new Room(true, false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                                // Build Left
                                            } else if (tempNode.getLeft() && Math.random() <= .33) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow()][tempNode.getCol() - 1] = new Room(true, false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                                // Build Right
                                            } else if (tempNode.getRight() && Math.random() <= .33) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow()][tempNode.getCol() + 1] = new Room(true, false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                            }

                                        } else if (random == 3) {
                                            // L shape

                                            // Building Right
                                            if (tempNode.getDown() && tempNode.getDownRight() && tempNode.getRight()
                                                    && Math.random() >= .5) {

                                                // Version One
                                                if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow() + 1][tempNode.getCol()] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow()][tempNode.getCol() + 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                            this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                            this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                    // Version Two
                                                } else if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow() + 1][tempNode.getCol()] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow() + 1][tempNode.getCol() + 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() + 1,
                                                            tempNode.getCol() + 1, this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() + 1,
                                                            tempNode.getCol() + 1, this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                    // Version Three
                                                } else if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow()][tempNode.getCol() + 1] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow() + 1][tempNode.getCol() + 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() + 1,
                                                            tempNode.getCol() + 1, this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() + 1,
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() + 1,
                                                            tempNode.getCol() + 1, this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                }

                                                // Building Left
                                            } else if (tempNode.getDown() && tempNode.getDownLeft()
                                                    && tempNode.getLeft() && Math.random() >= .5) {
                                                // Version One
                                                if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow() + 1][tempNode.getCol() - 1] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow()][tempNode.getCol() - 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() + 1,
                                                            tempNode.getCol() - 1, this));
                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                            this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() + 1,
                                                            tempNode.getCol() - 1, this));
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                            this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                    // Version Two
                                                } else if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow() + 1][tempNode.getCol()] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow() + 1][tempNode.getCol() - 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() + 1,
                                                            tempNode.getCol() - 1, this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() + 1,
                                                            tempNode.getCol() - 1, this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                    // Version Three
                                                } else if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow() + 1][tempNode.getCol()] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow()][tempNode.getCol() - 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                            this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                            this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                }

                                            }
                                        }

                                    } else {
                                        floor[locationNode.getRow() + 1][locationNode.getCol()] = new Room(false,
                                                false);
                                        queue.add(new LocationNode(locationNode.getRow() + 1, locationNode.getCol(),
                                                this));
                                        roomCount++;
                                    }
                                } else {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }

                } else if (index == d) {

                    if (locationNode.getLeft()) {
                        LocationNode tempNode = new LocationNode(locationNode.getRow(), locationNode.getCol() - 1,
                                this);
                        if (tempNode.getNeighbors() <= 1) {
                            if (roomCount < amountOfRooms) {
                                if (Math.random() <= .5) {

                                    if (Math.random() <= bigRoomChance) {
                                        int random = (int) (Math.random() * 3) + 1;

                                        if (random == 1) {
                                            // 2x2

                                            // Building Up
                                            if (tempNode.getUp() && tempNode.getUpLeft() && tempNode.getLeft()
                                                    && Math.random() >= .5) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow() - 1][tempNode.getCol()] = new Room(true, false);
                                                floor[tempNode.getRow() - 1][tempNode.getCol() - 1] = new Room(true,
                                                        false);
                                                floor[tempNode.getRow()][tempNode.getCol() - 1] = new Room(true, false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                        this));
                                                queue.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol() - 1,
                                                        this));
                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                        this));
                                                group.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol() - 1,
                                                        this));
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                                // Building Down
                                            } else if (tempNode.getDown() && tempNode.getLeft()
                                                    && tempNode.getDownLeft() && Math.random() >= .5) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow()][tempNode.getCol() - 1] = new Room(true, false);
                                                floor[tempNode.getRow() + 1][tempNode.getCol()] = new Room(true, false);
                                                floor[tempNode.getRow() + 1][tempNode.getCol() - 1] = new Room(true,
                                                        false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                        this));
                                                queue.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                        this));
                                                queue.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol() - 1,
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                        this));
                                                group.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                        this));
                                                group.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol() - 1,
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                            }

                                        } else if (random == 2) {
                                            // 2x1

                                            // Building Straight
                                            if (tempNode.getLeft() && Math.random() <= .33) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow()][tempNode.getCol() - 1] = new Room(true, false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                                // Build Up
                                            } else if (tempNode.getUp() && Math.random() <= .33) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow() - 1][tempNode.getCol()] = new Room(true, false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                                // Build Down
                                            } else if (tempNode.getDown() && Math.random() <= .33) {

                                                floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                floor[tempNode.getRow() + 1][tempNode.getCol()] = new Room(true, false);

                                                queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                queue.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                        this));

                                                List<LocationNode> group = new ArrayList<LocationNode>();
                                                group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(), this));
                                                group.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                        this));
                                                roomGroups.add(group);

                                                roomCount++;

                                            }

                                        } else if (random == 3) {
                                            // L shape

                                            // Building Up
                                            if (tempNode.getUp() && tempNode.getUpRight() && tempNode.getRight()
                                                    && Math.random() >= .5) {

                                                // Version One
                                                if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow() - 1][tempNode.getCol()] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow() - 1][tempNode.getCol() - 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() - 1,
                                                            tempNode.getCol() - 1, this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() - 1,
                                                            tempNode.getCol() - 1, this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                    // Version Two
                                                } else if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow() - 1][tempNode.getCol()] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow()][tempNode.getCol() - 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                            this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() - 1, tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                            this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                    // Version Three
                                                } else if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow() - 1][tempNode.getCol() - 1] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow()][tempNode.getCol() - 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() - 1,
                                                            tempNode.getCol() - 1, this));
                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                            this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() - 1,
                                                            tempNode.getCol() - 1, this));
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                            this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                }

                                                // Building Down
                                            } else if (tempNode.getLeft() && tempNode.getDown()
                                                    && tempNode.getDownLeft() && Math.random() >= .5) {
                                                // Version One
                                                if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow()][tempNode.getCol() - 1] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow() + 1][tempNode.getCol()] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                            this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                            this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                    // Version Two
                                                } else if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow() + 1][tempNode.getCol()] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow() + 1][tempNode.getCol() - 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() + 1,
                                                            tempNode.getCol() - 1, this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() + 1, tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() + 1,
                                                            tempNode.getCol() - 1, this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                    // Version Three
                                                } else if (Math.random() <= .33) {
                                                    floor[tempNode.getRow()][tempNode.getCol()] = new Room(false, true);
                                                    floor[tempNode.getRow()][tempNode.getCol() - 1] = new Room(true,
                                                            false);
                                                    floor[tempNode.getRow() + 1][tempNode.getCol() - 1] = new Room(true,
                                                            false);

                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                            this));
                                                    queue.add(new LocationNode(tempNode.getRow() + 1,
                                                            tempNode.getCol() - 1, this));

                                                    List<LocationNode> group = new ArrayList<LocationNode>();
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol(),
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow(), tempNode.getCol() - 1,
                                                            this));
                                                    group.add(new LocationNode(tempNode.getRow() + 1,
                                                            tempNode.getCol() - 1, this));
                                                    roomGroups.add(group);

                                                    roomCount++;

                                                }

                                            }
                                        }

                                    } else {
                                        floor[locationNode.getRow()][locationNode.getCol() - 1] = new Room(false,
                                                false);
                                        queue.add(new LocationNode(locationNode.getRow(), locationNode.getCol() - 1,
                                                this));
                                        roomCount++;
                                    }
                                } else {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }

                }

            }

        }

    }

    public void paint(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRoundRect(dp.screenWidth - mapWidth - padding, padding, mapWidth, mapHeight, 20, 20);
        int x = 0;
        int y = 0;
        for (int row = 0; row < floor.length; row++) {
            for (int col = 0; col < floor[0].length; col++) {
                if (floor[row][col] != null) {
                    if (floor[row][col].bigRoom || floor[row][col].filler) {
                        g2.setColor(Color.red);

                    } else {
                        g2.setColor(Color.gray);
                        // LATER ADD COLOR TO FOR ROOMS NOT EXPLORED
                    }
                    g2.fillRoundRect(dp.screenWidth - mapWidth - padding + x, y + padding, jump, jump, 5, 5);
                }
                x += jump;
            }
            x = 0;
            y += jump;
        }
    }
    
    
}
