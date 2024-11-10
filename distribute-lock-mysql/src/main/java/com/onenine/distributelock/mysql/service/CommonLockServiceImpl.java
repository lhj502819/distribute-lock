package com.onenine.distributelock.mysql.service;

import cn.hutool.core.util.StrUtil;
import com.onenine.distributelock.mysql.entity.CommonLock;
import com.onenine.distributelock.mysql.repository.CommonLockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommonLockServiceImpl implements ICommonLockService {

    private final CommonLockRepository commonLockRepository;

    @Override
    public boolean tryLock(String lockKey, String clientIp, String threadId) {
//        log.info("[尝试获取分布式锁]{}，{}，{}", lockKey, clientIp, threadId);
        CommonLock commonLock = commonLockRepository.findByLockKey(lockKey);
        try {
            if (commonLock != null &&
                    (clientIp.equals(commonLock.getClientIp()) && threadId.equals(commonLock.getThreadId()))) {
//                log.info("[尝试获取分布式锁]重复获取锁，{}，{}", clientIp, threadId);
                commonLock.setEntryCount(commonLock.getEntryCount() + 1);
                commonLockRepository.save(commonLock);
                return true;
            } else if (commonLock == null) {
//                log.info("[尝试获取分布式锁]当前锁无人持有，准备获取锁，{}，{}", clientIp, threadId);
                commonLock = new CommonLock();
                commonLock.setLockKey(lockKey);
                commonLock.setClientIp(clientIp);
                commonLock.setThreadId(threadId);
                commonLock.setEntryCount(1);
                commonLockRepository.save(commonLock);
//                log.info("[尝试获取分布式锁]获取锁成功，{}，{}", clientIp, threadId);
                return true;
            }
        } catch (Exception e) {
            if (!e.getMessage().contains("Duplicate entry")) {
                throw new RuntimeException(StrUtil.format("[尝试获取分布式锁]系统异常，{}，{}", clientIp, threadId));
            }
        }
//        log.info("[尝试获取分布式锁]获取锁失败，{}，{}", clientIp, threadId);
        return false;
    }

    @Override
    public void releaseLock(String lockKey, String clientIp, String threadId) {
        log.info("[释放分布式锁]{}，{}，{}", lockKey, clientIp, threadId);
        CommonLock commonLock = commonLockRepository.findByLockKey(lockKey);
        if (commonLock != null &&
                (clientIp.equals(commonLock.getClientIp()) && threadId.equals(commonLock.getThreadId()))) {
            if (commonLock.getEntryCount() > 1){
                log.info("[释放分布式锁]释放锁，重入次数减一，{}，{}", clientIp, threadId);
                commonLock.setEntryCount(commonLock.getEntryCount() - 1);
                commonLockRepository.save(commonLock);
            }else {
                log.info("[释放分布式锁]释放锁成功，{}，{}", clientIp, threadId);
                commonLockRepository.delete(commonLock);
            }
        }
    }
}
