# painless script

특정 필드 값의 길이를 조건으로 주어 조회한다.
```
GET index/_search
{
  "_source": "keyword", 
  "query": {
    "script": {
      "script": "doc['keyword'].value.length() > 10"
    }
  }
}
```