package org.Lin.ai.chatagent.rag.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: 企业级别深度设计 AI Agent。
 * @description: 单个子问题在某个检索通道上的执行痕迹
 * @author: Lin-RAGAgent
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubQuestionChannelTrace {

    private String channelName;

    private int recalledCount;

    private int acceptedCount;
}
