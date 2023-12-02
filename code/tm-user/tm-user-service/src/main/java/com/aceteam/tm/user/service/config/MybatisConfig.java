package com.aceteam.tm.user.service.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @description: some desc
 * @author: haoran
 */
@Configuration
//@ComponentScan(basePackages = {
//        "com.liang.bbs.user.persistence",
//        "com.liang.bbs.user.service.mapstruct"
//})
@ComponentScan(basePackages = {
        "com.aceteam.tm.user.persistence",
        "com.aceteam.tm.user.service.mapstruct"
})
@MapperScan(basePackages = {"com.aceteam.tm.user.persistence.mapper"})

public class MybatisConfig {
}
