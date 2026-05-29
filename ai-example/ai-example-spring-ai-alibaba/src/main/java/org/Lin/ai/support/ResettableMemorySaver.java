package org.Lin.ai.support;

import java.util.Collection;

import com.alibaba.cloud.ai.graph.checkpoint.Checkpoint;
import com.alibaba.cloud.ai.graph.checkpoint.savers.MemorySaver;

/**
 * @program: 企业级别深度设计 AI Agent。 
 * @description: 支撑组件
 * @author: Lin-RAGAgent
 **/
public class ResettableMemorySaver extends MemorySaver {

    public int clearThread(String threadId) {
        Collection<Checkpoint> removed = remove(threadId);
        return removed != null ? removed.size() : 0;
    }

}
