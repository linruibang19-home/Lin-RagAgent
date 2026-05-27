package org.Lin.lockinfo;

import org.aspectj.lang.JoinPoint;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 锁信息抽象
 * @author: Lin-RAGAgent
 **/
public interface LockInfoHandle {

    String getLockName(JoinPoint joinPoint, String name, String[] keys);

    String simpleGetLockName(String name,String[] keys);
}
