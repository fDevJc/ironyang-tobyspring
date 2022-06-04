package ironyang.tobyspring.user.service;

import ironyang.tobyspring.user.domain.Users;

import java.sql.SQLException;

public interface UserService {
    void upgradeLevels() throws SQLException, ClassNotFoundException;
    void add(Users user) throws SQLException, ClassNotFoundException;
}
