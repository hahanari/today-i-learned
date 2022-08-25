package spring.toby.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import spring.toby.user.dao.connection.ConnectionMaker;
import spring.toby.user.domain.User;

public class UserDao {
    private ConnectionMaker connectionMaker;

/*
    public UserDao() {
        // DConnectionMaker의 오브젝트의 레퍼런스를 UserDao의 connectionMaker 변수에 넣어서 사용하게 함
        connectionMaker = new DConnectionMaker();
    }
*/

    // Connectionmaker의 오브젝트를 전달받을 수 있도록 파라미터 추가
    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {

        // 관심1: connection
        Connection c = connectionMaker.makeConnection();

        // 관심2: SQL 문장을 담을 Statement
        PreparedStatement ps = c.prepareStatement("insert into local_db.users(id, name, password) values(?, ?, ?)");

        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        // 관심3: 리소스 반납
        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {

        Connection c = connectionMaker.makeConnection();
        PreparedStatement ps = c.prepareStatement("select * from local_db.users where id = ?");

        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();

        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();
        return user;
    }

    public void setConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }
}
