package ironyang.tobyspring.user.dao;

import ironyang.tobyspring.user.domain.Users;

import java.sql.SQLException;

public interface UserDao {
    void add(final Users user) throws ClassNotFoundException, SQLException;
    void deleteAll() throws SQLException, ClassNotFoundException;
    Users get(Long id) throws ClassNotFoundException, SQLException;
    int getCount() throws ClassNotFoundException, SQLException;
    void update(Users user) throws SQLException, ClassNotFoundException;
}
