package ironyang.tobyspring.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class DaoFactory {
    @Bean
    public JdbcContext jdbcContext() {
        return new JdbcContext(connectionMaker());
    }

    @Bean
    public UserDao userDao() {
        return new UserDao(connectionMaker(), new JdbcContext(connectionMaker()));
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new SimpleConnectionMaker();
    }
}
