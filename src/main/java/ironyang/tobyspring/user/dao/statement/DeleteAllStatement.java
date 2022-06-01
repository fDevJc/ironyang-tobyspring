package ironyang.tobyspring.user.dao.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStatement implements StatementStrategy{
    @Override
    public PreparedStatement makePrepareStatement(Connection c) throws SQLException {
        return c.prepareStatement("delete from users");
    }
}