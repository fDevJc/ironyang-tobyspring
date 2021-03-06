package ironyang.tobyspring.user.main;

import ironyang.tobyspring.user.dao.CountConnectionMaker;
import ironyang.tobyspring.user.dao.CountingDaoFactory;
import ironyang.tobyspring.user.dao.UserDaoMy;
import ironyang.tobyspring.user.domain.Level;
import ironyang.tobyspring.user.domain.Users;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class CountingMain {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);

        UserDaoMy userDao = context.getBean("userDao", UserDaoMy.class);

        userDao.deleteAll();

        Users users = new Users(1L, "name1", "password1", Level.BASIC, 49, 0);
        
        userDao.add(users);

        Users foundUser = userDao.get(users.getId());
        System.out.println("foundUser = " + foundUser);

        CountConnectionMaker connectionMaker = context.getBean("connectionMaker", CountConnectionMaker.class);
        System.out.println("connectionMaker.getCount() = " + connectionMaker.getCount());
    }
}
