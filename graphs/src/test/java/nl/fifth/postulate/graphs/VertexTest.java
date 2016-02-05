package nl.fifth.postulate.graphs;

import org.junit.Test;

import java.util.Set;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class VertexTest {
    public static final String A_VERTEX_LABEL = "a";
    public static final String OTHER_VERTEX_LABEL = "b";
    public static final String YET_AN_OTHER_VERTEX_LABEL = "c";

    @Test
    public void shouldKnowItsNeighbours() {
        Graph graph = new Graph();
        Graph.Vertex vertexA = graph.addVertex(A_VERTEX_LABEL);
        Graph.Vertex vertexB = graph.addVertex(OTHER_VERTEX_LABEL);
        Graph.Vertex vertexC = graph.addVertex(YET_AN_OTHER_VERTEX_LABEL);
        graph.addEdge(A_VERTEX_LABEL, OTHER_VERTEX_LABEL);

        Set<Graph.Vertex> neighboursOfA = vertexA.neighbours();

        assertThat(neighboursOfA, hasItem(vertexB));
        assertThat(neighboursOfA, not(hasItem(vertexC)));
    }
}
