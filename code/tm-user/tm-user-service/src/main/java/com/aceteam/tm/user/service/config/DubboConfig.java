package com.aceteam.tm.user.service.config;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @description: some desc
 * @author: haoran
 */
@Configuration
@DubboComponentScan(basePackages = {"com.aceteam.tm.user.service.impl"})
public class DubboConfig {
}
