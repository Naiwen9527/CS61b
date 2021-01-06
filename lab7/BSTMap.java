import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private int size;

    private BSTMap<K, V> left;

    private BSTMap<K, V> right;

    private K key;

    private V value;

    public BSTMap() {
        size = 0;
        left = null;
        right = null;
        key = null;
        value = null;
    }

    @Override
    public void clear() {
        size = 0;
        left = null;
        right = null;
        key = null;
        value = null;
    }

    @Override
    public boolean containsKey(K key) {
        return searchKey(this, key);
    }
    private boolean searchKey(BSTMap node, K key) {
        boolean result = false;
        if (node == null) {
            return false;
        } else if (node.key == null) {
            return false;
        } else if (node.key.compareTo(key) == 0) {
            return true;
        } else if (node.key.compareTo(key) > 0) {
            result = searchKey(node.left, key);
        } else if (node.key.compareTo(key) < 0) {
            result = searchKey(node.right, key);
        }
        return result;
    }

    private V getVal(BSTMap node, K key) {
        V result = null;
        if (node == null) {
            return null;
        } else if (node.key == null) {
            return null;
        } else if (node.key.compareTo(key) == 0) {
            return (V) node.value;
        } else if (node.key.compareTo(key) > 0) {
            result = getVal(node.left, key);
        } else if (node.key.compareTo(key) < 0) {
            result = getVal(node.right, key);
        }
        return result;
    }

    @Override
    public V get(K key) {
        V result = getVal(this, key);
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    private BSTMap insert(BSTMap node, K key, V value) {
        if (node == null) {
            BSTMap install = new BSTMap();
            install.key = key;
            install.value = value;
            install.size += 1;
            return install;
        } else if (node.key == null) {
            node.key = key;
            node.value = value;
        } else if (node.key.compareTo(key) == 0) {
            return null;
        } else if (node.key.compareTo(key) > 0) {
            node.left = insert(node.left, key, value);
        } else if (node.key.compareTo(key) < 0) {
            node.right = insert(node.right, key, value);
        }
        return node;
    }

    @Override
    public void put(K key, V value) {
        insert(this, key, value);
        size += 1;
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        BSTMap car = new BSTMap<Integer, Integer>();
        car.put(6, 6);
        car.put(3, 3);
        car.put(8, 8);
        car.put(7, 7);
        car.put(2, 2);
        car.put(4, 4);

    }
}
