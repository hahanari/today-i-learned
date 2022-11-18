## node, index, shard 세팅 api

특정 노드에서 샤드를 제거하는 명령어
- 클러스터에서 특정 노드 제외시킬 때 사용
```
PUT _cluster/settings
{
    "persistent": {
        "cluster.routing.allocation.exclude._ip": "0.0.0.0,0.0.0.1"
    }
}
```

특정 인덱스의 샤드를 다른 group으로 이동시키는 명령어
```
PUT category/_settings
{
    "settings": {
        "index": {
            "routing.allocation.require.data": "group3"
        }
    }
}
```

특정 인덱스의 replica 수 변경 명령어
```
PUT category/_settings
{
    "index": {
        "number_of_replicas": 2
    }
}
```
