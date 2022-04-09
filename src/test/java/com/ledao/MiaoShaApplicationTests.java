package com.ledao;

import cn.hutool.core.io.file.FileAppender;
import com.ledao.entity.User;
import com.ledao.rabbitmq.RabbitMQProducerService;
import com.ledao.service.UserService;
import com.ledao.util.RedisUtil;
import com.ledao.util.UUIDUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;

@SpringBootTest
class MiaoShaApplicationTests {

    @Resource
    private UserService userService;

    @Resource
    private RabbitMQProducerService rabbitMQProducerService;

    /**
     * 添加1000个用户
     */
    @Test
    void addUserAuto() {
        for (int i = 1; i <= 1000; i++) {
            User user = new User();
            user.setUserName("user" + i);
            user.setPassword("09bc717e846251b12f0801be7b7d7bf2");
            user.setName("user" + i);
            user.setRegisterDate(new Date());
            user.setAddress("贵州省 黔南布依族苗族自治州 福泉市");
            user.setPhone("18299115940");
            userService.add(user);
        }
    }

    /**
     * 1000个用户登录
     */
    @Test
    void loginAuto() {
        File file = new File("C:\\Users\\LeDao\\Desktop\\1.txt");
        for (int i = 1; i <= 1000; i++) {
            FileAppender fileAppender = new FileAppender(file, 16, true);
            User user = userService.findByUserName("user" + i);
            String token = "tk" + UUIDUtil.genUuid();
            RedisUtil.setKey(token, RedisUtil.entityToJson(user));
            RedisUtil.setKeyTime(token, (long) (60 * 60 * 24));
            fileAppender.append(user.getId() + "," + token);
            fileAppender.flush();
        }
    }

    @Test
    void sendInformation() {
        rabbitMQProducerService.sendInformation("--1--");
    }

    @Test
    void testRedis(){
        System.out.println(RedisUtil.getKeyTime("nameList"));
    }
}
