package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.lang.Math.*;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private HashSet<Room> rooms = new HashSet<>();
    private ArrayList<Room> roomCollection = new ArrayList<>();
    private int startX;
    private int startY;
    private HashMap<Room, Integer> exits = new HashMap<>();
    private ArrayList<HallWay> halls = new ArrayList<>();
    private int decision = 0;
    private ArrayList<Room> leftHalf;
    private ArrayList<Room> rightHalf;
    private int top1;
    private int top2;
    private int bottom1;
    private int bottom2;
    private int left1;
    private int left2;
    private int right1;
    private int right2;


    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        StdDraw.setCanvasSize(this.WIDTH * 16, this.HEIGHT * 16);
        StdDraw.setXscale(0, this.WIDTH);
        StdDraw.setYscale(0, this.HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        double centerX = WIDTH / 2;
        double centerY = HEIGHT / 2;

        Font font = new Font("Monaco", Font.BOLD, 50);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.red);

        StdDraw.text(centerX, centerY + 7, "Welcome");

        Font font2 = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font2);
        StdDraw.text(centerX, centerY, "New Game (press N)");
        StdDraw.text(centerX, centerY - 4, "Load Game (press L)");
        StdDraw.text(centerX, centerY - 8, "Quit Game (press Q)");
        StdDraw.show();

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        String firstInput = solicitNCharsInput(centerX, centerY);
        if (firstInput.equals("n")) {
            drawFrame("Enter a Random Number as Seed", centerX, centerY);
            String numString = solicitNCharsInput(centerX, centerY);
            long seed = Long.parseLong(numString);
            Random r = new Random(seed);

            for (int j = 0; j < WIDTH; j += 1) {
                for (int k = 0; k < HEIGHT; k += 1) {
                    finalWorldFrame[j][k] = Tileset.NOTHING;
                }
            }
            generateRooms(r, finalWorldFrame);
            separate();
            calculateAll();
            connect(finalWorldFrame, r);
            startX = roomCollection.get(0).getX();
            startY = roomCollection.get(0).getY();
            finalWorldFrame[startX][startY] = Tileset.AVATAR;
        } else if (firstInput.equals("l")) {
            File check = new File("Saved World.txt");
            if (!check.exists()) {
                System.exit(0);
                return;
            }
            finalWorldFrame = readMap();
            ArrayList<Integer> position = readPosition();
            startX = position.get(0);
            startY = position.get(1);
        } else if (firstInput.equals("q")) {
            System.exit(0);
            return;
        }
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(finalWorldFrame);
        //TETile player = Tileset.AVATAR;
        //player.draw(startX, startY);
        solicitNCharsInput(finalWorldFrame);

    }
    public void drawFrame(String s, double a, double b) {

        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 50);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.red);
        StdDraw.text(a, b + 7, s);
        StdDraw.show();
    }

    public String solicitNCharsInput(double a, double b) {
        String input = "";
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char current = StdDraw.nextKeyTyped();
                if (current == 's' || current == 'S') {
                    return input;
                }
                if (current == 'n' || current == 'N') {
                    return "n";
                }
                if (current == 'l' || current == 'L') {
                    return "l";
                }
                if (current == 'q' || current == 'Q') {
                    return "q";
                }
                input += current;
                drawFrame(input, a, b);

            }
        }
    }

    public String solicitNCharsInput(TETile[][] finalWorldFrame) {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.line(0, HEIGHT - 1, WIDTH, HEIGHT - 1);
        Font fontSmall = new Font("Monaco", Font.ITALIC, 10);
        StdDraw.setFont(fontSmall);
        StdDraw.show();
        String quit = "";
        while (true) {
            int a = (int) StdDraw.mouseX();
            int b = (int) StdDraw.mouseY();
            TETile pointing = null;
            if (a < WIDTH && b < HEIGHT) {
                pointing = finalWorldFrame[a][b];
            }
            if (pointing == Tileset.AVATAR) {
                flick("You");
            }
            if (pointing == Tileset.WALL) {
                flick("Wall");
            }
            if (pointing == Tileset.NOTHING) {
                StdDraw.textLeft(0, HEIGHT - 1, "");
                StdDraw.show();
            }
            if (pointing == Tileset.FLOOR) {
                flick("Floor");
            }

            String quitSyntax = "";
            if (quit.length() >= 2) {
                quitSyntax += quit.charAt(quit.length() - 2);
                quitSyntax += quit.charAt(quit.length() - 1);
            }

            if (quitSyntax.equals(":q")) {
                Interactivity saveTheDamnThing = new Interactivity(finalWorldFrame, startX, startY);
                saveTheDamnThing.save();
                System.exit(0);
                return null;
            }

            if (StdDraw.hasNextKeyTyped()) {
                char current = StdDraw.nextKeyTyped();
                walking(current, finalWorldFrame);
                if (current == ':') {
                    quit += current;
                }
                if (current == 'q') {
                    quit += current;
                }
                if (current == 'Q') {
                    quit += Character.toString(current).toLowerCase();
                }
            }
        }
    }

    private void flick(String s) {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.textLeft(0, HEIGHT - 0.5, s);
        StdDraw.show();
        StdDraw.pause(100);
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.textLeft(0, HEIGHT - 0.5, s);
        StdDraw.show();
    }

    private void walking(char current, TETile[][] finalWorldFrame) { // control by A, D, W, S.
        if (current == 'a') {
            if (finalWorldFrame[startX - 1][startY].equals(Tileset.FLOOR)) {
                TETile avatar = finalWorldFrame[startX][startY];
                TETile floor = finalWorldFrame[startX - 1][startY];
                floor.draw(startX, startY);
                avatar.draw(startX - 1, startY);
                finalWorldFrame[startX][startY] = floor;
                finalWorldFrame[startX - 1][startY] = avatar;
                startX -= 1;
                StdDraw.show();
            }
        }
        if (current == 'd') {
            if (finalWorldFrame[startX + 1][startY].equals(Tileset.FLOOR)) {
                TETile avatar = finalWorldFrame[startX][startY];
                TETile floor = finalWorldFrame[startX + 1][startY];
                floor.draw(startX, startY);
                avatar.draw(startX + 1, startY);
                finalWorldFrame[startX][startY] = floor;
                finalWorldFrame[startX + 1][startY] = avatar;
                startX += 1;
                StdDraw.show();
            }
        }
        if (current == 'w') {
            if (finalWorldFrame[startX][startY + 1].equals(Tileset.FLOOR)) {
                TETile avatar = finalWorldFrame[startX][startY];
                TETile floor = finalWorldFrame[startX][startY + 1];
                floor.draw(startX, startY);
                avatar.draw(startX, startY + 1);
                finalWorldFrame[startX][startY] = floor;
                finalWorldFrame[startX][startY + 1] = avatar;
                startY += 1;
                StdDraw.show();
            }
        }
        if (current == 's') {
            if (finalWorldFrame[startX][startY - 1].equals(Tileset.FLOOR)) {
                TETile avatar = finalWorldFrame[startX][startY];
                TETile floor = finalWorldFrame[startX][startY - 1];
                floor.draw(startX, startY);
                avatar.draw(startX, startY - 1);
                finalWorldFrame[startX][startY] = floor;
                finalWorldFrame[startX][startY - 1] = avatar;
                startY -= 1;
                StdDraw.show();
            }
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {

        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.
        // byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        TETile[][] finalWorldFrame = null;
        String loweredInput = input.toLowerCase();
        String numString = null;
        String rest = null;

        if (loweredInput.charAt(0) == 'n') {
            numString = loweredInput.substring(loweredInput.indexOf("n") + 1,
                    loweredInput.indexOf("s"));
            long seed = Long.parseLong(numString);
            rest = loweredInput.substring(loweredInput.indexOf("s") + 1, loweredInput.length());
            Random r = new Random(seed);
            if (seed % 2 == 0) {
                decision = 1;
            }

            // initialize tiles
            finalWorldFrame = new TETile[WIDTH][HEIGHT];
            for (int x = 0; x < WIDTH; x += 1) {
                for (int y = 0; y < HEIGHT; y += 1) {
                    finalWorldFrame[x][y] = Tileset.NOTHING;
                }
            }

            generateRooms(r, finalWorldFrame);
            separate();
            calculateAll();
            connect(finalWorldFrame, r);

            startX = roomCollection.get(0).getX();
            startY = roomCollection.get(0).getY();
        }
        if (loweredInput.charAt(0) == 'l') {
            File check = new File("Saved World.txt");
            if (!check.exists()) {
                return null;
            }
            finalWorldFrame = readMap();
            ArrayList<Integer> position = readPosition();
            startX = position.get(0);
            startY = position.get(1);
            rest = loweredInput.substring(loweredInput.indexOf("l") + 1, loweredInput.length());
        }

        //gardener(finalWorldFrame);

        // begin interaction
        Interactivity player = new Interactivity(finalWorldFrame, startX, startY);
        player.parsing(rest);

        //ter.initialize(WIDTH, HEIGHT);
        //ter.renderFrame(finalWorldFrame);

        return finalWorldFrame;
    }

    private void gardener(TETile[][] world) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                if (world[x][y] == Tileset.NOTHING) {
                    world[x][y] = Tileset.WATER;
                }
            }
        }
    }

    private TETile[][] readMap() {
        TETile[][] output = new TETile[WIDTH][HEIGHT];
        File path = new File("Saved World.txt");
        Scanner reader = null;
        try {
            reader = new Scanner(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String tiles = "";
        if (reader.hasNext()) {
            tiles = reader.next();

            for (int j = 0; j < WIDTH; j += 1) {
                for (int k = 0; k < HEIGHT; k += 1) {
                    output[j][k] = Tileset.NOTHING;
                }
            }
            int currentTile = 0;
            for (int a = 0; a < WIDTH; a++) {
                for (int b = 0; b < HEIGHT; b++) {
                    Character tile = tiles.charAt(currentTile);
                    if (tile.equals('w')) {
                        output[a][b] = Tileset.WALL;
                        currentTile += 1;
                    }
                    if (tile.equals('f')) {
                        output[a][b] = Tileset.FLOOR;
                        currentTile += 1;
                    }
                    if (tile.equals('a')) {
                        output[a][b] = Tileset.AVATAR;
                        currentTile += 1;
                    }
                    if (tile.equals('n')) {
                        output[a][b] = Tileset.NOTHING;
                        currentTile += 1;
                    }
                }
            }
        }
        return output;
    }

    private ArrayList<Integer> readPosition() {
        File path = new File("Saved Position.txt");
        Scanner reader = null;
        try {
            reader = new Scanner(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String pos = "";
        while (reader.hasNext()) {
            pos += reader.next();
        }
        String x = "";
        String y = "";
        int curr = 0;
        Character character = pos.charAt(curr);
        while (!character.equals(',')) {
            x += pos.charAt(curr);
            curr += 1;
            character = pos.charAt(curr);
        }
        curr += 1;
        while (curr < pos.length()) {
            y += pos.charAt(curr);
            curr += 1;
        }
        ArrayList<Integer> positions = new ArrayList<>();
        positions.add(Integer.parseInt(x));
        positions.add(Integer.parseInt(y));
        return positions;
    }

    private void generateRooms(Random r, TETile[][] world) {
        int numTiles = 0;
        double rate = 0;
        while (rate < 0.3) {
            int w = RandomUtils.uniform(r, 3, 8);
            int h = RandomUtils.uniform(r, 3, 8);
            int x = RandomUtils.uniform(r, 5, WIDTH - 5);
            int y = RandomUtils.uniform(r, 5, HEIGHT - 5);
            if (inRange(x, y, w, h)) {
                if (!overlap(world, x, y, w, h)) {
                    Room newR = new Room(x, y, w, h);
                    rooms.add(newR);
                    roomCollection.add(newR);
                    newR.drawRoom(world);
                    int exit = RandomUtils.uniform(r, newR.leftBound() + 1, newR.rightBound());
                    exits.put(newR, exit);
                    numTiles += (w * 2) * (h * 2);
                    rate = (double) numTiles / (double) (WIDTH * HEIGHT);
                }
            }
        }
    }

    private boolean inRange(int x, int y, int w, int h) {
        if ((x + w < WIDTH - 2) && (x - w > 2)) {
            if ((y + h < HEIGHT - 2) && (y - h > 2)) {
                return true;
            }
        }
        return false;
    }

    private boolean overlap(TETile[][] world, int x, int y, int w, int h) {
        for (int i = x - w; i <= x + w; i++) {
            for (int k = y - h; k <= y + h; k++) {
                if (world[i][k] != Tileset.NOTHING) {
                    return true;
                }
            }
        }
        return false;
    }

    private void connect(TETile[][] world, Random r) {

        // deploy vertical hallways dependent on the decision
        verticalHelper(world, r);

        // further develop the map with two horizontal hallways
        horHelper(world, r);

    }

    private void separate() {
        // sorting rooms
        leftHalf = new ArrayList<>();
        rightHalf = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getX() <= WIDTH / 2) {
                leftHalf.add(room);
            } else {
                rightHalf.add(room);
            }
        }
    }

    private void calculateAll() {
        // bottoms and tops
        top1 = HEIGHT / 2;
        bottom1 = HEIGHT / 2;

        top2 = HEIGHT / 2;
        bottom2 = HEIGHT / 2;

        // things for left half
        for (Room room : leftHalf) {
            if (room.upperBound() > top1) {
                top1 = room.upperBound();
            }
            if (room.lowerBound() < bottom1) {
                bottom1 = room.lowerBound();
            }
        }

        // things for right half
        for (Room room : rightHalf) {
            if (room.upperBound() > top2) {
                top2 = room.upperBound();
            }
            if (room.lowerBound() < bottom2) {
                bottom2 = room.lowerBound();
            }
        }

        left1 = WIDTH / 2;
        right1 = WIDTH / 2;
        left2 = WIDTH / 2;
        right2 = WIDTH / 2;
        // things for the left side
        for (Room room : leftHalf) {
            if (room.leftBound() < left1) {
                left1 = room.leftBound();
            }
            if (room.rightBound() > right1) {
                right1 = room.rightBound();
            }
        }
        // things for the right side
        for (Room room : rightHalf) {
            if (room.leftBound() < left2) {
                left2 = room.leftBound();
            }
            if (room.rightBound() > right2) {
                right2 = room.rightBound();
            }
        }
    }

    private void verticalHelper(TETile[][] world, Random r) {
        if (decision == 0) {
            for (Room room : leftHalf) {
                HallWay hall = new HallWay(exits.get(room), top1, true);
                halls.add(hall);
                hall.drawVertical(world, hall.getXY(), top1, room.lowerBound(), "top");
            }
            for (Room room : rightHalf) {
                HallWay hall = new HallWay(exits.get(room), bottom2, true);
                halls.add(hall);
                hall.drawVertical(world, hall.getXY(), room.upperBound(), bottom2, "bottom");
            }
        } else {
            for (Room room : leftHalf) {
                HallWay hall = new HallWay(exits.get(room), bottom1, true);
                halls.add(hall);
                hall.drawVertical(world, hall.getXY(), room.upperBound(), bottom1, "bottom");
            }
            for (Room room : rightHalf) {
                HallWay hall = new HallWay(exits.get(room), top2, true);
                halls.add(hall);
                hall.drawVertical(world, hall.getXY(), top2, room.lowerBound(), "top");
            }
        }
    }

    private void horHelper(TETile[][] world, Random r) {
        int hope1 = 0;
        int hope2 = 0;
        int crossover = RandomUtils.uniform(r, WIDTH / 4);
        int leftNeo = WIDTH / 2 - 1 - crossover;
        int rightNeo = WIDTH / 2 + 1 + crossover;
        int height1 = 0;
        int height2 = 0;

        // left high right low
        if (decision == 0) {
            // get my last hope
            hope1 = 0;
            hope2 = top2;
            for (Room room : leftHalf) {
                if (room.lowerBound() > hope1) {
                    hope1 = room.lowerBound();
                }
            }
            for (Room room : rightHalf) {
                if (room.upperBound() < hope2) {
                    hope2 = room.upperBound();
                }
            }
            height1 = RandomUtils.uniform(r, hope1 + 1, top1 - 1);
            HallWay avenue1 = new HallWay(0, height1, false);
            avenue1.drawHorizontal(world, avenue1.getXY(), left1, rightNeo);

            height2 = RandomUtils.uniform(r, bottom2 + 1, hope2 - 1);
            HallWay avenue2 = new HallWay(0, height2, false);
            avenue1.drawHorizontal(world, avenue2.getXY(), leftNeo, right2);
        } else { // left low right high
            // get my last hope
            hope1 = top1;
            hope2 = 0;
            for (Room room : leftHalf) {
                if (room.upperBound() < hope1) {
                    hope1 = room.upperBound();
                }
            }
            for (Room room : rightHalf) {
                if (room.lowerBound() > hope2) {
                    hope2 = room.lowerBound();
                }
            }
            height1 = RandomUtils.uniform(r, bottom1 + 1, hope1 - 1);
            HallWay avenue1 = new HallWay(0, height1, false);
            avenue1.drawHorizontal(world, avenue1.getXY(), left1, rightNeo);

            height2 = RandomUtils.uniform(r, hope2 + 1, top2 - 1);
            HallWay avenue2 = new HallWay(0, height2, false);
            avenue1.drawHorizontal(world, avenue2.getXY(), leftNeo, right2);
        }
        int upper = Math.max(height1, height2);
        int lower = Math.min(height1, height2);
        int lastVer = RandomUtils.uniform(r, leftNeo + 1, rightNeo);
        HallWay summary = new HallWay(lastVer, 0, true);
        summary.drawVertical(world, summary.getXY(), upper, lower, "summary");
    }



    public static void main(String[] args) {
        Engine test = new Engine();
        test.interactWithKeyboard();
        //test.interactWithInputString("N343SAAAAAWWWW:Q");
        //test.interactWithInputString("LWWWWWWWWWWWWWWW:Q");
        //test.interactWithInputString("LSSSSSSSSSSSSSSSSSSS:Q");
        //test.interactWithInputString("L:Q");
/*
        //test.interactWithInputString("asdfsdf5197880843569031643etrasdfsadf");
        test.interactWithInputString("N7789SAAAAAWWWWWWWWWWWWWWWWWWW:Q");
        test.read();
        boolean check = "StRinG".contains("n");
        String lower = "N1172635SAAASSWWWWD".toLowerCase();
        String numString = lower.substring(lower.indexOf("n") + 1,
                lower.indexOf("s"));
        int non = lower.indexOf("z");

 */
        String quit = "QQQ:::Q::Q";
        String quitSyntax = "";
        quitSyntax += quit.charAt(quit.length() - 2);
        quitSyntax += quit.charAt(quit.length() - 1);

    }
}
