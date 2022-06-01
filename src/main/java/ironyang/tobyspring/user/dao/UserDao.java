package ironyang.tobyspring.user.dao;

import ironyang.tobyspring.user.domain.Users;

import java.sql.*;

public class UserDao {
    private ConnectionMaker connectionMaker;
    private JdbcContext jdbcContext;

    public UserDao(ConnectionMaker connectionMaker, JdbcContext jdbcContext) {
        this.connectionMaker = connectionMaker;
        this.jdbcContext = jdbcContext;
    }

    public void add(final Users user) throws ClassNotFoundException, SQLException {
        jdbcContext.workWithStatementStrategy(c -> {
            PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
            ps.setLong(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());
            return ps;
        });
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        jdbcContext.workWithStatementStrategy(c -> c.prepareStatement("delete from users"));
    }

    public Users get(Long id) throws ClassNotFoundException, SQLException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?"
        );

        ps.setLong(1, id);

        ResultSet rs = ps.executeQuery();
        Users user = null;
        if (rs.next()) {
            user = new Users();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();

        if (user == null) {
            throw new IllegalArgumentException();
        }

        return user;
    }

    public int getCount() throws ClassNotFoundException, SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = connectionMaker.makeConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count;
        } catch (Exception e) {
            throw e;
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {}
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {}
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {}
            }
        }
    }
}
