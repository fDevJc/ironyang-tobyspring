package ironyang.tobyspring.user.dao;

import ironyang.tobyspring.user.domain.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

class UserDaoTest {
    UserDao userDao;

    @BeforeEach
    void setUp() {
        ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        userDao = context.getBean("userDao", UserDao.class);
    }

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();

        assertThat(userDao.getCount()).isEqualTo(0);

        Users user1 = new Users(1L, "yang11", "pwd11");
        Users user2 = new Users(2L, "yang22", "pwd22");

        userDao.add(user1);
        userDao.add(user2);

        assertThat(userDao.getCount()).isEqualTo(2);

        Users foundUser1 = userDao.get(user1.getId());
        Users foundUser2 = userDao.get(user2.getId());

        assertThat(foundUser1.getName()).isEqualTo(user1.getName());
        assertThat(foundUser1.getPassword()).isEqualTo(user1.getPassword());

        assertThat(foundUser2.getName()).isEqualTo(user2.getName());
        assertThat(foundUser2.getPassword()).isEqualTo(user2.getPassword());
    }

    @Test
    void count() throws SQLException, ClassNotFoundException {
        //given
        Users user1 = new Users(1L, "name1", "password1");
        Users user2 = new Users(2L, "name2", "password2");
        Users user3 = new Users(3L, "name3", "password3");

        //when && then
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);
        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);
        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);
        userDao.add(user3);
        assertThat(userDao.getCount()).isEqualTo(3);
    }

    @Test
    void getUserFailure() throws SQLException, ClassNotFoundException {
        //given
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        Long unknownId = 9999L;
        assertThatThrownBy(() -> userDao.get(unknownId))
                .isInstanceOf(IllegalArgumentException.class);
    }
}