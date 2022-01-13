# suggest
## Term suggest
편집거리를 이용해 비슷한 단어를 제안
- 편집 거리란: 하나의 문자열을 다른 문자열로 바꾸는 데 필요한 편집 횟수
```
OST my-index-000001/_search
{
  "query" : {
    "match": {
      "message": "남해 독일마음"
    }
  },
  "suggest" : {
    "my-suggestion" : {
      "text" : "남해 독일마음",
      "term" : {
        "field" : "name"
      }
    }
  }
}
```
