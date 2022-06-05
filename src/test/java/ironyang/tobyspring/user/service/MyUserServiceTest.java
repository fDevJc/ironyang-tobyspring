package ironyang.tobyspring.user.service;

import ironyang.tobyspring.user.dao.DaoFactory;
import ironyang.tobyspring.user.dao.SimpleConnectionMaker;
import ironyang.tobyspring.user.dao.UserDao;
import ironyang.tobyspring.user.dao.UserDaoMy;
import ironyang.tobyspring.user.domain.Level;
import ironyang.tobyspring.user.domain.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static ironyang.tobyspring.user.service.levelupgradepolicy.SimpleUserLevelUpgradePolicy.MIN_LOGIN_COUNT_FOR_SILVER;
import static ironyang.tobyspring.user.service.levelupgradepolicy.SimpleUserLevelUpgradePolicy.MIN_RECOMMEND_FOR_GOLD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Import({MyUserServiceTest.TestConfig.class, DaoFactory.class})
@SpringBootTest
public class MyUserServiceTest {
    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Autowired
    UserService testUserService;

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
    //TODO 테스트 통과시키기
    @Test
    void upgradeAllOrNothing() throws SQLException, ClassNotFoundException {
        for (Users user : users) {
            userDao.add(user);
        }

        System.out.println("userService.getClass() = " + userService.getClass());
        System.out.println("userService = " + userService);
        System.out.println("testUserService.getClass() = " + testUserService.getClass());
        System.out.println("testUserService = " + testUserService);

        assertThatThrownBy(() -> testUserService.upgradeLevels())
                .isInstanceOf(RuntimeException.class);
        for (Users user : users) {
            checkLevel(user, false);
        }
    }

    @Configuration
    static class TestConfig {

        @Bean
        public UserService testUserService() {
            return new UserServiceImpl(new UserDaoMy(new SimpleConnectionMaker()), new UserServiceTest.TestUserLevelUpgradePolicy(4L));
        }
    }

    private void checkLevel(Users user, boolean upgraded) throws ClassNotFoundException, SQLException {
        Users upgradedUser = userDao.get(user.getId());
        if (upgraded) {
            assertThat(upgradedUser.getLevel()).isEqualTo(user.getLevel().getNext());
        }else {
            assertThat(upgradedUser.getLevel()).isEqualTo(user.getLevel());
        }
    }
}
