package com.onenine.distributelock.redis;

import com.google.common.hash.Hashing;
import lombok.AllArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author li.hongjian
 * @Date 2024/11/9
 */
@RequestMapping
@RestController
@AllArgsConstructor
@SpringBootApplication
public class DistributeLockRedisApplication {

    @Autowired
    private RedissonClient redissonClient;

    public static void main(String[] args) {
        SpringApplication.run(DistributeLockRedisApplication.class, args);
    }

    @PostMapping("/post")
    public void post() throws Exception{
        // 获取锁
        RLock mylock = redissonClient.getLock("mylock");

    }


}
