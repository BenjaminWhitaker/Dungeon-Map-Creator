import java.util.ArrayList;
import java.util.List;

public class Room {
    public boolean beenExplored;
    public String fileName;
    public List<GameObject> objectsInRoom;
    public boolean bigRoom;
    public boolean filler;
    public boolean[][] layout = { { false, false }, { false, false } };
    public LocationNode[][] layoutLocation;
    public int floorLength;
    public boolean fourxfour;
    public boolean tall;
    public boolean wide;
    public boolean regular;
    public int rowStart;
    public int colStart;

    public Room(boolean filler, boolean bigRoom) {
        this.bigRoom = bigRoom;
        this.filler = filler;
        this.beenExplored = false;
        objectsInRoom = new ArrayList<GameObject>();
        layoutLocation = new LocationNode[2][2];
        rowStart = 0;
        colStart = 0;
        if (filler == false && bigRoom == false) {
            createRoom();
        }
    }

    private void createRoom() {
        int random = (int) (Math.random() * 14) + 2;
        this.fileName = String.format("/maps/map%02d.txt", random);
        this.regular = true;
        System.out.println(fileName);
    }

    public void createBigRoom() {
        if (this.numOfRooms() == 4) {
            this.fileName = "/bigMaps/bigMap01.txt";
            this.fourxfour = true;
        } else if (this.numOfRooms() == 2) {
            if (layout[0][1]) {
                this.fileName = "/bigMaps/bigMap06.txt";
                this.wide = true;
            } else if (layout[1][0]) {
                this.fileName = "/bigMaps/bigMap07.txt";
                this.tall = true;
            }
        } else {
            if (!layout[0][0]) {
                this.fileName = "/bigMaps/bigMap05.txt";
            } else if (!layout[0][1]) {
                this.fileName = "/bigMaps/bigMap03.txt";
            } else if (!layout[1][0]) {
                this.fileName = "/bigMaps/bigMap04.txt";
            } else if (!layout[1][1]) {
                this.fileName = "/bigMaps/bigMap02.txt";
            }
            this.fourxfour = true;
        }
    }

    private int numOfRooms() {
        int total = 0;
        if (layout[0][0]) {
            total++;
        }
        if (layout[1][0]) {
            total++;
        }
        if (layout[0][1]) {
            total++;
        }
        if (layout[1][1]) {
            total++;
        }
        return total;
    }

    public void determineLayout(List<LocationNode> group) {

        boolean[][] temp = new boolean[floorLength][floorLength];

        for (int index = 0; index < group.size(); index++) {
            temp[group.get(index).getRow()][group.get(index).getCol()] = true;
        }
        for (int row = 0; row < temp.length; row++) {
            for (int col = 0; col < temp.length; col++) {
                if (temp[row][col] == true) {
                    rowStart = row - 1;
                }
            }
        }
        for (int col = 0; col < temp.length; col++) {
            for (int row = 0; row < temp.length; row++) {
                if (temp[row][col] == true) {
                    colStart = col - 1;
                }
            }
        }

        if (group.size() > 2) {
            layout[0][0] = temp[rowStart][colStart];
            layout[0][1] = temp[rowStart][colStart + 1];
            layout[1][0] = temp[rowStart + 1][colStart];
            layout[1][1] = temp[rowStart + 1][colStart + 1];

        } else if (group.size() == 2) {
            if (group.get(0).getRow() == group.get(1).getRow()) {
                layout[0][0] = true;
                layout[0][1] = true;
            } else if (group.get(0).getCol() == group.get(1).getCol()) {
                layout[0][0] = true;
                layout[1][0] = true;
            }
        }

    }

    public void determineAdvancedLayout(FloorManager floorM) {
        if (layout[0][0]) {
            if (this.wide) {
                layoutLocation[0][0] = new LocationNode(rowStart + 1, colStart, floorM);
            } else if (this.tall) {
                layoutLocation[0][0] = new LocationNode(rowStart, colStart + 1, floorM);
            } else {
                layoutLocation[0][0] = new LocationNode(rowStart, colStart, floorM);
            }
        }
        if (layout[0][1]) {
            if (this.wide) {
                layoutLocation[0][1] = new LocationNode(rowStart + 1, colStart + 1, floorM);
            } else {
                layoutLocation[0][1] = new LocationNode(rowStart, colStart + 1, floorM);
            }
        }
        if (layout[1][0]) {
            if (this.tall) {
                layoutLocation[1][0] = new LocationNode(rowStart + 1, colStart + 1, floorM);
            } else {
                layoutLocation[1][0] = new LocationNode(rowStart + 1, colStart, floorM);
            }
        }
        if (layout[1][1]) {
            layoutLocation[1][1] = new LocationNode(rowStart + 1, colStart + 1, floorM);
        }

    }

    public void addInfo(int length) {
        this.floorLength = length;
    }

}
