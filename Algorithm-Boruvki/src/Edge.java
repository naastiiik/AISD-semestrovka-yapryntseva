// Модель ребра графа
public class Edge {
    private final int src; // начальная вершина
    private final int dest; // конечная вершина
    private final int weight; // вес ребра (стоимость прохождения)

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    public int getSrc() { return src; }
    public int getDest() { return dest; }
    public int getWeight() { return weight; }

    @Override
    public String toString() {
        return src + "-" + dest + "-" + weight;
    }
}