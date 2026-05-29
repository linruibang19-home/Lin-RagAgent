package org.Lin.core;

import org.Lin.servicelock.LockType;
import org.Lin.servicelock.ServiceLocker;
import org.Lin.servicelock.impl.RedissonFairLocker;
import org.Lin.servicelock.impl.RedissonReadLocker;
import org.Lin.servicelock.impl.RedissonReentrantLocker;
import org.Lin.servicelock.impl.RedissonWriteLocker;
import org.redisson.api.RedissonClient;

import java.util.HashMap;
import java.util.Map;

import static org.Lin.servicelock.LockType.Fair;
import static org.Lin.servicelock.LockType.Read;
import static org.Lin.servicelock.LockType.Reentrant;
import static org.Lin.servicelock.LockType.Write;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 分布式锁 锁缓存
 * @author: Lin-RAGAgent
 **/
public class ManageLocker {

    private final Map<LockType, ServiceLocker> cacheLocker = new HashMap<>();

    public ManageLocker(RedissonClient redissonClient){
        cacheLocker.put(Reentrant,new RedissonReentrantLocker(redissonClient));
        cacheLocker.put(Fair,new RedissonFairLocker(redissonClient));
        cacheLocker.put(Write,new RedissonWriteLocker(redissonClient));
        cacheLocker.put(Read,new RedissonReadLocker(redissonClient));
    }

    public ServiceLocker getReentrantLocker(){
        return cacheLocker.get(Reentrant);
    }

    public ServiceLocker getFairLocker(){
        return cacheLocker.get(Fair);
    }

    public ServiceLocker getWriteLocker(){
        return cacheLocker.get(Write);
    }

    public ServiceLocker getReadLocker(){
        return cacheLocker.get(Read);
    }
}
