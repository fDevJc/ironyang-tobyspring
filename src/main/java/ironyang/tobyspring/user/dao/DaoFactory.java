package ironyang.tobyspring.user.dao;

import ironyang.tobyspring.user.service.UserService;
import ironyang.tobyspring.user.service.UserServiceImpl;
import ironyang.tobyspring.user.service.UserServiceTx;
import ironyang.tobyspring.user.service.levelupgradepolicy.SimpleUserLevelUpgradePolicy;
import ironyang.tobyspring.user.service.levelupgradepolicy.UserLevelUpgradePolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao() {
        return new UserDaoMy(connectionMaker());
    }

    @Bean
    public UserService userService() {
        return new UserServiceTx(userServiceImpl());
    }

    @Bean
    public UserService userServiceImpl() {
        return new UserServiceImpl(userDao(), userLevelUpgradePolicy());
    }

    @Bean
    public UserLevelUpgradePolicy userLevelUpgradePolicy() {
        return new SimpleUserLevelUpgradePolicy();
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new SimpleConnectionMaker();
    }
}
