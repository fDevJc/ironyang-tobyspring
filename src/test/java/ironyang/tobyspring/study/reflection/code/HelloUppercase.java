package ironyang.tobyspring.study.reflection.code;


public class HelloUppercase implements Hello{
    private final Hello target;

    public HelloUppercase(Hello target) {
        this.target = target;
    }

    @Override
    public String sayHello(String name) {
        return target.sayHello(name).toUpperCase();
    }

    @Override
    public String sayHi(String name) {
        return target.sayHi(name).toUpperCase();
    }

    @Override
    public String sayThankYou(String name) {
        return target.sayThankYou(name).toUpperCase();
    }
}
