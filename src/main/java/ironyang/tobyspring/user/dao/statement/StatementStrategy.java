package ironyang.tobyspring.user.dao.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {
    PreparedStatement makePrepareStatement(Connection c) throws SQLException;
}
