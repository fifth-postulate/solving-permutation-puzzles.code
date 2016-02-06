package nl.fifth.postulate.graphs.algorithm;

import nl.fifth.postulate.graphs.Graph;

import java.util.*;

public class Dijkstra {
    private final Graph graph;

    public Dijkstra(Graph graph){
        this.graph = graph;
    }

    public Path shortestPath(String fromLabel, String toLabel) {
        Graph.Vertex from = graph.findVertex(fromLabel);
        Graph.Vertex to = graph.findVertex(toLabel);

        Map<Graph.Vertex, Path> knownPaths = new HashMap<Graph.Vertex, Path>();
        for (Graph.Vertex vertex: graph.vertices()) {
            knownPaths.put(vertex, PathConstants.UNREACHABLE);
        }
        knownPaths.put(from, new Start(from));

        Queue<Graph.Vertex> toVisit = new LinkedList<Graph.Vertex>();
        Set<Graph.Vertex> visited = new HashSet<Graph.Vertex>();
        toVisit.add(from);
        while (!toVisit.isEmpty() && !toVisit.peek().equals(to)) {
            Graph.Vertex vertex = toVisit.poll();

            for (Graph.Vertex neighbour: vertex.neighbours()) {
                if ((knownPaths.get(vertex).distance() + 1) < knownPaths.get(neighbour).distance()) {
                    knownPaths.put(neighbour, new Step(knownPaths.get(vertex), neighbour));
                }

                if (!visited.contains(neighbour)) {
                    toVisit.add(neighbour);
                }
            }
        }

        return knownPaths.get(to);
    }
}

class Start implements Path {
    private final Graph.Vertex vertex;

    public Start(Graph.Vertex vertex) {
        this.vertex = vertex;
    }

    @Override
    public int distance() {
        return 0;
    }

    @Override
    public List<Graph.Vertex> vertices() {
        return Arrays.asList(vertex);
    }
}

class Step implements Path {
    private final Path before;
    private final Graph.Vertex vertex;

    public Step(Path before, Graph.Vertex vertex) {
        this.before = before;
        this.vertex = vertex;
    }

    @Override
    public int distance() {
        return before.distance() + 1;
    }

    @Override
    public List<Graph.Vertex> vertices() {
        List<Graph.Vertex> vertices = new ArrayList<Graph.Vertex>();
        vertices.addAll(before.vertices());
        vertices.add(vertex);
        return vertices;
    }
}

enum PathConstants implements Path {
    UNREACHABLE {
        @Override
        public int distance() {
            return Integer.MAX_VALUE;
        }

        @Override
        public List<Graph.Vertex> vertices() {
            throw new IllegalStateException("destination is unreachable");
        }
    }
}