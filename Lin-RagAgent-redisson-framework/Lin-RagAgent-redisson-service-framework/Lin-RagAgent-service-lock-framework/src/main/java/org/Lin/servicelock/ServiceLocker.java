package org.Lin.servicelock;

import org.redisson.api.RLock;

import java.util.concurrent.TimeUnit;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 分布式锁 方法抽象
 * @author: Lin-RAGAgent
 **/
public interface ServiceLocker {

    RLock getLock(String lockKey);

    RLock lock(String lockKey);

    RLock lock(String lockKey, long leaseTime);

    RLock lock(String lockKey, TimeUnit unit, long leaseTime);

    boolean tryLock(String lockKey, TimeUnit unit, long waitTime);

    boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);
}