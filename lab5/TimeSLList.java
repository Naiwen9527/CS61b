import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class that collects timing information about SLList getLast method.
 */
public class TimeSLList {
    private static void printTimingTable(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
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

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        int[] calls = new int[] {1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000};
        List<Integer> Ns = new LinkedList<Integer>();
        List<Double> times = new LinkedList<Double>();
        List<Integer> ops = new LinkedList<Integer>();

        for (int a : calls) {
            SLList subject = new SLList();
            for (int j = 0; j <= a; j++) {
                subject.addLast(1);
            }
            Stopwatch sw = new Stopwatch();
            for (int f = 0; f < 10000; f++) {
                subject.getLast();
            }
            double timeInSeconds = sw.elapsedTime();
            Ns.add(a);
            times.add(timeInSeconds);
            ops.add(10000);
        }
        printTimingTable(Ns, times, ops);
    }

}
