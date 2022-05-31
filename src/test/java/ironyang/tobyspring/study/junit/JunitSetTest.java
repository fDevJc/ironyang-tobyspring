package ironyang.tobyspring.study.junit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class JunitSetTest {
    static Set<JunitSetTest> junitTest = new HashSet<>();

    @Test
    void notSameInstance1() {
        System.out.println("junitTest = " + junitTest);
        assertThat(junitTest).doesNotContain(this);
        junitTest.add(this);
    }

    @Test
    void notSameInstance2() {
        System.out.println("junitTest = " + junitTest);
        assertThat(junitTest).doesNotContain(this);
        junitTest.add(this);
    }

    @Test
    void notSameInstance3() {
        System.out.println("junitTest = " + junitTest);
        assertThat(junitTest).doesNotContain(this);
        junitTest.add(this);
    }
}
