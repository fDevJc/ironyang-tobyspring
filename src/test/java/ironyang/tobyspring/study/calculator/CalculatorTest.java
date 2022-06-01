package ironyang.tobyspring.study.calculator;

import ironyang.tobyspring.study.calculator.code.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class CalculatorTest {
    private Calculator calculator;
    @BeforeEach
    void setUp() {
        calculator = new Calculator(getClass().getResource("numbers.txt").getPath());
    }

    @Test
    void calcSumTest() throws IOException {
        //when & then
        assertThat(calculator.sum()).isEqualTo(10);
    }

    @Test
    void calcMulTest() throws IOException {
        //when & then
        assertThat(calculator.multiple()).isEqualTo(24);
    }

    @Test
    void calcConcatTest() throws IOException {
        //when & then
        assertThat(calculator.concat()).isEqualTo("1234");
    }
}
