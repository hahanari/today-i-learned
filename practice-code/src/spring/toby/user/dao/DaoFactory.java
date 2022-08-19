package spring.toby.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// UserDao의 생성 책임을 맡은 팩토리 클래스
// 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정정보라는 표시
@Configuration
public class DaoFactory {

    @Bean // 오브젝트의 생성을 담당하는 IoC용 메소드라는 표시
    public UserDao userDao() {
        // 팩토리 메소드는 UserDao 타입의 오브젝트를 어떻게 만들고, 어떻게 준비시킬지를 결정한다.
        // ConnectionMaker connectionMaker = new DConnectionMaker();
        return new UserDao(connectionMaker());
    }

    // ConnectionMaker 구현 클래스를 선정하고 생성하는 코드의 중복이 계속 발생된다.
    public AccountDao accountDao() {
        // ConnectionMaker connectionMaker = new DConnectionMaker();
        return new AccountDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }
}
