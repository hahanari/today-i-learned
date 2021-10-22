# 예외처리
## @ExceptionHandler
@Controller, @RestController 가 적용된 Bean내에서 발생하는 예외를 잡아서 하나의 메서드에서 처리해주는 기능.
```java
@RestController
public class MyRestController {
	@ExceptionHandler(NullPointerException.class)
    public Object handleException(Exception e) {
		return "NullPointerException";
    }
}
```
- Controller, RestController 에만 적용 가능하다. @Service 같은 빈에서는 안된다.
- 리턴 타입은 자유롭게 해도 된다. 
- @ExceptionHandler를 등록한 Controller에만 적용된다.

## @ControllerAdvice
@ExceptionHandler가 하나의 클래스에 대한 것이라면, @ControllerAdvice는 모든 @Controller 즉, 전역에서 발생할 수 있는 예외를 잡아 처리해주는 어노테이션이다.
```java
@ControllerAdvice
public class MyAdvice {
	@ExceptionHandler(value = {BindException.class, IllegalArgumentException.class})
	public String handleBadRequestException() {
		return sendRedirectErrorPage(HttpStatus.BAD_REQUEST);
	}
}
```
- viewResolver를 통해서 예외 처리 페이지로 리다이렉트 시키려면 @ControllerAdvice만 써도 되고,
- API서버여서 에러 응답으로 객체를 리턴해야 한다면, @RestControllerAdvice를 적용하면 된다.
  - @ControllerAdvice + @ResponseBody = @RestControllerAdvice

- 전역의 예외를 잡긴 하되, 패키지 단위로 제한하려면,
    ```java
    @RestControllerAdvice("com.example.demo.controller")
    ```

----
######reference
* https://jeong-pro.tistory.com/195
* https://velog.io/@hanblueblue/Spring-ExceptionHandler