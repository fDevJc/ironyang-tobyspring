package ironyang.tobyspring.user.service;

import ironyang.tobyspring.user.dao.UserDao;
import ironyang.tobyspring.user.domain.Level;
import ironyang.tobyspring.user.domain.Users;
import ironyang.tobyspring.user.service.levelupgradepolicy.SimpleUserLevelUpgradePolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static ironyang.tobyspring.user.service.levelupgradepolicy.SimpleUserLevelUpgradePolicy.MIN_LOGIN_COUNT_FOR_SILVER;
import static ironyang.tobyspring.user.service.levelupgradepolicy.SimpleUserLevelUpgradePolicy.MIN_RECOMMEND_FOR_GOLD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
                new Users(1L, "name1", "password1", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER - 1, 0),
                new Users(2L, "name2", "password2", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER, 0),
                new Users(3L, "name3", "password3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD - 1),
                new Users(4L, "name4", "password3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD),
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
        checkLevel(users.get(0), false);
        checkLevel(users.get(1), true);
        checkLevel(users.get(2), false);
        checkLevel(users.get(3), true);
        checkLevel(users.get(4), false);
    }

    private void checkLevel(Users user, boolean upgraded) throws ClassNotFoundException, SQLException {
        Users upgradedUser = userDao.get(user.getId());
        if (upgraded) {
            assertThat(upgradedUser.getLevel()).isEqualTo(user.getLevel().getNext());
        }else {
            assertThat(upgradedUser.getLevel()).isEqualTo(user.getLevel());
        }
    }

    @Test
    void addUserLevelCheck() throws SQLException, ClassNotFoundException {
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

//    @Test
    void upgradeAllOrNothing() throws SQLException, ClassNotFoundException {
        UserServiceImpl testUserService = new UserServiceImpl(this.userDao, new TestUserLevelUpgradePolicy(users.get(3).getId()));
        for (Users user : users) {
            userDao.add(user);
        }
        assertThatThrownBy(() -> testUserService.upgradeLevels())
                .isInstanceOf(RuntimeException.class);
        for (Users user : users) {
            checkLevel(user, false);
        }
    }

    static class TestUserLevelUpgradePolicy extends SimpleUserLevelUpgradePolicy {
        private Long id;

        public TestUserLevelUpgradePolicy(Long id) {
            this.id = id;
        }

        @Override
        public void upgradeLevel(Users user) {
            if (user.getId() == this.id) {
                throw new RuntimeException();
            }
            super.upgradeLevel(user);
        }
    }
}