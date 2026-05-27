package com.baidu.fsg.uid.config;

import com.baidu.fsg.uid.UidGenerator;
import com.baidu.fsg.uid.impl.CachedUidGenerator;
import com.baidu.fsg.uid.worker.WorkerIdAssigner;
import org.Lin.toolkit.SnowflakeIdGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 百度uid集成配置类
 * @author: Lin-RAGAgent
 **/
@Configuration
public class WorkerNodeConfig {

    @Bean("cachedUidGenerator")
    public UidGenerator uidGenerator(WorkerIdAssigner disposableWorkerIdAssigner, SnowflakeIdGenerator snowflakeIdGenerator){
        CachedUidGenerator cachedUidGenerator = new CachedUidGenerator();
        cachedUidGenerator.setWorkerIdAssigner(disposableWorkerIdAssigner);
        cachedUidGenerator.setSnowflakeIdGenerator(snowflakeIdGenerator);
        return cachedUidGenerator;
    }
}