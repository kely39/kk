@echo off 
echo clean-------------package-------------install

call mvn clean package install 

@echo cd kk-parent
cd kk-parent
call mvn clean package install 

@echo cd authorization
cd ../authorization
call mvn clean package install -DskipTests

@echo cd framework-web
cd ../framework-web
call mvn clean package install -DskipTests

@echo cd kk-pay
cd ../kk-pay
call mvn clean package install -DskipTests

@echo cd kk-util
cd ../kk-util
call mvn clean package install -DskipTests

@echo cd third-party
cd ../third-party
call mvn clean package install -DskipTests

@echo cd kk-mq
cd ../kk-mq
call mvn clean package install -DskipTests

@echo cd kk-qny
cd ../kk-qny
call mvn clean package install -DskipTests

echo finishd!!!!!
pause