package spring.toby.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import spring.toby.user.dao.connection.ConnectionMaker;
import spring.toby.user.domain.Account;

public class AccountDao {
    private ConnectionMaker connectionMaker;

    public AccountDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(Account account) throws ClassNotFoundException, SQLException {

        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement("insert into local_db.account(id, name, password) values(?, ?, ?)");

        ps.setString(1, account.getId());
        ps.setString(2, account.getName());
        ps.setString(3, account.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }
}
