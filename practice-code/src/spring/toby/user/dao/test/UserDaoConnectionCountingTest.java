package spring.toby.user.dao.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.toby.user.dao.CountingDaoFactory;
import spring.toby.user.dao.UserDao;
import spring.toby.user.dao.connection.CountingConnectionMaker;

public class UserDaoConnectionCountingTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        CountingDaoFactory.class
                );

        UserDao dao = context.getBean("userDao", UserDao.class);

        CountingConnectionMaker ccm = context.getBean("connectionMaker",
                CountingConnectionMaker.class);

        System.out.println("Connection counter: " + ccm.getCounter());
    }
}
