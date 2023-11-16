package com.aceteam.tm.post.service.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: haoran
 */

@Configuration
@ComponentScan(basePackages =
    {"com.aceteam.tm.post.persistence",
    "com.aceteam.tm.post.service.mapstruct"})
@MapperScan(basePackages = {"com.aceteam.tm.post.persistence.mapper"})
public class MybatisConfig {
}
