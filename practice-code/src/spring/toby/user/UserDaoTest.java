package spring.toby.user;

import java.sql.SQLException;

import spring.toby.user.dao.ConnectionMaker;
import spring.toby.user.dao.DConnectionMaker;
import spring.toby.user.dao.UserDao;
import spring.toby.user.domain.User;

public class UserDaoTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        // UserDao가 사용할 ConnectionMaker 구현 클래스를 결정하고 오브젝트를 생성한다.
        ConnectionMaker connectionMaker = new DConnectionMaker();

        // UserDao생성, 사용할 ConnectionMaker 타입의 오브젝트 제공, 두 오브젝트 사이의 의존관계 설정
        UserDao dao = new UserDao(connectionMaker);

        User user = new User();
        user.setId("iris");
        user.setName("아이리스");
        user.setPassword("dkdlfltm");

        dao.add(user);
    }
}


