package ironyang.tobyspring.user.main;

import ironyang.tobyspring.user.dao.SimpleConnectionMaker;
import ironyang.tobyspring.user.dao.UserDao;
import ironyang.tobyspring.user.domain.Users;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        UserDao userDao = new UserDao(new SimpleConnectionMaker());

        Users users = new Users();
        users.setId(1L);
        users.setName("yang");
        users.setPassword("pw");

        userDao.add(users);

        Users foundUser = userDao.get(users.getId());
        System.out.println("foundUser = " + foundUser);
    }
}
