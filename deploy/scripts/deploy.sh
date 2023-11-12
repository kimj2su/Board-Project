#!/bin/bash
# (1) 스크립트에 필요한 변수 값 할당
BASE_PATH=/home/ubuntu/app/nonstop
BUILD_PATH=$(ls $BASE_PATH/base_jar/*.jar)
JAR_NAME=$(basename $BUILD_PATH)
echo "> (1) build 파일명: $JAR_NAME"

# (2) 빌드 된 jar 파일 jar 디렉토리로 복사
echo "> (2) build 파일 복사"
DEPLOY_PATH=$BASE_PATH/deploy/jar/
cp $BUILD_PATH $DEPLOY_PATH

## (3) 현재 구동 중인 포트 확인
echo "> (3) 현재 구동중인 포트 확인"
CURRENT_PORT=$(curl -s http://localhost/health | egrep -o "[0-9]+" | tail -1)
echo "> Current port of running WAS is ${CURRENT_PORT}."

# (4) Nginx에 연결되어 있지 않은 Port 찾기
if [ ${CURRENT_PORT} -eq 8080 ];
then
  IDLE_PORT=8081 # 현재포트가 8080이면 8081로 배포
elif [ ${CURRENT_PORT} -eq 8081 ];
then
  IDLE_PORT=8080 # 현재포트가 8081라면 8080로 배포
else
  echo "> 일치하는 Port가 없습니다. Port: $CURRENT_PORT"
  IDLE_PORT=8080
fi
echo "> Port를 할당합니다. IDLE_PORT: $IDLE_PORT"

## (5) 미연결된 jar로 신규 jar 심볼릭 링크
echo "> application.jar 교체"
IDLE_APPLICATION=board-$IDLE_PORT.jar
IDLE_APPLICATION_PATH=$DEPLOY_PATH$IDLE_APPLICATION
echo "> IDLE_APPLICATION_PATH: $IDLE_APPLICATION_PATH"

if test -f "$IDLE_APPLICATION_PATH";
then
    echo "> $IDLE_APPLICATION_PATH exists."
else
    echo "> $IDLE_APPLICATION_PATH does not exist."
fi

cp $DEPLOY_PATH$JAR_NAME $IDLE_APPLICATION_PATH

## (6) Nginx와 연결되지 않은 Profile을 종료
echo "> $IDLE_PORT 에서 구동중인 애플리케이션 pid 확인"
IDLE_PID=$(pgrep -f $IDLE_APPLICATION)
echo "> $IDLE_PID "

if [ -z $IDLE_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  kill -15 $IDLE_PID
  sleep 5
fi

### (7) (6)의 Profile로 Jar 실행
echo "> $IDLE_PORT 배포"
nohup java -jar -Dspring.profiles.active=dev -Dserver.port=$IDLE_PORT $IDLE_APPLICATION_PATH &

echo "> $IDLE_PORT 10초 후 Health check 시작"
echo "> curl -s http://localhost:$IDLE_PORT/management/health_check "
sleep 10

## (8) 아래 코드를 10회 반복 수행
# do : /health 요청 결과 저장
# if : "UP"이 문자열로 있는지 확인해서 있다면 for문 종료 없다면 메시지 출력후 아래 코드 실행
# else: 10회 다 실행될 동안 안됐다면 스크립트 종료
for retry_count in {1..10}
do
  response=$(curl -s http://localhost:$IDLE_PORT/management/health_check)
  up_count=$(echo $response | grep 'UP' | wc -l)

  if [ $up_count -ge 1 ]
  then # $up_count >= 1 ("UP" 문자열이 있는지 검증)
      echo "> Health check 성공"
      break
  else
      echo "> Health check의 응답을 알 수 없거나 혹은 status가 UP이 아닙니다."
      echo "> Health check: ${response}"
  fi

  if [ $retry_count -eq 10 ]
  then
    echo "> Health check 실패. "
    echo "> Nginx에 연결하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo "> Health check 연결 실패. 재시도..."
  sleep 10
done

echo "> 스위칭"
sleep 10
/home/ubuntu/app/nonstop/deploy/scripts/switch.sh