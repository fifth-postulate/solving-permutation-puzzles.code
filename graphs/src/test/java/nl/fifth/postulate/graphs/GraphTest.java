package nl.fifth.postulate.graphs;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
        Vertex v = graph.addVertex(A_VERTEX_LABEL);

        assertThat(graph.hasVertex(A_VERTEX_LABEL), is(true));
        assertThat(graph.findVertex(A_VERTEX_LABEL), is(equalTo(v)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotFindVerticesNotAdded() {
        graph.findVertex(A_VERTEX_LABEL);
    }
}

