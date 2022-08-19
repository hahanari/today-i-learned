# AOP
관점 지향 프로그래밍; Aspect oriented Programming

- 어떤 로직을 기준으로 핵심적인 관점, 부가적인 관점으로 나누어서 보고 그 관점을 기준으로 각각 모듈화 하겠다.
- 흩어진 관심사를 Aspect로 모듈화하고 핵심적인 비즈니스 로직에서 분리하여 재사용 하겠다.

## AOP 주요 개념
- Aspect: 흩어진 관심사를 모듈화 한 것. 주로 부가기능을 모듈화 함.
- Target: Aspect를 적용하는 곳 (Class, Method...)
- Advice: 실질적으로 어떤 일을 해야할 지 에 대한 것, 실질적인 부가기능을 담은 구현체.
- JoinPoint: Advice가 적용될 위치. 끼어들 수 있는 지점. 메소드 진입 지점, 생성자 호출 시점, 필드에서 값을 꺼내올 때 등 다양한 시점에 적용 가능
- PointCut: JoinPoint의 상세한 스펙을 정의한 것. ('A란 메소드의 진입 시점에 호출 할 것' 과 같이 더욱 구체적으로 Advice가 실행될 지점을 정할 수 있음.)

## 스프링 AOP 특징
- 프록시 패턴 기반의 AOP 구현체, 프록시 객체를 쓰는 이유는 접근 제어 및 부가기능을 추가하기 위해서다.
- 스프링 빈에만 AOP를 적용 가능
- 모든 AOP 기능을 제공하는 것이 아닌, 스프링 IoC와 연동하여 엔터프라이즈 애플리케이션에서 가장 흔한 문제(중복 코드, 프록시 클래스 작성의 번거로움, 객체들 간 관계 복잡도 증가...)에 대한 해결책을 지원하는 것이 목적.

## 스프링 AOP: @AOP
스프링 @AOP를 사용하기 위해서는 다음과 같은 의존성을 추가해야 한다.
```
    implementation group: 'org.springframework.boot', name: 'spring-boot-starter-aop'
```

@Aspect 어노테이션을 붙여 이 클래스가 Aspect를 나타내는 클래스라는 것을 명시, @Component를 붙여 스프링 빈으로 등록한다.

@Around 어노테이션은 타겟 메소드를 감싸서 특정 Advice를 실행한다는 의미이다.

@Around("@annotation()"): 특정 어노테이션이 붙은 포인트에 해당 Aspect를 적용한다라고 지정할 수 있다.

### Aspect 실행 시점을 지정하는 어노테이션
- @Before: Advice 타겟 메소드가 호출되기 전에 Advice 기능을 수행.
- @After: 타겟 메소드의 결과에 관계 없이, 타겟 메소드가 완료되면 Advice 기능을 수행.
- @AfterReturning: 타겟 메소드가 성공적으로 결과값을 반환 후에 Advice 기능을 수행.
- @AfterThrowing: 타겟 메소드가 수행 중 예외를 던지게 되면 Advice 기능을 수행.
- @Around: Advice가 타겟메소드를 감싸서 타겟 메소드 호출 전과 후에 Advice 기능을 수행.

######reference
- https://engkimbs.tistory.com/746