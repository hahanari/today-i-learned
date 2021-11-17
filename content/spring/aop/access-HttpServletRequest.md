# Spring AOP 사용 시 HttpServletRequest 에 접근하기
```java
public class CustomAspect {

	public Object customAspect(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = null;

        // (Before) doing
        
		for (Object object: joinPoint.getArgs()) {
			if (object instanceof HttpServletRequest || object instanceof MultipartHttpServletRequest) {
				HttpServletRequest request = (HttpServletRequest) object;
			}
		}
		
		try {
			result = joinPoint.proceed();
		} catch (Throwable e){
			throw e;
		}
		
		// (After) doing
        
        return result;
	}
}
```
- ProceedingJoinPoint 클래스는 Target Class, Target Method 정보를 담고 있다.
- proceed 메서드 호출 시 Target Method를 실행한다.
- 보통 joinPoint.getArgs() 함수를 사용하여 Target Method의 인자 값을 확인 후 사용하지만, 매번 Controller Method에 인자로 HttpServletRequest를 넣어줘야 하는 불편함이 있다.

```java
HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
```
- 위 방법을 사용하면, AOP에서도 HttpServletRequest 객체에 접근이 가능하다.
- Controller 개발 시 인자로 HttpServletRequest를 받을 필요가 없다.

######reference
- https://blog.whitelife.co.kr/214