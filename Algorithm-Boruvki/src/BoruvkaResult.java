// Вспомогательный класс. Хранит результат работы алгоритма
class BoruvkaResult {
    final int mstWeight; // вес минимального остова
    final long timeNanos; // время работы в наносекундах
    final int iterations; // количество итераций

    BoruvkaResult(int mstWeight, long timeNanos, int iterations) {
        this.mstWeight = mstWeight;
        this.timeNanos = timeNanos;
        this.iterations = iterations;
    }
}
