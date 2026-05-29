package org.Lin.context;

import org.Lin.core.ConsumerTask;
import lombok.Data;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 消息主题
 * @author: Lin-RAGAgent
 **/
@Data
public class DelayQueuePart {

    private final DelayQueueBasePart delayQueueBasePart;

    private final ConsumerTask consumerTask;

    public DelayQueuePart(DelayQueueBasePart delayQueueBasePart, ConsumerTask consumerTask){
        this.delayQueueBasePart = delayQueueBasePart;
        this.consumerTask = consumerTask;
    }
}
