package ironyang.tobyspring.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker implements ConnectionMaker{
    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection c = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/h2/db/etc", "sa", "");
        return c;
    }

    @Override
    public Connection makeConnection() throws SQLException, ClassNotFoundException {
        return getConnection();
    }
}
