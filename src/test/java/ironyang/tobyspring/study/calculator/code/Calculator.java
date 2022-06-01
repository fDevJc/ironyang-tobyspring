package ironyang.tobyspring.study.calculator.code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public int calculate(String path) throws IOException {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
            int sum = 0;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sum = sum(sum, Integer.parseInt(line));
            }
            return sum;
        } catch (IOException e) {
            throw e;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
    }
    private int sum(int sum, int number) {
        sum = sum + number;
        return sum;
    }
}
