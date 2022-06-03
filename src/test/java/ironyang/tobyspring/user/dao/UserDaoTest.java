package ironyang.tobyspring.user.dao;

import ironyang.tobyspring.user.domain.Level;
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
//@ContextConfiguration(classes = {DaoFactory.class})
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

        user1 = new Users(1L, "name1", "password1", Level.BASIC, 1, 0);
        user2 = new Users(2L, "name2", "password2", Level.SILVER, 55, 10);
        user3 = new Users(3L, "name3", "password3", Level.GOLD, 100, 40);
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

        checkSameUser(foundUser1, user1);

        checkSameUser(foundUser2, user2);
    }

    private void checkSameUser(Users user1, Users user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
        assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
        assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
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

    @Test
    void update() throws SQLException, ClassNotFoundException {
        //given
        userDao.deleteAll();
        //when
        userDao.add(user1);
        userDao.add(user2);

        user1.setName("바뀐이름");
        user1.setPassword("바뀐비번");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);
        userDao.update(user1);
        //then
        Users user1Update = userDao.get(user1.getId());
        checkSameUser(user1, user1Update);
        Users user2Same = userDao.get(user2.getId());
        checkSameUser(user2, user2Same);
    }
}