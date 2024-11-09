package com.onenine.distributelock.service;

public interface ICommonLockService {

    boolean tryLock(String lockKey, String clientIp, String threadId);

    void releaseLock(String lockKey, String clientIp, String threadId);

}