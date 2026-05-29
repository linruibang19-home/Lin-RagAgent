package org.Lin.servicelock.annotion;

import org.Lin.servicelock.LockType;
import org.Lin.servicelock.info.LockTimeOutStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 分布式锁 注解
 * @author: Lin-RAGAgent
 **/
@Target(value= {ElementType.TYPE, ElementType.METHOD})
@Retention(value= RetentionPolicy.RUNTIME)
public @interface ServiceLock {

    LockType lockType() default LockType.Reentrant;

    String name() default "";

    String [] keys();

    long waitTime() default 10;

    TimeUnit timeUnit() default TimeUnit.SECONDS;

    LockTimeOutStrategy lockTimeoutStrategy() default LockTimeOutStrategy.FAIL;

    String customLockTimeoutStrategy() default "";
}
