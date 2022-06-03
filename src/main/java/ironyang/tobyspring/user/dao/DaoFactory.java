package ironyang.tobyspring.user.dao;

import ironyang.tobyspring.user.service.UserService;
import org.apache.catalina.User;
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
        return new UserService(userDao());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new SimpleConnectionMaker();
    }
}
