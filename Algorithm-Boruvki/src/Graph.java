import java.util.List;

// Модель графа
public class Graph {
    private final int verticesCount; // количество вершин
    private final List<Edge> edges; // список ребер

    public Graph(int verticesCount, List<Edge> edges) {
        this.verticesCount = verticesCount;
        this.edges = edges;
    }

    public int getVerticesCount() { return verticesCount; }
    public List<Edge> getEdges() { return edges; }

    @Override
    public String toString() {
        return "Graph{V=" + verticesCount + ", E=" + edges.size() + "}";
    }
}
