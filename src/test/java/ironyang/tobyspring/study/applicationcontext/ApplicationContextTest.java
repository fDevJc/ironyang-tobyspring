package ironyang.tobyspring.study.applicationcontext;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ApplicationContextTest {
    @Autowired ApplicationContext applicationContext;

    static ApplicationContext applicationContextObject;

    @Test
    void applicationContextSameInstanceTest1() {
        assertThat(applicationContextObject == null || applicationContextObject == applicationContext).isTrue();
        applicationContextObject = applicationContext;
    }

    @Test
    void applicationContextSameInstanceTest2() {
        assertThat(applicationContextObject == null || applicationContextObject == applicationContext).isTrue();
        applicationContextObject = applicationContext;
    }

    @Test
    void applicationContextSameInstanceTest3() {
        assertThat(applicationContextObject == null || applicationContextObject == applicationContext).isTrue();
        applicationContextObject = applicationContext;
    }
}
