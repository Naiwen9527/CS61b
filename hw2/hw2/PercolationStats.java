package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int spec;
    private int times;
    private PercolationFactory fuss;
    private double[] here;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 | T <= 0) {
            throw new IllegalArgumentException();
        }
        spec = N;
        times = T;
        fuss = pf;
        here = new double[times];
        for (int i = 0; i < T; i++) {
            here[i] = permanentSecretary();
        }
    }

    private double permanentSecretary() {
        double opened = 0.0;
        Percolation field = fuss.make(spec);
        while (!field.percolates()) {
            int x = StdRandom.uniform(spec);
            int y = StdRandom.uniform(spec);
            if (!field.isOpen(x, y)) {
                field.open(x, y);
                opened += 1.0;
            }
        }
        double denom = spec * spec;
        return opened / denom;
    }

    public double mean() {
        return StdStats.mean(here);
    }

    public double stddev() {
        return StdStats.stddev(here);
    }

    public double confidenceLow() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(times));
    }

    public double confidenceHigh() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(times));
    }

    private static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats lsp = new PercolationStats(4, 5, pf);
        double a = lsp.mean();
        double b = lsp.stddev();
        double c = lsp.confidenceLow();
        double d = lsp.confidenceHigh();
    }

}
