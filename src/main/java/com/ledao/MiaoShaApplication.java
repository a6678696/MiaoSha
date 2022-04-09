package com.ledao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author LeDao
 */
@SpringBootApplication
@MapperScan("com.ledao.mapper")
public class MiaoShaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiaoShaApplication.class, args);
    }

}
