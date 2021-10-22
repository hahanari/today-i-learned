# Custom Exception 처리
- Custom Exception 생성
```java
public class CustomException extends RuntimeException {
	public CustomException(String message) {
		super(message);
    }
}
```
- 예외 사항 체크
```java
if (isExeption) {
    throw new CustomEsception("message");
}
```
- ExceptionHandler에서 처리
```java
@ControllerAdvice
public class ControllerExceptionHandler extends AbstractControllerExceptionHandler {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = {
			CustomException.class
	})
	public ModelAndView handleBadRequestException(Exception ex, WebRequest webRequest) {
		return sendRedirectErrorPage(HttpStatus.BAD_REQUEST);
	}
}
```

