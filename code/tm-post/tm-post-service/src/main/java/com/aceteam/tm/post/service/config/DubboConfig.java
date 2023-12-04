package com.aceteam.tm.post.service.config;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @description: Generally we need to configure @DubboComponentScan to define the scan path for @Service.
 * If you don't configure @DubboComponentScan, the package path of the class with @EnableDubbo annotation is used by default
 * @author: haoran
 */

@Configuration
@DubboComponentScan(basePackages = {"com.aceteam.tm.post.service.impl"})
public class DubboConfig {
}
