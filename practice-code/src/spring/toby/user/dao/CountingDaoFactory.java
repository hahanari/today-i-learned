package spring.toby.user.dao;

import java.sql.Connection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.toby.user.dao.connection.ConnectionMaker;
import spring.toby.user.dao.connection.CountingConnectionMaker;
import spring.toby.user.dao.connection.DConnectionMaker;

@Configuration
public class CountingDaoFactory {

    @Bean
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(realConnectionMaker());
    }

    @Bean
    public ConnectionMaker realConnectionMaker() {
        return new DConnectionMaker();
    }
}
