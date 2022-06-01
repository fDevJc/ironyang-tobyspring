package ironyang.tobyspring.study.calculator;

import ironyang.tobyspring.study.calculator.code.Calculator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class CalculatorTest {
    @Test
    void calcSumTest() throws IOException {
        //given
        Calculator calculator = new Calculator();

        //when
        String path = getClass().getResource("numbers.txt").getPath();
        int sum = calculator.calcSum(path);

        //then
        assertThat(sum).isEqualTo(10);
    }
}
