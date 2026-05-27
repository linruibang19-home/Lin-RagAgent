package com.baidu.fsg.uid.buffer;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 处理器
 * @author: Lin-RAGAgent
 **/

@FunctionalInterface
public interface RejectedPutBufferHandler {

    void rejectPutBuffer(RingBuffer ringBuffer, long uid);
}
