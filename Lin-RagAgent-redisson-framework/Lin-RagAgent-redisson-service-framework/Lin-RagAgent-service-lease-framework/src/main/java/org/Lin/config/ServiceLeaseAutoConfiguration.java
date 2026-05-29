package org.Lin.config;

import org.Lin.lease.RedisLeaseManager;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 自动配置类
 * @author: Lin-RAGAgent
 **/

public class ServiceLeaseAutoConfiguration {

    @Bean
    public RedisLeaseManager redisLeaseManager(RedissonClient redissonClient) {
        return new RedisLeaseManager(redissonClient);
    }
}
