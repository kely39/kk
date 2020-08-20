#config-server

```bash
# 单机
java -jar config-server-1.0-SNAPSHOT.jar --spring.profiles.active=native,test-native

#高可用
java -jar config-server-1.0-SNAPSHOT.jar --spring.profiles.active=native,test-native --spring.application.name=config-server-1 --server.port=8080
java -jar config-server-1.0-SNAPSHOT.jar --spring.profiles.active=native,test-native --spring.application.name=config-server-2 --server.port=8081
```

##docker启动
```bash
sudo docker run -d --name config-server -p 8080:8080 \
    -e spring.profiles.active=native,product \
    -v /usr/local/eshop/config:/usr/local/eshop/config:ro \
    -v /etc/localtime:/etc/localtime:ro \
    registry.eshop.com/eshop/config-server
```


# Spring Cloud动态刷新配置应用

## 添加依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
```
# 自动刷新配置 默认 health,info
management:
  endpoints:
    web:
      exposure:
        include: refresh,health,info
  ##是否需要权限拉去，默认是true,如果不false就不允许你去拉取配置中心Server更新的内容
  security:
    enabled: false
```
spring-boot-starter-actuator：这个模块的/refresh(POST请求)端点可以刷新配置

## 生效前提

> 修改配置后需要在各业务服务执行：[curl -X POST http://localhost:8989/actuator/refresh]()
>
> 在需要刷新的Bean上添加@RefreshScope注解。
>
> 当配置更改时，标有@RefreshScope的Bean将得到特殊处理来生效配置。
>

 *如果项目少配置少的情况可以通过/refresh来手动刷新配置，如果项目比较复杂的情况呢这种肯定是行不通的，Spring Cloud Bus消息总线可以解决配置修改的真正的动态刷新。*