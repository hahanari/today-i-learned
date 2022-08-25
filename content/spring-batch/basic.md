# Spring batch 개념 알고 쓰기!


- Job과 Step은 일대다 관계 
- 각 Step에는 ItemReader 하나, ItemProcessor 하나, ItemWriter 하나로 구성 
- JobLauncher로 Job 시작, JobRepository에 현재 실행중인 프로세스에 대한 메타데이터 저장

### Job
- 단순히 컨테이너 Job을 실행하는 다양한 방법과 실행 중 메타데이터가 저장되는 다양한 방법이 존재

### JobConfiguration
- Job의 이름 
- Step 인스턴스의 정의 및 순서 
- Job 재시작 여부

### JobInstance
- 논리적인 Job 실행 개념

### JobParameters
- JobInstance를 구분할 수 있는 데이터 
- JobInstance = Job + JobParameters

### JobExecution
- Job을 실행하려는 단일 시도의 기술적 개념

### Step
- Job은 하나 이상의 Step으로 구성 
- Step에는 실제 일괄 처리를 정의하고 제어하는 데 필요한 모든 정보가 존재

### StepExecution
- StepExecution은 Step을 실행하려는 단일 시도 
- JobExecution과 유사하게 실행될 때마다 새로 생성됨 
- 실제로 Step이 시작될 때 생성

### ExecutionContext
- Step을 재시작 하기 위해 필요한 상태정보 등의 데이터가 포함되어 있음 
- key-value 형태로 저장 
- JobExecution당 하나가 존재하고 StepExecution당 하나가 존재, 그러나 둘은 다름

### JobRepository
- 메타 데이터와 배치 실행 계획 등이 저장되어 있음 
- JobLauncher가 JobExecution을 생성하기 위해 이용 
- 모니터링 영역에서도 사용됨 (어디까지 실행되었나, 처리되는데 걸리는 시간 등)

### JobLauncher
- 주어진 JobParameters로 JOb을 시작하기 위한 인터페이스


    public interface JobLauncher { 
        public JobExecution run(Job job, JobParameters jobParameters) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException; 
    }

### ItemReader
- 하나의 Step은 0~1개의 ItemReader를 포함하며, 데이터를 조회하는 역할 
- 제공할 수 있는 Item이 소진되면 null을 반환

### ItemProcessor
- 하나의 Step은 0~1개의 ItemProcessor를 포함하며, ItemReader를 통해 조회한 데이터를 중간에서 가공하는 역할

### ItemWriter
- 하나의 Step은 0~1개의 ItemWriter를 포함하며, ItemProcessor를 통해 가공된 데이터를 Write하는 역할
- 이름이 Writer 지만, 쓰기만 가능한 건 아님!

### Chunk
- 하나의 Transaction 안에서 처리할 Item의 덩어리

### Tasklet
- Step에서 실행되는 작업

