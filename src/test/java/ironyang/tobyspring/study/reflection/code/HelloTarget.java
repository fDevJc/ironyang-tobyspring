package ironyang.tobyspring.study.reflection.code;

public class HelloTarget implements Hello {

    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }

    @Override
    public String sayHi(String name) {
        return "hi " + name;
    }

    @Override
    public String sayThankYou(String name) {
        return "thank you " + name;
    }

}
