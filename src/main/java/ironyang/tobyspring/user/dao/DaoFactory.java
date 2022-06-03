package ironyang.tobyspring.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao() {
        return new UserDaoMy(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new SimpleConnectionMaker();
    }
}
