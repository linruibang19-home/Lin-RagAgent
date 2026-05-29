package org.Lin.servicelock.info;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 分布式锁 策略
 * @author: Lin-RAGAgent
 **/
public enum LockTimeOutStrategy implements LockTimeOutHandler{

    FAIL(){
        @Override
        public void handler(String lockName) {
            String msg = String.format("%s请求频繁",lockName);
            throw new RuntimeException(msg);
        }
    }
}
