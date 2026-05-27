package org.Lin.util;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 分布式锁 方法类型执行 有返回值的业务
 * @author: Lin-RAGAgent
 **/
@FunctionalInterface
public interface TaskCall<V> {

    V call();
}
