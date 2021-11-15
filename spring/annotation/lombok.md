# Lombok

## @Builder
Builder pattern 자동 추가

### @Builder.Default
- 특정 속성에 기본값을 지정할 때 사용
- @Builder 사용한 경우 build()시 @Builder.Default를 사용하지 않을 경우,
`0`/`null`/`false`로 설정된다.

## @Getter / @Setter
접근자, 설정자 자동 생성

## 생성자 자동 생성
### @NoArgsConstructor
파라미터가 없는 기본 생성자 생성
### @AllArgsConstructor
모든 필드 값을 파라미터로 받는 생성자 생성
### @RequiredArgsConstructor
`final`이나 `@NonNull`인 필드 값만 파라미터로 받는 생성자 생성

## @ToString
toString() 메소드 자동 생성
### @ToString(exclude = "특정 필드"0)
특정 필드 제외

## @Data
@Getter, @Setter, @RequiredArgsConstructor, @ToString. @EqualsAndHashCode를 한꺼번에 설정
