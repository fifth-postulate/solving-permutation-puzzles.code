package nl.fifth.postulate.graphs;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Graph {
    private Set<Vertex> vertices = new HashSet<>();

    public Set<Vertex> vertices() {
        return Collections.unmodifiableSet(vertices);
    }

    public Vertex addVertex(String label) {
        Vertex vertex = new Vertex(label);
        if (vertices.contains(vertex)) {
            throw new IllegalArgumentException(String.format("can not add vertex with label %s, it already exists", label));
        }
        vertices.add(vertex);
        return vertex;
    }

    public boolean hasVertex(String label) {
        return vertices.contains(new Vertex(label));
    }

    public Vertex findVertex(String label) {
        if (!hasVertex(label)) {
            throw new IllegalArgumentException(String.format("no vertex with label %s", label));
        }
        return new Vertex(label);
    }
}
