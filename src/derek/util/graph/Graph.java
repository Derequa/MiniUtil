package derek.util.graph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class Graph implements Iterable<Vertex<? extends Comparable<?>, ? extends Comparable<?>>>{

    private HashSet<Vertex<? extends Comparable<?>, ? extends Comparable<?>>> verts = new HashSet<Vertex<? extends Comparable<?>, ? extends Comparable<?>>>();

    public Graph(){}

    public Graph(Collection<Vertex<? extends Comparable<?>, ? extends Comparable<?>>> verts) {
        this.verts.addAll(verts);
    }

    public void addVertex(Vertex<? extends Comparable<?>, ? extends Comparable<?>> v) {
        verts.add(v);
    }

    public boolean contains(Vertex<? extends Comparable<?>, ? extends Comparable<?>> v) {
        return verts.contains(v);
    }

    public Iterator<Vertex<? extends Comparable<?>, ? extends Comparable<?>>> iterator() {
        return verts.iterator();
    }

    @Override
    public String toString() {
        String ret = "";
        for (Vertex<? extends Comparable<?>, ? extends Comparable<?>> v : this )
            ret += v + "\n";
        
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Graph))
            return false;

        Graph g = (Graph) o;
        for (Vertex<? extends Comparable<?>, ? extends Comparable<?>> v : this) {
            if (g.contains(v))
                return false;
        }
        return true;
    }

}