package ironyang.tobyspring.user.dao;

import ironyang.tobyspring.user.domain.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ContextConfiguration(classes = {DaoFactory.class})
class UserDaoTest {
    @Autowired
    ApplicationContext context;
    @Autowired
    UserDao userDao;
    Users user1;
    Users user2;
    Users user3;

    @BeforeEach
    void setUp() {

        System.out.println("context = " + context);
        System.out.println("this = " + this);
        System.out.println("userDao = " + userDao);

        user1 = new Users(1L, "name1", "password1");
        user2 = new Users(2L, "name2", "password2");
        user3 = new Users(3L, "name3", "password3");
    }

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();

        assertThat(userDao.getCount()).isEqualTo(0);

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