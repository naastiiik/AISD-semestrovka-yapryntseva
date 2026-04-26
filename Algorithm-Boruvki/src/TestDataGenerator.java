import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Класс создаёт гарантированно связные графы случайного размера.
// Гарантирует, что у графа всегда существует остовное дерево.
public class TestDataGenerator {
    private final Random random = new Random();

    // Метод генерирует count графов с количеством вершин от minV до maxV
    public List<Graph> generate(int count, int minV, int maxV) {
        List<Graph> graphs = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            // Равномерно распределяем размеры в диапазоне
            int v = minV + (int) ((maxV - minV) * ((double) i / count));
            graphs.add(createConnectedGraph(v));
        }
        return graphs;
    }

    /*
    Создаёт гарантированно связный граф.
    Алгоритм: сначала строим случайное остовное дерево, затем добавляем случайные дополнительные рёбра.
    */
    private Graph createConnectedGraph(int vertices) {
        List<Edge> edges = new ArrayList<>();
        boolean[] visited = new boolean[vertices];
        visited[0] = true; // начинаем с вершины 0
        int visitedCount = 1;

        // 1. Строим случайное остовное дерево
        List<Integer> frontier = new ArrayList<>();
        frontier.add(0);

        while (visitedCount < vertices) {
            // Берём случайную уже посещённую вершину
            int from = frontier.get(random.nextInt(frontier.size()));

            // Находим ещё не посещённую вершину
            int to;
            do { to = random.nextInt(vertices); } while (visited[to]);

            visited[to] = true;
            visitedCount++;
            int weight = random.nextInt(1, 100);
            edges.add(new Edge(from, to, weight));
            frontier.add(to);
        }

        // 2. Добавляем случайные дополнительные рёбра
        int extraEdges = (int) (vertices * 1.5);
        for (int i = 0; i < extraEdges; i++) {
            int u = random.nextInt(vertices);
            int v = random.nextInt(vertices);
            if (u != v) {
                int weight = random.nextInt(1, 100);
                edges.add(new Edge(u, v, weight));
            }
        }

        return new Graph(vertices, edges);
    }
}