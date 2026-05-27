package org.Lin.lockinfo.factory;

import org.Lin.lockinfo.LockInfoHandle;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 锁信息工厂
 * @author: Lin-RAGAgent
 **/
public class LockInfoHandleFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public LockInfoHandle getLockInfoHandle(String lockInfoType){
        return applicationContext.getBean(lockInfoType,LockInfoHandle.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
