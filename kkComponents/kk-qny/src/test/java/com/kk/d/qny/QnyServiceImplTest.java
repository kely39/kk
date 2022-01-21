package com.kk.d.qny;

import com.kk.d.qny.enums.BucketEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @Description: 内部测试类
 * @Author kk
 * @Date 10:54 2018/12/25
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationTest.class)
@Slf4j
public class QnyServiceImplTest {

    @Resource
    private QnyService qnyService;

    @Ignore
    @Test
    public void getToken() {
        String token = qnyService.getUpToken(BucketEnum.PUBLIC.getVal());
        log.info("token={}", token);
    }

    @Ignore
    @Test
    public void getUpTokenWithPfopType() {
        String token = qnyService.getUpTokenWithPfopType(BucketEnum.PUBLIC.getVal(), 1);
        log.info("token={}", token);
    }

    @Ignore
    @Test
    public void getDownUrl() {
        String url = qnyService.getDownUrl("test-1008601", BucketEnum.PUBLIC.getVal(), null);
        System.out.println("url=" + url);
        log.info("url:{}", url);
    }

    @Ignore
    @Test
    public void uploadPublicLocalPath() {
        String ret = qnyService.uploadFile("D:\\ranldo.png", BucketEnum.PUBLIC.getVal(), false, "ykey-" + UUID.randomUUID());
        log.info("ret:{}" + ret);
    }

    @Ignore
    @Test
    public void uploadPublicInputStream() {
        String ret = "";
        try {
            File file = new File("D:\\ranldo.png");
            ret = qnyService.uploadFile(new FileInputStream(file), BucketEnum.PUBLIC.getVal(), false, "ykey-" + UUID.randomUUID());
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("ret:{}" + ret);
    }

    @Ignore
    @Test
    public void download() {
        qnyService.downLoad("http://test.static.public.chetailian.com/ykey-15baba1d-728f-42e3-9900-be6aa5631005", "test-ranldo_bak1.png", "D:\\");
        log.info("download success");
    }
}
