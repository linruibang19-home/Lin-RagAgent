package org.Lin.core;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 延迟队列 消费者接口
 * @author: Lin-RAGAgent
 **/
public interface ConsumerTask {

    void execute(String content);

    String topic();
}
