package com.onenine.distributelock.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Description：
 *
 * @author li.hongjian
 * @email lhj502819@163.com
 * @since 2024/11/16 13:55
 */
@ConfigurationProperties(prefix = "distribution-lock-redis")
@Configuration
public class DistributionLockRedisProperties {

    private Redis redis;

    public static class Redis {
        private String url;
        private String password;
    }


}
