package com.baidu.fsg.uid;

import com.baidu.fsg.uid.exception.UidGenerateException;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 生成器
 * @author: Lin-RAGAgent
 **/

public interface UidGenerator {

    long getUid() throws UidGenerateException;

    long getId();

    long getOrderNumber(long userId,long tableCount,long databaseCount);

    long getOrderNumber(long userId);

    @Deprecated
    long getOrderNumber(long userId,long tableCount);

    String parseUid(long uid);

}
