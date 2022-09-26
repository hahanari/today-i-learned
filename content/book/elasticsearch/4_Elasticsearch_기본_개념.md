# 1주차 (p75 ~ p107)
## 클러스터와 노드

클러스터
- Elasticsearch 프로세스를 논리적으로 결합
- 노드를 하나의 Elasticsearch처럼 동작하게 하는 것

노드
- 클러스터를 구성하는 프로세스

```text
GET _cat/nodes

 - - search-coordinating-i-
 d - unifiedsearch-group2-data4
 d - unifiedsearch-group1-data3
 - - search-coordinating-indexer
 d - unifiedsearch-group2-data1
 m - search-master2
 - - search-coordinating-i-
 d - unifiedsearch-group1-i-
 m * search-master1
 - - search-coordinating-i-
 - unifiedsearch-group2-data2
 - unifiedsearch-group2-data3
 d - unifiedsearch-group1-data4
 - - search-coordinating-i-
 m - search-master3
 d - unifiedsearch-group1-data1
 d - unifiedsearch-group2-i-
 d - unifiedsearch-group1-data2
```
- m: 3 / d: 10 / c: 4

다수의 노드로 클러스터를 구성
- 하나의 노드에 장애가 발생하는 것을 대비, 다른 노드에 요청 가능 -> 안정성
- 고유의 클러스터 이름과 UUID를 가짐 -> 2가지 속성을 통해 노드들이 클러스터링 됨
  - cluster_name, cluster_uuid

노드의 역할
- 마스터
  - 클러스터 상태 등 메타 데이터(노드의 상태, 성능 정보, 샤드 정보) 관리
  - 반드시 한대 이상으로 구성 
    - 실제로 한대만 마스터 노드역할을 수행, 나머지 마스터 노드는 마스터 후보노드임!
- 데이터
  - 문서를 실제로 저장
- 인제스트
  - 문서가 색인되기 전 전처리 작업
- 코디네이트
  - 사용자의 요청을 데이터 노드로 전달 및 결과 취합

-> 노드는 다양한 역할을 동시에 수행 가능!
> **우리는?**
> - 노드 하나가 장애났을 때를 대비하여 하나의 역할만 수행하도록 함!
> - 코디네이팅노드는 asg로 세팅되어 있음
>   - 20%, 40%, 60% 자동 증설되게 되어 있음
>   - 코디네이팅 노드 증설되면서 트래픽이 늘어 데이터 노드가 힘들어함


## 인덱스와 타입

타입
- 한개다 : _doc

인덱스
- 인덱스에 저장된 문서들은 데이터 노드에 분산 저장됨!!!
- 하나의 인덱스는 다수의 샤드로 구성


## 샤드와 세그먼트
샤드
- 인덱스에 색인되는 문서들이 저장되는 논리적인 공간
- 하나의 샤드는 다수의 세그먼트로 구성

```text
GET _cat/shards

goods-202209260609                                            2 p STARTED                  unifiedsearch-group1-data4
goods-202209260609                                            2 r STARTED                  unifiedsearch-group1-data2
goods-202209260609                                            3 r STARTED                  unifiedsearch-group1-i-
goods-202209260609                                            3 p STARTED                  unifiedsearch-group1-data2
goods-202209260609                                            1 p STARTED                  unifiedsearch-group1-data1
goods-202209260609                                            1 r STARTED                  unifiedsearch-group1-data4
goods-202209260609                                            0 p STARTED                  unifiedsearch-group1-i-
goods-202209260609                                            0 r STARTED                  unifiedsearch-group1-data3
```
- p: 4 / r: 1

세그먼트: '루씬에서 사용하는 개념'
- 샤드의 데이터를 가지고 있는 물리적인 파일
- 불변의 특성을 갖음 (기존 데이터를 업데이트하지 않는다)
  - 업데이트 시 새로운 세그먼트 생성, 기존 세그먼트는 불용 처리
    - 양이 방대해질 수 있음 -> 세그먼트 병합(merging) 진행
- 세그먼트에 역색인 구조로 저장된다
- 삭제 시 플래그 값 변경

> 인덱스에 저장되는 문서는 해시 알고리즘에 의해 샤드들에 분산 저장됨
> - 실제로는 세그먼트라는 물리적 파일에 저장
> 문서 색인 -> 시스템의 메모리 버퍼 캐시에 저장 -> refresh 이후 세그먼트 단위로 문서 저장

세그먼트 병합
- 여러개의 작은 세그먼트를 하나의 큰 세그먼트로 합치는 작업 
  - 불용처리한 데이터는 실제로 디스크에서 삭제됨
-> 꼭 좋은 것만이 아닐 수 도 있다, 분산되어 있는 것을 합치면 더 성능이 안나올 수 있다!!

## 프라이머리 샤드와 레플리카 샤드
프라이머리 샤드
- 최초 인덱스를 생성할 대 개수 결정, 이후에 변경 불가능

레플리카 샤드
- 샤드를 복제하여 데이터 안정성을 보장
- 운영중에도 변경 가능

샤드 할당
- 프라이머리 샤드에 문서 할당
- 레플리카 샤드 번호는 프라이머리 샤드 번호와 겹치지 않도록 할당 -> 데이터 유실 방지

노드 여러대 운영 장점
- 색인/검색 요청 분배 처리 가능
- 레플리카 샤드 구성 가능
-> 데이터 안정성

## 매핑
정적 매핑
- 미리 정의해두고 사용하는 것

동적 매핑(dynamic)
- 정의하지 않고 사용하는 것

정의해둔 타입의 데이터가 들어오면 색인되지 않는다!!



















