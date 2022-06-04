package ironyang.tobyspring.study.reflection;

import ironyang.tobyspring.study.reflection.code.Hello;
import ironyang.tobyspring.study.reflection.code.HelloTarget;
import ironyang.tobyspring.study.reflection.code.HelloUppercase;
import ironyang.tobyspring.study.reflection.code.UppercaseHandler;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.*;

public class ReflectionTest {
    @Test
    void reflectionTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String name = "yang";

        Method lengthMethod = String.class.getMethod("length");

        int length = (int) lengthMethod.invoke(name);

        assertThat(length).isEqualTo(name.length());
    }

    @Test
    void simpleProxy() {
        Hello hello = new HelloTarget();
        assertThat(hello.sayHello("yang")).isEqualTo("hello yang");
        assertThat(hello.sayHi("yang")).isEqualTo("hi yang");
        assertThat(hello.sayThankYou("yang")).isEqualTo("thank you yang");
    }

    @Test
    void simpleHelloUppercaseTest() {
        Hello hello = new HelloUppercase(new HelloTarget());
        assertThat(hello.sayHello("yang")).isEqualTo("HELLO YANG");
        assertThat(hello.sayHi("yang")).isEqualTo("HI YANG");
        assertThat(hello.sayThankYou("yang")).isEqualTo("THANK YOU YANG");
    }

    @Test
    void helloUppercaseHandlerTest() {
        Hello hello = (Hello) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{Hello.class},
                new UppercaseHandler(new HelloTarget()));

        assertThat(hello.sayHello("yang")).isEqualTo("HELLO YANG");
        assertThat(hello.sayHi("yang")).isEqualTo("HI YANG");
        assertThat(hello.sayThankYou("yang")).isEqualTo("THANK YOU YANG");
    }

}
