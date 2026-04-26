import java.util.List;

public class Main {
    private static final int TESTS_COUNT = 100;
    private static final int MIN_VERTICES = 100;
    private static final int MAX_VERTICES = 10000;

    public static void main(String[] args) {
        System.out.println("Запуск семестровой работы: Алгоритм Борувки");

        // 1. Генерация или загрузка тестов
        boolean regenerate = true; // если true — создаём новые тесты
        List<Graph> graphs;
        if (regenerate) {
            System.out.println("Генерация " + TESTS_COUNT + " тестов...");
            TestDataGenerator generator = new TestDataGenerator();
            graphs = generator.generate(TESTS_COUNT, MIN_VERTICES, MAX_VERTICES);
            TestFileManager.saveTests(graphs);
        } else {
            System.out.println("Загрузка сохранённых тестов...");
            graphs = TestFileManager.loadTests(); // читаем из файлов
        }

        if (graphs.isEmpty()) {
            System.out.println("Нет данных для запуска. Проверьте папку " + TestFileManager.class.getSimpleName());
            return;
        }

        // 2. Запуск алгоритма на всех графах
        BoruvkaAlgorithm algorithm = new BoruvkaAlgorithm();
        List<BoruvkaResult> results = new java.util.ArrayList<>();

        System.out.println("Запуск алгоритма на графах...");
        for (int i = 0; i < graphs.size(); i++) {
            Graph g = graphs.get(i);
            BoruvkaResult res = algorithm.run(g); // запуск алгоритма
            results.add(res); // сохранение результата

            // Выводим прогресс каждые 10 тестов
            if ((i + 1) % 10 == 0 || i == graphs.size() - 1) {
                System.out.printf("  Готово: %d/%d (V=%d, E=%d, Время=%.1f мс, Итераций=%d)%n",
                        i + 1, graphs.size(), g.getVerticesCount(), g.getEdges().size(),
                        res.timeNanos / 1_000_000.0, res.iterations);
            }
        }

        // 3. Экспорт результатов
        TestFileManager.exportResults(results, graphs);
        System.out.println("Работа завершена.");
    }
}