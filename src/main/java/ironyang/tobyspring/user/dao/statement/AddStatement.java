package ironyang.tobyspring.user.dao.statement;

import ironyang.tobyspring.user.domain.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStatement implements StatementStrategy{
    private Users user;

    public AddStatement(Users user) {
        this.user = user;
    }

    @Override
    public PreparedStatement makePrepareStatement(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
        ps.setLong(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());
        return ps;
    }
}
