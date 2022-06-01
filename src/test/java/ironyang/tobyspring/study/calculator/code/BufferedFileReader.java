package ironyang.tobyspring.study.calculator.code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BufferedFileReader {
    public int byPath(String path, int initNumber, CalculateStrategy strategy) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))){
            int result = initNumber;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result = strategy.calculate(result, line);
            }
            return result;
        }
    }
}
