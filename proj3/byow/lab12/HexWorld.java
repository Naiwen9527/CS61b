package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 60;
    private static final int HEIGHT = 30;
    TERenderer ter;
    TETile[][] world;

    public HexWorld() {
        ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        world = new TETile[WIDTH][HEIGHT];
    }

    public void addHexagon(int length, int[] start) {

        createHexagon(4, start, Tileset.FLOWER);

    }

    private void createHexagon(int length, int[] start, TETile style) {

    }

    public static void main(String[] args) {

    }
}
