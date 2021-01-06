import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V>{

    private double loadF;

    private class inLinked {
        K key;
        V value;
        inLinked(K k, V v) {
            key = k;
            value = v;
        }
    }

    private LinkedList<inLinked>[] buckets;

    private int length;

    private int size;

    private Set<K> keySet;

    public MyHashMap() {
        buckets = (LinkedList<inLinked>[]) new LinkedList[16];
        loadF = 0.75;
        length = buckets.length;
        size = 0;
        keySet = new HashSet<K>();
    }
    public MyHashMap(int initialSize) {
        buckets = (LinkedList<inLinked>[]) new LinkedList[initialSize];
        loadF = 0.75;
        length = buckets.length;
        size = 0;
        keySet = new HashSet<K>();
    }
    public MyHashMap(int initialSize, double loadFactor) {
        buckets = (LinkedList<inLinked>[]) new LinkedList[initialSize];
        loadF = loadFactor;
        length = buckets.length;
        size = 0;
        keySet = new HashSet<K>();
    }

    @Override
    public void clear() {
        buckets = (LinkedList<inLinked>[]) new LinkedList[length];
        size = 0;
        keySet.clear();
    }

    @Override
    public boolean containsKey(K key) {
        return keySet.contains(key);
    }

    private inLinked getNode(K k) {
        LinkedList<inLinked> thang = buckets[getIndex(k, this.length)];
        if (thang != null) {
            for (inLinked i : thang) {
                if (i.key.equals(k)) {
                    return i;
                }
            }
        }

        return null;
    }

    private int getIndex(K k, int len) {
        int hash = k.hashCode();
        int index = Math.floorMod(hash, len);
        return index;
    }

    @Override
    public V get(K key) {
        inLinked node = getNode(key);
        if (node != null) {
            return node.value;
        }

        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        inLinked node= getNode(key);

        // update
        if (node != null) {
            node.value = value;
            return;
        }

        // resize first if needed
        if ((double) size / (double) length > loadF) {
            resize();
        }

        // if non-exist a key
        int index = getIndex(key, length);
        LinkedList<inLinked> slot = buckets[index];

        // if non-exist a LinkedList
        if (slot == null) {
            buckets[index] = new LinkedList<>();
        }

        // add the key and value to both buckets and hashset
        buckets[index].add(new inLinked(key, value));
        this.size += 1;
        keySet.add(key);
    }

    private void resize() {
        LinkedList<inLinked>[] newBuckets = (LinkedList<inLinked>[]) new LinkedList[length * 2];

        // fill the new array with LinkedLists first
        for (int i = 0; i < newBuckets.length; i++) {
            newBuckets[i] = new LinkedList<>();
        }

        // add items to their new home
        for (K key : keySet) {
            int index = getIndex(key, newBuckets.length);
            LinkedList<inLinked> newHome = newBuckets[index];
            inLinked node = getNode(key);
            newHome.add(node);
        }

        buckets = newBuckets;
        length = newBuckets.length;
    }

    @Override
    public Set<K> keySet() {
        return keySet;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet.iterator();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    private static void main(String[] args) {
        MyHashMap<String, Integer> m = new MyHashMap();
        m.put("one", 1);
        m.put("two", 2);
        m.put("one", 2);
        m.clear();
    }
}
