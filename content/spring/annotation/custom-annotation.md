# Custom Annotation
직접 커스텀해서 어노테이션을 만든다.

```java
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface WebCrawlerValidation {
    }
```
1. @interface 를 추가하여 어노테이션으로 선언한다.
2. @Target: 어노테이션이 생성될 수 있는 위치를 지정한다.
3. @Retention: 어노테이션이 언제까지 유효할 지 정한다.

### @Target
|타겟|설명|
|:---|:---|
| PACKAGE| 패키지 선언 시    |
| TYPE| 타입(클래스, 인터페이스, ENUM) 선언 시|
| CONSTRUCTOR| 생성자 선언 시|
| FIELD| enum 상수를 포함한 멤버변수 선언 시|
| METHOD| 메서드 선언 시|
| ANNOTATION_TYPE| 어노테이션 타입 선언 시|
| LOCAL_VARIABLE| 지역변수 선언 시|
| PARAMETER| 파라미터 선언 시|
| TYPE_PARAMETER| 파라미터 타입 선언 시|


### @Retention
|보유 기간|설명|
|:---|:---| 
|RUNTIME| 컴파일 이후에도 참조 가능 |
|CLASS| 클래스를 참조할 때 까지 유효 |
|SOURCE| 컴파일 이후 어노테이션 정보 소멸| 