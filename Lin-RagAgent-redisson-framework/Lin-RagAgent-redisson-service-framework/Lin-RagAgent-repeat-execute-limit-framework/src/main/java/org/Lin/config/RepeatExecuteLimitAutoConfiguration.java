package org.Lin.config;

import org.Lin.constant.LockInfoType;
import org.Lin.handle.RedissonDataHandle;
import org.Lin.locallock.LocalLockCache;
import org.Lin.lockinfo.LockInfoHandle;
import org.Lin.lockinfo.factory.LockInfoHandleFactory;
import org.Lin.lockinfo.impl.RepeatExecuteLimitLockInfoHandle;
import org.Lin.repeatexecutelimit.aspect.RepeatExecuteLimitAspect;
import org.Lin.servicelock.factory.ServiceLockFactory;
import org.springframework.context.annotation.Bean;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 防重复幂等配置
 * @author: Lin-RAGAgent
 **/
public class RepeatExecuteLimitAutoConfiguration {

    @Bean(LockInfoType.REPEAT_EXECUTE_LIMIT)
    public LockInfoHandle repeatExecuteLimitHandle(){
        return new RepeatExecuteLimitLockInfoHandle();
    }

    @Bean
    public RepeatExecuteLimitAspect repeatExecuteLimitAspect(LocalLockCache localLockCache,
                                                             LockInfoHandleFactory lockInfoHandleFactory,
                                                             ServiceLockFactory serviceLockFactory,
                                                             RedissonDataHandle redissonDataHandle){
        return new RepeatExecuteLimitAspect(localLockCache, lockInfoHandleFactory,serviceLockFactory,redissonDataHandle);
    }
}
