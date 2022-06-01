package ironyang.tobyspring.user.dao;

import ironyang.tobyspring.user.dao.statement.DeleteAllStatement;
import ironyang.tobyspring.user.dao.statement.StatementStrategy;
import ironyang.tobyspring.user.domain.Users;

import java.sql.*;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(final Users user) throws ClassNotFoundException, SQLException {
        class AddStatement implements StatementStrategy{
            @Override
            public PreparedStatement makePrepareStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
                ps.setLong(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());
                return ps;
            }
        }
        jdbcContextWithStatementStrategy(new AddStatement());
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        jdbcContextWithStatementStrategy(new DeleteAllStatement());
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

    public void deleteAll_try_with_resources() throws SQLException, ClassNotFoundException {
        try (Connection c = connectionMaker.makeConnection();
             PreparedStatement ps = c.prepareStatement("delete from users")) {
            ps.executeUpdate();
        }
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException, ClassNotFoundException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = connectionMaker.makeConnection();
            ps = stmt.makePrepareStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }finally {
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


    public int getCount_try_with_resources() throws ClassNotFoundException, SQLException {
        try (Connection c = connectionMaker.makeConnection();
             PreparedStatement ps = c.prepareStatement("select count(*) from users");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            int count = rs.getInt(1);
            return count;
        }
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
