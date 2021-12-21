# doc, _source, stored_fields, script_fields 비교
## doc
- 가장 빠름
- 한번 읽힌 정보는 memory에 cache 됨
- single term field 또는 not analyzed field에 대해서

## _source
- 매우 느림
- 매번 파싱하고 읽어 들임

## stored_fields
- field mapping 시 stored 설정이 된 field 만 사용이 가능함
- 역시 느림
- 추천하지 않음
```
GET /_search
{
    "stored_fields" : ["user", "postDate"],
    "query" : {
        "term" : { "user" : "kimchy" }
    }
}
```

## script_fields
- 데이터 후처리
```
GET /_search
{
    "query" : {
        "match_all": {}
    },
    "script_fields" : {
        "test1" : {
            "script" : {
                "lang": "painless",
                "source": "doc['price'].value * 2"
            }
        },
        "test2" : {
            "script" : {
                "lang": "painless",
                "source": "doc['price'].value * params.factor",
                "params" : {
                    "factor"  : 2.0
                }
            }
        }
    }
}
```
