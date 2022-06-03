package ironyang.tobyspring.user.dao;

import ironyang.tobyspring.user.domain.Level;
import ironyang.tobyspring.user.domain.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoMy implements UserDao{
    private ConnectionMaker connectionMaker;
    private JdbcContext jdbcContext;

    public UserDaoMy(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
        jdbcContext = new JdbcContext(connectionMaker);
    }

    @Override
    public List<Users> getAll() throws SQLException, ClassNotFoundException {
        Connection c = connectionMaker.makeConnection();
        List<Users> users = new ArrayList<>();

        PreparedStatement ps = c.prepareStatement(
                "select * from users"
        );

        ResultSet rs = ps.executeQuery();
        Users user = null;
        while (rs.next()) {
            user = new Users();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(Level.valueOf(rs.getInt("level")));
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));
            users.add(user);
        }

        rs.close();
        ps.close();
        c.close();

        if (user == null) {
            throw new IllegalArgumentException();
        }

        return users;
    }

    @Override
    public void add(final Users user) throws ClassNotFoundException, SQLException {
        String sql = "insert into users(id, name, password, level, login, recommend) values(?,?,?,?,?,?)";
        jdbcContext.executeSql(sql, user.getId(), user.getName(), user.getPassword(), user.getLevel().getValue(), user.getLogin(), user.getRecommend());
    }

    @Override
    public void deleteAll() throws SQLException, ClassNotFoundException {
        jdbcContext.executeSql("delete from users");
    }

    @Override
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
            user.setLevel(Level.valueOf(rs.getInt("level")));
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));
        }

        rs.close();
        ps.close();
        c.close();

        if (user == null) {
            throw new IllegalArgumentException();
        }

        return user;
    }

    @Override
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

    @Override
    public void update(Users user) throws SQLException, ClassNotFoundException {
        String sql = "update users" +
                " set name = ?" +
                " , password = ?" +
                " , level = ?" +
                " , login = ?" +
                " , recommend = ?" +
                " where id = ?";
        jdbcContext.executeSql(sql, user.getName(), user.getPassword(), user.getLevel().getValue(), user.getLogin(), user.getRecommend(), user.getId());
    }
}
