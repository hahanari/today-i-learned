package spring.toby;

import java.sql.SQLException;

import spring.toby.User;
import spring.toby.UserDao;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        UserDao userDao = new UserDao();
        User user = new User();
        user.setId("iris");
        user.setName("아이리스");
        user.setPassword("dkdlfltm");

        userDao.add(user);

        System.out.println(user.getId() + " 등록 성공");

        User user2 = userDao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());

        System.out.println(user2.getId() + " 조회 성공");
    }
}
