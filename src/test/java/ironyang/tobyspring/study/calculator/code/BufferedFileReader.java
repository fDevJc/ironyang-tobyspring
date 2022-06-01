package ironyang.tobyspring.study.calculator.code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BufferedFileReader {
    public <T> T byPath(String path, T initNumber, CalculateStrategy<T> strategy) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            T result = initNumber;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result = strategy.calculate(result, line);
            }
            return result;
        }
    }
}
