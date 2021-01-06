package bearmaps;

import java.util.*;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private Node<T>[] minHeap;

    private int size;

    private Map<T, Integer> dataBase;


    private class Node<T> {
        T key;
        double priority;

        Node(T key, double priority) {
            this.key = key;
            this.priority = priority;
        }
    }

    public ArrayHeapMinPQ() {
        minHeap = (Node<T>[]) new Node[10];
        minHeap[0] = new Node(null, 0);
        size = 0;
        dataBase = new HashMap<>();
        dataBase.put(null, 0);
    }

    private void resize(String order) {
        if (order.equals("up")) {

            Node<T>[] newHeap = (Node<T>[]) new Node[minHeap.length * 2];

            newHeap[0] = new Node(null, 0);

            for (int i = 1; i < minHeap.length; i++) {
                newHeap[i] = minHeap[i];
            }
            minHeap = newHeap;
        }
        if (order.equals("down")) {

            Node<T>[] newHeap = (Node<T>[]) new Node[minHeap.length / 2];

            newHeap[0] = new Node(null, 0);

            for (int i = 1; i <= size; i++) {
                newHeap[i] = minHeap[i];
            }
            minHeap = newHeap;
        }

    }

    @Override
    public void add(T item, double priority) {
        if (dataBase.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        // resize up
        if (size + 1 == minHeap.length) {
            resize("up");
        }

        minHeap[size + 1] = (new Node(item, priority));

        dataBase.put(item, size + 1);
        size += 1;
        if (size <= 1) {
            return;
        }
        swim(size);
    }

    private void swim(int target)  {
        int fresh = target;
        int parent = fresh / 2;
        while (minHeap[fresh].priority < minHeap[parent].priority & parent != 0) {

            swap(fresh, parent);

            fresh = parent;
            parent = parent / 2;
        }
    }

    private void swap(int jesus, int christ) {
        Node temp = minHeap[christ];
        T keyNew = (T) minHeap[jesus].key;
        T keyOld = (T) minHeap[christ].key;
        dataBase.put(keyNew, christ);
        dataBase.put(keyOld, jesus);


        minHeap[christ] = minHeap[jesus];

        minHeap[jesus] = temp;
    }

    @Override
    public boolean contains(T item) {
        return dataBase.containsKey(item);
    }

    @Override
    public T getSmallest() {
        return (T) minHeap[1].key;
    }

    @Override
    public T removeSmallest() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        // resize down
        double factor = (double) size / (double) (minHeap.length);
        double limit = 1.0 / 4.0;
        if (factor <= limit) {
            resize("down");
        }

        T wasted = minHeap[1].key;

        minHeap[1] = minHeap[size];
        T stig = (T) minHeap[1].key;

        minHeap[size] = null;

        size -= 1;
        dataBase.put(stig, 1);
        dataBase.remove(wasted);
        washDown(1);
        return wasted;
    }

    private void washDown(int target) {
        int current = target;
        int leftChild = current * 2;
        int rightChild = leftChild + 1;


        // no child or nothing
        if (size <= 1) {
            return;
        }

        Node cu = minHeap[current];
        Node leftN = null;
        Node rightN = null;

        if (leftChild < minHeap.length) {
            leftN = minHeap[leftChild];
        }

        if (rightChild < minHeap.length) {
            rightN = minHeap[rightChild];
        }

        Node swap = leftN;
        int swapInd = leftChild;

        // swap right
        if (rightN != null) {
            if (rightN.priority <= leftN.priority) {
                swap = rightN;
                swapInd = rightChild;
            }
        }

        // swap
        if (swap != null) {
            if (swap.priority < cu.priority) {
                swap(current, swapInd);
                current = swapInd;
                washDown(current);
            }
        }

    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public void changePriority(T item, double priority) {
        if (!dataBase.containsKey(item)) {
            throw new NoSuchElementException();
        }
        int targetLock = dataBase.get(item);
        minHeap[targetLock].priority = priority;

        swim(targetLock);
        washDown(targetLock);
    }

    private static void main(String[] args) {
        ArrayHeapMinPQ a = new ArrayHeapMinPQ();
        int[] addList = new int[] {2000, 8, 7, 100, 6, 9, 10, 30, 500, 3000}; //{7997, 7998, 7999}
        for (int p : addList) {
            a.add("key" + p, p);
        }
        String shit = (String) a.getSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        for (int p : addList) {
            a.add("key" + p, p);
        }
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
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
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
        a.removeSmallest();
    }
}
