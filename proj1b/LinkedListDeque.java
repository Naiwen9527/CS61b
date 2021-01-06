/** LLD class. */
public class LinkedListDeque<T> implements Deque<T> {

    /** nested class for creating a node. */
    private class IntNode {
        /** value T.*/
        private T value;
        /** the next node. */
        private IntNode next;
        /**the previous node*/
        private IntNode prev;

        /** intnode constructor.
         * @param x the value
         * @param y previous point
         * @param z next point.*/
        IntNode(T x, IntNode y, IntNode z) {
            value = x;
            prev = y;
            next = z;
        }
    }

    /**the sentinel node. */
    private IntNode daddy;
    /**the first node of the list. */
    private IntNode front;
    /**the last item of the list. */
    private IntNode back;
    /**the size of the list, always an int. */
    private int size;

    /**LLD constructor. */
    public LinkedListDeque() {
        daddy = new IntNode(null, daddy, daddy);
        front = daddy;
        back = daddy;
        size = 0;
    }

    /** add one item to the front of the list.
     * @param x the item added.*/
    @Override
    public void addFirst(T x) {
        front.prev = new IntNode(x, daddy, front);
        daddy.next = front.prev;
        front = daddy.next;
        back = daddy.prev;
        size += 1;
    }

    /** add one item to the back of the list.
     * @param x item added.*/
    @Override
    public void addLast(T x) {
        back.next = new IntNode(x, back, daddy);
        daddy.prev = back.next;
        back = daddy.prev;
        front = daddy.next;
        size += 1;
    }

    /** check if the array is empty, returns true if it is and vice versa.
     * @return boolean. */
    /**@Override
    public boolean isEmpty() {
        return this.size == 0;
    }*/

    /**return the size of the list.
     * @return int. */
    @Override
    public int size() {
        return size;
    }

    /** print all the elements in the list, each separated by a space,
     * after which it will start a new line. */
    @Override
    public void printDeque() {
        IntNode p = daddy.next;
        while (p != daddy) {
            System.out.print(p.value + " ");
            p = p.next;
        }
        System.out.println();
    }

    /** remove the first element in the list.
     * @return T. */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T temp = front.value;
        front.next.prev = daddy;
        daddy.next = front.next;
        front = daddy.next;
        back = daddy.prev;
        size -= 1;
        return temp;
    }

    /** remove the last element of the list.
     * @return T. */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T temp = back.value;
        back.prev.next = daddy;
        daddy.prev = back.prev;
        back = daddy.prev;
        front = daddy.next;
        size -= 1;
        return temp;
    }

    /** return the ith item of the list iteratively.
     * @param i the index to get.
     * @return T. */
    @Override
    public T get(int i) {
        if (size == 0) {
            return null;
        }
        IntNode p = front;
        while (i != 0) {
            i -= 1;
            p = p.next;
        }

        return p.value;
    }

    /** a helper function for the getrecursive function.
     * @param i the index
     * @param p the starting node of recursion
     * @return T. */
    private T getReHelper(int i, IntNode p) {
        if (size == 0) {
            return null;
        }
        if (i == 0) {
            return p.value;
        }
        return getReHelper(i - 1, p.next);
    }

    /** return the ith item of the list using recursion.
     * @param i index
     * @return T. */
    public T getRecursive(int i) {
        return getReHelper(i, front);
    }

    private static void main(String[] args) {
        LinkedListDeque L = new LinkedListDeque<Integer>();
        L.removeLast();
        L.removeFirst();
    }
}
