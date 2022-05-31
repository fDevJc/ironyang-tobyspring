package ironyang.tobyspring.user.dao;

import ironyang.tobyspring.user.domain.Users;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

class UserDaoTest {
    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);

        UserDao userDao = context.getBean("userDao", UserDao.class);

        userDao.deleteAll();

        assertThat(userDao.getCount()).isEqualTo(0);

        Users user = new Users();
        user.setId(1L);
        user.setName("yang");
        user.setPassword("pw");

        userDao.add(user);

        assertThat(userDao.getCount()).isEqualTo(1);

        Users foundUser = userDao.get(user.getId());

        assertThat(foundUser.getName()).isEqualTo(user.getName());
        assertThat(foundUser.getPassword()).isEqualTo(user.getPassword());
    }
}