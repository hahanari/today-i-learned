# Result window is too large ?
잘 돌고 있던 ES 조회 배치에서 `query_phase_execution_exception` 에러가 났음. 확인해보니,

    Result window is too large, from + size must be less than or equal to: [10000] but was [11000]


얼른 구글링 해보니, ES 인덱스에서 너무 많은 양의 데이터를 검색해오는 경우 발생할 수 있다고,,

ES는 `index.max_result_window`라는 값을 설정 할 수 있는데, 이는 검색 시 최대 데이터 수를 지정할 때 사용하며 default값은 10000이다.

따라서, 원하는 값 만큼 늘려주면 된다.

    PUT your_index_name/_settings
    {
        "max_result_window" : 500000
    }


### 주의점

해당 값을 너무 많이 늘릴경우, 성능에 이슈가 있을 수 있다고 함!!

이유는, max_result_window 값의 개수만큼 각 샤드에서 문서를 가져와 정렬하기 때문이다.