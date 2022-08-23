package spring.toby.user.dao.test;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.toby.user.dao.DaoFactory;
import spring.toby.user.dao.UserDao;
import spring.toby.user.domain.User;

public class UserDaoTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        // UserDao가 사용할 ConnectionMaker 구현 클래스를 결정하고 오브젝트를 생성한다.
        // ConnectionMaker connectionMaker = new DConnectionMaker();

        // UserDao생성, 사용할 ConnectionMaker 타입의 오브젝트 제공, 두 오브젝트 사이의 의존관계 설정
        // UserDao dao = new UserDao(connectionMaker);

        // UserDao가 어떻게 생성되는지 신경쓰지 않고 팩토리로부터 오브젝트를 받아 활용
        // UserDao dao = new DaoFactory().userDao();

        // @Bean이 붙은 메소드의 이름을 통해 오브젝트를 가져온다.
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("iris");
        user.setName("아이리스");
        user.setPassword("dkdlfltm");

        dao.add(user);
    }
}


