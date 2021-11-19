# Logstash vs Filebeat 차이점

Logstash는 성능 이슈가 있다.
- JVM을 실행해야 하며, Ruby의 구현과 결합 된 종속상이 메모리 소비를 크게 증가시킨다.
- 파일 로깅 시에는 Filebeat와 Logstash를 조합하여 사용할 필요가 있다.
