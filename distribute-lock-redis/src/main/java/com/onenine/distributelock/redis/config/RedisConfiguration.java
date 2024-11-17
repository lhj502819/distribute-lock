package com.onenine.distributelock.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description：
 *
 * @author li.hongjian
 * @email lhj502819@163.com
 * @since 2024/11/16 13:54
 */
@ConditionalOnClass(Redisson.class)
@Configuration
public class RedisConfiguration {

    @Bean
    public RedissonClient redissonClient(DistributionLockRedisProperties properties){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.31.125:6379").setPassword("123456");
        return Redisson.create(config);
    }

}
