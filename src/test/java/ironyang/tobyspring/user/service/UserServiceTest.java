package ironyang.tobyspring.user.service;

import ironyang.tobyspring.user.dao.DaoFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

//@ContextConfiguration(classes = {DaoFactory.class})
@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    void bean() {
        assertThat(userService).isNotNull();
    }
}