package ironyang.tobyspring.user.dao;

public class DaoFactory {
    public UserDao userDao() {
        return new UserDao(new SimpleConnectionMaker());
    }
}