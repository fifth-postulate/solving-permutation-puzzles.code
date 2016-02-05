package nl.fifth.postulate.graphs;

import nl.fifth.postulate.graphs.reader.GraphReader;
import org.junit.Test;

import java.io.*;

import static nl.fifth.postulate.matchers.SizeMatcher.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GraphReaderTest {
    @Test
    public void shouldReadAGraphFromAFile() {
        Graph graph = GraphReader.readFrom(new File("src/test/resources/graph-reader.graph"));

        assertThat(graph.vertices(), hasSize(3));
        assertThat(graph.hasVertex("1"), is(true));
        assertThat(graph.hasVertex("2"), is(true));
        assertThat(graph.hasVertex("3"), is(true));

        assertThat(graph.edges(), hasSize(4));
        assertThat(graph.hasEdge("1", "2"), is(true));
        assertThat(graph.hasEdge("1", "3"), is(true));
        assertThat(graph.hasEdge("2", "1"), is(true));
        assertThat(graph.hasEdge("3", "1"), is(true));
    }
}

