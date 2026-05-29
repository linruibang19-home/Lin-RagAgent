package org.Lin.core;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 延迟队列 阻塞队列
 * @author: Lin-RAGAgent
 **/
@Slf4j
public class DelayBaseQueue {

    protected final RedissonClient redissonClient;
    protected final RBlockingQueue<String> blockingQueue;

    public DelayBaseQueue(RedissonClient redissonClient,String relTopic){
        this.redissonClient = redissonClient;
        this.blockingQueue = redissonClient.getBlockingQueue(relTopic);
    }
}
