package ironyang.tobyspring.study.factorybean;

import ironyang.tobyspring.study.factorybean.code.Message;
import ironyang.tobyspring.study.factorybean.code.MessageConfig;
import ironyang.tobyspring.study.factorybean.code.MessageFactoryBean;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@Import(MessageConfig.class)
@SpringBootTest
public class FactoryBeanTest {
    @Autowired
    ApplicationContext context;

    @Test
    void getMessageBean() {
        Object message = context.getBean("message");
        Assertions.assertThat(message).isInstanceOf(Message.class);
    }
    @Test
    void getMessageFromFactoryBean() {
        Object message = context.getBean("&message");
        Assertions.assertThat(message).isInstanceOf(MessageFactoryBean.class);
    }

}
