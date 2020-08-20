@echo off 
echo clean-------------package-------------install

@echo cd config-server
cd config-server
call mvn clean package install -DskipTests

@echo cd eureka-server
cd ../eureka-server
call mvn clean package install -DskipTests

echo finishd!!!!!
pause