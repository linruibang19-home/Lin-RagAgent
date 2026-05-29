package org.Lin.servicelock.info;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 分布式锁 处理失败抽象
 * @author: Lin-RAGAgent
 **/
public interface LockTimeOutHandler {

    void handler(String lockName);
}
