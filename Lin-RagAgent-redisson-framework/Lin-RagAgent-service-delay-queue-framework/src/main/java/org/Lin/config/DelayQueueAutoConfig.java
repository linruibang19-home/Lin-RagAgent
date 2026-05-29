package org.Lin.config;

import org.Lin.context.DelayQueueBasePart;
import org.Lin.context.DelayQueueContext;
import org.Lin.event.DelayQueueInitHandler;
import org.redisson.api.RedissonClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 延迟队列 配置
 * @author: Lin-RAGAgent
 **/
@EnableConfigurationProperties(DelayQueueProperties.class)
public class DelayQueueAutoConfig {

    @Bean
    public DelayQueueInitHandler delayQueueInitHandler(DelayQueueBasePart delayQueueBasePart){
        return new DelayQueueInitHandler(delayQueueBasePart);
    }

    @Bean
    public DelayQueueBasePart delayQueueBasePart(RedissonClient redissonClient,DelayQueueProperties delayQueueProperties){
        return new DelayQueueBasePart(redissonClient,delayQueueProperties);
    }

    @Bean
    public DelayQueueContext delayQueueContext(DelayQueueBasePart delayQueueBasePart){
        return new DelayQueueContext(delayQueueBasePart);
    }
}
