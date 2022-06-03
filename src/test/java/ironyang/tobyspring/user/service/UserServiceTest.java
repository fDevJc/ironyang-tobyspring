package ironyang.tobyspring.user.service;

import ironyang.tobyspring.user.dao.UserDao;
import ironyang.tobyspring.user.domain.Level;
import ironyang.tobyspring.user.domain.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;

    List<Users> users;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        userDao.deleteAll();
        users = Arrays.asList(
                new Users(1L, "name1", "password1", Level.BASIC, 49, 0),
                new Users(2L, "name2", "password2", Level.BASIC, 50, 0),
                new Users(3L, "name3", "password3", Level.SILVER, 60, 29),
                new Users(4L, "name4", "password3", Level.SILVER, 60, 30),
                new Users(5L, "name5", "password3", Level.GOLD, 100, 100)
        );
    }

    @Test
    void bean() {
        assertThat(userService).isNotNull();
        assertThat(userDao).isNotNull();
    }

    @Test
    void updateLevels() throws SQLException, ClassNotFoundException {
        //given
        for (Users user : users) {
            userDao.add(user);
        }
        //when
        userService.upgradeLevels();

        //then
        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);
    }

    private void checkLevel(Users user, Level level) throws ClassNotFoundException, SQLException {
        assertThat(userDao.get(user.getId()).getLevel()).isEqualTo(level);
    }

    @Test
    void add() throws SQLException, ClassNotFoundException {
        //given
        Users userWithLevel = users.get(0);
        Users userWithoutLevel = users.get(1);
        userWithoutLevel.setLevel(null);

        //when
        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        Users foundUserWithLevel = userDao.get(userWithLevel.getId());
        Users foundUserWithoutLevel = userDao.get(userWithoutLevel.getId());

        //then
        assertThat(foundUserWithLevel.getLevel()).isEqualTo(userWithLevel.getLevel());
        assertThat(foundUserWithoutLevel.getLevel()).isEqualTo(userWithoutLevel.getLevel());

    }
}