package org.Lin.lockinfo.impl;

import org.Lin.lockinfo.AbstractLockInfoHandle;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 锁信息实现(分布式锁)
 * @author: Lin-RAGAgent
 **/
public class ServiceLockInfoHandle extends AbstractLockInfoHandle {

    private static final String LOCK_PREFIX_NAME = "SERVICE_LOCK";

    @Override
    protected String getLockPrefixName() {
        return LOCK_PREFIX_NAME;
    }
}
