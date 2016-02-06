package nl.fifth.postulate.graphs.algorithm;

import nl.fifth.postulate.graphs.Graph;

import java.util.List;

public interface Path {
    int distance();
    List<Graph.Vertex> vertices();
}
