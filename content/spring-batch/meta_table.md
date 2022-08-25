# spring batch에서는 메타 테이블이 필요하다?

spring batch를 구현하면서 만난 이슈 3가지

첫번째, spring batch가 정상 작동하려면 메타 테이블이 필요하다.

H2 DB를 사용하는 경우에는 자동으로 생성해주지만, MySQL이나 Oracle 같은 DB를 사용할 때는 개발자가 직접 메타 테이블을 생성해야 한다.

테이블 스키마는 spring batch에 존재한다.
- /org/springframework/batch/core/schema-mysql.sql


해당 파일을 찾아 스키마를 복사하여 날려주면 생성 완료~


두번째, 처음 실행에 성공하고 다시 실행했더니 실행이 제대로 되지 않는다.

BATCH_JOB_EXECUTION 테이블을 확인해봤더니, NOOP이라는 CODE가 찍혀있었다.
![img.png](img.png)

구글링 해보니,

해당 Job은 이미 실행이 완료되었다는 것! JobInstance를 고유하게 식별할 수 있기 하기 위해 JobParameter가 필요하다.

JobParameter를 줘서 실행하니 정상적으로 실행이 완료되었고 BATCH_JOB_EXECUTION 테이블에도 COMPLETE 되었다는 메세지가 남겨졌다.



세번째, 실행하면서 매번 파라미터를 변경해줘야 하니 불편하다. 다른 방법은 없을까

메타 테이블을 사용하지 않고, 스프링 배치를 구성할 수 있다는 것을 찾았고 별도의 datasource 의존 없이 ConcurrentHashMap으로 메타 데이터를 관리할 수 있다는 것!



방법1. MapJobRepositoryFactoryBean 과 ResourcelessTransactionManager를 가지고 JobRepository를 구성

public class BatchApplication extends DefaultBatchConfigurer {

    public BatchConfigurer(@Qualifier(NAME_DATASOURCE) DataSource dataSource, @Qualifier(NAME_TRANSACTION_MANAGER) PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        super.setDataSource(dataSource);
    }

    @Override
    protected JobRepository createJobRepository() throws Exception {
        MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean();
        factory.setTransactionManager(new ResourcelessTransactionManager());
        return factory.getObject();
    }
}
- MapJobRepositoryFactoryBean을 사용하면 배치 프로그램이 실행되고 있는 동안에만 해당 데이터가 유지되며, 종료한 뒤에는 없어지니 간단한 테스트 용도로는 적합
- 'org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean' is deprecated


방법2. createJobRepository() 메서드를 오버라이드 할 필요도 없이, setDataSource() 메서드를 오버라이드 하여 비워놓는다.

public class BatchSampleApplication extends DefaultBatchConfigurer {

    @Override
    public void setDataSource(DataSource dataSource) {
        // 여기를 비워놓는다
    }
}
- 초기화 과정에서 datasource를 발견하지 못하면 MapJobRepositoryFactoryBean을 사용하며, Transaction manager도 없는 경우 ResourcelessTransactionManager도 같이 사용한다.

- 다만, 이 과정을 거치기 위해서는 주입 받을 수 있는 datasource가 존재하지 않아야하며, 배치 사용을 위해 설정한 datasource가 있는 경우 setDataSource 메서드를 오버라이드 하여 아무런 동작을 하지 않게 만들어줘야 한다.



그러나, 운영환경에서는 사용하지 말라고 되어있음,,,
![img_1.png](img_1.png)

위에서 찾은 방법은 테스트 및 프로토 타이핑에 적합한 방식이며, 실제 운영 환경에서 구동해야 하는 배치인 경우 반드시 메타 테이블을 사용하는 것이 좋다고 하였다.

메타테이블을 사용하면서, jobParameter를 바꿔줄 수 있는 방법을 찾아보았다.


`RunIdIncrementer` 사용하기

spring batch에서는 동일 파라미터인데 다시 실행하고 싶을 때 사용할 수 있는 RunIdIncrementer를 제공한다.



사용 방법은,

      public Job job() {
         return jobBuilderFactory.get(JOB_NAME)
                  .start(step(null))
                  .incrementer(new RunIdIncrementer())
                  .build();
      }


그러나, spring batch 4로 버전업이 되면서 배치 잡이 실패한 경우 파라미터를 변경해도 계속 실패한 파라미터가 재사용되는 버그가 발생,,, (issue-report)

해결 방법은, 넘어온 파라미터와 run.id만 사용하도록 CustomRunIdIncrementer 클래스를 만들어 사용하는 것이다.

      import org.springframework.batch.core.JobParameters;
      import org.springframework.batch.core.JobParametersBuilder;
      import org.springframework.batch.core.launch.support.RunIdIncrementer;
      
      public class UniqueRunIdIncrementer extends RunIdIncrementer {
         private static final String RUN_ID = "run.id";
      
          @Override
          public JobParameters getNext(JobParameters parameters) {
              JobParameters params = (parameters == null) ? new JobParameters() : parameters;
              return new JobParametersBuilder()
                      .addLong(RUN_ID, params.getLong(RUN_ID, 0L) + 1)
                      .toJobParameters();
          }
      }



      public Job job() {
         return jobBuilderFactory.get(JOB_NAME)
                  .start(step(null))
                  .incrementer(new UniqueRunIdIncrementer())
                  .build();
      }
이렇게 하면 파라미터 중복 실행이 가능하다. 굿굿
