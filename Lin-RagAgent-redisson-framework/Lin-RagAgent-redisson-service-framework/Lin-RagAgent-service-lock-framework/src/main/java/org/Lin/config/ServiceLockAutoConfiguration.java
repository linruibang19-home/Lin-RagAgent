package org.Lin.config;

import org.Lin.constant.LockInfoType;
import org.Lin.core.ManageLocker;
import org.Lin.lockinfo.LockInfoHandle;
import org.Lin.lockinfo.factory.LockInfoHandleFactory;
import org.Lin.lockinfo.impl.ServiceLockInfoHandle;
import org.Lin.servicelock.aspect.ServiceLockAspect;
import org.Lin.servicelock.factory.ServiceLockFactory;
import org.Lin.util.ServiceLockTool;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 分布式锁 配置
 * @author: Lin-RAGAgent
 **/
public class ServiceLockAutoConfiguration {

    @Bean(LockInfoType.SERVICE_LOCK)
    public LockInfoHandle serviceLockInfoHandle(){
        return new ServiceLockInfoHandle();
    }

    @Bean
    public ManageLocker manageLocker(RedissonClient redissonClient){
        return new ManageLocker(redissonClient);
    }

    @Bean
    public ServiceLockFactory serviceLockFactory(ManageLocker manageLocker){
        return new ServiceLockFactory(manageLocker);
    }

    @Bean
    public ServiceLockAspect serviceLockAspect(LockInfoHandleFactory lockInfoHandleFactory,ServiceLockFactory serviceLockFactory){
        return new ServiceLockAspect(lockInfoHandleFactory,serviceLockFactory);
    }

    @Bean
    public ServiceLockTool serviceLockUtil(LockInfoHandleFactory lockInfoHandleFactory,ServiceLockFactory serviceLockFactory){
        return new ServiceLockTool(lockInfoHandleFactory,serviceLockFactory);
    }
}
