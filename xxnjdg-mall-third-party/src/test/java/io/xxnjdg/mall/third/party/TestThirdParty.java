package io.xxnjdg.mall.third.party;

import com.aliyun.oss.OSS;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author xxnjdg
 * @version 1.0
 * @date 2020/6/8 1:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestThirdParty {
    @Autowired
    private OSS ossClient;

    @Test
    public void testOss() throws FileNotFoundException {
        ossClient.putObject("xxnjdgoss", "fileName.jpg",
                new FileInputStream("D:\\app\\guli\\分布式基础篇\\docs\\pics\\0d40c24b264aa511.jpg"));
        log.info("upload success");
    }
}
