package com.onenine.distributelock.mysql.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.onenine.distributelock.mysql.utils.IpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.onenine.distributelock.mysql.utils.FinalString.PROJECT_NAME;

/**
 * Description：订单服务
 *
 * @author li.hongjian
 * @email lhj502819@163.com
 * @since 2024/11/9 14:37
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements IOrderService {

    private final ICommonLockService commonLockService;

    private final int maxRetry = 5;


    public void pay(String skuNo, long userId) {
        final String threadId = String.valueOf(Thread.currentThread().getId());
        final String ipAddress = IpUtil.getIpAddress();
        final String lockKey = StrUtil.join("-", PROJECT_NAME, "pay", skuNo, userId);
        log.info("[订单服务]开始支付，{}，{}，{}，{}", lockKey, userId, threadId, ipAddress);
        int retryCount = 0;
        while (retryCount < maxRetry) {
            try {
                if (this.commonLockService.tryLock(lockKey, ipAddress, threadId)) {
                    long sleepSeconds = RandomUtil.randomLong(2, 6);
                    log.info("[订单服务]获取锁成功，支付，{}，{}，{}，{}，等待{}s", lockKey, userId, threadId, ipAddress, sleepSeconds);
                    TimeUnit.SECONDS.sleep(sleepSeconds);
                    return;
                } else {
                    retryCount++;
                    log.info("[订单服务]获取锁失败，已经重试{}次，{}，{}，{}，{}", retryCount, lockKey, userId, threadId, ipAddress);
                    // 等待1s 重新获取
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                this.commonLockService.releaseLock(lockKey, ipAddress, threadId);
            }
        }

        log.info("[订单服务]尝试获取锁超过{}次，放弃重试，{}，{}，{}，{}", maxRetry, lockKey, userId, threadId, ipAddress);

    }

}
