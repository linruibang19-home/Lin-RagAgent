package org.Lin.servicelock.factory;

import org.Lin.core.ManageLocker;
import org.Lin.servicelock.LockType;
import org.Lin.servicelock.ServiceLocker;
import lombok.AllArgsConstructor;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 分布式锁类型工厂
 * @author: Lin-RAGAgent
 **/
@AllArgsConstructor
public class ServiceLockFactory {

    private final ManageLocker manageLocker;

    public ServiceLocker getLock(LockType lockType){
        ServiceLocker lock;
        switch (lockType) {
            case Fair:
                lock = manageLocker.getFairLocker();
                break;
            case Write:
                lock = manageLocker.getWriteLocker();
                break;
            case Read:
                lock = manageLocker.getReadLocker();
                break;
            default:
                lock = manageLocker.getReentrantLocker();
                break;
        }
        return lock;
    }
}
