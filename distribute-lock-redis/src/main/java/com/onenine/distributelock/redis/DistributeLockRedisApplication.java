package com.onenine.distributelock.redis;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author li.hongjian
 * @Date 2024/11/9
 */
@AllArgsConstructor
@SpringBootApplication
public class DistributeLockRedisApplication implements ApplicationRunner {

    private final RedisTemplate<String,Object> redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DistributeLockRedisApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        redisTemplate.opsForValue().setIfAbsent
    }
}
