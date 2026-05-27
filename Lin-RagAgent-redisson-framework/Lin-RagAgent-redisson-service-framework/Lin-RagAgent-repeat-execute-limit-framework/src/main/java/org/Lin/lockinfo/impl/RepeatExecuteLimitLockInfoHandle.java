package org.Lin.lockinfo.impl;

import org.Lin.lockinfo.AbstractLockInfoHandle;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 锁信息实现(防重复幂等)
 * @author: Lin-RAGAgent
 **/
public class RepeatExecuteLimitLockInfoHandle extends AbstractLockInfoHandle {

    public static final String PREFIX_NAME = "REPEAT_EXECUTE_LIMIT";

    @Override
    protected String getLockPrefixName() {
        return PREFIX_NAME;
    }
}
