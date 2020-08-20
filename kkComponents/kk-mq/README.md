
## 介绍

本starter包含以下特性：

- 生产
  - [x] 同步发送
  - [x] 同步顺序发送
  - [x] 异步发送
  - [x] 异步顺序发送
  - [x] One-way方式发送
  - [ ] 事务发送

- 消费
  - [x] 并发消费（集群/广播）
  - [x] 顺序消费
  - [ ] Pull消费

- 其它特性
  - [x] 支持消息tag和key



## Quick Start

### 2、添加配置

#### 全量配置

```yaml
spring:
  application:
    name: my-application
  rocketmq:
    name-server: 127.0.0.1:9876
    producer:
      group: my-producer-group
      vip-channel-enabled: false  #是否vip通道，默认值false
      send-msg-timeout: 3000      #发送消息超时时间，单位毫秒，默认值3000
      compress-msg-body-over-howmuch: 4096   #压缩消息体的阀值，默认1024 * 4，4k，即默认大于4k的消息体将开启压缩
      retry-times-when-send-failed: 2        #在同步模式下，声明发送失败之前内部执行的最大重试次数
      retry-times-when-send-async-failed: 2  #在异步模式下，声明发送失败之前内部执行的最大重试次数
      retry-another-broker-when-not-store-ok: false  #内部发送失败时是否重试另一个broker，默认值false
      max-message-size: 4194304  #消息体最大值，单位byte，默认4Mb
```

**注意：**

- 指定多个`spring.rocketmq.name-server`以 ;分号分隔
- 如果没有配置`spring.rocketmq.producer.group` 则以`spring.application.name`作为生产组名称，如果都配置以`spring.rocketmq.producer.group`为主
- 除nameserver、producer-group以外的配置都有默认值
- yml中的配置以生产者为主，消费者的配置在@RocketMQMessageListener注解上指定


#### 启动最小配置

```yaml
spring:
  application:
    name: my-application
  rocketmq:
    name-server: 127.0.0.1:9876
```

只需要指定name-server和通过spring.application.name间接指定生产组名称即可成功启动


### 3、启动类增加注解，开启自动配置

```java
@EnableRocketMQ  //增加注解，开启RocketMQ自动配置
@SpringBootApplication
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
```


### 4、发送消息

```java
public class A{
    //注入RocketMQTemplate
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    // ..... 
    public void send(){
        rocketMQTemplate.send("test-topic-1", "test-tag-1", "Hello World");
    }
}
```

RocketMQTemplate中目前提供以下几种**发送方式**：

- 同步发送  --  send()
- 同步顺序发送  --  sendOrderly()
- 异步发送  --  asyncSend()
- 异步顺序发送  --  asyncSendOrderly()
- One-way方式发送  --  sendOneWay()

每种发送港式都有几个重载的方法，**方法参数**：

- 同步发送
  - topic（String）  --  主题
  - tag（String）  --  标签，无需指定则传null或空串
  - message（Object）  --  消息体，Object类型，如果是String类型，会按照utf-8转换为byte[]，如果是非String类型会使用fastjson转换成JSON格式的字符串后，再统一按照utf-8转换为byte[]
  - RocketMQHeader  --  RocketMQ消息头信息，可以设置keys
  - timeout（long）  --  消息发送超时时间，单位毫秒

- 顺序发送
  - selectQueueKey（String）  --  用于选择队列的key，使用默认队列选择器的SelectMessageQueueByHash
  - MessageQueueSelector  --  自定义队列选择器，需要传入实现了MessageQueueSelector接口的实例

- 异步发送
  - SendCallback  --  发送结束后的回调方法，需要传入实现了SendCallback接口的实例

RocketMQTemplate具体[请见]()

### 5、消费消息

```java
@RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "test-consumer-group-1")
public class MyConsumer1 implements RocketMQListener<String>{
    public void onMessage(String message) {
        log.info("received message: {}", message);
    }
}

@RocketMQMessageListener(topic = "test-topic-2", consumerGroup = "test-consumer-group-2")
public class MyConsumer2 implements RocketMQListener<MessageExt>{
    public void onMessage(MessageExt message) {
        log.info("received message: {}", message);
    }
}
```

上面两个例子中使用@RocketMQMessageListener注解指定了消费的topic、消费组名称

实现RocketMQListener接口，并指定接收的消息类型，可以指定为String、业务相关 或 如果需要获取RocketMQ消息的其它系统属性可指定为`com.alibaba.rocketmq.common.message.MessageExt`

在`onMessage()`方法中实现消息接收后的业务逻辑，如运行正常，无需任何返回；如发生已知或位置的错误，可以统一捕获异常并上抛，上抛异常后会再次消费



> @RocketMQMessageListener支持的相关配置[请见]()
>
> 其中 topic、consumerGroup、selectorType等字符串类型参数支持使用${}占位符的形式读取配置文件中的值
> 
> 参考：https://github.com/trust-freedom/rocketmq-spring-boot-starter
 
 
# rocketmq docker安装
```
创建目录：
mkdir -p  /opt/rocketmq/logs
mkdir -p /opt/rocketmq/store
mkdir -p /opt/rocketmq/conf

创建broker.conf文件：
$ tee /opt/rocketmq/conf/broker.conf <<-'EOF'
brokerClusterName = DefaultCluster
brokerName = broker-a
brokerId = 0
deleteWhen = 04
fileReservedTime = 48
brokerRole = ASYNC_MASTER
flushDiskType = ASYNC_FLUSH
brokerIP1=10.11.46.100
EOF

创建docker编排文件：
$ tee /opt/rocketmq/docker-compose.yml <<-'EOF'
version: '2'

services:

  namesrv:
    image: rocketmqinc/rocketmq
    container_name: rmqnamesrv
    ports:
      - 9876:9876
    volumes:
      - /opt/rocketmq/logs:/home/rocketmq/logs
      - /opt/rocketmq/store:/home/rocketmq/store
    command: sh mqnamesrv
  broker:
    image: rocketmqinc/rocketmq
    container_name: rmqbroker
    ports:
      - 10909:10909
      - 10911:10911
      - 10912:10912
    volumes:
      - /opt/rocketmq/logs:/home/rocketmq/logs
      - /opt/rocketmq/store:/home/rocketmq/store
      - /opt/rocketmq/conf/broker.conf:/opt/rocketmq-4.4.0/conf/broker.conf
    #command: sh mqbroker -n namesrv:9876
    command: sh mqbroker -n namesrv:9876 -c ../conf/broker.conf
    depends_on:
      - namesrv
    environment:
      - JAVA_HOME=/usr/lib/jvm/jre
  console:
    image: styletang/rocketmq-console-ng
    container_name: rocketmq-console-ng
    ports:
      - 8087:8080
    depends_on:
      - namesrv
    environment:
      - JAVA_OPTS= -Dlogging.level.root=info   -Drocketmq.namesrv.addr=rmqnamesrv:9876
      - Dcom.rocketmq.sendMessageWithVIPChannel=false
EOF

授权：
$ cd /opt/rocketmq
$ chmod 777 docker-compose.yml

安装启动：
$ docker-compose -f docker-compose.yml up -d
```
> https://www.cnblogs.com/goodAndyxublog/p/11457164.html
> 
> https://www.cnblogs.com/qdhxhz/p/11096682.html