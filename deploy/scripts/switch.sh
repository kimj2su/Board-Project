#!/bin/bash
echo "> 현재 구동중인 Port 확인"
CURRENT_PORT=$(curl -s http://localhost/health | egrep -o "[0-9]+" | tail -1)

echo "> Nginx에 연결되어 있지 않은 Port 찾기"
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

echo "> 전환할 Port: $IDLE_PORT"
echo "> Port 전환"
echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" |sudo tee /etc/nginx/conf.d/service-url.inc

PROXY_PORT=$(curl -s http://localhost/health | egrep -o "[0-9]+" | tail -1)
echo "> Nginx Current Proxy Port: $PROXY_PORT"

echo "> Nginx Reload"
sudo service nginx reload