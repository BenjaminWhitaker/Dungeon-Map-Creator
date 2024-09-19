import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import java.awt.Graphics2D;

public class TileManager {

    DungeonPanel dp;
    int mapTileNum[][];
    public List<Tile> tileCollection;

    // make it so you cant set file name

    public TileManager(DungeonPanel dp) {
        this.dp = dp;
        mapTileNum = new int[dp.maxScreenCol * 5][dp.maxScreenRow * 5];
        tileCollection = new ArrayList<Tile>();
        loadMap(dp.floorM.floor[dp.floorM.floor.length / 2][dp.floorM.floor.length / 2].fileName, 1, 1);
        readMap(1, 1);
    }

    public void updateMap() {
        tileCollection.clear();
        adjustPos();
        loadMap(dp.floorM.floor[dp.player.currentRow][dp.player.currentCol].fileName, getRowScale(), getColScale());
        readMap(getRowScale(), getColScale());
    }

    // Makes it so player doesent walk into void
    private void adjustPos() {
        if (!dp.floorM.floor[dp.player.currentRow][dp.player.currentCol].regular) {

            Room temp = dp.floorM.floor[dp.floorM.roomGroups
                    .get(new LocationNode(dp.player.currentRow, dp.player.currentCol, dp.floorM).findIndexOfGroup())
                    .get(0).getRow()][dp.floorM.roomGroups
                            .get(new LocationNode(dp.player.currentRow, dp.player.currentCol, dp.floorM)
                                    .findIndexOfGroup())
                            .get(0).getCol()];
            LocationNode tempNode;
            for (int row = 0; row < temp.layoutLocation.length; row++) {
                for (int col = 0; col < temp.layoutLocation[row].length; col++) {
                    if (temp.layoutLocation[row][col] != null && temp.layoutLocation[row][col].equals(new LocationNode(
                            dp.floorM.roomGroups
                                    .get(new LocationNode(dp.player.currentRow, dp.player.currentCol, dp.floorM)
                                            .findIndexOfGroup())
                                    .get(0).getRow(),
                            dp.floorM.roomGroups
                                    .get(new LocationNode(dp.player.currentRow, dp.player.currentCol, dp.floorM)
                                            .findIndexOfGroup())
                                    .get(0).getCol(),
                            dp.floorM))) {
                        tempNode = temp.layoutLocation[row][col];
                        dp.player.currentRow = tempNode.row;
                        dp.player.currentCol = tempNode.col;
                    }
                }
            }

        }

    }

    public void loadMap(String filename, int rowScale, int colScale) {

        try {
            InputStream input = getClass().getResourceAsStream(filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(input));

            int col = 0;
            int row = 0;
            while (col < dp.maxScreenCol * colScale && row < dp.maxScreenRow * rowScale) {
                String line = br.readLine();
                while (col < dp.maxScreenCol * colScale) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == dp.maxScreenCol * colScale) {
                    col = 0;
                    row++;
                }

            }
            br.close();
        } catch (Exception e) {

        }
    }

    public void paint(Graphics2D g2) {

    }

    public void readMap(int rowScale, int colScale) {
        int x = 0;
        int y = 0;

        for (int row = 0; row < dp.maxScreenRow * rowScale; row++) {
            for (int col = 0; col < dp.maxScreenCol * colScale; col++) {
                tileCollection.add(new Tile(x, y, mapTileNum[col][row], dp));
                x += dp.tileSize;
            }
            x = 0;
            y += dp.tileSize;
        }
    }

    private int getRowScale() {
        if (dp.floorM.floor[dp.player.currentRow][dp.player.currentCol].regular) {
            return 1;
        } else if (dp.floorM.floor[dp.player.currentRow][dp.player.currentCol].fourxfour) {
            return 2;
        } else if (dp.floorM.floor[dp.player.currentRow][dp.player.currentCol].tall) {
            return 2;
        } else if (dp.floorM.floor[dp.player.currentRow][dp.player.currentCol].wide) {
            return 1;
        }
        return -1;
    }

    private int getColScale() {
        if (dp.floorM.floor[dp.player.currentRow][dp.player.currentCol].regular) {
            return 1;
        } else if (dp.floorM.floor[dp.player.currentRow][dp.player.currentCol].fourxfour) {
            return 2;
        } else if (dp.floorM.floor[dp.player.currentRow][dp.player.currentCol].tall) {
            return 1;
        } else if (dp.floorM.floor[dp.player.currentRow][dp.player.currentCol].wide) {
            return 2;
        }
        return -1;
    }

}

