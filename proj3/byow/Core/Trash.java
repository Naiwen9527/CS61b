package byow.Core;


public class Trash {
    /*
    // add a point on each side of the given Vertical Node
    private void pointsGeneratorVer(Random r, Node given, KDTree tree) {
        if (given.depth < 2) {
            int limiterH = HEIGHT / 4;
            // right side
            int x1 = (WIDTH - given.x) / 2;
            int y1 = RandomUtils.uniform(r, HEIGHT - limiterH);
            tree.add(new Point(x1, y1));
            // left side
            int x2 = given.x / 2;
            int y2 = RandomUtils.uniform(r, HEIGHT - limiterH);
            tree.add(new Point(x2, y2));
        } else {
            if (given.y > given.parent.y) { // upper
                int limiterH = (HEIGHT - given.parent.y) / 4;
                // right side
                int x1 = (WIDTH - given.x) / 2;
                int boundL = (HEIGHT - given.parent.y) - limiterH;
                int boundR = given.parent.y + limiterH;
                int y1 = RandomUtils.uniform(r, boundL, boundR);
                tree.add(new Point(x1, y1));
                // left side
                int x2 = (given.x - given.parent.parent.x) / 2;
                int y2 = RandomUtils.uniform(r, boundL, boundR);
                tree.add(new Point(x2, y2));
            }
            if (given.y < given.parent.y) { // lower
                int limiterH = (given.parent.y) / 4;
                // right side
                int x1 = (WIDTH - given.x) / 2;
                int y1 = RandomUtils.uniform(r, given.parent.y - limiterH);
                tree.add(new Point(x1, y1));
                // left side
                int x2 = (given.x - given.parent.parent.x) / 2;
                int y2 = RandomUtils.uniform(r, given.parent.y - limiterH);
                tree.add(new Point(x2, y2));
            }

        }

    }

    private void pointsGeneratorHor(Random r, Node given, KDTree tree) {
        if (given.depth < 2) {
            int limiterH = HEIGHT / 4;
            // right side
            int x1 = (WIDTH - given.x) / 2;
            int y1 = RandomUtils.uniform(r, HEIGHT - limiterH);
            tree.add(new Point(x1, y1));
            // left side
            int x2 = given.x / 2;
            int y2 = RandomUtils.uniform(r, HEIGHT - limiterH);
            tree.add(new Point(x2, y2));
        } else {
            if (given.y > given.parent.y) { // upper
                int limiterH = (HEIGHT - given.parent.y) / 4;
                // right side
                int x1 = (WIDTH - given.x) / 2;
                int boundL = (HEIGHT - given.parent.y) - limiterH;
                int boundR = given.parent.y + limiterH;
                int y1 = RandomUtils.uniform(r, boundL, boundR);
                tree.add(new Point(x1, y1));
                // left side
                int x2 = (given.x - given.parent.parent.x) / 2;
                int y2 = RandomUtils.uniform(r, boundL, boundR);
                tree.add(new Point(x2, y2));
            }
            if (given.y < given.parent.y) { // lower
                int limiterH = (given.parent.y) / 4;
                // right side
                int x1 = (WIDTH - given.x) / 2;
                int y1 = RandomUtils.uniform(r, given.parent.y - limiterH);
                tree.add(new Point(x1, y1));
                // left side
                int x2 = (given.x - given.parent.parent.x) / 2;
                int y2 = RandomUtils.uniform(r, given.parent.y - limiterH);
                tree.add(new Point(x2, y2));
            }

        }
    }*/

        /*
    private void trimHallWays(Random r, TETile[][] world, int bottomLine) {
        int index = 0;
        for (Room room : rooms) {
            HallWay fitted = halls.get(index);
            if (room.upperBound() < bottomLine) {
                int max = fitted.getTop() - bottomLine - 1;
                int trimLength = RandomUtils.uniform(r, 0, max);
                trimHelper(world, room.getX(), fitted.getTop(), trimLength);
            }
            index += 1;
        }
    }

    private void trimHelper(TETile[][] world, int x, int y, int trimLength) {
        int curr = y;
        int countDown = trimLength;
        while (countDown > 0) {
            world[x - 1][curr] = Tileset.NOTHING;
            world[x][curr] = Tileset.NOTHING;
            world[x + 1][curr] = Tileset.NOTHING;
            curr -= 1;
        }
        world[x - 1][curr] = Tileset.WALL;
        world[x][curr] = Tileset.WALL;
        world[x + 1][curr] = Tileset.WALL;
    } */

    // deploy vertical hallways dependent on the decision

        /*
        for (Room room : rooms) {
            int exit = RandomUtils.uniform(r, room.leftBound() + 1, room.rightBound());
            HallWay hall = new HallWay(exit, top, true);
            halls.add(hall);
            hall.drawVertical(world, hall.getXY(), top, room.lowerBound());
        } */


    // further develop the map with two horizontal hallways

        /*
        int height = RandomUtils.uniform(r, hope + 1, top - 1);
        HallWay avenue = new HallWay(0, height, false);
        //avenue.drawHorizontal(world, avenue.getXY(), left, right);

        int confuser = RandomUtils.uniform(r, bottom + 1, top - 1);
        int regulator = confuser / 2;

        int aux = averageC / rooms.size();
        // avenue.drawHorizontal(world, aux, left + regulator, right - regulator);

         */
}
