package com.baidu.fsg.uid.config;

import com.baidu.fsg.uid.worker.WorkerIdAssigner;
import org.Lin.enums.BaseCode;
import org.Lin.exception.SuperAgentFrameException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: redis配置生成work_id
 * @author: Lin-RAGAgent
 **/
public class RedisDisposableWorkerIdAssigner implements WorkerIdAssigner {

    private RedisTemplate redisTemplate;

    public RedisDisposableWorkerIdAssigner (RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Override
    public long assignWorkerId() {
        String key = "uid_work_id";
        Long increment = redisTemplate.opsForValue().increment(key);
        return Optional.ofNullable(increment).orElseThrow(() -> new SuperAgentFrameException(BaseCode.UID_WORK_ID_ERROR));
    }
}
