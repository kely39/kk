# 使用说明
支持微信、支付宝
    
## 添加依赖
```bash
<dependency>
    <groupId>com.kk.d</groupId>
    <artifactId>kk-pay</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## properties（或yaml）文件中配置
```bash
kk.weixin.enabled=true
kk.weixin.appId=AAAAA
kk.weixin.mchId=AAAAA
kk.weixin.mchKey=AAAAA
kk.weixin.keyPath=AAAAA
kk.weixin.signType=MD5
kk.weixin.notifyUrl=www.baidu.com
kk.weixin.httpConnectionTimeout=8000
kk.weixin.httpTimeout=10000

kk.alipay.enabled=true
kk.alipay.appId=2018081461019338
kk.alipay.privateKey=AAAAA
kk.alipay.alipayPublicKey=AAAAA
kk.alipay.returnUrl=www.baidu.com
kk.alipay.notifyUrl=www.baidu.com
```

## 具体调用参考单元测试类

### 微信：CtlWxAbstractPayServiceTest
 微信退款时需要将证书放到resourses一级目录下（微信商户平台->账户中心->API安全->[下载证书](https://pay.weixin.qq.com/index.php/core/cert/api_cert)）[证书使用说明](https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3)

### 支付宝：CtlAliAbstractPayServiceTest

## 官方API
[微信](https://github.com/binarywang/weixin-java-pay-demo) | [支付宝](https://docs.open.alipay.com/api_1/alipay.trade.precreate)


## 问题解决
* java.lang.NoClassDefFoundError: com/alipay/api/AlipayClient
```
## 部署本地包
mvn install:install-file -DgroupId=com.alipay -DartifactId=alipay-trade-sdk -Dversion=20161215 -Dpackaging=jar -Dfile=D:\workspace\kk\kkComponents\kk-pay\src\main\resources\lib\alipay-trade-sdk-20161215.jar

## 部署远程包
<repository>
    <id>nexus-snapshots</id>
    <url>http://ip:port/content/repositories/releases</url>
</repository>
<snapshotRepository>
    <id>nexus-releases</id>
    <url>http://ip:port/content/repositories/snapshots</url>
</snapshotRepository>
maven setting.xml配置：
<server>  
   <id>nexus</id>  
   <username>xxxxx</username>  
   <password>xxxxx</password>
</server>
并执行命令: mvn clean deploy
```