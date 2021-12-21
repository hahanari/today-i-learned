# Enum Validation Annotation
DTO의 Enum필드에 매핑된 값의 validation check하는 custom annotation을 구현한다.

### Enum에 정의된 값의 일부만 정의해서 사용
```java
@Component
public class SearchContentSortSubSetValidator implements ConstraintValidator<SearchContentSortSubSetValidation, SearchTabSortEnum> {

    private SearchTabSortEnum[] subset;

    @Override
    public void initialize(SearchContentSortSubSetValidation constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(SearchTabSortEnum value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}
```
```java
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SearchContentSortSubSetValidator.class)
public @interface SearchContentSortSubSetValidation {
    SearchTabSortEnum[] anyOf();
    String message() default "사용 가능한 정렬 조건: {anyOf}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
```
- 주의점: DTO에 `@Valid` annotation을 붙여주어야 동작한다.
  - ```java
    @RestController
    @RequiredArgsConstructor
    @RequestMapping()
    @Validated
    public class ControllerV2 {
        @GetMapping("/")
        public ResponseDTO getList(
                @Valid @ModelAttribute RequestDTO request
        ) {
            return null;
        }
    }
    ```