package nl.fifth.postulate;

import nl.fifth.postulate.graphs.Graph;
import nl.fifth.postulate.graphs.algorithm.Dijkstra;
import nl.fifth.postulate.graphs.algorithm.Path;
import nl.fifth.postulate.graphs.reader.GraphReader;

import java.io.File;

public class ShortestPath {
    public static void main(String[] args) {
        Graph graph = GraphReader.readFrom(new File("/home/dvberkel/scratch/fifth-postulate/solving-permutation-puzzles.code/content/graphs/wolf-sheep-cabbage.graph"));

        Dijkstra dijkstra = new Dijkstra(graph);
        Path path = dijkstra.shortestPath("1", "16");

        System.out.println(String.format("path %s has length %d", path.vertices(), path.distance()));
    }
}
