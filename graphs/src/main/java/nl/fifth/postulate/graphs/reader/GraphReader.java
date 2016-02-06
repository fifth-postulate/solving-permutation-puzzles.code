package nl.fifth.postulate.graphs.reader;

import nl.fifth.postulate.graphs.Graph;

import java.io.*;
import java.util.*;

public class GraphReader {

    public static Graph readFrom(File file) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }

        List<String> lines = new ArrayList<String>();
        try {
            for (String line; (line = reader.readLine()) != null;) {
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        GraphReader graphReader = new GraphReader(lines);

        return graphReader.build();
    }


    private Map<String, Set<String>> neighbours = new HashMap<String, Set<String>>();

    private GraphReader(List<String> lines){
        parse(lines);
    }

    private void parse(List<String> lines) {
        for (String line: lines) {
            parse(line);
        }
    }

    private void parse(String line) {
        String[] parts = line.split(":");
        String from = parts[0];
        String[] adjacents = parts[1].split(",");
        Set<String> neighbours = new HashSet<String>();
        for (String to: adjacents) {
            neighbours.add(to);
        }
        this.neighbours.put(from, neighbours);
    }

    private Graph build() {
        Graph graph = new Graph();
        for(String from: neighbours.keySet()) {
                graph.addVertex(from);
        }
        for (Map.Entry<String, Set<String>> entry: neighbours.entrySet()) {
            String from = entry.getKey();
            for (String to: entry.getValue()) {
                graph.addEdge(from, to);
            }
        }
        return graph;
    }

}
