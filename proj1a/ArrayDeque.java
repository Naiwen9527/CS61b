/** the bigass AD major class. */
public class ArrayDeque<T> {

    /**the array it self. */
    private T[] container;
    /**the size of the array, always an int. */
    private int size;
    /**the index of the first item in the array. */
    private int front;
    /**the index of the last item in the array. */
    private int back;
    /**the index of the item next to last. */
    private int next;
    /**the index of the item before the first. */
    private int prev;

    /**AD constructor of a empty array. */
    public ArrayDeque() {
        container = (T[]) new Object[8];
        size = 0;
        front = 0;
        back = 0;
        next = back + 1;
        prev = container.length - 1;
    }

    /** add one item x to the front of the array.
     * @param x the item added to the first. */
    public void addFirst(T x) {
        if (size == 0) {
            container[size] = x;
            size += 1;
            prev = -1;
            next = 1;
        } else {
            if (size + 1 == container.length) {
                resizeUp();
            }
            if (front == 0) {
                prev = container.length - 1;
            }
            container[prev] = x;
            front = prev;
            prev -= 1;
            size += 1;

        }
    }

    /** add one item x to the back of the array.
     * @param x the item added to the last. */
    public void addLast(T x) {
        if (size == 0) {
            container[size] = x;
            size += 1;
            prev = -1;
            next = 1;
        } else {
            if (size + 1 == container.length) {
                resizeUp();
            }
            if (back == container.length - 1) {
                next = 0;
            }
            container[next] = x;
            back = next;
            size += 1;
            next += 1;
        }
    }

    /** return true if the array is empty and vice versa.
     * @return boolean. */
    public boolean isEmpty() {
        return size == 0;
    }

    /**return the size of the array.
     * @return int. */
    public int size() {
        return size;
    }

    /** print all the items in the array separated by a space.
     * Starting a new line when all is printed.*/
    public void printDeque() {
        if (front > back) {
            int p = front;
            while (p <= container.length - 1) {
                System.out.print(container[p] + " ");
                p += 1;
            }
            p = 0;
            while (p <= back) {
                System.out.print(container[p] + " ");
                p += 1;
            }
        } else {
            int p = front;
            while (p <= back) {
                System.out.print(container[p] + " ");
                p += 1;
            }
        }
        System.out.println();
    }

    /** a helper function to enlarge the size of the original array
     * by copying from the original one and rearrange.*/
    private void resizeUp() {
        if (front > back) {
            T[] young = (T[]) new Object[size * 2];
            System.arraycopy(container, front, young, 0, container.length - front);
            int i = container.length - front;
            int p = 0;
            while (p <= back) {
                young[i] = container[p];
                p += 1;
                i += 1;
            }
            container = young;
            front = 0;
            back = size - 1;
            next = back + 1;
            prev = container.length - 1;
        } else {
            T[] young = (T[]) new Object[size * 2];
            System.arraycopy(container, front, young, 0, size);
            container = young;
            front = 0;
            back = size - 1;
            next = back + 1;
            prev = container.length - 1;
        }
    }

    /** a helper function to reduce the size of the original array
     * by copying from the original one and rearrange.*/
    private void resizeDown() {
        if (front > back) {
            T[] young = (T[]) new Object[container.length / 2];
            System.arraycopy(container, front, young, 0, container.length - front);
            int i = container.length - front;
            int p = 0;
            while (p <= back) {
                young[i] = container[p];
                p += 1;
                i += 1;
            }
            container = young;
            front = 0;
            back = size - 1;
            next = back + 1;
            prev = container.length - 1;
        } else {
            T[] young = (T[]) new Object[container.length / 2];
            System.arraycopy(container, front, young, 0, size);
            container = young;
            front = 0;
            back = size - 1;
            next = back + 1;
            prev = container.length - 1;
        }
    }

    /** remove and return the first item of the array.
     * @return T. */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T temp = container[front];
        if ((size - 1 < container.length * 0.25) && (container.length >= 16)) {
            resizeDown();
        }
        if (front > back) {
            if (front == container.length - 1) {
                container[front] = null;
                front = 0;
                size -= 1;
            } else {
                container[front] = null;
                front += 1;
                size -= 1;
            }
        } else {
            if (front == back) {
                container[front] = null;
                front = 0;
                back = 0;
                size -= 1;
            } else {
                container[front] = null;
                front += 1;
                size -= 1;
            }
        }
        prev = front - 1;
        if (front == -1) {
            prev = container.length - 1;
        }
        return temp;
    }

    /** remove and return the last item of the array.
     * @return T. */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T temp = container[back];
        if ((size - 1 < container.length * 0.25) && (container.length >= 16)) {
            resizeDown();
        }
        if (front > back) {
            if (back == 0) {
                container[back] = null;
                back = container.length - 1;
                size -= 1;
            } else {
                container[back] = null;
                back -= 1;
                size -= 1;
            }
        } else {
            if (front == back) {
                container[back] = null;
                front = 0;
                back = 0;
                size -= 1;
            } else {
                container[back] = null;
                back -= 1;
                size -= 1;
            }
        }
        next = back + 1;
        if (next == container.length) {
            next = 0;
        }
        return temp;
    }

    /**return the xth item in the array.
     * @param x the index to get.
     * @return T. */
    public T get(int x) {
        if (x > size) {
            return null;
        } else {
            return container[(x + front) % container.length];
        }
    }

    private static void main(String[] args) {
        ArrayDeque L = new ArrayDeque<Integer>();
        /**L.isEmpty();
        L.addLast(1);
        L.addLast(2);
        L.addFirst(-1);
        L.addFirst(-2);
        L.size();
        L.addFirst(-3);
        L.addFirst(-4);
        L.printDeque();
        L.get(5);
        L.get(4);
        L.get(3);
        L.addLast(3);
        L.addLast(4);
        L.addLast(5);
        L.addLast(6);
        L.addFirst(-5);
        L.addFirst(-6);
        L.get(5);
        L.get(4); */

        L.addFirst(10);
        L.addFirst(2);
        L.addFirst(3);
        L.addFirst(4);
        L.addFirst(5);
        L.addFirst(6);
        L.addFirst(7);
        L.addFirst(8);
        L.addFirst(9);
        L.addLast(10);
        L.addFirst(4);
        L.addFirst(5);
        L.addFirst(6);
        L.addFirst(7);
        L.addFirst(8);
        L.addFirst(9);
        L.addLast(10);
        L.removeFirst();
        L.removeFirst();
        L.removeFirst();
        L.removeFirst();
        L.removeFirst();
        L.removeFirst();
        L.removeFirst();
        L.removeLast();
        L.removeLast();
        L.removeLast();
        L.removeLast();
        L.removeLast();
        L.removeLast();
    /**  ==> 11
        //L.addFirst(1);
        //L.removeFirst();
        L.addLast(0);
        L.addLast(1);
        L.addLast(2);
        L.addLast(3);
        L.removeFirst();*/
    }

}
