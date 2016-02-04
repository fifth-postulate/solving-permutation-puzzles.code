package nl.fifth.postulate.graphs;

public class Vertex {
    private final String label;

    public Vertex(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        return label.equals(vertex.label);
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }
}
