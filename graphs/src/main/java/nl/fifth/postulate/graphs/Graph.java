package nl.fifth.postulate.graphs;

import java.util.*;

public class Graph {
    private Map<Vertex, Set<Vertex>> neighbours = new HashMap<Vertex, Set<Vertex>>();

    public Set<Vertex> vertices() {
        return Collections.unmodifiableSet(neighbours.keySet());
    }

    public Vertex addVertex(String label) {
        if (hasVertex(label)) {
            throw new IllegalArgumentException(String.format("can not add vertex with label %s, it already exists", label));
        }
        Vertex vertex = new Vertex(label);
        neighbours.put(vertex, new HashSet<Vertex>());
        return vertex;
    }

    public boolean hasVertex(String label) {
        return neighbours.containsKey(new Vertex(label));
    }

    public Vertex findVertex(String label) {
        if (!hasVertex(label)) {
            throw new IllegalArgumentException(String.format("no vertex with label %s", label));
        }
        return new Vertex(label);
    }

    public Set<Edge> edges() {
        Set<Edge> edges = new HashSet<Edge>();
        for (Map.Entry<Vertex, Set<Vertex>> entry: neighbours.entrySet()) {
            for (Vertex to: entry.getValue()) {
                edges.add(new Edge(entry.getKey(), to));
            }
        }
        return edges;
    }

    public Edge addEdge(String from, String to) {
        if (!hasVertex(from) || !hasVertex(to)) {
            throw new IllegalArgumentException("can not form an edge between unknown vertices");
        }
        return addEdge(new Vertex(from), new Vertex(to));
    }

    private Edge addEdge(Vertex from, Vertex to) {
        neighbours.get(from).add(to);
        return new Edge(from, to);
    }

    public boolean hasEdge(String fromLabel, String toLabel) {
        if (!hasVertex(fromLabel) || !hasVertex(toLabel)) {
            return false;
        }
        Vertex from = new Vertex(fromLabel);
        Vertex to = new Vertex(toLabel);
        return neighbours.get(from).contains(to);
    }

    public Edge findEdge(String from, String to) {
        if (!hasEdge(from, to)) {
            throw new IllegalArgumentException(String.format("no edge from %s to %s", from, to));
        }
        return new Edge(new Vertex(from), new Vertex(to));
    }
}

