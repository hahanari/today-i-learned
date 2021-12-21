# Local 환경 setting

## ES 설치 및 구동
- https://www.elastic.co/kr/downloads/elasticsearch 에서 OS에 맞는 파일 다운로드합니다. 
- 다운로드 후 압축 해제해주세요~
- ES가 잘 실행되는지 체크합니다. 
  - ES 실행 방법!
    ```bash
    // 실행 파일 위치: elasticsearch-7.10.0/bin/elasticsearch
    
    $ cd elasticsearch-7.10.0
    $ ./bin/elasticsearch
    
    // Daemon (Background) 로 실행시
    $ ./bin/elasticsearch -d
    ```
    
  - http://localhost:9200 접속하여 아래와 같은 response가 찍히면 실행이 잘 되는 것! 확인 완료했으면 데몬 종료!
    ```json
    {
      "name": "nariui-MacBookPro.local",
      "cluster_name": "elasticsearch",
      "cluster_uuid": "OL6Yq8d0TL-GISpv5G2rAA",
      "version": {
        "number": "7.10.0",
        "build_flavor": "default",
        "build_type": "tar",
        "build_hash": "51e9d6f22758d0374a0f3f5c6e8f3a7997850f96",
        "build_date": "2020-11-09T21:30:33.964949Z",
        "build_snapshot": false,
        "lucene_version": "8.7.0",
        "minimum_wire_compatibility_version": "6.8.0",
        "minimum_index_compatibility_version": "6.0.0-beta1"
      },
      "tagline": "You Know, for Search"
    }
    ```
    
  - 개발 환경에 맞게 config 파일을 작성합니다. 
    - elasticsearch.yml 파일 열기
      ```bash
      $cd cofig
      $vi elasticsearch.yml
      ```
    - config 수정 
      - 로컬 실행은 설정 변경 없이 단일 노드로 가능, 그러나 운영에서는 안정성을 위해 최소 3개이상의 master 노드를 두어야 함 (Split Brain)

<details><summary>config 설정 값 설명</summary><div markdown="1">

```

# ======================== Elasticsearch Configuration =========================
#
# NOTE: Elasticsearch comes with reasonable defaults for most settings.
#       Before you set out to tweak and tune the configuration, make sure you
#       understand what are you trying to accomplish and the consequences.
#
# The primary way of configuring a node is via this file. This template lists
# the most important settings you may want to configure for a production cluster.
#
# Please consult the documentation for further information on configuration options:
# https://www.elastic.co/guide/en/elasticsearch/reference/index.html
#
# ---------------------------------- Cluster -----------------------------------
#
# 클러스터 명:
cluster.name: es-demo
#
# ------------------------------------ Node ------------------------------------
#
# 노드 명:
node.name: node-1
#
# 노드 속성(클러스터가 2개 이상일때 사용):
node.attr.data: group1
#
# 노드 역할 설정
# master, data, ingest, ml 모두 false로 할 경우 오직 클라이언트와 통신만 하는 역할인 코디네이트 노드로 사용 가능!
node.mater: true # 마스터 후보 노드 여부
node.data: true # 데이터 저장 여부
node.ingest: false # 데이터 전처리 작업 수행 여부
node.ml: false # 머신러닝 작업 수행 여부
#
# ----------------------------------- Paths ------------------------------------
#
# 인덱스 저장 경로 (설정 안하면 elasticsearch의 data폴더에 생성):
path.data: /elasticsearch
#
# 로그 저장 경로:
path.logs: /var/log/elasticsearch
#
# 스냅샷을 위한 백업 경로:
path.repo: /elasticsearch/backup
#
# ----------------------------------- Memory -----------------------------------
#
# ES가 사용중인 힙메모리 영역을 다른 자바 프로그램이 간섭 못하도록 메모리 잠금 설정:
bootstrap.memory_lock: true
#
# ---------------------------------- Network -----------------------------------
#
# ES가 실행되는 서버의 IP 주소 (default: 127.0.0.1), 설정시 개발모드에서 운영모드로 전환:
network.host: 192.168.0.1
#
# ES와 클라이언트가 통신하기 위한 http 포트번호 설정 (보안적으로 포트번호는 바꿔주는게 좋음!)
http.port: 9200
#
# ES 노드들끼리 통신하기 위한 tcp 포트번호 설정:
transport.port: 9300
# --------------------------------- Discovery ----------------------------------
#
# 클러스터 구성을 위해 바인딩 할 노드의 IP주소(도메인주소):
discovery.seed_hosts: ["host1", "host2"]
#
# 클러스터 실행 시 마스터 노드 후보 노드 명:
cluster.initial_master_nodes: ["node-1", "node-2"]
#
# ---------------------------------- Gateway -----------------------------------
#
# 클러스터가 recovery를 수행할 수 있는 클러스터에 바인딩 된 노드의 최소개수, (전체 마스터 후보 노드)/2+1:
gateway.recover_after_nodes: 3
#
# ---------------------------------- Various -----------------------------------
#
# 모든 인덱스 삭제 시 _all, *(wildcard) 사용 가능하도록 설정:
action.destructive_requires_name: true
```
</div></details>


