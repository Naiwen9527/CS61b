public class SLList {
    private static class IntNode {
        int item;
        IntNode next;

        IntNode(int i, IntNode n) {
            item = i;
            next = n;
        }
    }

    private IntNode first;


    public SLList(int x) {
        first = new IntNode(x, null);
    }

    /** Adds x to the front of the list. */
    public void addFirst(int x) {
        first = new IntNode(x, first);
    }

    /** Returns the first item in the list. */
    public int getFirst() {
        return first.item;
    }

    /** Adds x to the end of the list. */
    public void addLast(int x) {
        IntNode p = first;

        /* Advance p to the end of the list. */
        while (p.next != null) {
            p = p.next;
        }

        p.next = new IntNode(x, null);
    }


    public static void main(String[] args) {
        /* Creates a list of one integer, namely 10 */
        SLList L = new SLList(10);
        L.addLast(20);
        L.addLast(30);
        L.addLast(40);
    }
}
