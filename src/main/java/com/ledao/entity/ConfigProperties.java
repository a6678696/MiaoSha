package com.ledao.entity;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置文件属性实体类
 *
 * @author LeDao
 * @company
 * @create 2022-04-02 21:31
 */
@Data
@Component
public class ConfigProperties {

    /**
     * Redis的IP地址
     */
    @Value("${spring.redis.host}")
    private String redisHost;
    /**
     * Redis的IP地址对应的接口
     */
    @Value("${spring.redis.port}")
    private String redisPort;
    /**
     * Redis的连接密码
     */
    @Value("${spring.redis.password}")
    private String redisPassword;
}
