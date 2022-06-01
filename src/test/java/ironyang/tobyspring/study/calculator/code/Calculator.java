package ironyang.tobyspring.study.calculator.code;

import java.io.IOException;

public class Calculator {
    private String path;
    private BufferedFileReader bfr;

    public Calculator(String path) {
        this.path = path;
        bfr = new BufferedFileReader();
    }

    public int sum() throws IOException {
        return bfr.byPath(path, 0, (result, line) -> result + Integer.parseInt(line));
    }

    public int multiple() throws IOException {
        return bfr.byPath(path, 1, (result, line) -> result * Integer.parseInt(line));
    }
}
