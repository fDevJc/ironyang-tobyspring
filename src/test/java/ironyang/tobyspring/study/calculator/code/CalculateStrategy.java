package ironyang.tobyspring.study.calculator.code;

public interface CalculateStrategy<T> {
    T calculate(T result, String line);
}
