package derek.util.graph;

import java.util.HashSet;
import java.util.Set;

public class Vertex<K extends Comparable<K>, V extends Comparable<V>> implements Comparable<Vertex<K, V>>{

    public K key;
    public V value;
    public HashSet<Vertex<K, V>> edges = new HashSet<Vertex<K, V>>();

    public Vertex(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public Vertex(K key, V value, Set<Vertex<K, V>> connected) {
        this(key, value);
        this.edges.addAll(edges);

        for (Vertex<K, V> v : this.edges )
            v.addEdge(this);
    }

    public void addEdge(Vertex<K, V> v) {
        if (edges.contains(v))
            return;
        else {
            edges.add(v);
            v.edges.add(this);
        }
    }

    public int compareTo(Vertex<K, V> v) {
        int val = key.compareTo(v.key);
        if (val == 0)
            return value.compareTo(v.value);
        else
            return val;
    }

    @Override
    public String toString() {
        return "Key: [" + key + "], Value: [" + value + "]";
    }

    @Override
    public int hashCode() {
        int result = -1;
        result = 37 * result + key.hashCode();
        result = 37 * result + value.hashCode(); 
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vertex))
            return false;
        Vertex<K, V> v = (Vertex<K, V>) o;
        return key.equals(v.key) && value.equals(v.value);
    }
}