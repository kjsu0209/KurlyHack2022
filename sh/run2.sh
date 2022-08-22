REPOSITORY=/home/ubuntu/kurly/KurlyHack2022
PROJECT_NAME=chunyan
cd $REPOSITORY
echo "> GIT pull"
git pull origin master

cd $REPOSITORY/api/$PROJECT_NAME
echo "> Start build"
./mvnw package

cd $REPOSITORY

cp $REPOSITORY/api/$PROJECT_NAME/target/*.jar $REPOSITORY/

CURRENT_PID=$(pgrep -f ${PROJECT_NAME}*.jar)
echo "Running pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
	echo "app is running"
else
	echo "> kill -15 $CURRENT_PID"
	kill -15 $CURRENT_PID
	sleep 5
fi

echo "> deploy new app"
JAR_NAME=$(ls -tr $REPOSITORY/ | grep *.jar | tail -n 1)

echo "> JAR : $JAR_NAME"
nohup java -jar $REPOSITORY/$JAR_NAME 2<&1 &

