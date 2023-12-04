package com.aceteam.tm.rest.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: Springboot Integration with Redisson for Distributed Locks
 * @author: haoran
 */
@Configuration
public class RedissonConfig {
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${spring.redis.timeout}")
    private Integer redisTimeout;

    /**
     * Redisson client registration (stand-alone mode)
     *
     * @return RedissonClient
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient createRedissonClient() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress("redis://" + redisHost + ":" + redisPort);
        singleServerConfig.setPassword(redisPassword);
        singleServerConfig.setTimeout(redisTimeout);

        return Redisson.create(config);
    }
}