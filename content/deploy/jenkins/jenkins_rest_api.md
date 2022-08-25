# Jenkins REST API를 이용하여 Job 생성, 실행, 결과 조회하기
Jenkins Job의 수많은 기능 중, 외부에서 Job을 호출할 수 있다는 것을 알게되어서 정리하는 오늘의 TIL

1. 외부에서 호출하기 위해 Token을 발행해야 한다.
- `Jenkins menu` > `사람` > `사용자` > `token 발행 할 user 명` > `설정`
- `Add new Token`으로 Token 생성

2. REST API 사용법 
- 사용법에 대한 것은 `http://[jenkins_url]/api`에 자세히 나와 있다. (https://jenkins-search.musinsa.io/api/)

### Job 생성 [POST]
```text
http://[jenkins_url]/createItem?name=[job_name]
```
### Job 조회 [GET]
```text
http://[jenkins_url]/job/[job_name]/api/json or xml
```
### Job 빌드 수행 [POST]
```text
http://[jenkins_url]/job/[job_name]/build
```
### Job 빌드 결과 조회 [GET]
```text
http://[jenkins_url]/job/[job_name]/[build number]/api/json or xml
```

### Job 빌드 결과 조회 - 마지막 빌드 조회 [GET]
```text
http://[jenkins_url]/job/[job_name]/lastStableBuild/api/json or xml
```
