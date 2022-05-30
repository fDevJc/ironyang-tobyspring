package ironyang.tobyspring.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountConnectionMaker implements ConnectionMaker{
    private final ConnectionMaker connectionMaker;
    int count = 0;

    public CountConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    @Override
    public Connection makeConnection() throws SQLException, ClassNotFoundException {
        count++;
        return connectionMaker.makeConnection();
    }

    public int getCount() {
        return count;
    }
}
