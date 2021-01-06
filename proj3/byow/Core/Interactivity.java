package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Interactivity {
    private int x;
    private int y;
    TETile[][] world;

    public Interactivity(TETile[][] world, int a, int b) {
        x = a;
        y = b;
        this.world = world;
        world[a][b] = Tileset.AVATAR;
    }

    public void parsing(String orders) {
        String remain = orders;
        while (remain.length() > 0) {
            Character current = remain.charAt(0);
            if (current.equals('w')) {
                goUp();
            }

            if (current.equals('s')) {
                goDown();
            }

            if (current.equals('a')) {
                goLeft();
            }

            if (current.equals('d')) {
                goRight();
            }

            if (current.equals(':')) {
                if (remain.charAt(1) == 'q') {
                    save();
                    return;
                }
            }

            remain = remain.substring(1, remain.length());
        }
    }

    private void goLeft() {
        if (world[x - 1][y] == Tileset.FLOOR) {
            world[x][y] = Tileset.FLOOR;
            world[x - 1][y] = Tileset.AVATAR;
            x -= 1;
        }
    }

    private void goRight() {
        if (world[x + 1][y] == Tileset.FLOOR) {
            world[x][y] = Tileset.FLOOR;
            world[x + 1][y] = Tileset.AVATAR;
            x += 1;
        }
    }

    private void goUp() {
        if (world[x][y + 1] == Tileset.FLOOR) {
            world[x][y] = Tileset.FLOOR;
            world[x][y + 1] = Tileset.AVATAR;
            y += 1;
        }
    }

    private void goDown() {
        if (world[x][y - 1] == Tileset.FLOOR) {
            world[x][y] = Tileset.FLOOR;
            world[x][y - 1] = Tileset.AVATAR;
            y -= 1;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void save() {
        File saving1 = new File("Saved World.txt");
        if (!saving1.exists()) {
            try {
                saving1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String enigma = "";
        for (int a = 0; a < 80; a++) {
            for (int b = 0; b < 30; b++) {
                TETile record = world[a][b];
                if (record == Tileset.WALL) {
                    enigma += "w";
                }
                if (record == Tileset.FLOOR) {
                    enigma += "f";
                }
                if (record == Tileset.AVATAR) {
                    enigma += "a";
                }
                if (record == Tileset.NOTHING) {
                    enigma += "n";
                }
            }
        }
        write(enigma);

        File saving2 = new File("Saved Position.txt");
        if (!saving2.exists()) {
            try {
                saving2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String a = Integer.toString(getX());
        String b = Integer.toString(getY());
        String position = a + "," + b;
        try {
            FileWriter pen = new FileWriter("Saved Position.txt");
            pen.write(position);
            pen.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void write(String s) {
        try {
            FileWriter pen = new FileWriter("Saved World.txt");
            pen.write(s);
            pen.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        Interactivity testing = new Interactivity(new TETile[80][30], 40, 15);
        testing.parsing("adswwwwwdsda");
        testing.save();
        String test = "";
        test += "a";
        test += "b";
        test += "c";
    }
}
