package com.aceteam.tm.post.service.config;

import com.aceteam.tm.common.config.RestTemplateConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @description: some desc
 * @author: haoran
 */

@ImportAutoConfiguration(value = {RestTemplateConfig.class})
@Configuration
public class CommonConfig {
}
