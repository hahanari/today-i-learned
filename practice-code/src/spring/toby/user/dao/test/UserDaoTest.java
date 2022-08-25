package spring.toby.user.dao.test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import spring.toby.user.dao.DaoFactory;
import spring.toby.user.dao.UserDao;
import spring.toby.user.domain.User;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "../applicationContext.xml")
public class UserDaoTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private UserDao dao;
    private User user;

    @Autowired
    DataSource dataSource;

    @Before
    public void setUp() {
        this.user = new User();
        this.user.setId("11");
        this.user.setName("dd");
        this.user.setPassword("adfadf");
    }

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {

        // this.dao = this.context.getBean("userDao", UserDao.class);
        dao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " 조회 성공");

        assertThat(user2.getName(), is(user.getName()));
    }
}


