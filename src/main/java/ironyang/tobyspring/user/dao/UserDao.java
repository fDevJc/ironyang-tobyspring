package ironyang.tobyspring.user.dao;

import ironyang.tobyspring.user.domain.Users;

import java.sql.*;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao() {
        this.connectionMaker = new SimpleConnectionMaker();
    }

    public void add(Users users) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) values(?,?,?)"
        );
        ps.setLong(1, users.getId());
        ps.setString(2, users.getName());
        ps.setString(3, users.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public Users get(Long id) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?"
        );

        ps.setLong(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        Users user = new Users();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return user;
    }
}
