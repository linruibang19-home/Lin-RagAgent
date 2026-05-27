package org.Lin.toolkit;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 雪花算法相关常量
 * @author: Lin-RAGAgent
 **/
public class IdGeneratorConstant {

    public static final long WORKER_ID_BITS = 5L;
    public static final long DATA_CENTER_ID_BITS = 5L;
    public static final long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);
    public static final long MAX_DATA_CENTER_ID = -1L ^ (-1L << DATA_CENTER_ID_BITS);
}
