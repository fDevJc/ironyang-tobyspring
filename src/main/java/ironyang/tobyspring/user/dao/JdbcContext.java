package ironyang.tobyspring.user.dao;

import ironyang.tobyspring.user.dao.statement.StatementStrategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcContext {
    private ConnectionMaker connectionMaker;

    public JdbcContext(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException, ClassNotFoundException {
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

}
