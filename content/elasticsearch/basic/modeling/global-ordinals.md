# Global ordinals
doc values 최상위에 존재하는 데이터 구조.
- 각 유니크한 term에 대해 증가하는 넘버를 부여하는 형태로 존재한다.
- `keyword` 와 `text` 필드에 적용 가능하다.
- 실제 doc values 에는 ordinals 를 가지고 있는데, 이는 세그먼트와 필드에 유니크 넘버를 부여한 것이고, Global ordinals는 그 위에 존재함으로써 segment ordinals와 global ordinals 사이의 맵핑을 제공한다.
- segment ordinals를 사용하는 작업 (ex, terms aggregation, join) 이 일어날 때 실행시간을 단축 시키는 역할.
  - warm up으로 메모리에 로드한다.
  - 이유는, global ordinals만을 사용하여 shard level에서 aggregation을 수행하고, global ordinals 를 real term으로 변환하는 과정은 final reduce phase에서만 일어나기 때문이다.

## eager global ordinals
- global ordinals는 default로 search-time에 로드됩니다.
  - indexing speed 향상
- `eager_global_ordinals: true` 를 field의 속성으로 주면 index의 refresh time에 로드 시킵니다.
  - search speed 향상
```
PUT index
{
  "mappings": {   
    "properties": {
      "foo": {
        "type": "keyword",
        "eager_global_ordinals": true
      }
    }
  }
}
```

→ aggregation 연산시 인덱싱 때 미리 로드되어 있는 상태이기 때문에 검색 성능이 향상