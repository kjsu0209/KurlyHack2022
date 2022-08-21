APP_NAME="chunyan"
JDK_HOME="/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.242.b08-0.el7_7.x86_64/bin/java"
APP_LOCATION="./api/chunyan"
PID_CMD="ps -ef |grep $APP_LOCATION |grep -v |awk '{print \$2}'"
VM_OPTS="-Xms512m -Xmx2048m"

info() {
  echo "=============================info=============================="
  echo "APP_LOCATION: $APP_LOCATION"
  echo "APP_NAME: $APP_NAME"
  echo "JDK_HOME: $JDK_HOME"
  echo "VM_OPTS: $VM_OPTS"
  echo "=============================info=============================="
}

status() {
  echo "=============================status=============================="
  PID=$(eval $PID_CMD)
  if [[ -n $PID ]]; then
       echo "$APP_NAME is running,PID is $PID"
  else
       echo "$APP_NAME is not running!!!"
  fi
  echo "=============================status=============================="
}

start() {
  echo "=============================start=============================="
  PID=$(eval $PID_CMD)
  if [[ -n $PID ]]; then
    echo "$APP_NAME is already running,PID is $PID"
  else
    nohup $JDK_HOME $VM_OPTS -jar $APP_LOCATION >/dev/null 2>\$1 &
    echo "nohup $JDK_HOME $VM_OPTS -jar $APP_LOCATION >/dev/null 2>\$1 &"
    PID=$(eval $PID_CMD)
    if [[ -n $PID ]]; then
       echo "Start $APP_NAME successfully,PID is $PID"
    else
       echo "Failed to start $APP_NAME !!!"
    fi
  fi
  echo "=============================start=============================="
}

stop() {
  echo "=============================stop=============================="
  PID=$(eval $PID_CMD)
  if [[ -n $PID ]]; then
    kill -15 $PID
    sleep 5
    PID=$(eval $PID_CMD)
    if [[ -n $PID ]]; then
      echo "Stop $APP_NAME failed by kill -15 $PID,begin to kill -9 $PID"
      kill -9 $PID
      sleep 2
      echo "Stop $APP_NAME successfully by kill -9 $PID"
    else
      echo "Stop $APP_NAME successfully by kill -15 $PID"
    fi
  else
    echo "$APP_NAME is not running!!!"
  fi
  echo "=============================stop=============================="
}

restart() {
  echo "=============================restart=============================="
  stop
  start
  echo "=============================restart=============================="
}

help() {
   echo "start: start server"
   echo "stop: shutdown server"
   echo "restart: restart server"
   echo "status: display status of server"
   echo "info: display info of server"
   echo "help: help info"
}