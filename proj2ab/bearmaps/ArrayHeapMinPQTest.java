package bearmaps;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {

    private Random random = new Random(200);

    @Test
    public void basicSanityChecks() {
        ArrayHeapMinPQ a = new ArrayHeapMinPQ();
        int[] addList = new int[] {2000, 8, 7, 100, 6, 9, 10, 30, 500, 3000};
        for (int p : addList) {
            a.add("key" + p, p);
        }

        for (int i = 0; i < 10; i++) {
            a.removeSmallest();
        }

        for (int p : addList) {
            a.add("key" + p, p);
        }
        a.changePriority("key3000", 0);
        a.changePriority("key3000", 3000);
        a.changePriority("key3000", 4000);
        a.changePriority("key3000", 3000);
        a.changePriority("key6", 0);
        a.changePriority("key6", 6);
        a.changePriority("key6", 4000);
        a.changePriority("key6", 6);
    }

    @Test
    public void randomizedTest() {
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ();
        NaiveMinPQ<String> n = new NaiveMinPQ<>();
        int trials = 8000;
        for (int i = 0; i < trials; i++) {
            double adding = random.nextDouble();
            a.add("key" + i, i);
            n.add("key" + i, i);
        }
        for (int k = 0; k < trials; k++) {
            assertEquals(a.removeSmallest(), n.removeSmallest());
        }

    }

    @Test
    public void changePriorityTest() {
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ();
        NaiveMinPQ<String> n = new NaiveMinPQ<>();
        int trials = 4000;
        for (int i = 0; i < trials; i++) {
            a.add("key" + i, i);
            n.add("key" + i, i);
        }
        for (int k = 0; k < trials; k++) {
            double smallest = Double.NEGATIVE_INFINITY;
            a.changePriority("key" + (trials - 1 - k), smallest);
            n.changePriority("key" + (trials - 1 - k), smallest);
            assertEquals(a.removeSmallest(), n.removeSmallest());
        }
    }

    private static void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts, String sth) {
        if (sth == "array") {
            System.out.println("Timing table for ArrayHeapMinPQ Add");
        }
        if (sth == "native") {
            System.out.println("Timing table for Naive Add");
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

    private static void timeArrayAdd() {
        int[] calls = new int[] {10000, 20000, 40000, 80000, 160000, 320000, 640000};
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> ops = new ArrayList<>();

        for (int c : calls) {
            Random r = new Random(200);
            ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ();
            Stopwatch sw = new Stopwatch();
            for (int j = 0; j <= c; j++) {
                double add = r.nextDouble();
                a.add("key" + add, add);
            }

            double timeInSeconds = sw.elapsedTime();

            Ns.add(c);
            times.add(timeInSeconds);
            ops.add(c);
        }

        printTimingTable(Ns, times, ops, "array");
    }

    private static void timeNativeAdd() {
        int[] calls = new int[] {10000, 20000, 40000, 80000, 160000, 320000, 640000};
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> ops = new ArrayList<>();

        for (int c : calls) {
            Random r = new Random(200);
            NaiveMinPQ<String> a = new NaiveMinPQ();
            Stopwatch sw = new Stopwatch();
            for (int j = 0; j <= c; j++) {
                double add = r.nextDouble();
                a.add("key" + add, add);
            }

            double timeInSeconds = sw.elapsedTime();

            Ns.add(c);
            times.add(timeInSeconds);
            ops.add(c);
        }

        printTimingTable(Ns, times, ops, "native");
    }

    @Test
    public void timeTest() {
        timeNativeAdd();
        timeArrayAdd();

    }
}
