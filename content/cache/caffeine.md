# Caffeine cache

## 장점
- size-based eviction: 사이즈를 최초에 설정하면 딱 그만큼만 저장.
- time-based eviction: 정해진 시간만큼 caching.
    - read based: cache를 마지막으로 읽은 시간부터 특정시간
    - write based: cache에 write한 시점부터 특정시간

## 사용방법
```gradle
compile group: 'com.github.ben-manes.caffeine', name: 'caffeine', version: '2.7.0'
```
```java
public class CategoryService {

    private final CategoryService categoryService;
    
    @Cacheable(cacheNames = "categoryList")
    public List<Cagetory> categoryList(Pageable pageable) {
        return categoryRepository.getCategoryList(pageable);
    }
}
```
```java
@Getter
public enum CacheTypeEnum {

  CATEGORY_LIST("categoryList", 5 * 60, 10000);

    CacheTypeEnum(String cacheName, int expiredAfterWrite, int maximumSize) {
    this.cacheName = cacheName;
    this.expiredAfterWrite = expiredAfterWrite;
    this.maximumSize = maximumSize;
  }

  private String cacheName;
  private int expiredAfterWrite;
  private int maximumSize;
}
```
```java
@Configuration
public class CacheConfig {

  @Bean
  @Profile({"local", "devel", "alpha", "prod"})
  public CacheManager cacheManager() {
      SimpleCacheManager cacheManager = new SimpleCacheManager();
      List<CaffeineCache> caches = Arrays.stream(CacheTypeEnum.values())
              .map(cache -> new CaffeineCache(cache.getCacheName(), Caffeine.newBuilder().recordStats()
                              .expireAfterWrite(cache.getExpiredSecondAfterWrite(), TimeUnit.SECONDS)
                              .maximumSize(cache.getMaximumSize())
                              .build()
                      )
              )
              .collect(Collectors.toList());

      cacheManager.setCaches(caches);

      return cacheManager;
  }
}
```
