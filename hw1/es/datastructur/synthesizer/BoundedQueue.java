package es.datastructur.synthesizer;

public interface BoundedQueue<T> extends Iterable<T> {
    int capacity();

    int fillCount();

    void enqueue(T x);

    T dequeue();

    T peek();

    default boolean isEmpty() {
        return this.fillCount() == 0;
    }

    default boolean isFull() {
        return this.fillCount() == this.capacity();
    }
}
