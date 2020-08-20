
> 每个module工程都必须继承，控制依赖jar版本
```bash    
<parent>
    <groupId>com.kk.d</groupId>
    <artifactId>kk-parent</artifactId>
    <version>1.0-SNAPSHOT</version>
</parent>
```

> 修改本地mavent的setting.xml文件新增配置项
> 根目录执行：mvn clean package deploy 发布到远程私库
```
<server>
    <id>ctl-releases</id>
    <username></username>
    <password></password>
</server>
<server>
    <id>ctl-snapshots</id>
    <username></username>
    <password></password>
</server>
```