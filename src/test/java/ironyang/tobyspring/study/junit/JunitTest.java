package ironyang.tobyspring.study.junit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class JunitTest {
    static JunitTest junitTest;
    @Test
    void notSameInstance1() {
        Assertions.assertThat(this).isNotSameAs(junitTest);
        Assertions.assertThat(this).isNotEqualTo(junitTest);
        System.out.println("junitTest = " + junitTest);
        System.out.println("this = " + this);
        junitTest = this;
    }

    @Test
    void notSameInstance2() {
        Assertions.assertThat(this).isNotSameAs(junitTest);
        Assertions.assertThat(this).isNotEqualTo(junitTest);
        System.out.println("junitTest = " + junitTest);
        System.out.println("this = " + this);
        junitTest = this;
    }

    @Test
    void notSameInstance3() {
        Assertions.assertThat(this).isNotSameAs(junitTest);
        Assertions.assertThat(this).isNotEqualTo(junitTest);
        System.out.println("junitTest = " + junitTest);
        System.out.println("this = " + this);
        junitTest = this;
    }
}
