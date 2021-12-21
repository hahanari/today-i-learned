# Filebeat + Logstash + Slack 을 이용한 에러로그 알림
## Filebeat, Logstash 설치

```bash
/home/nari/apps/filebeat
/home/nari/apps/logstash
```


## Flow

1. 에러 로그 발생
2. filebeat input(prospector) listen
3. filebeat output
4. logstash beat input listen
5. logstash http output (slack)
6. slack alarm

## Filebeat 설정

- download
    - [https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-6.3.2-linux-x86_64.tar.gz](https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-6.3.2-linux-x86_64.tar.gz)
- config 설정

    ```yaml
    #errorlog.yml
    
    filebeat:
      prospectors:
        -
          paths:
                    - /var/log/application/api_v2/*.log
                    - /var/log/application/api_v2/*.php
          encoding: utf-8
          input_type: log
     
          exclude_lines: ['DEBUG', 'INFO']
          include_lines: ['(E|e)rror', 'ERROR', '(E|e)xception', 'EXCEPTION']
     
          document_type: error-log
     
          ignore_older: 5m
     
          scan_frequency: 10s
     
          multiline:
            pattern: '^[[:space:]]+|^Caused by:'
            negate: false
            match: after
            max_lines: 10
            timeout: 5s
     
          tail_files: true
     
          backoff: 1s
          max_backoff: 10s
          backoff_factor: 2
     
      registry_file: /home/nari/config/filebeat/.filebeat-errorlog
     
    output:
      logstash:
        hosts: ["XXX.XXX.XXX.XXX:XXXXX"]
     
    shipper:
     
    logging:
      to_syslog: false
      to_files: true
     
      level: warning
     
      files:
        path: /home/nari/logs/filebeat
        name: filebeat-errorlog.log
        rotateeverybytes: 10485760 # = 10MB
    ```

  수정해야 할 부분

    - prospectors.paths
    - registry_file
    - output.logstash.hosts
    - logging.files.path
    - 이외 수정이 필요한 부분

    ```bash
    # ./start.sh
    
    #!/usr/bin/env bash
     
    DIR_HOME=`dirname "$0"`
    CONFIG_FILE=$1
    ENV=$2
    if [ -z "$CONFIG_FILE" ]; then
    exit 1
    fi
     
    PATH_DATA=/home/nari/apps/filebeat/data
    PATH_LOGS=/home/nari/apps/filebeat/logs
    CONF_YML=/home/nari/apps/filebeat/$CONFIG_FILE.yml
     
    mkdir -p $PATH_DATA
    mkdir -p $PATH_LOGS
    chmod go-w $CONF_YML
     
    echo "/home/nari/apps/filebeat/filebeat --path.data $PATH_DATA --path.logs $PATH_LOGS -c $CONF_YML"
     
    /home/nari/apps/filebeat/filebeat --path.data $PATH_DATA --path.logs $PATH_LOGS -c $CONF_YML > /dev/null 2>&1 & FB_PID=$!
    echo $FB_PID > $DIR_HOME/$CONFIG_FILE.pid
    ```

    ```bash
    # ./stop.sh
    
    #!/usr/bin/env bash
    
    DIR_HOME=`dirname "$0"`
    CONFIG_FILE=$1
    if [ -z "$CONFIG_FILE" ]; then
      exit 1
    fi
     
    FB_PID=`cat $DIR_HOME/$CONFIG_FILE.pid`
     
    kill -9 $FB_PID
    echo "Filebeat Daemon($FB_PID) is killed"
    rm -f $DIR_HOME/$CONFIG_FILE.pid
    ```


수정해야 할 부분
- PATH_DATA
- PATH_LOGS

## Logstash 설정

- download
    - [https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-6.3.2-linux-x86_64.tar.gz](https://artifacts.elastic.co/downloads/logstash/logstash-6.3.2.tar.gz)
- config 설정

    ```
    #errorlog.conf
    
    input {
      beats {
       port => 포트를넣으세요.
      }
    }
     
    output {
      http {
        url => "https://slack.com/api/chat.postMessage"
        content_type => "application/json"
        http_method => "post"
        format => "json"
        mapping => [ "channel", "채널명을넣으세요.", "text", "%{message}" ]
        headers => ["Authorization", "Bearer xoxb-API토큰을넣으세요."]
      }
    }
    ```

  수정해야 할 부분

    - input.beats.port
    - output.http.mapping.channel
    - output.http.mapping.headers
    - 이외 수정이 필요한 부분

    ```bash
    # ./start.sh
    
    #!/bin/bash
    CONF=$1
    if [ -z "$CONF" ]; then
    exit 1
    fi
     
    export JAVA_HOME=/home/nari/apps/jdk
    export LS_HEAP_SIZE=512m
     
    LS_HOME=/home/nari/apps/logstash
    LS_DEFAULT_PATH=/home/nari/apps/logstash/config
    LS_DEFAULT_DATA_PATH=/home/nari/apps/logstash/data/errorlog
     
    # 프로세스 실행중인지 체크
    PROCESS_FILE="$LS_DEFAULT_PATH/nari-php-$CONF.pid"
     
    if [ -f "${PROCESS_FILE}" ]; then
    PREV_ES_PID=`cat ${PROCESS_FILE}`
     
    if [ -n "${PREV_ES_PID}" -a -e /proc/${PREV_ES_PID} ]; then
    echo "process exists"
    exit 1
    fi
    fi
     
    $LS_HOME/bin/logstash -f $LS_DEFAULT_PATH/$CONF.conf --path.data=$LS_DEFAULT_DATA_PATH/$CONF -l /home/nari/apps/logstash/logs/$CONF --config.reload.automatic --verbose > /dev/null 2>&1 & LS_PID=$!
    echo $LS_PID > $PROCESS_FILE
    ```

    ```bash
    # ./stop.sh
    
    #!/bin/bash
    CONF=$1
     
    if [ -z "$CONF" ]; then
      exit 1
    fi
     
    set -e
     
    LS_DEFAULT_PATH=/home/nari/apps/logstash/config
     
    if [ -a  $LS_DEFAULT_PATH/$CONF.pid ]; then
      LS_PID=`cat $LS_DEFAULT_PATH/$CONF.pid`
     
      kill -9 $LS_PID
      echo "Logstash Daemon($LS_PID) is killed"
      rm -f $LS_DEFAULT_PATH/$CONF.pid
    fi
     
    PROCESS_COUNT=`ps -ef | grep nari-php-$CONF | grep -v grep | wc -l`
     
    echo $PROCESS_COUNT
     
    if [ $PROCESS_COUNT -gt 0 ]; then
      echo "kill process"
      ps -ef | grep nari-php-$CONF | grep -v grep | awk '{print $2}' | xargs kill -9
    fi
     
    exit
    ```


수정해야 할 부분
- JAVA_HOME
- LS_HOME / LS_DEFAULT_PATH / LS_DEFAULT_DATA_PATH