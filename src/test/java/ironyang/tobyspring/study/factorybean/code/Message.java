package ironyang.tobyspring.study.factorybean.code;

public class Message {
    String text;

    private Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static Message getInstance(String text) {
        return new Message(text);
    }
}
