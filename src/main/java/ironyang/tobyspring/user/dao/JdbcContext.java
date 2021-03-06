package ironyang.tobyspring.user.dao;

import ironyang.tobyspring.user.dao.statement.StatementStrategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.IntStream;

public class JdbcContext {
    private ConnectionMaker connectionMaker;

    public JdbcContext(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException, ClassNotFoundException {
        try (Connection c = connectionMaker.makeConnection();
             PreparedStatement ps = stmt.makePrepareStatement(c)) {
            ps.executeUpdate();
        }
    }

    public void executeSql(String sql) throws SQLException, ClassNotFoundException {
        workWithStatementStrategy(c -> c.prepareStatement(sql));
    }

    public void executeSql(String sql, Object... args) throws SQLException, ClassNotFoundException {
        workWithStatementStrategy(c -> {
            PreparedStatement ps = c.prepareStatement(sql);
            IntStream.range(0, args.length)
                    .forEach(index -> {
                        try {
                            ps.setObject(index + 1, args[index]);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
            return ps;
        });
    }
}
