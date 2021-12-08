# @Cacheable

라이브러리 로드
```gradle
compile group: 'org.springframework.boot', name: 'spring-boot-starter-cache'
```
Application 클래스에 @EnableCaching 설정
```java
@EnableCaching
@SpringBootApplication
public class Application {
    
}
```
@Cacheable 메서드에 사용
```java
@Cacheable(cacheNames = CacheNameConst.BOOK,
            key = "#root.method.name + '_' + #getBookList")
public Book getBookList(String bookNo){return new Book();}
```
- cacheNames
  - cache 이름 지정 (=value)
- key
  - cache key
  - '+' 사용하여 string 조합 가능
