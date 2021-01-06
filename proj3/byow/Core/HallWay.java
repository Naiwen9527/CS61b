package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class HallWay {
    private int x;
    private int y;
    private boolean vertical;

    public HallWay(int x, int y, boolean vertical) {
        if (vertical) {
            this.vertical = true;
            this.x = x;
            this.y = y;
        }
        if (!vertical) {
            this.vertical = false;
            this.y = y;
        }
    }

    public int getXY() {
        if (vertical) {
            return x;
        }
        return y;
    }

    public int getTop() {
        if (vertical) {
            return y;
        }
        return -1;
    }

    public void drawVertical(TETile[][] world, int a, int top, int bottom, String type) {
        if (type.equals("top")) {
            world[a - 1][top] = Tileset.WALL;
            world[a][top] = Tileset.WALL;
            world[a + 1][top] = Tileset.WALL;
            for (int k = top - 1; k > bottom; k--) {
                world[a][k] = Tileset.FLOOR;
                if (world[a - 1][k] == Tileset.NOTHING) {
                    world[a - 1][k] = Tileset.WALL;
                }
                if (world[a + 1][k] == Tileset.NOTHING) {
                    world[a + 1][k] = Tileset.WALL;
                }
            }
        }
        if (type.equals("bottom")) {
            world[a - 1][bottom] = Tileset.WALL;
            world[a][bottom] = Tileset.WALL;
            world[a + 1][bottom] = Tileset.WALL;
            for (int k = bottom + 1; k < top; k++) {
                world[a][k] = Tileset.FLOOR;
                if (world[a - 1][k] == Tileset.NOTHING) {
                    world[a - 1][k] = Tileset.WALL;
                }
                if (world[a + 1][k] == Tileset.NOTHING) {
                    world[a + 1][k] = Tileset.WALL;
                }
            }
        }
        if (type.equals("summary")) {
            for (int k = top - 1; k > bottom; k--) {
                world[a][k] = Tileset.FLOOR;
                if (world[a - 1][k] == Tileset.NOTHING) {
                    world[a - 1][k] = Tileset.WALL;
                }
                if (world[a + 1][k] == Tileset.NOTHING) {
                    world[a + 1][k] = Tileset.WALL;
                }
            }
        }

    }

    public void drawHorizontal(TETile[][] world, int b, int left, int right) {
        world[left][b + 1] = Tileset.WALL;
        world[left][b] = Tileset.WALL;
        world[left][b - 1] = Tileset.WALL;
        for (int i = left + 1; i < right; i++) {
            world[i][b] = Tileset.FLOOR;
            if (world[i][b + 1] == Tileset.NOTHING) {
                world[i][b + 1] = Tileset.WALL;
            }
            if (world[i][b - 1] == Tileset.NOTHING) {
                world[i][b - 1] = Tileset.WALL;
            }
        }
        world[right][b + 1] = Tileset.WALL;
        world[right][b] = Tileset.WALL;
        world[right][b - 1] = Tileset.WALL;
    }
}
