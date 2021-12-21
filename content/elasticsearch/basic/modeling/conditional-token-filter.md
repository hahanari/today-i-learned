#Conditional token filter

```
GET /_analyze
{
"tokenizer": "standard",
    "filter": [
        {
            "type": "condition",
            "filter": [ "lowercase" ],
            "script": {
            "source": "token.getTerm().length() < 5"
        }
    }],
    "text": "THE QUICK BROWN FOX"
}
```

```
PUT /palindrome_list
{
  "settings": {
    "analysis": {
      "analyzer": {
        "whitespace_reverse_first_token": {
          "tokenizer": "whitespace",
          "filter": [ "reverse_first_token" ]
        }
      },
      "filter": {
        "reverse_first_token": {
          "type": "condition",
          "filter": [ "reverse" ],
          "script": {
            "source": "token.getPosition() === 0"
          }
        }
      }
    }
  }
}
```
----

refer
- https://www.elastic.co/guide/en/elasticsearch/reference/current/analysis-condition-tokenfilter.html