# 라이브러리를 이용하여 Jenkins REST API를 쉽게 호출하기

### 사용 할 라이브러리는?
- [jenkins-rest](https://github.com/cdancy/jenkins-rest) github를 참고하세요

### 라이브러리를 사용하기 위해서
- build.gradle에 선언해주기

```gradle
compile "com.cdancy:jenkins-rest:0.0.19:all"
```

### 사용 방법은?
- 예를들어 job의 주소가 이렇다!

```text
https://jenkins-search.dev.musinsa.io/job/batch.search/job/jenkins-api-test/

endPoint: https://jenkins-search.dev.musinsa.io
folderPath: batch.search
jobName: jenkins-api-test
credentials: ${user_id}:${token}
```

1. JenkinsClient 생성하기


    private JenkinsClient getJenkinsClient() {
        return JenkinsClient.builder()
                .endPoint(endPoint)
                .credentials(credentials)
                .build();
    }


2. Jenkins job 조회하기



    public JobInfo getJenkinsJob(String folderPath, String jobName) {
        JenkinsClient client = getJenkinsClient();
        JobInfo jobInfo = client.api().jobsApi().jobInfo(folderPath, jobName);
        return jobInfo;
    }


3. Jenkins job 실행하기



    public void runJenkinsJob(String folderPath, String jobName) {
        JenkinsClient client = getJenkinsClient();
        IntegerResponse response = client.api().jobsApi().build(folderPath, jobName);
    }


4. Jenkins job 실행하기 (job parameter가 있는 경우)



    public void runJenkinsJobWithParameters(String folderPath, String jobName, Map<String, List<String>> parameters) {
        JenkinsClient client = getJenkinsClient();
        IntegerResponse response = client.api().jobsApi().buildWithParameters(folderPath, jobName, parameters);
    }


5. 사용한 JenkinsClient 반납하기



    private void closeJenkinsClient(JenkinsClient client) throws IOException {
        if (ObjectUtils.isNotEmpty(client)) {
            client.api().close();
        }
    }




