package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import edu.princeton.cs.algs4.Stopwatch;

public class KDTreeTest {

    private Random random = new Random(200);

    @Test
    public void basicSanityChecks() {
        Point A = new Point(2, 3);
        Point B = new Point(4, 2);
        Point C = new Point(4, 5);
        Point D = new Point(3, 3);
        Point E = new Point(1, 5);
        Point F = new Point(4, 4);
        KDTree tree = new KDTree(List.of(A, B, C, D, E, F));
        Point nearst = tree.nearest(5, 1);
        Point nearst1 = tree.nearest(0, 7);
        Point nearst2 = tree.nearest(5, 6);
    }

    @Test
    public void randomizedTest() {
        List<Point> pointsToPut = new ArrayList<>();
        List<Point> pointsToTest = new ArrayList<>();
        for (int i = 0; i < 4000; i++) {
            pointsToPut.add(new Point(random.nextDouble(), random.nextDouble()));
        }
        for (int j = 0; j < 400; j++) {
            pointsToTest.add(new Point(random.nextDouble(), random.nextDouble()));
        }

        NaivePointSet naive = new NaivePointSet(pointsToPut);
        KDTree kd = new KDTree(pointsToPut);

        for (Point p : pointsToTest) {
            assertEquals(naive.nearest(p.getX(), p.getY()).getX(), kd.nearest(p.getX(), p.getY()).getX(), 0.000001);
            assertEquals(naive.nearest(p.getX(), p.getY()).getY(), kd.nearest(p.getX(), p.getY()).getY(), 0.000001);
        }
    }

    private static void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts, String sth) {
        if (sth == "kd_cons") {
            System.out.println("Timing table for Kd-Tree Construction");
        }
        if (sth == "native_near") {
            System.out.println("Timing table for Naive Nearest");
        }
        if (sth == "kd_near") {
            System.out.println("Timing table for Kd-Tree Nearest");
        }
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    private static void timeKDConstruct() {
        int[] calls = new int[] {31250, 62500, 125000, 250000, 500000, 1000000, 2000000};
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> ops = new ArrayList<>();

        for (int a : calls) {
            Random r = new Random(200);
            List<Point> pointsTime = new ArrayList<>();
            for (int j = 0; j <= a; j++) {
                pointsTime.add(new Point(r.nextDouble(), r.nextDouble()));
            }

            Stopwatch sw = new Stopwatch();
            KDTree subject = new KDTree(pointsTime);
            double timeInSeconds = sw.elapsedTime();

            Ns.add(a);
            times.add(timeInSeconds);
            ops.add(a);
        }

        printTimingTable(Ns, times, ops, "kd_cons");
    }

    private static void timeNaiveNearest() {
        int[] size = new int[] {125, 250, 500, 1000};
        Random r = new Random(200);
        List<Point> testPool = new ArrayList();
        for (int k = 0; k < 1000000; k++) {
            testPool.add(new Point(r.nextDouble(), r.nextDouble()));
        }

        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> ops = new ArrayList<>();

        for (int a : size) {

            List<Point> pointsTime = new ArrayList<>();
            for (int j = 0; j <= a; j++) {
                pointsTime.add(new Point(r.nextDouble(), r.nextDouble()));
            }
            NaivePointSet subject = new NaivePointSet(pointsTime);

            Stopwatch sw = new Stopwatch();
            for (Point b : testPool) {
                subject.nearest(b.getX(), b.getY());
            }
            double timeInSeconds = sw.elapsedTime();

            Ns.add(a);
            times.add(timeInSeconds);
            ops.add(1000000);
        }

        printTimingTable(Ns, times, ops, "native_near");
    }

    private static void timeKdNearest() {
        int[] size = new int[] {31250, 62500, 125000, 250000, 500000, 1000000};
        Random r = new Random(200);
        List<Point> testPool = new ArrayList();
        for (int k = 0; k < 1000000; k++) {
            testPool.add(new Point(r.nextDouble(), r.nextDouble()));
        }

        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> ops = new ArrayList<>();

        for (int a : size) {

            List<Point> pointsTime = new ArrayList<>();
            for (int j = 0; j <= a; j++) {
                pointsTime.add(new Point(r.nextDouble(), r.nextDouble()));
            }
            KDTree subject = new KDTree(pointsTime);

            Stopwatch sw = new Stopwatch();
            for (Point b : testPool) {
                subject.nearest(b.getX(), b.getY());
            }
            double timeInSeconds = sw.elapsedTime();

            Ns.add(a);
            times.add(timeInSeconds);
            ops.add(1000000);
        }

        printTimingTable(Ns, times, ops, "kd_near");
    }

    @Test
    public void timeTest() {
        timeKDConstruct();
        timeNaiveNearest();
        timeKdNearest();
    }
}
