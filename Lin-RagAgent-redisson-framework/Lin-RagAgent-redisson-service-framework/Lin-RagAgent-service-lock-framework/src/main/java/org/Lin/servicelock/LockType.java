package org.Lin.servicelock;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 分布式锁 锁类型
 * @author: Lin-RAGAgent
 **/
public enum LockType {

    Reentrant,

    Fair,

    Read,

    Write;

    LockType() {
    }

}
