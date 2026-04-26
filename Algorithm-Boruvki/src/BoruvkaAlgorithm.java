import java.util.Arrays;
import java.util.List;

// Реализация алгоритма Борувки для построения минимального остовного дерева
// Замеры времени и итераций
public class BoruvkaAlgorithm {

    // Метод запуска алгоритма
    public BoruvkaResult run(Graph graph) {
        int n = graph.getVerticesCount();
        List<Edge> edges = graph.getEdges();

        DSU dsu = new DSU(n); // создаем DSU для n вершин
        int components = n; // изначально n компонент (сколько вершин)
        int mstWeight = 0;
        int iterations = 0; // счетчик итераций

        // Массив для хранения индекса самого дешёвого ребра для каждой компоненты
        int[] cheapestEdgeIndex = new int[n];

        long startTime = System.nanoTime(); // запуск таймера (до начала алгоритма)

        // Алгоритм Борувки
        while (components > 1) {
            iterations++; // засчитываем одну итерацию внешнего цикла

            // Сбрасываем массив самых дешёвых рёбер
            Arrays.fill(cheapestEdgeIndex, -1);

            // 1. Ищем самое дешевое ребро для каждой компоненты
            for (int i = 0; i < edges.size(); i++) {
                Edge edge = edges.get(i);
                int set1 = dsu.find(edge.getSrc());
                int set2 = dsu.find(edge.getDest());

                if (set1 != set2) { // вершины в разных компонентах
                    // Обновляем cheapest для set1
                    if (cheapestEdgeIndex[set1] == -1 ||
                            edges.get(cheapestEdgeIndex[set1]).getWeight() > edge.getWeight()) {
                        cheapestEdgeIndex[set1] = i;
                    }
                    // Обновляем cheapest для set2
                    if (cheapestEdgeIndex[set2] == -1 ||
                            edges.get(cheapestEdgeIndex[set2]).getWeight() > edge.getWeight()) {
                        cheapestEdgeIndex[set2] = i;
                    }
                }
            }

            // 2. Добавляем найденные самые дешёвые рёбра в MST и объединяем компоненты
            for (int i = 0; i < n; i++) {
                if (cheapestEdgeIndex[i] != -1) {
                    Edge edge = edges.get(cheapestEdgeIndex[i]);
                    int set1 = dsu.find(edge.getSrc());
                    int set2 = dsu.find(edge.getDest());

                    // Проверяем ещё раз: могли уже объединиться в рамках этой же итерации
                    if (set1 != set2) { // еще не объединились
                        dsu.union(set1, set2); // объединяем
                        mstWeight += edge.getWeight(); // добавляем вес
                        components--; // компонент стало меньше
                    }
                }
            }
        }

        long endTime = System.nanoTime(); // Остановка таймера (после завершения алгоритма)

        return new BoruvkaResult(mstWeight, endTime - startTime, iterations);
    }
}