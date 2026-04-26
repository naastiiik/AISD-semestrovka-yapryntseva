// Структура «Система непересекающихся множеств». Отвечает за отслеживание компонент связности графа.
public class DSU {
    private final int[] parent; // родитель каждой вершины
    private final int[] rank; // ранг (глубина дерева) для оптимизации

    public DSU(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i; // изначально каждая вершина - своя собственная компонента
            rank[i] = 0;
        }
    }

    // Поиск корня
    public int find(int i) {
        if (parent[i] != i) {
            parent[i] = find(parent[i]); // рекурсивно сжимаем путь
        }
        return parent[i];
    }

    // Объединение двух множеств
    public void union(int i, int j) {
        int rootI = find(i);
        int rootJ = find(j);

        if (rootI != rootJ) {
            // Объединяем по рангу, чтобы дерево оставалось плоским
            if (rank[rootI] < rank[rootJ]) {
                parent[rootI] = rootJ;
            } else if (rank[rootI] > rank[rootJ]) {
                parent[rootJ] = rootI;
            } else {
                parent[rootJ] = rootI;
                rank[rootI]++;
            }
        }
    }
}
