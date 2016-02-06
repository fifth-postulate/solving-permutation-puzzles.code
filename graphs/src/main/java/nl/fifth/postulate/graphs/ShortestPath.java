package nl.fifth.postulate.graphs;

import nl.fifth.postulate.graphs.algorithm.Dijkstra;
import nl.fifth.postulate.graphs.reader.GraphReader;

import java.io.File;

public class ShortestPath {
    public static void main(String[] args) {
        Graph graph = GraphReader.readFrom(new File("/home/dvberkel/scratch/fifth-postulate/solving-permutation-puzzles.code/content/graphs/wolf-sheep-cabbage.graph"));

        Dijkstra dijkstra = new Dijkstra(graph);
        Integer pathLength = dijkstra.shortestPath("1", "16");

        System.out.println(String.format("Distance is %d", pathLength));
    }
}
