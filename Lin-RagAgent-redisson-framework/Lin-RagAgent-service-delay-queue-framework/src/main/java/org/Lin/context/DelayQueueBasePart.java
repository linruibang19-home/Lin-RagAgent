package org.Lin.context;

import org.Lin.config.DelayQueueProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.redisson.api.RedissonClient;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 延迟队列配置信息
 * @author: Lin-RAGAgent
 **/
@Data
@AllArgsConstructor
public class DelayQueueBasePart {

    private final RedissonClient redissonClient;

    private final DelayQueueProperties delayQueueProperties;
}
