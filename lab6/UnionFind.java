import java.util.ArrayList;

public class UnionFind {
    int[] parent;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        parent = new int[n];
        // set all the parents to be -1 to symbolize that they are disjoint
        for (int i = 0; i < n; i++) {
            parent[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid vertex. */
    private void validate(int v1) {
        if (v1 > parent.length | v1 < 0) {
            throw new IllegalArgumentException();
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        int root = find(v1);
        return -1 * parent[root];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        return parent[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean isConnected(int v1, int v2) {
        validate(v1);
        validate(v2);
        int p1 = v1;
        int p2 = v2;
        while (parent(p1) > 0) {
            p1 = parent(p1);
        }
        while (parent(p2) > 0) {
            p2 = parent(p2);
        }
        if (p1 == p2) {
            return true;
        }
        return false;
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Connecting a
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void connect(int v1, int v2) {
        validate(v1);
        validate(v2);
        if (v1 == v2) {
            return;
        }
        if (find(v1) == find(v2)) {
            return;
        }
        if (sizeOf(v1) > sizeOf(v2)) {
            int p = v1;
            while (parent(p) > 0) {
                p = parent(p);
            }
            parent[v2] = p;
            parent[p] -= 1;
            return;
        }
        if (sizeOf(v1) < sizeOf(v2)) {
            int p = v2;
            while (parent(p) > 0) {
                p = parent(p);
            }
            parent[v1] = p;
            parent[p] -= 1;
            return;
        }
        if  (sizeOf(v1) == sizeOf(v2)) {
            int p1 = v1;
            int p2 = v2;
            while (parent(p1) > 0) {
                p1 = parent(p1);
            }
            while (parent(p2) > 0) {
                p2 = parent(p2);
            }
            parent[v1] = p2;
            parent[p2] -= 1;
            return;
        }
    }

    /* Returns the root of the set v1 belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int v1) {
        validate(v1);
        int p = v1;
        while (parent(p) > 0) {
            p = parent(p);
        }
        if (parent(v1) < 0) {
            return v1;
        }
        return p;
    }

    public static void main(String[] args) {
        UnionFind u =  new UnionFind(5);
        u.connect(2,1);
        u.connect(3,1);
        u.connect(4,3);
        u.connect(0,4);
        u.find(4);
        u.connect(1,4);
        u.isConnected(0,3);
    }

}
