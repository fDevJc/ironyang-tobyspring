package ironyang.tobyspring.user.main;

import ironyang.tobyspring.user.dao.CountingDaoFactory;
import ironyang.tobyspring.user.dao.UserDao;
import ironyang.tobyspring.user.domain.Users;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class CountingMain {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);

        UserDao userDao = context.getBean("userDao", UserDao.class);

        userDao.delete();

        Users users = new Users();
        users.setId(1L);
        users.setName("yang");
        users.setPassword("pw");
        ;
        userDao.add(users);

        Users foundUser = userDao.get(users.getId());
        System.out.println("foundUser = " + foundUser);
    }
}
