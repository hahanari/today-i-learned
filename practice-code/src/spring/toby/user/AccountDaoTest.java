package spring.toby.user;

import java.sql.SQLException;

import spring.toby.user.dao.AccountDao;
import spring.toby.user.dao.DaoFactory;
import spring.toby.user.dao.UserDao;
import spring.toby.user.domain.Account;
import spring.toby.user.domain.User;

public class AccountDaoTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        // UserDao가 사용할 ConnectionMaker 구현 클래스를 결정하고 오브젝트를 생성한다.
        // ConnectionMaker connectionMaker = new DConnectionMaker();

        // UserDao생성, 사용할 ConnectionMaker 타입의 오브젝트 제공, 두 오브젝트 사이의 의존관계 설정
        // UserDao dao = new UserDao(connectionMaker);

        // UserDao가 어떻게 생성되는지 신경쓰지 않고 팩토리로부터 오브젝트를 받아 활용
        AccountDao dao = new DaoFactory().accountDao();

        Account account = new Account();
        account.setId("iris");
        account.setName("아이리스");
        account.setPassword("dkdlfltm");

        dao.add(account);
    }
}


