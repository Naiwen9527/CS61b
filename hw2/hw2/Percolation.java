package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int num;
    private boolean[] opened;
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF maybe;
    private int numOpen;
    private int top;


    public Percolation(int N) {
        valiGrid(N);
        num = N;
        top = N * N;
        numOpen = 0;
        grid = new WeightedQuickUnionUF(top + 1);
        maybe = new WeightedQuickUnionUF(top + 2);
        for (int i = 0; i < num; i++) {
            grid.union(top, i);
        }
        for (int j = (num - 1) * num; j < num * num; j++) {
            maybe.union(num * num + 1, j);
        }
        for (int k = 0; k < num; k++) {
            maybe.union(top, k);
        }
        opened = new boolean[num * num];
    }

    private int converter(int r, int c) {
        if (r < 0) {
            if (c < 0) {
                return 0;
            }
            if (c == num) {
                return num - 1;
            }
            return c;
        }
        if (r == num) {
            if (c < 0) {
                return (num - 1) * num;
            }
            if (c == num) {
                return (num * num) - 1;
            }
            return (r - 1) * num + c;
        }
        if (c < 0) {
            return (r * num);
        }
        if (c == num) {
            return (r * num) + c - 1;
        }
        return (r * num) + c;
    }

    private void valiPosition(int r, int c) {
        if (r < 0 | r >= num | c < 0 | c >= num) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void valiGrid(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
    }

    private void connectProximity(int row, int col) {

        int above = converter(row - 1, col);
        int below = converter(row + 1, col);
        int left = converter(row, col - 1);
        int right = converter(row, col + 1);

        if (opened[above]) {
            grid.union(above, converter(row, col));
            maybe.union(above, converter(row, col));
        }
        if (opened[below]) {
            grid.union(below, converter(row, col));
            maybe.union(below, converter(row, col));
        }
        if (opened[left]) {
            grid.union(left, converter(row, col));
            maybe.union(left, converter(row, col));
        }
        if (opened[right]) {
            grid.union(right, converter(row, col));
            maybe.union(right, converter(row, col));
        }
    }

    public void open(int row, int col) {
        valiPosition(row, col);
        if (!isOpen(row, col)) {
            opened[converter(row, col)] = true;
            numOpen += 1;
            connectProximity(row, col);
        }
    }

    public boolean isOpen(int row, int col) {
        valiPosition(row, col);
        return opened[converter(row, col)];
    }

    public int numberOfOpenSites() {
        return numOpen;
    }

    public boolean isFull(int row, int col) {
        valiPosition(row, col);
        int subject = converter(row, col);
        return isOpen(row, col) & grid.connected(subject, top);
    }

    public boolean percolates() {
        if (numOpen == 0) {
            return false;
        }
        return maybe.connected(top, top + 1);
    }

    public static void main(String[] args) {
        /*Percolation p = new Percolation(5);
        int shit = p.converter(0, 1);
        int shit1 = 5 * 5 + 2;
        p.open(0, 2);
        p.open(1, 2);
        p.open(1, 3);
        p.open(2, 3);
        p.open(3, 3);
        p.open(3, 2);
        p.open(4, 2);
        int bbc = p.numberOfOpenSites();
        boolean fuck = p.percolates();
        System.out.println(fuck);
        p.open(4, 4);
        p.numberOfOpenSites();
        boolean cock = p.isFull(4, 4);*/
        /*Percolation o = new Percolation(3);
        o.percolates();
        o.open(0,0);
        o.open(0,1);
        o.open(1,0);*/
    }
}
