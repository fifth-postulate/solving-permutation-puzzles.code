package nl.fifth.postulate.graphs;

import org.junit.Before;
import org.junit.Test;

import static nl.fifth.postulate.matchers.SizeMatcher.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class GraphTest {
    public static final String A_VERTEX_LABEL = "a";
    public static final String OTHER_VERTEX_LABEL = "b";
    public static final String YET_AN_OTHER_VERTEX_LABEL = "c";
    private Graph graph;

    @Before
    public void createAGraph() {
        graph = new Graph();
    }

    @Test
    public void shouldBeAbleToAddVertices() {
        graph.addVertex(A_VERTEX_LABEL);
        graph.addVertex(OTHER_VERTEX_LABEL);
        graph.addVertex(YET_AN_OTHER_VERTEX_LABEL);

        assertThat(graph.vertices(), hasSize(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotBeAbleToAddTwoVerticesWithTheSameLabel() {
        graph.addVertex(A_VERTEX_LABEL);
        graph.addVertex(A_VERTEX_LABEL);
    }

    @Test
    public void shouldFindVerticesByLabel() {
        Graph.Vertex v = graph.addVertex(A_VERTEX_LABEL);

        assertThat(graph.hasVertex(A_VERTEX_LABEL), is(true));
        assertThat(graph.findVertex(A_VERTEX_LABEL), is(equalTo(v)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotFindVerticesNotAdded() {
        graph.findVertex(A_VERTEX_LABEL);
    }

    @Test
    public void shouldAddEdgesBetweenVertices() {
        graph.addVertex(A_VERTEX_LABEL);
        graph.addVertex(OTHER_VERTEX_LABEL);

        graph.addEdge(A_VERTEX_LABEL, OTHER_VERTEX_LABEL);

        assertThat(graph.edges(), hasSize(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAddEdgesBetweenUnknownVertices() {
        graph.addEdge(A_VERTEX_LABEL, OTHER_VERTEX_LABEL);
    }

    @Test
    public void shouldFindEdgeByLabels() {
        graph.addVertex(A_VERTEX_LABEL);
        graph.addVertex(OTHER_VERTEX_LABEL);
        Edge e = graph.addEdge(A_VERTEX_LABEL, OTHER_VERTEX_LABEL);

        assertThat(graph.hasEdge(A_VERTEX_LABEL, OTHER_VERTEX_LABEL), is(true));
        assertThat(graph.findEdge(A_VERTEX_LABEL, OTHER_VERTEX_LABEL), is(equalTo(e)));

    }
}

