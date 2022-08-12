package spring.toby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserDao {
    public void add(User user) throws ClassNotFoundException, SQLException {

        // 관심1: connection
        Connection c = getConnection();

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

        //ㅇ메서드 추출 기법
        Connection c = getConnection();
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

    // 추상 메서드 (구현은 서브클래스가 담당)
    // 팩토리 메서드
    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;

    public class NUserDao extends UserDao {
        public Connection getConnection() throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/local_db", "iris", "iris");
        }
    }

    public class DUserDao extends UserDao {
        public Connection getConnection() throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost/local_db", "iris", "iris");
        }
    }
}
