package nl.fifth.postulate.graphs.algorithm;

import nl.fifth.postulate.graphs.Graph;

import java.util.*;

public class Dijkstra {
    private final Graph graph;

    public Dijkstra(Graph graph){
        this.graph = graph;
    }

    public Integer shortestPath(String fromLabel, String toLabel) {
        Graph.Vertex from = graph.findVertex(fromLabel);
        Graph.Vertex to = graph.findVertex(toLabel);

        Map<Graph.Vertex, Integer> knownDistances = new HashMap<Graph.Vertex, Integer>();
        for (Graph.Vertex vertex: graph.vertices()) {
            knownDistances.put(vertex, Integer.MAX_VALUE);
        }
        knownDistances.put(from, 0);

        Queue<Graph.Vertex> toVisit = new LinkedList<Graph.Vertex>();
        Set<Graph.Vertex> visited = new HashSet<Graph.Vertex>();
        toVisit.add(from);
        while (!toVisit.isEmpty() && !toVisit.peek().equals(to)) {
            Graph.Vertex vertex = toVisit.poll();

            for (Graph.Vertex neighbour: vertex.neighbours()) {
                if ((knownDistances.get(vertex) + 1) < knownDistances.get(neighbour)) {
                    knownDistances.put(neighbour, knownDistances.get(vertex) + 1);
                }

                if (!visited.contains(neighbour)) {
                    toVisit.add(neighbour);
                }
            }
        }

        return knownDistances.get(to);
    }
}
