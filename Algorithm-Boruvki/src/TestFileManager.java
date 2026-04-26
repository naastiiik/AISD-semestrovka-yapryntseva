import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
 Класс отвечает за:
    Сохранение сгенерированных графов в файлы
    Чтение графов из файлов
    Экспорт результатов в CSV для Excel
 */
public class TestFileManager {
    private static final String TESTS_DIR = "tests_data";
    private static final String RESULTS_FILE = "results.csv";

    /*
    Метод Сохраняет каждый граф в отдельный файл tests_data/test_0.txt, test_1.txt и т.д.
    Первая строка: #номер;вершины;рёбра
    Остальные строки: src-dest-weight
     */
    public static void saveTests(List<Graph> graphs) {
        File dir = new File(TESTS_DIR);
        dir.mkdirs();

        try {
            for (int i = 0; i < graphs.size(); i++) {
                File file = new File(dir, "test_" + i + ".txt");
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
                    Graph g = graphs.get(i);
                    bw.write("#" + i + ";" + g.getVerticesCount() + ";" + g.getEdges().size());
                    bw.newLine();
                    for (Edge e : g.getEdges()) {
                        bw.write(e.getSrc() + "-" + e.getDest() + "-" + e.getWeight());
                        bw.newLine();
                    }
                }
            }
            System.out.println("Тесты сохранены в папку: " + dir.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Ошибка сохранения тестов: " + e.getMessage());
        }
    }

    // Метод читает все файлы из папки tests_data и создаёт объекты Graph
    public static List<Graph> loadTests() {
        List<Graph> graphs = new ArrayList<>();
        File dir = new File(TESTS_DIR);
        if (!dir.exists()) return graphs;

        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if (files == null) return graphs;

        // Сортируем по имени, чтобы порядок совпадал с сохранением
        java.util.Arrays.sort(files);

        for (File file : files) {
            try (BufferedReader br = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                String header = br.readLine();
                if (header == null) continue;
                String[] parts = header.split(";");
                int v = Integer.parseInt(parts[1]);

                List<Edge> edges = new ArrayList<>();
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;
                    String[] edgeData = line.split("-");
                    edges.add(new Edge(
                            Integer.parseInt(edgeData[0]),
                            Integer.parseInt(edgeData[1]),
                            Integer.parseInt(edgeData[2])
                    ));
                }
                graphs.add(new Graph(v, edges));
            } catch (IOException | NumberFormatException e) {
                System.err.println("Пропущен файл " + file.getName() + ": " + e.getMessage());
            }
        }
        System.out.println("Загружено графов: " + graphs.size());
        return graphs;
    }

    // Метод экспортирует результаты в results.csv для построения графиков.
    public static void exportResults(List<BoruvkaResult> results, List<Graph> graphs) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RESULTS_FILE, StandardCharsets.UTF_8))) {
            bw.write("GraphIndex,Vertices,Edges,MST_Weight,Time_ns,Time_ms,Iterations");
            bw.newLine();
            for (int i = 0; i < results.size(); i++) {
                BoruvkaResult r = results.get(i);
                Graph g = graphs.get(i);
                bw.write(String.format("%d;%d;%d;%d;%d;%.2f;%d",
                        i, g.getVerticesCount(), g.getEdges().size(),
                        r.mstWeight, r.timeNanos, r.timeNanos / 1_000_000.0,
                        r.iterations));
                bw.newLine();
            }
            System.out.println("Результаты экспортированы в: " + new File(RESULTS_FILE).getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Ошибка экспорта результатов: " + e.getMessage());
        }
    }
}