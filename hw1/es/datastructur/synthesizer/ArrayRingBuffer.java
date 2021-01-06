package es.datastructur.synthesizer;
import java.util.Iterator;



public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {

        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    private int ind_renewer(int index) {
        if (index == rb.length) {
            return 0;
        }
        return index;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {

        if (fillCount == rb.length) {
            throw new RuntimeException("Ring Buffer overflow");
        }

        rb[ind_renewer(last)] = x;
        last = ind_renewer(last);
        last += 1;
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {

        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }

        first = ind_renewer(first);
        T temp = rb[first];
        rb[first] = null;
        first += 1;
        fillCount -= 1;
        return temp;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {

        if (fillCount == 0) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        first = ind_renewer(first);
        return rb[first];
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }

    public Iterator<T> iterator() {

        return new ARB_Iterator();
    }

    private class ARB_Iterator implements Iterator<T> {
        private int position;
        public ARB_Iterator() {
            position = 0;
        }

        @Override
        public boolean hasNext() {
            return position < fillCount;
        }

        @Override
        public T next() {
            T return_val = rb[position];
            position += 1;
            return return_val;
        }


    }

    @Override
    public boolean equals(Object o) {
        ArrayRingBuffer obj = (ArrayRingBuffer) o;
        if (this.fillCount() != obj.fillCount()) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Iterator<T> x = this.iterator();
        Iterator<T> y = obj.iterator();
        while (x.hasNext() & y.hasNext()) {
            if (x.next() != y.next()) {
                return false;
            }
        }
        return true;
    }

    private static void main(String[] args) {
        BoundedQueue<Integer> white = new ArrayRingBuffer(3);
        BoundedQueue<Integer> black = new ArrayRingBuffer(3);
        white.enqueue(1);
        white.enqueue(2);
        white.enqueue(3);
        black.enqueue(1);
        black.enqueue(2);
        black.enqueue(3);

        white.equals(black);
    }


}

